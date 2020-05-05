package org.atlanmod.salut.domains;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class HostTest {

    private Host maredsous;

    @BeforeEach
    public void beforeEach() {
        maredsous = new Host(
            new LocalHostName("maredsous"),
            IPAddressBuilder.fromBytes(new byte[] {51,53,127,16})
        );
    }

    @Test
    public void constructor_with_null_name() {
        byte[]  bytes = {1, 1, 1, 1};
        IPAddress address = IPAddressBuilder.fromBytes(bytes);

        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> {
                Host host = new Host(null, address);
            }
            );
    }


    @Test
    public void constructor_with_null_address() {
        LocalHostName name = new LocalHostName("mac-pro");

        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> {
                    Host host = new Host(name, null);
                }
            );
    }

    @Test
    public void nominal_constructor() {
        byte[] bytes = {1, 1, 1, 1};
        IPAddress address = IPAddressBuilder.fromBytes(bytes);
        LocalHostName name = new LocalHostName("mac-pro");
        Host host = new Host(name, address);

        assertThat(host.name()).isEqualTo(name);
        assertThat(host.address()).isEqualTo(IPAddressBuilder.fromBytes(new byte[]{1, 1, 1, 1}));
    }

    @Test
    public void equals_to_self() {
        assertThat(maredsous).isEqualTo(maredsous);
    }

    @Test
    public void not_equal_to_null() {
        assertThat(maredsous.equals(null)).isFalse();
    }

    @Test
    public void not_equal_to_different() {
        Host guinness = new Host(
            new LocalHostName("guinness"),
            IPAddressBuilder.fromBytes(new byte[] {51,53,127,16})
            );

        assertThat(maredsous).isNotEqualTo(guinness);
    }

    @Test
    public void same_hashcode() {
        Host similar = new Host(
            new LocalHostName("maredsous"),
            IPAddressBuilder.fromBytes(new byte[] {51,53,127,16})
        );

        assertThat(similar.hashCode()).isEqualTo(maredsous.hashCode());
    }

    @Test
    public void different_hashcode() {
        Host guinness = new Host(
            new LocalHostName("guinness"),
            IPAddressBuilder.fromBytes(new byte[] {51,53,127,16})
        );

        assertThat(guinness.hashCode()).isNotEqualTo(maredsous.hashCode());
    }

    @Test
    public void local_host_with_string() throws UnknownHostException {
        byte[] bytes = InetAddress.getLocalHost().getAddress();
        IPAddress address = IPAddressBuilder.fromBytes(bytes);
        String ubuntu = "ubuntu";
        Host actual = Host.localHost(ubuntu);
        Host expected = new Host(
            LocalHostName.fromString(ubuntu),
            address
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void local_host() throws UnknownHostException, ParseException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String hostName = inetAddress.getHostName();
        Domain expectedDomain = DomainBuilder.parseString(hostName);
        IPAddress expectedAddress = IPAddressBuilder.fromBytes(inetAddress.getAddress());

        Host actual = Host.localHost();
        assertThat(actual.address()).isEqualTo(expectedAddress);
        assertThat(actual.name()).isEqualTo(expectedDomain);
    }

    @Test
    public void testLocalRoutableAdresses() throws SocketException {
        InetAddress[] addresses = Host.localRoutableAdresses();

        assertThat(addresses).filteredOn(each -> each.isMulticastAddress()).isEmpty();
        assertThat(addresses).filteredOn(each -> each.isLinkLocalAddress()).isEmpty();
        assertThat(addresses).filteredOn(each -> each.isLoopbackAddress()).isEmpty();
    }

    @Test
    public void testToString() {
        Host other = new Host(maredsous.name(), maredsous.address());

        assertThat(other.toString()).isEqualTo(maredsous.toString());
        assertThat(other.toString()).doesNotContain("@");
    }
}