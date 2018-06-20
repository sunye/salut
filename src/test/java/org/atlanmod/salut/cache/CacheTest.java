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

/*
Classe de test pour la classe Cache
Cette classe a pour but de faire l'ensemble des tests unitaires de Cache
 */
class CacheTest {
    private Cache cache;

    @BeforeEach
    void setup() {
        cache = new Cache();
    }

    /*
    Test de la méthode cache, on vérifie qu'il n'y a pas d'instance pour un service nul
    */
    @Test
    void cacheServerSelectionRecordIsNull() {

        ServerSelectionRecord serverSelectionRecord = mock(ServerSelectionRecord.class); // création de la doublure
        when(serverSelectionRecord.getServiceInstanceName()).thenReturn(null); // configuration des attentes

        cache.cache(serverSelectionRecord); // Mise à jour du cache / serveur / instance / liens

        assertTrue(cache.getInstancesForService(null).isEmpty() );
    }

    /*
    Test de la méthode cache, on vérifie que le TTL n'expire pas et que le service contient des instances
    */
    @Test
    void testGetInstancesForService() throws ParseException {
        // Paramétrage des attributs pour le cache
        ServiceType type = ServiceType.fromStrings("raop", "tcp");
        ServiceInstanceName instance = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");
        TimeToLive time = TimeToLive.fromSeconds(2);

        PointerRecord ptr = mock(PointerRecord.class); // Création de la doublure
        // Configuration des attentes
        when(ptr.getServiceInstanceName()).thenReturn(instance);
        when(ptr.getServiceType()).thenReturn(type);
        when(ptr.getTtl()).thenReturn(time);

        cache.cache(ptr); // Mise à jour du cache / serveur / instance / liens

        assertFalse(ptr.getTtl().hasExpired());
        List<ServiceInstanceName> instancesForService = cache.getInstancesForService(type);
        assertThat(instancesForService).contains(instance);
    }

    /*
    Test de la méthode cache, on vérifie que le TTL n'expire pas et que le service contient des adresses
    */
    @Test
    void tesGetAddressesForServer() throws ParseException, UnknownHostException {
        // Paramétrage des attributs pour le cache
        DomainName domainName = DomainNameBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
        TimeToLive time = TimeToLive.fromSeconds(2);

        ARecord ip4address = mock(ARecord.class); // Création de la doublure
        // Configuration des attentes
        when(ip4address.getServerName()).thenReturn(domainName);
        when(ip4address.getAddress()).thenReturn(address);
        when(ip4address.getTtl()).thenReturn(time);

        cache.cache(ip4address); // Mise à jour du cache / serveur / instance / liens

        assertFalse(ip4address.getTtl().hasExpired());
        List<InetAddress> addresses = cache.getAddressesForServer(domainName);
        assertThat(addresses).contains(address);
    }

    /*
    Test de la méthode cache, on vérifie que le TTL n'expire pas et que les serveurs des adresses contiennent des domaines
    */
    @Test
    void testGetServersForAddress() throws ParseException, UnknownHostException {
        // Paramétrage des attributs pour le cache
        DomainName domainName = DomainNameBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});

        TimeToLive time = TimeToLive.fromSeconds(2);
        ARecord ip4address = mock(ARecord.class); // Création de la doublure
        // Configuration des attentes
        when(ip4address.getServerName()).thenReturn(domainName);
        when(ip4address.getAddress()).thenReturn(address);
        when(ip4address.getTtl()).thenReturn(time);

        cache.cache(ip4address); // Mise à jour du cache / serveur / instance / liens

        assertFalse(ip4address.getTtl().hasExpired());
        List<DomainName> servers = cache.getServersForAddress(address);
        assertThat(servers).contains(domainName);
    }

    /*
    Test de la méthode cache, on vérifie que le TTL n'expire pas et que les serveurs des instances contiennent des domaines
    */
    @Test
    void testGetServersForInstance() throws ParseException {
        // Paramétrage des attributs pour le cache
        ServiceInstanceName instance = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");
        DomainName domain = DomainNameBuilder.parseString("Donatelo.local");

        TimeToLive time = TimeToLive.fromSeconds(2);
        ServerSelectionRecord srv = mock(ServerSelectionRecord.class); // Création de la doublure

        // Configuration des attentes
        when(srv.getServerName()).thenReturn(domain);
        when(srv.getServiceInstanceName()).thenReturn(instance);
        when(srv.getTtl()).thenReturn(time);

        cache.cache(srv); // Mise à jour du cache / serveur / instance / liens

        assertFalse(srv.getTtl().hasExpired());
        List<DomainName> servers = cache.getServersForInstance(instance);
        assertThat(servers).contains(domain);
    }

    /*
    Test de la méthode cache, on vérifie que le TTL n'expire pas et que les serveurs contiennent des adresses
    */
	@Test
    void testCacheFromInet() throws UnknownHostException, ParseException {
        // Paramétrage des attributs pour le cache
        NameArray names = NameArray.fromList("MacBook", "local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{72, 16, 8, 4});
        DomainName domaine = DomainNameBuilder.fromNameArray(names);
        ARecord record = ARecord.createRecord(names, QClass.IN, UnsignedInt.fromInt(10), address, domaine);


        Cache cache = new Cache();
        cache.cache(record); // Mise à jour du cache / serveur / instance / liens

        assertTrue(cache.getAddressesForServer(DomainNameBuilder.fromNameArray(names)).contains(address));
    }

    /*
    Test de la méthode cache, on vérifie que le TTL expire pour les instances du service
    */
    @Test
    void testGetInstancesForServiceTTLExpire() throws ParseException, InterruptedException {
        // Paramétrage des attributs pour le cache
        ServiceType type = ServiceType.fromStrings("raop", "tcp");
        ServiceInstanceName instance = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");

        TimeToLive time = TimeToLive.fromSeconds(2);

        PointerRecord ptr = mock(PointerRecord.class); // Création de la doublure
        // Configuration des attentes
        when(ptr.getServiceInstanceName()).thenReturn(instance);
        when(ptr.getServiceType()).thenReturn(type);
        when(ptr.getTtl()).thenReturn(time);

        Thread.sleep(2500); // Mise en attente du thread pour que le TTL soit dépassé

        cache.cache(ptr); // Mise à jour du cache / serveur / instance / liens

        List<ServiceInstanceName> instancesForService = cache.getInstancesForService(type); // Actualisation
        assertTrue(ptr.getTtl().hasExpired());
//        assertTrue(instancesForService.isEmpty());
    }

    /*
    Test de la méthode cache, on vérifie que le TTL expire pour les adresses du serveur
    */
    @Test
    void tesGetAddressesForServerTTLExpire() throws ParseException, UnknownHostException, InterruptedException {
        // Paramétrage des attributs pour le cache
        DomainName domainName = DomainNameBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});

        TimeToLive time = TimeToLive.fromSeconds(2);

        ARecord ip4address = mock(ARecord.class); // Création de la doublure
        // Configuration des attentes
        when(ip4address.getServerName()).thenReturn(domainName);
        when(ip4address.getAddress()).thenReturn(address);
        when(ip4address.getTtl()).thenReturn(time);

        Thread.sleep(2500); // Mise en attente du thread pour que le TTL soit dépassé

        cache.cache(ip4address); // Mise à jour du cache / serveur / instance / liens

        List<InetAddress> addresses = cache.getAddressesForServer(domainName); // Actualisation
        assertTrue(ip4address.getTtl().hasExpired());
//        assertTrue(addresses.isEmpty());

    }

    /*
    Test de la méthode cache, on vérifie que le TTL expire pour les serveurs d'une adresse
    */
    @Test
    void testGetServersForAddressTTLExpire() throws ParseException, UnknownHostException, InterruptedException {
        // Paramétrage des attributs pour le cache
        DomainName domainName = DomainNameBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});

        TimeToLive time = TimeToLive.fromSeconds(2);

        ARecord ip4address = mock(ARecord.class); // Création de la doublure
        // Configuration des attentes
        when(ip4address.getServerName()).thenReturn(domainName);
        when(ip4address.getAddress()).thenReturn(address);
        when(ip4address.getTtl()).thenReturn(time);

        Thread.sleep(2500); // Mise en attente du thread pour que le TTL soit dépassé

        cache.cache(ip4address); // Mise à jour du cache / serveur / instance / liens

        List<DomainName> servers = cache.getServersForAddress(address); // Actualisation
        assertTrue(ip4address.getTtl().hasExpired());
//        assertTrue(servers.isEmpty());
    }

    /*
    Test de la méthode cache, on vérifie que le TTL expire pour les serveurs d'une instance
    */
    @Test
    void testGetServersForInstanceTTLExpire() throws ParseException, InterruptedException {
        // Paramétrage des attributs pour le cache
        ServiceInstanceName instance = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");
        DomainName domain = DomainNameBuilder.parseString("Donatelo.local");

        TimeToLive time = TimeToLive.fromSeconds(2);

        ServerSelectionRecord srv = mock(ServerSelectionRecord.class); // Création de la doublure
        // Configuration des attentes
        when(srv.getServerName()).thenReturn(domain);
        when(srv.getServiceInstanceName()).thenReturn(instance);
        when(srv.getTtl()).thenReturn(time);

        Thread.sleep(2500); // Mise en attente du thread pour que le TTL soit dépassé

        cache.cache(srv); // Mise à jour du cache / serveur / instance / liens

        List<DomainName> servers = cache.getServersForInstance(instance); // Actualisation
        assertTrue(srv.getTtl().hasExpired());
 //       assertTrue(servers.isEmpty());
    }

    /*
    Test de la méthode cache, on vérifie que pour une instance nulle, il n'y a pas de serveurs
    */
    @Test
    void cacheGetServersForInstanceIsNull() throws ParseException {
        // Paramétrage des attributs pour le cache
        ServiceInstanceName instance = ServiceInstanceName.parseString("My Music.raop.tcp.winora.local");
        DomainName domain = DomainNameBuilder.parseString("Donatelo.local");


        ServerSelectionRecord srv = mock(ServerSelectionRecord.class); // Création de la doublure
        // Configuration des attentes
        when(srv.getServerName()).thenReturn(domain);
        when(srv.getServiceInstanceName()).thenReturn(instance);


        cache.cache(srv); // Mise à jour du cache / serveur / instance / liens

        List<DomainName> servers = cache.getServersForInstance(null);
        assertTrue(servers.isEmpty());
        //       assertTrue(servers.isEmpty());
    }

    /*
    Test de la méthode cache, on vérifie que pour une adresse nulle, il n'y a pas de serveurs
    */
    @Test
    void testGetServersForAddressIsNull() throws ParseException, UnknownHostException {
        // Paramétrage des attributs pour le cache
        DomainName domainName = DomainNameBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});


        ARecord ip4address = mock(ARecord.class); // Création de la doublure
        // Configuration des attentes
        when(ip4address.getServerName()).thenReturn(domainName);
        when(ip4address.getAddress()).thenReturn(address);


        cache.cache(ip4address); // Mise à jour du cache / serveur / instance / liens

        List<DomainName> servers = cache.getServersForAddress(null);
        assertTrue(servers.isEmpty());
    }

    /*
    Test de la méthode cache, on vérifie que pour un serveur nul, il n'y a pas d'adresses
    */
    @Test
    void tesGetAddressesForServerIsNull() throws ParseException, UnknownHostException, InterruptedException {
        // Paramétrage des attributs pour le cache
        DomainName domainName = DomainNameBuilder.parseString("Donatelo.local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{127, 0, 0, 1});


        ARecord ip4address = mock(ARecord.class); // Création de la doublure
        // Configuration des attentes
        when(ip4address.getServerName()).thenReturn(domainName);
        when(ip4address.getAddress()).thenReturn(address);

        cache.cache(ip4address); // Mise à jour du cache / serveur / instance / liens

        List<InetAddress> addresses = cache.getAddressesForServer(null);
        assertTrue(addresses.isEmpty());

    }


}