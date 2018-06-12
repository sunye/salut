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

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
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

}