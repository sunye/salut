package org.atlanmod.salut.io;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PointerTest {

    @Test
    void offset() {
        LabelPointer p = new LabelPointer(42);
        assertThat(p.offset()).isEqualTo(42&0x3FFF);
    }

    @Test
    void fromBytes() {
        UnsignedByte h=null;
        LabelPointer p = LabelPointer.fromBytes(h.fromInt(10),h.fromInt(1));
        LabelPointer p1 = new LabelPointer(2561);
        assertThat(p.intValue()).isEqualTo(p1.intValue());
    }
}