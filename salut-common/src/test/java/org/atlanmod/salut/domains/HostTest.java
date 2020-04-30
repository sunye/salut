package org.atlanmod.salut.domains;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.net.InetAddress;
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
            new byte[] {51,53,127,16}
        );
    }

    @Test
    public void constructor_with_invalid_name() {
        byte[] address = {1, 1, 1, 1};

        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> {
                Host host = new Host(null, address);
            }
            );
    }

    @Test
    public void constructor_with_invalid_address() {
        byte[] address = {1, 1, 1};
        LocalHostName name = new LocalHostName("mac-pro");

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                    Host host = new Host(name, address);
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
        byte[] address = {1, 1, 1, 1};
        LocalHostName name = new LocalHostName("mac-pro");
        Host host = new Host(name, address);

        assertThat(host.name()).isEqualTo(name);
        assertThat(host.address()).isEqualTo(new byte[]{1, 1, 1, 1});
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
            new byte[] {51,53,127,16}
            );

        assertThat(maredsous).isNotEqualTo(guinness);
    }

    @Test
    public void same_hashcode() {
        Host similar = new Host(
            new LocalHostName("maredsous"),
            new byte[] {51,53,127,16}
        );

        assertThat(similar.hashCode()).isEqualTo(maredsous.hashCode());
    }

    @Test
    public void different_hashcode() {
        Host guinness = new Host(
            new LocalHostName("guinness"),
            new byte[] {51,53,127,16}
        );

        assertThat(guinness.hashCode()).isNotEqualTo(maredsous.hashCode());
    }

    @Test
    public void local_host_with_string() throws UnknownHostException {
        byte[] address = InetAddress.getLocalHost().getAddress();
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
        byte[] expectedAddress = inetAddress.getAddress();

        Host actual = Host.localHost();
        assertThat(actual.address()).isEqualTo(expectedAddress);
        assertThat(actual.name()).isEqualTo(expectedDomain);
    }
}