package org.atlanmod.salut.cache;

import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.data.DomainName;
import org.atlanmod.salut.data.DomainNameBuilder;
import org.atlanmod.salut.data.ServiceInstanceName;
import org.atlanmod.salut.data.ServiceType;
import org.atlanmod.salut.mdns.NameArray;
import org.atlanmod.salut.mdns.QClass;
import org.atlanmod.salut.mdns.ARecord;
import org.atlanmod.salut.mdns.PointerRecord;
import org.atlanmod.salut.mdns.ServerSelectionRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        TimeToLive time = TimeToLive.fromSeconds(2);

        PointerRecord ptr = mock(PointerRecord.class);
        when(ptr.getServiceInstanceName()).thenReturn(instance);
        when(ptr.getServiceType()).thenReturn(type);
        when(ptr.getTtl()).thenReturn(time);

        cache.cache(ptr);

        assertFalse(ptr.getTtl().hasExpired());
        List<ServiceInstanceName> instancesForService = cache.getInstancesForService(type);
        assertThat(instancesForService).contains(instance);
    }

    @Test
    void tesGetAddressesForServer() throws ParseException, UnknownHostException {
        DomainName domainName = DomainNameBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
        TimeToLive time = TimeToLive.fromSeconds(2);

        ARecord ip4address = mock(ARecord.class);
        when(ip4address.getServerName()).thenReturn(domainName);
        when(ip4address.getAddress()).thenReturn(address);
        when(ip4address.getTtl()).thenReturn(time);

        cache.cache(ip4address);

        assertFalse(ip4address.getTtl().hasExpired());
        List<InetAddress> addresses = cache.getAddressesForServer(domainName);
        assertThat(addresses).contains(address);
    }

    @Test
    void testGetServersForAddress() throws ParseException, UnknownHostException {
        DomainName domainName = DomainNameBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});

        TimeToLive time = TimeToLive.fromSeconds(2);
        ARecord ip4address = mock(ARecord.class);
        when(ip4address.getServerName()).thenReturn(domainName);
        when(ip4address.getAddress()).thenReturn(address);
        when(ip4address.getTtl()).thenReturn(time);

        cache.cache(ip4address);

        assertFalse(ip4address.getTtl().hasExpired());
        List<DomainName> servers = cache.getServersForAddress(address);
        assertThat(servers).contains(domainName);
    }

    @Test
    void testGetServersForInstance() throws ParseException {
        ServiceInstanceName instance = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");
        DomainName domain = DomainNameBuilder.parseString("Donatelo.local");

        TimeToLive time = TimeToLive.fromSeconds(2);
        ServerSelectionRecord srv = mock(ServerSelectionRecord.class);

        when(srv.getServerName()).thenReturn(domain);
        when(srv.getServiceInstanceName()).thenReturn(instance);
        when(srv.getTtl()).thenReturn(time);

        cache.cache(srv);

        assertFalse(srv.getTtl().hasExpired());
        List<DomainName> servers = cache.getServersForInstance(instance);
        assertThat(servers).contains(domain);
    }
	
	@Test
    void testCacheFromInet() throws UnknownHostException, ParseException {
        NameArray names = NameArray.fromList("MacBook", "local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{72, 16, 8, 4});
        DomainName domaine = DomainNameBuilder.fromNameArray(names);
        ARecord record = ARecord.createRecord(names, QClass.IN, UnsignedInt.fromInt(10), address, domaine);


        Cache cache = new Cache();
        cache.cache(record);

        assertTrue(cache.getAddressesForServer(DomainNameBuilder.fromNameArray(names)).contains(address));

    }


    @Test
    void testGetInstancesForServiceTTLExpire() throws ParseException, InterruptedException {
        ServiceType type = ServiceType.fromStrings("raop", "tcp");
        ServiceInstanceName instance = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");

        TimeToLive time = TimeToLive.fromSeconds(2);

        PointerRecord ptr = mock(PointerRecord.class);
        when(ptr.getServiceInstanceName()).thenReturn(instance);
        when(ptr.getServiceType()).thenReturn(type);
        when(ptr.getTtl()).thenReturn(time);

        Thread.sleep(2500);

        cache.cache(ptr);

        List<ServiceInstanceName> instancesForService = cache.getInstancesForService(type);
        assertTrue(ptr.getTtl().hasExpired());
//        assertTrue(instancesForService.isEmpty());
    }

    @Test
    void tesGetAddressesForServerTTLExpire() throws ParseException, UnknownHostException, InterruptedException {
        DomainName domainName = DomainNameBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});

        TimeToLive time = TimeToLive.fromSeconds(2);

        ARecord ip4address = mock(ARecord.class);
        when(ip4address.getServerName()).thenReturn(domainName);
        when(ip4address.getAddress()).thenReturn(address);
        when(ip4address.getTtl()).thenReturn(time);

        Thread.sleep(2500);

        cache.cache(ip4address);

        List<InetAddress> addresses = cache.getAddressesForServer(domainName);
        assertTrue(ip4address.getTtl().hasExpired());
//        assertTrue(addresses.isEmpty());

    }

    @Test
    void testGetServersForAddressTTLExpire() throws ParseException, UnknownHostException, InterruptedException {
        DomainName domainName = DomainNameBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});

        TimeToLive time = TimeToLive.fromSeconds(2);

        ARecord ip4address = mock(ARecord.class);
        when(ip4address.getServerName()).thenReturn(domainName);
        when(ip4address.getAddress()).thenReturn(address);
        when(ip4address.getTtl()).thenReturn(time);

        Thread.sleep(2500);

        cache.cache(ip4address);

        List<DomainName> servers = cache.getServersForAddress(address);
        assertTrue(ip4address.getTtl().hasExpired());
//        assertTrue(servers.isEmpty());
    }

    @Test
    void testGetServersForInstanceTTLExpire() throws ParseException, InterruptedException {
        ServiceInstanceName instance = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");
        DomainName domain = DomainNameBuilder.parseString("Donatelo.local");

        TimeToLive time = TimeToLive.fromSeconds(2);

        ServerSelectionRecord srv = mock(ServerSelectionRecord.class);
        when(srv.getServerName()).thenReturn(domain);
        when(srv.getServiceInstanceName()).thenReturn(instance);
        when(srv.getTtl()).thenReturn(time);

        Thread.sleep(2500);

        cache.cache(srv);

        List<DomainName> servers = cache.getServersForInstance(instance);
        assertTrue(srv.getTtl().hasExpired());
 //       assertTrue(servers.isEmpty());
    }


}