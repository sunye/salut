package org.atlanmod.salut.cache;

import org.atlanmod.salut.data.DomainName;
import org.atlanmod.salut.data.DomainNameBuilder;
import org.atlanmod.salut.data.ServiceInstanceName;
import org.atlanmod.salut.data.ServiceType;
import org.atlanmod.salut.mdns.ARecord;
import org.atlanmod.salut.mdns.PointerRecord;
import org.atlanmod.salut.mdns.ServerSelectionRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CacheTest {
    private Cache cache;

    @BeforeEach
    void setup() {
        cache = new Cache();

    }

    @Test
    void testGetInstancesForService() throws ParseException {
        ServiceType type = ServiceType.fromStrings("raop", "tcp");
        ServiceInstanceName instance = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");

        PointerRecord ptr = mock(PointerRecord.class);
        when(ptr.getServiceInstanceName()).thenReturn(instance);
        when(ptr.getServiceType()).thenReturn(type);

        cache.cache(ptr);

        List<ServiceInstanceName> instancesForService = cache.getInstancesForService(type);
        assertThat(instancesForService).contains(instance);
    }

    @Test
    void tesGetAddressesForServer() throws ParseException, UnknownHostException {
        DomainName domainName = DomainNameBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});

        ARecord ip4address = mock(ARecord.class);
        when(ip4address.getServerName()).thenReturn(domainName);
        when(ip4address.getAddress()).thenReturn(address);

        cache.cache(ip4address);

        List<InetAddress> addresses = cache.getAddressesForServer(domainName);
        assertThat(addresses).contains(address);
    }

    @Test
    void testGetServersForAddress() throws ParseException, UnknownHostException {
        DomainName domainName = DomainNameBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});

        ARecord ip4address = mock(ARecord.class);
        when(ip4address.getServerName()).thenReturn(domainName);
        when(ip4address.getAddress()).thenReturn(address);

        cache.cache(ip4address);

        List<DomainName> servers = cache.getServersForAddress(address);
        assertThat(servers).contains(domainName);
    }

    @Test
    void testGetServersForInstance() throws ParseException {
        ServiceInstanceName instance = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");
        DomainName domain = DomainNameBuilder.parseString("Donatelo.local");

        ServerSelectionRecord srv = mock(ServerSelectionRecord.class);
        when(srv.getServerName()).thenReturn(domain);
        when(srv.getServiceInstanceName()).thenReturn(instance);

        cache.cache(srv);

        List<DomainName> servers = cache.getServersForInstance(instance);
        assertThat(servers).contains(domain);
    }



    @Test
    void testGetInstancesForServiceTimeOut() throws ParseException, InterruptedException {
        ServiceType type = ServiceType.fromStrings("testtest", "tcp");
        ServiceInstanceName instance = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");
        PointerRecord pointerRecord = mock(PointerRecord.class);
        when(pointerRecord.getServiceInstanceName()).thenReturn(instance);
        when(pointerRecord.getServiceType()).thenReturn(type);
        when(pointerRecord.getTtl()).thenReturn(TimeToLive.fromSeconds(1));
        cache.cache(pointerRecord);
        Thread.currentThread().sleep(2000);
        List<ServiceInstanceName> instancesForService = cache.getInstancesForService(type);
        assertThat(instancesForService).isEmpty();
    }

    @Test
    void tesGetAddressesForServerTimeout() throws ParseException, UnknownHostException, InterruptedException {
        DomainName domainName = DomainNameBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
        ARecord ip4address = mock(ARecord.class);
        when(ip4address.getServerName()).thenReturn(domainName);
        when(ip4address.getAddress()).thenReturn(address);
        when(ip4address.getTtl()).thenReturn(TimeToLive.fromSeconds(1));
        cache.cache(ip4address);
        Thread.currentThread().sleep(2000);
        List<InetAddress> addresses = cache.getAddressesForServer(domainName);
        assertThat(addresses).isEmpty();
    }

    @Test
    public void testGetInstancesForServiceConcurrent() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                assertThrows(ParseException.class, new Executable() {
                    @Override
                    public void execute() throws Throwable {
                        testGetInstancesForService();
                    }
                });

            }
        });

        executor.execute(new Runnable() {
            @Override
            public void run() {
                 assertThrows(ParseException.class, new Executable() {
                    @Override
                    public void execute() throws Throwable {
                        testGetInstancesForService();
                    }
                });

            }
        });
        executor.awaitTermination(2, TimeUnit.SECONDS);
    }

    @Test
    void testGetInstancesForServiceNotFound() throws ParseException {
        ServiceType type = ServiceType.fromStrings("testtest", "tcp");
        List<ServiceInstanceName> instancesForService = cache.getInstancesForService(type);
        assertThat(instancesForService).isEmpty();
    }

    @Test
    void testGetServersForAddressNoDomainName() throws ParseException, UnknownHostException {
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{12, 0, 0, 5});
        List<DomainName> domainNames = cache.getServersForAddress(address);
        assertThat(domainNames).isEmpty();
    }


    @Test
    void testGetServersForInstanceNoDomainName() throws ParseException {
        ServiceInstanceName instance = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");
        List<DomainName> servers = cache.getServersForInstance(instance);
        assertThat(servers).isEmpty();
    }

}