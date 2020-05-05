package org.atlanmod.salut.cache;

import org.atlanmod.salut.data.ReverseInet4Address;
import org.atlanmod.salut.data.ReverseInet6Address;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.domains.InternetDomain;
import org.atlanmod.salut.domains.LocalHostName;
import org.atlanmod.salut.labels.Labels;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

class HostNameTest {

    private Labels reverseInet6Name = Labels.fromList("b", "a", "9", "8", "7", "6", "5", "0", "0",
            "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "8",
            "b", "d", "0", "1", "0", "0", "2", "ip6", "arpa");;

    @Test
    void testCreateLocalHostName() throws ParseException {
        Labels names = Labels.fromList("MacBook", "local");
        Domain localHost = DomainBuilder.fromLabels(names);
        Domain internetHost = DomainBuilder.fromLabels(Labels.fromList("www", "macbook", "org"));

        assertThat(localHost).isInstanceOf(LocalHostName.class);
        assertThat(internetHost).isInstanceOf(InternetDomain.class);
        assertThat(localHost).isNotEqualTo(internetHost);
    }

    @Test
    void testReverseInet4Address() throws UnknownHostException, ParseException {
        Labels names = Labels.fromList("4", "4", "8", "8", "in-addr", "arpa");
        ReverseInet4Address reverseIp4 = (ReverseInet4Address) DomainBuilder.fromLabels(names);
        byte[] ipAddr = new byte[]{8, 8, 4, 4};
        InetAddress expected = InetAddress.getByAddress(ipAddr);

        assertThat(reverseIp4.address()).isEqualTo(expected);
    }

    @Test
    void testReverseInet6Address() throws ParseException, UnknownHostException {

        ReverseInet6Address reverseIp6 = (ReverseInet6Address) DomainBuilder.fromLabels(reverseInet6Name);
        InetAddress expected = InetAddress.getByName("2001:db8::567:89ab");

        assertThat(reverseIp6.address()).isEqualTo(expected);
    }
}
