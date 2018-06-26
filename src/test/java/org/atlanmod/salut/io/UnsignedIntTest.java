package org.atlanmod.salut.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UnsignedIntTest {
    @ParameterizedTest
    @ValueSource(ints = {Integer.MAX_VALUE, Integer.MIN_VALUE, -31, 467})
    void fromInt(int value) {
        UnsignedInt unsignedInt = UnsignedInt.fromInt(value);
        assertThat(unsignedInt.intValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MAX_VALUE, Integer.MIN_VALUE, -31, 467})
    void intValue(int value) {
        UnsignedInt unsignedInt = UnsignedInt.fromInt(value);
        assertThat(unsignedInt.intValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MAX_VALUE, 31, 467})
    void longValue(int value) {
        UnsignedInt unsignedInt0 = UnsignedInt.fromInt(value);
        assertThat(unsignedInt0.longValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MAX_VALUE, 31, 467})
    void floatValue(int value) {
        UnsignedInt unsignedInt = UnsignedInt.fromInt(value);
        assertThat(unsignedInt.floatValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MAX_VALUE, 31, 467})
    void doubleSignedValue(int value) {
        UnsignedInt unsignedInt = UnsignedInt.fromInt(value);
        assertThat(unsignedInt.doubleValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MAX_VALUE, 31, 467})
    void toString(int value) {
        UnsignedInt unsignedInt = UnsignedInt.fromInt(value);
        assertThat(unsignedInt.toString()).isEqualTo(Integer.toString(value));
    }

    @Test
    void compareTo() {
        UnsignedInt unsignedInt0 = UnsignedInt.fromInt(0);
        UnsignedInt unsignedInt1 = UnsignedInt.fromInt(1);
        UnsignedInt unsignedInt2 = UnsignedInt.fromInt(1);

        assertThat(unsignedInt0.compareTo(unsignedInt1)).isEqualTo(-1);
        assertThat(unsignedInt1.compareTo(unsignedInt0)).isEqualTo(1);
        assertThat(unsignedInt2.compareTo(unsignedInt1)).isEqualTo(0);
    }

}