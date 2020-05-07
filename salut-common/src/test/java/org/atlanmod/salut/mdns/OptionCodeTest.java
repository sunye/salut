package org.atlanmod.salut.mdns;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

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
