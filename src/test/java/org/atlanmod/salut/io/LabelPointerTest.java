package org.atlanmod.salut.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

class LabelPointerTest {

    @DisplayName("Tests method offset()")
    @Test
    void testOffset() {
        UnsignedByte pointerFlag = UnsignedByte.fromInt(0xC0);
        UnsignedByte offsetValue = UnsignedByte.fromInt(0x03);

        LabelPointer p = LabelPointer.fromBytes(pointerFlag, offsetValue);
        assertThat(p.offset()).isEqualTo(0x03);
    }

    @DisplayName("Tests method fromBytes() with invalid pointer values")
    @ParameterizedTest
    @ValueSource(shorts = {0, 1})
    void testFromBytesInvalid(short value) {
        byte highValue = (byte) (value >> 8);
        byte lowValue = (byte) (value & 0xFF);

        assertThrows(IllegalArgumentException.class, () -> LabelPointer.fromBytes(
                UnsignedByte.fromInt(highValue), UnsignedByte.fromByte(lowValue)));
    }

    @DisplayName("Tests method fromBytes() with valid pointer values")
    @ParameterizedTest
    @ValueSource(shorts = {(short)0xC000, (short) 0xCFFF})
    void testFromBytes(short value) {
        byte highValue = (byte) (value >> 8);
        byte lowValue = (byte) (value & 0xFF);

        LabelPointer pointer = LabelPointer.fromBytes(UnsignedByte.fromByte(highValue),
                UnsignedByte.fromByte(lowValue));

        assertThat(pointer.offset()).isEqualTo(value & LabelPointer.POINTER_MASK);
    }


}