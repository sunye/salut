package org.atlanmod.salut.mdns;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

class OptionCodeTest {

    @Test
    void testFromCode() {

        assertThat(OptionCode.fromCode(1).get()).isEqualTo(OptionCode.LLQ);
        assertThat(OptionCode.fromCode(0).isPresent()).isFalse();
    }

    @Test
    void testToString() {
        OptionCode code = OptionCode.NSID;
        assertThat(code.toString()).isEqualTo("NSID");
    }
}
