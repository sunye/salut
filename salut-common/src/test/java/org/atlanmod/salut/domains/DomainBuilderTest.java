package org.atlanmod.salut.domains;

import org.atlanmod.salut.data.ReverseInet4Address;
import org.atlanmod.salut.data.ReverseInet6Address;
import org.atlanmod.salut.labels.Labels;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.UnknownHostException;
import java.text.ParseException;

import static com.google.common.truth.Truth.assertThat;

class DomainBuilderTest {

    @DisplayName("Given: a Label Array containing a reverse address"
        + "When: builder fromLabels() is called"
        + "Then: a Reverse INet4 Address is created")
    @Test
    void create_reverse_inet4_from_labels() throws ParseException, UnknownHostException {
        Labels names = Labels.fromList("10", "44", "192", "220", "in-addr", "arpa");
        Domain actual = DomainBuilder.fromLabels(names);

        ReverseInet4Address expected = new ReverseInet4Address(
            (Inet4Address) Inet4Address.getByName("220.192.44.10"));

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Given: a Label Array containing a reverse address"
        + "When: builder fromLabels() is called"
        + "Then: a Reverse INet6 Address is created")
    @Test
    void create_reverse_inet6_from_labels() throws ParseException, UnknownHostException {
        Labels names = Labels.fromList("1", "1", "4", "4", "0", "b", "4", "0", "8",
            "a", "9", "9", "c", "0", "8", "8", "0", "0", "0", "0", "0", "0", "0", "0", "0",
            "0", "0", "0", "c", "0", "b", "4", "ip6", "arpa");
        Domain actual = DomainBuilder.fromLabels(names);

        ReverseInet6Address expected = new ReverseInet6Address(
            (Inet6Address) Inet6Address.getByName("4B0C:0:0:0:880C:99A8:4B0:4411"));

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Given: a Label Array containing a local address"
        + "When: builder fromLabels() is called"
        + "Then: a Local Domain is created")
    @Test
    void create_local_domain_from_labels() throws ParseException {
        Labels labels = Labels.fromList("appletv", "local");
        Domain actual = DomainBuilder.fromLabels(labels);

        Domain expected = new LocalHostName("appletv");

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Given: a Label Array containing an Internet address"
        + "When: builder fromLabels() is called"
        + "Then: a Internet Domain is created")
    @Test
    void create_internet_domain_from_labels() throws ParseException {
        Labels labels = Labels.fromList("smtp", "nantes", "org");
        Domain actual = DomainBuilder.fromLabels(labels);

        Domain expected = new InternetDomain(labels);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Given a string containing an Internet Domain"
        + "When parseString() is called"
        + "Then an Internet Domain is created")
    @Test
    void create_internet_domain_from_strings() throws ParseException {
        String name = "smtp.nantes.fr";
        Domain actual = DomainBuilder.parseString(name);

        Domain expected = new InternetDomain(Labels.fromList("smtp", "nantes", "fr"));

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Given a string only containing 'anyone' "
        + "When parseString() is called"
        + "Then an Local Domain is created")
    @Test
    void create_local_domain() throws ParseException {
        String name = "anyone";
        Domain actual = DomainBuilder.parseString(name);

        assertThat(actual).isEqualTo(LocalHostName.fromString(name));
    }
}
