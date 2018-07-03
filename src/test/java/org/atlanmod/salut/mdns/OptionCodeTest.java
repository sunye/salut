package org.atlanmod.salut.mdns;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

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