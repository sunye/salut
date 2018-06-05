package org.atlanmod.salut.io;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

class UnsignedByteTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testToInt(int value) {
        UnsignedByte ub = UnsignedByte.fromByte((byte) value);
        assertThat(ub.intValue()).isEqualTo(value);
    }

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
    @MethodSource("joinedBytes")
    void testWithByte(int a, int b, int result) {
        UnsignedByte ub = UnsignedByte.fromByte((byte) a);
        UnsignedShort us = ub.withLowByte(UnsignedByte.fromByte((byte) b));
        UnsignedShort expected = UnsignedShort.fromShort((short) result);
        assertThat(us).isEqualTo(expected);
    }

    private static Stream<Arguments> joinedBytes() {
        return Stream.of(
                Arguments.of(0, 0, 0),
                Arguments.of(255, 128, (255 << 8) + 128),
                Arguments.of(127, 11, (127 << 8) + 11)
        );
    }

    @Test
    void testToString() {
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testFromByte(int value) {
        UnsignedByte ub = UnsignedByte.fromByte((byte) value);
        assertThat(ub).isEqualTo(UnsignedByte.fromByte((byte) value));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testFromInteger(int value) {
        UnsignedByte ub = UnsignedByte.fromInt(value);
        assertThat(ub).isEqualTo(UnsignedByte.fromInt(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testFromShort(int value) {
        UnsignedByte ub = UnsignedByte.fromShort((short) value);
        assertThat(ub).isEqualTo(UnsignedByte.fromShort((short) value));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 256, Short.MAX_VALUE, Short.MIN_VALUE})
    void testInvalidFromShort(int value) {
        assertThrows(IllegalArgumentException.class, () -> UnsignedByte.fromShort((short) value));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 256, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void testInvalidFromInt(int value) {
        assertThrows(IllegalArgumentException.class, () -> UnsignedByte.fromInt(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testLongValue(int value) {
        UnsignedByte bb = UnsignedByte.fromInt(value);
        assertEquals((long) Long.valueOf(value) , bb.longValue());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testDoubleValue(int value) {
        UnsignedByte bb = UnsignedByte.fromInt(value);
        assertEquals((double) Double.valueOf(value) , bb.doubleValue());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testFloatValue(int value) {
        UnsignedByte bb = UnsignedByte.fromInt(value);
        assertEquals((float) Float.valueOf(value) , bb.floatValue());
    }

    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testToString(int value) {
        UnsignedByte bb = UnsignedByte.fromInt(value);
        assertEquals(String.valueOf(value) , bb.toString());
    }

    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testEqual(int value) {
        UnsignedByte bb = UnsignedByte.fromInt(value);
        UnsignedByte cc = UnsignedByte.fromInt(value);
        UnsignedByte dd = null;
        assertAll(
                ()->assertTrue(bb.equals(cc)),
                ()->assertTrue(bb.equals(bb)),
                ()->assertFalse(bb.equals(dd)),
                ()->assertFalse(bb.equals(value)),
                ()->assertFalse(bb.equals(UnsignedByte.fromInt(3)))
        );
    }

    @Test
    void testHash() {
        UnsignedByte bb = UnsignedByte.fromInt(5);
        UnsignedByte cc = UnsignedByte.fromInt(35);

        assertAll(
                ()->assertEquals(36, bb.hashCode()),
                ()->assertEquals(66, cc.hashCode())
        );
    }

    @Test
    void testCompare() {
        UnsignedByte bb = UnsignedByte.fromInt(5);
        assertAll(
            ()->assertEquals(0, bb.compareTo(UnsignedByte.fromInt(5))),
            ()->assertEquals(-1, bb.compareTo(UnsignedByte.fromInt(10))),
            ()->assertEquals(1, bb.compareTo(UnsignedByte.fromInt(1)))
        );
    }

}