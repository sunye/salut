package org.atlanmod.salut.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.atlanmod.testing.Verifier.verifyEqualsOf;

import org.junit.jupiter.api.Test;

class KnownApplicationProtocolTest {

    @Test
    void testEquals() {
        Object[] args1 = {IANAApplicationProtocol.adisk};
        Object[] args2 = {IANAApplicationProtocol.airplay};

        verifyEqualsOf(KnownApplicationProtocol.class)
            .withArguments(args1)
            .andVariants(args2)
            .check();
    }

    @Test
    void testNameAccessor() {
        KnownApplicationProtocol protocol = new KnownApplicationProtocol(
            IANAApplicationProtocol.http);

        assertThat(protocol.name()).isEqualTo("http");
    }
}