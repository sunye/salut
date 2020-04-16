package org.atlanmod.salut.cache;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.domains.LocalDomain;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.mdns.ARecord;
import org.atlanmod.salut.mdns.AbstractPointerRecord;
import org.atlanmod.salut.mdns.BaseARecord;
import org.atlanmod.salut.mdns.BaseServerSelectionRecord;
import org.atlanmod.salut.mdns.QClass;
import org.atlanmod.salut.names.PointerName;
import org.atlanmod.salut.names.ServiceInstanceName;
import org.atlanmod.salut.names.ServiceName;
import org.awaitility.Durations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CacheIT {
    private BaseCache cache;

    @BeforeEach
    void setUp() {
        cache = new BaseCache();
    }

    @Test
    void testCacheServerSelectionRecord() throws ParseException {
        ServiceInstanceName instanceName = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");
        Domain domain = DomainBuilder.parseString("Donatelo.local");
        TimeToLive ttl = TimeToLive.fromSeconds(2);
        BaseServerSelectionRecord srv = mock(BaseServerSelectionRecord.class);

        when(srv.serviceInstanceName()).thenReturn(instanceName);
        when(srv.serverName()).thenReturn(domain);
        when(srv.ttl()).thenReturn(ttl);
        cache.cache(srv);

        assertThat(cache.servers()).containsKey(domain);
        assertThat(cache.instances()).containsKey(instanceName);


    }

    @Test
    void testCachePointerRecord() throws ParseException {
        PointerName pointerName = PointerName.create(
            ServiceName.fromStrings("raop", "tcp"),
            LocalDomain.getInstance()
        );


        ServiceInstanceName instanceName = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");
        TimeToLive time = TimeToLive.fromSeconds(2);

        AbstractPointerRecord ptr = mock(AbstractPointerRecord.class);
        when(ptr.serviceInstanceName()).thenReturn(instanceName);
        when(ptr.pointerName()).thenReturn(pointerName);
        when(ptr.ttl()).thenReturn(time);

        cache.cache(ptr);
        assertThat(cache.services()).containsKey(pointerName.service());
        assertThat(cache.instances()).containsKey(instanceName);
    }

    @Test
    void testCacheARecord() throws ParseException, UnknownHostException {
        Domain domain = DomainBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
        TimeToLive time = TimeToLive.fromSeconds(2);

        ARecord ip4address = mock(BaseARecord.class);
        when(ip4address.serverName()).thenReturn(domain);
        when(ip4address.address()).thenReturn(address);
        when(ip4address.ttl()).thenReturn(time);

        cache.cache(ip4address);

        assertThat(cache.addresses()).containsKey(address);
    }

    @Test
    void testGetInstancesForService() throws ParseException {
        PointerName serviceName = PointerName.create(
            ServiceName.fromStrings("raop", "tcp"),
            LocalDomain.getInstance()
        );

        ServiceInstanceName instanceName = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");
        TimeToLive time = TimeToLive.fromSeconds(2);

        AbstractPointerRecord ptr = mock(AbstractPointerRecord.class);
        when(ptr.serviceInstanceName()).thenReturn(instanceName);
        when(ptr.pointerName()).thenReturn(serviceName);
        when(ptr.ttl()).thenReturn(time);

        cache.cache(ptr);
        assertThat(cache.getInstancesForService(serviceName.service())).contains(instanceName);
    }

    @Test
    void tesGetAddressesForServer() throws ParseException, UnknownHostException {
        Domain domain = DomainBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
        TimeToLive time = TimeToLive.fromSeconds(2);

        ARecord aRecord = mock(BaseARecord.class);
        when(aRecord.serverName()).thenReturn(domain);
        when(aRecord.address()).thenReturn(address);
        when(aRecord.ttl()).thenReturn(time);

        Inet4Address secondaryAddress = (Inet4Address) InetAddress
            .getByAddress(new byte[]{127, 0, 0, 1});
        ARecord otherRecord = mock(BaseARecord.class);
        when(otherRecord.serverName()).thenReturn(domain);
        when(otherRecord.address()).thenReturn(secondaryAddress);
        when(otherRecord.ttl()).thenReturn(time);

        cache.cache(aRecord);
        cache.cache(otherRecord);

        assertThat(cache.getAddressesForServer(domain)).contains(address, secondaryAddress);
    }

    @Test
    void testGetServersForAddress() throws ParseException, UnknownHostException {
        Domain domain = DomainBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
        TimeToLive time = TimeToLive.fromSeconds(2);

        ARecord ip4address = mock(BaseARecord.class);
        when(ip4address.serverName()).thenReturn(domain);
        when(ip4address.address()).thenReturn(address);
        when(ip4address.ttl()).thenReturn(time);

        cache.cache(ip4address);
        assertThat(cache.getServersForAddress(address)).contains(domain);
    }


    @Test
    void testGetServersForInstance() throws ParseException {
        ServiceInstanceName instance = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");
        Domain domain = DomainBuilder.parseString("Donatelo.local");
        TimeToLive time = TimeToLive.fromSeconds(2);

        BaseServerSelectionRecord srv = mock(BaseServerSelectionRecord.class);
        when(srv.serverName()).thenReturn(domain);
        when(srv.serviceInstanceName()).thenReturn(instance);
        when(srv.ttl()).thenReturn(time);

        cache.cache(srv);
        assertThat(cache.getServersForInstance(instance)).contains(domain);
    }


    @Test
    void testCacheFromInet() throws UnknownHostException, ParseException {
        Labels names = Labels.fromList("MacBook", "local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{72, 16, 8, 4});
        Domain domaine = DomainBuilder.fromLabels(names);
        ARecord record = BaseARecord
            .createRecord(QClass.IN, UnsignedInt.fromInt(10), address, domaine);


        Cache cache = new BaseCache();
        cache.cache(record);

        assertThat(cache.getAddressesForServer(DomainBuilder.fromLabels(names))).contains(address);
    }


    @Test
    void testGetInstancesForServiceTTLExpire() throws ParseException {
        PointerName type = PointerName.create(
            ServiceName.fromStrings("raop", "tcp"),
            LocalDomain.getInstance()
        );

        ServiceInstanceName instance = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");
        TimeToLive time = TimeToLive.fromSeconds(1);

        AbstractPointerRecord ptr = mock(AbstractPointerRecord.class);
        when(ptr.serviceInstanceName()).thenReturn(instance);
        when(ptr.pointerName()).thenReturn(type);
        when(ptr.ttl()).thenReturn(time);

        cache.cache(ptr);

        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            cache.doMaintenance();
            List<ServiceInstanceName> instancesForService = cache.getInstancesForService(type.service());
            assertThat(instancesForService).doesNotContain(instance);
        });

    }


    @Test
    void tesGetAddressesForServerTTLExpire() throws ParseException, UnknownHostException {
        Domain donatelo = DomainBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
        TimeToLive time = TimeToLive.fromSeconds(1);

        ARecord aRecord = mock(BaseARecord.class);
        when(aRecord.serverName()).thenReturn(donatelo);
        when(aRecord.address()).thenReturn(address);
        when(aRecord.ttl()).thenReturn(time);

        ARecord otherRecord = mock(BaseARecord.class);
        Domain michelangelo = DomainBuilder.parseString("Michelangelo.local");
        when(otherRecord.serverName()).thenReturn(michelangelo);
        when(otherRecord.address()).thenReturn(address);
        when(otherRecord.ttl()).thenReturn(TimeToLive.fromSeconds(200));

        cache.cache(aRecord);
        cache.cache(otherRecord);

        await().atMost(Durations.FIVE_SECONDS).untilAsserted(() -> {
            cache.doMaintenance();
            List<Domain> domains = cache.getServersForAddress(address);
            assertThat(domains).contains(michelangelo)
                .doesNotContain(donatelo);
        });
    }

    @Test
    void testGetServersForAddressTTLExpire() throws ParseException, UnknownHostException {
        // Given:
        Domain domain = DomainBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
        TimeToLive time = TimeToLive.fromSeconds(1);
        ARecord ip4address = mock(BaseARecord.class);
        when(ip4address.serverName()).thenReturn(domain);
        when(ip4address.address()).thenReturn(address);
        when(ip4address.ttl()).thenReturn(time);

        //When:
        cache.cache(ip4address);

        // Then:
        await().atMost(Durations.FIVE_SECONDS).untilAsserted(() -> {
            cache.doMaintenance();
            List<Domain> servers = cache.getServersForAddress(address);
            assertThat(servers).doesNotContain(domain);
        });
    }

    @Test
    void testGetServersForInstanceTTLExpire() throws ParseException {
        // Given:
        ServiceInstanceName instance = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");
        Domain domain = DomainBuilder.parseString("Donatelo.local");
        TimeToLive time = TimeToLive.fromSeconds(1);
        BaseServerSelectionRecord srv = mock(BaseServerSelectionRecord.class);
        when(srv.serverName()).thenReturn(domain);
        when(srv.serviceInstanceName()).thenReturn(instance);
        when(srv.ttl()).thenReturn(time);
        // When:
        cache.cache(srv);

        // Then:
        await().atMost(Durations.FIVE_SECONDS).untilAsserted(() -> {
            cache.doMaintenance();
            List<Domain> servers = cache.getServersForInstance(instance);
            assertThat(servers).doesNotContain(domain);
        });
    }

    @Test
    void testGetInstancesForServiceTimeOut() throws ParseException {
        // Given:
        PointerName type = PointerName.create(
            ServiceName.fromStrings("testtest", "tcp"),
            LocalDomain.getInstance()
        );
        ServiceInstanceName instance = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");
        AbstractPointerRecord pointerRecord = mock(AbstractPointerRecord.class);
        when(pointerRecord.serviceInstanceName()).thenReturn(instance);
        when(pointerRecord.pointerName()).thenReturn(type);
        when(pointerRecord.ttl()).thenReturn(TimeToLive.fromSeconds(1));

        // When:
        cache.cache(pointerRecord);

        // Then:
        await().atMost(Durations.FIVE_SECONDS).untilAsserted(() -> {
            cache.doMaintenance();
            List<ServiceInstanceName> instancesForService = cache.getInstancesForService(type.service());
            assertThat(instancesForService).isEmpty();
        });
    }

    @Test
    void tesGetAddressesForServerTimeout() throws ParseException, UnknownHostException {
        // Given:
        cache.start();
        Domain domain = DomainBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
        ARecord aRecord = mock(BaseARecord.class);
        when(aRecord.serverName()).thenReturn(domain);
        when(aRecord.address()).thenReturn(address);
        when(aRecord.ttl()).thenReturn(TimeToLive.fromSeconds(1));
        
        //When:
        cache.cache(aRecord);

        // Then:
        await().atLeast(Durations.ONE_SECOND)
            .atMost(Durations.FIVE_SECONDS)
            .untilAsserted(() -> {
                List<InetAddress> addresses = cache.getAddressesForServer(domain);
                assertThat(addresses).isEmpty();
            });

        cache.stop();
    }

    @Test
    void testGetInstancesForServiceNotFound() {
        ServiceName type = ServiceName.fromStrings("testtest", "tcp");
        List<ServiceInstanceName> instancesForService = cache.getInstancesForService(type);
        assertThat(instancesForService).isEmpty();
    }

    @Test
    void testGetServersForAddressNoDomainName() throws UnknownHostException {
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{12, 0, 0, 5});
        List<Domain> domains = cache.getServersForAddress(address);
        assertThat(domains).isEmpty();
    }

    @Test
    void testGetServersForInstanceNoDomainName() throws ParseException {
        ServiceInstanceName instance = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");
        List<Domain> servers = cache.getServersForInstance(instance);
        assertThat(servers).isEmpty();
    }
}
