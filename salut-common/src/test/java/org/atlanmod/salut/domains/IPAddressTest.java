package org.atlanmod.salut.domains;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IPAddressTest {

    private IPv4Address localIPv4;
    private IPv6Address localIPv6;

    @BeforeEach
    public void beforeEach() {
        localIPv4 = new IPv4Address(new byte[] {127,0,0,1});
        localIPv6 = new IPv6Address(new byte[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1});
    }

    @Test
    public void testEqualsIPv4() {
        IPv4Address expected = new IPv4Address(new byte[] {127,0,0,1});
        IPv4Address maredsous = new IPv4Address(new byte[] {53,15,83,124});

        assertAll(
            () -> assertThat(localIPv4).isEqualTo(expected),
            () -> assertThat(localIPv4.equals(localIPv4)).isTrue(),
            () -> assertThat(localIPv4.equals(null)).isFalse(),
            () -> assertThat(localIPv4).isNotEqualTo(maredsous),
            () -> assertThat(localIPv4.hashCode()).isNotEqualTo(maredsous.hashCode()),
            () -> assertThat(localIPv4.hashCode()).isEqualTo(expected.hashCode())
        );
    }

    @Test
    public void testEqualsIPv6() {
        IPv6Address expected = new IPv6Address(new byte[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1});
        IPv6Address maredsous = new IPv6Address(new byte[] {0,0,0,0,0,0,0,0,0,0,0,0,53,15,83,124});

        assertAll(
            () -> assertThat(localIPv6).isEqualTo(expected),
            () -> assertThat(localIPv6.equals(localIPv6)).isTrue(),
            () -> assertThat(localIPv6.equals(null)).isFalse(),
            () -> assertThat(localIPv6).isNotEqualTo(maredsous),
            () -> assertThat(localIPv6.hashCode()).isNotEqualTo(maredsous.hashCode()),
            () -> assertThat(localIPv6.hashCode()).isEqualTo(expected.hashCode())
        );
    }

    @Test
    public void testGetters() {
        assertThat(localIPv4.address()).isEqualTo(new byte[] {127,0,0,1});
    }
}