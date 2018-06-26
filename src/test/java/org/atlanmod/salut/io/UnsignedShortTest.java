package org.atlanmod.salut.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UnsignedShortTest {

    @Test
    void isLessThan() {
        UnsignedByte ub0 = UnsignedByte.fromInt(0);
        UnsignedByte ub1 = UnsignedByte.fromInt(1);

        assertThat(ub0.isLessThan(ub1)).isTrue();
        assertThat(ub1.isLessThan(ub0)).isFalse();
        assertThat(ub1.isLessThan(ub1)).isFalse();
    }
    @Test
    void isZero() {
        UnsignedByte ub = UnsignedByte.fromInt(0);
        UnsignedByte ub1 = UnsignedByte.fromInt(1);

        assertThat(ub.isZero()).isTrue();
        assertThat(ub1.isZero()).isFalse();
    }
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 100, 254, 255})
    void testToString(int value) {
        UnsignedByte ub = UnsignedByte.fromInt(value);
        assertThat(ub.toString()).isEqualTo(Integer.toString(value));
    }
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 100, 254, 255})
    void testEquals(int value) {
        final int other = 100;

        UnsignedByte oub = UnsignedByte.fromInt(other);
        UnsignedByte ub = UnsignedByte.fromInt(value);
        assertThat(ub.equals(oub)).isEqualTo(Integer.valueOf(value).equals(Integer.valueOf(other)));
    }
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 100, 254, 255})
    void testCompareTo(int value) {
        final int other = 100;

        UnsignedByte ub = UnsignedByte.fromInt(value);
        UnsignedByte oub = UnsignedByte.fromInt(other);

        assertThat(ub.compareTo(oub)).isEqualTo(Integer.compare(value, other));
    }
    @ParameterizedTest
    @ValueSource(ints = {UnsignedByte.MIN_VALUE, UnsignedByte.MAX_VALUE, 1, 254})
    void testHashCode(int value) {
        UnsignedByte ub = UnsignedByte.fromInt(value);
        assertThat(ub.hashCode()).isEqualTo(Objects.hash(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {UnsignedByte.MIN_VALUE, UnsignedByte.MAX_VALUE, 1, 254})
    void testToInt(int value) {
        UnsignedByte ub = UnsignedByte.fromByte((byte) value);
        assertThat(ub.intValue()).isEqualTo(value);
    }
    
    @ParameterizedTest
    @ValueSource(floats = {UnsignedByte.MIN_VALUE, UnsignedByte.MAX_VALUE, 1, 254})
    void testToFloat(float value) {
        UnsignedByte ub = UnsignedByte.fromByte((byte) value);
        assertThat(ub.floatValue()).isEqualTo(value);
    }
    
    @ParameterizedTest
    @ValueSource(doubles = {UnsignedByte.MIN_VALUE, UnsignedByte.MAX_VALUE, 1, 254})
    void testToDouble(double value) {
        UnsignedByte ub = UnsignedByte.fromByte((byte) value);
        assertThat(ub.doubleValue()).isEqualTo(value);
    }
    @ParameterizedTest
    @ValueSource(shorts = {UnsignedByte.MIN_VALUE, UnsignedByte.MAX_VALUE, 1, 254})
    void testToShorts(short value) {
        UnsignedByte ub = UnsignedByte.fromByte((byte) value);
        assertThat(ub.shortValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(ints = {UnsignedByte.MIN_VALUE, UnsignedByte.MAX_VALUE, 1, 254})
    void testFromByte(int value) {
        UnsignedByte ub = UnsignedByte.fromByte((byte) value);
        assertThat(ub).isEqualTo(UnsignedByte.fromByte((byte) value));
    }

    @ParameterizedTest
    @ValueSource(shorts = {UnsignedByte.MIN_VALUE, UnsignedByte.MAX_VALUE, 1, 254})
    void testFromShort(short value) {
        UnsignedByte ub = UnsignedByte.fromShort(value);
        assertThat(ub).isEqualTo(UnsignedByte.fromShort(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {UnsignedByte.MIN_VALUE, UnsignedByte.MAX_VALUE, 1, 254})
    void testFromInt(int value) {
        UnsignedByte ub = UnsignedByte.fromInt(value);
        assertThat(ub).isEqualTo(UnsignedByte.fromInt(value));
    }

    @ParameterizedTest
    @ValueSource(shorts = {-42, 345, Short.MAX_VALUE, Short.MIN_VALUE})
    void testInvalidFromShort(short value) {
        assertThrows(IllegalArgumentException.class, () -> UnsignedByte.fromShort( value));
    }

    @ParameterizedTest
    @ValueSource(ints = {-42, 345, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void testInvalidFromInt(int value) {
        assertThrows(IllegalArgumentException.class, () -> UnsignedByte.fromInt(value));
    }



}