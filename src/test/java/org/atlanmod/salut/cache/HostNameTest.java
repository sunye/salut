package org.atlanmod.salut.cache;

import org.atlanmod.salut.mdns.NameArray;
import org.atlanmod.salut.data.*;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class HostNameTest {

    private NameArray reverseInet6Name = NameArray.fromList("b", "a", "9", "8", "7", "6", "5", "0", "0",
            "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "8",
            "b", "d", "0", "1", "0", "0", "2", "ip6", "arpa");;

    @Test
    void testCreateLocalHostName() throws ParseException {
        NameArray names = NameArray.fromList("MacBook", "local");
        DomainName localHost = DomainNameBuilder.fromNameArray(names);
        DomainName internetHost = DomainNameBuilder.fromNameArray(NameArray.fromList("www", "macbook", "org"));

        assertTrue(localHost instanceof LocalDomainName);
        assertTrue(internetHost instanceof InternetDomainName);
        assertNotEquals(localHost, internetHost);
    }

    @Test
    void testReverseInet4Address() throws UnknownHostException, ParseException {
        NameArray names = NameArray.fromList("4", "4", "8", "8", "in-addr", "arpa");
        ReverseInet4Address reverseIp4 = (ReverseInet4Address) DomainNameBuilder.fromNameArray(names);
        byte[] ipAddr = new byte[]{8, 8, 4, 4};
        InetAddress expected = InetAddress.getByAddress(ipAddr);

        assertEquals(expected, reverseIp4.address());
    }

    @Test
    void testReverseInet6Address() throws ParseException, UnknownHostException {

        ReverseInet6Address reverseIp6 = (ReverseInet6Address) DomainNameBuilder.fromNameArray(reverseInet6Name);
        InetAddress expected = InetAddress.getByName("2001:db8::567:89ab");

        assertEquals(expected, reverseIp6.address());
    }
}