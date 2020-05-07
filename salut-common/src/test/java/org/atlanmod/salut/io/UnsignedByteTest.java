package org.atlanmod.salut.io;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Objects;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class UnsignedByteTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 255, UnsignedByte.MAX_VALUE})
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

    @ParameterizedTest
    @ValueSource(ints = {0, 255, UnsignedByte.MIN_VALUE, UnsignedByte.MAX_VALUE})
    void testToString(int value) {
        UnsignedByte ub = UnsignedByte.fromByte((byte) value);
        assertThat(ub.toString()).isEqualTo(Integer.toString(value));
        assertThat(ub.toString()).isEqualTo(""+value+"");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 255, Integer.MIN_VALUE, Integer.MAX_VALUE})
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
    @ValueSource(shorts = {0, 1, UnsignedByte.MAX_VALUE, UnsignedByte.MAX_VALUE -1})
    void testFromShort(short value) {
        UnsignedByte ub = UnsignedByte.fromShort(value);
        assertThat(ub.shortValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(shorts = {-1, 256, Short.MAX_VALUE, Short.MIN_VALUE})
    void testInvalidFromShort(short value) {
        assertThatThrownBy(() -> UnsignedByte.fromShort((short) value))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 256, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void testInvalidFromInt(int value) {
        assertThatThrownBy(() -> UnsignedByte.fromInt(value))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testLongValue(int value) {
        UnsignedByte bb = UnsignedByte.fromInt(value);
        assertThat(bb.longValue()).isEqualTo((long) value);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testDoubleValue(int value) {
        UnsignedByte bb = UnsignedByte.fromInt(value);
        assertThat(bb.doubleValue()).isEqualTo((double) value);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testFloatValue(int value) {
        UnsignedByte bb = UnsignedByte.fromInt(value);
        assertThat(bb.floatValue()).isEqualTo((float) value);
    }

    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testToStringBis(int value) {
        UnsignedByte bb = UnsignedByte.fromInt(value);
        assertThat(String.valueOf(value)).isEqualTo(bb.toString());
    }

    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testEqual(int value) {
        UnsignedByte bb = UnsignedByte.fromInt(value);
        UnsignedByte cc = UnsignedByte.fromInt(value);
        UnsignedByte dd = null;

        assertThat(bb).isEqualTo(cc)
            .isEqualTo(bb)
            .isNotEqualTo(dd)
            .isNotEqualTo(value)
            .isNotEqualTo(UnsignedByte.fromInt(3));
    }

    @Test
    void testHash() {
        UnsignedByte bb = UnsignedByte.fromInt(5);
        UnsignedByte cc = UnsignedByte.fromInt(35);

        assertThat(bb.hashCode()).isEqualTo(36);
        assertThat(cc.hashCode()).isEqualTo(66);
    }

    @Test
    void testCompare() {
        UnsignedByte actual = UnsignedByte.fromInt(5);
        UnsignedByte five = UnsignedByte.fromInt(5);
        UnsignedByte ten = UnsignedByte.fromInt(10);
        UnsignedByte one = UnsignedByte.fromInt(1);

        assertThat(actual).isGreaterThan(one)
            .isLessThan(ten)
            .isEqualByComparingTo(five);
    }

    @ParameterizedTest
    @ValueSource(shorts = {-1, 256, Short.MAX_VALUE, Short.MIN_VALUE})

    void testInvalidFromUnsignedByte(short value) {
        assertThatThrownBy(() -> new UnsignedByte(value)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(floats = {0, 255, Byte.MAX_VALUE})
    void testToFloat(float value) {
        UnsignedByte ub = UnsignedByte.fromByte((byte) value);
        assertThat(ub.floatValue()).isEqualTo((float) value);

        assertThat(UnsignedByte.fromByte((byte) Float.MIN_VALUE).floatValue()).isEqualTo((float) 0);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 255})
    void testToDouble(double value) {
        UnsignedByte ub = UnsignedByte.fromByte((byte) value);
        assertThat(ub.doubleValue()).isEqualTo(value);

        assertThat(UnsignedByte.fromByte((byte) Double.MIN_VALUE).doubleValue()).isEqualTo(0);
    }
    @ParameterizedTest
    @ValueSource(shorts = {0, 255})
    void testToShorts(short value) {
        UnsignedByte ub = UnsignedByte.fromByte((byte) value);
        assertThat(ub.shortValue()).isEqualTo(value);

        assertThat(UnsignedByte.fromByte((byte) Short.MIN_VALUE).shortValue()).isEqualTo((short)0);
    }



    @ParameterizedTest
    @ValueSource(ints = {0, 255, UnsignedByte.MIN_VALUE, UnsignedByte.MAX_VALUE})
    void testFromInt(int value) {
        UnsignedByte ub = UnsignedByte.fromInt(value);
        assertThat(ub).isEqualTo(UnsignedByte.fromInt(value));
    }

    @ParameterizedTest
    @ValueSource(shorts = {0, 1, 100, UnsignedByte.MAX_VALUE, UnsignedByte.MIN_VALUE})
    void testEquals(short value) {
        UnsignedByte expected = UnsignedByte.fromShort(value);
        UnsignedByte actual = UnsignedByte.fromShort(value);

        assertThat(actual).isEqualTo(expected);
    }
    
    @ParameterizedTest
    @ValueSource(shorts = {0, 1, 100, UnsignedByte.MIN_VALUE, UnsignedByte.MAX_VALUE})
    void testCompareTo(short value) {
        final short other = 100;

        UnsignedByte ub = UnsignedByte.fromShort(value);
        UnsignedByte oub = UnsignedByte.fromShort(other);

        assertThat(ub.compareTo(oub)).isEqualTo(Short.compare(value, other));
    }
    
    @ParameterizedTest
    @ValueSource(shorts = {0, 255, UnsignedByte.MIN_VALUE, UnsignedByte.MAX_VALUE})
    void testHashCode(short value) {
        UnsignedByte ub = UnsignedByte.fromShort(value);
        assertThat(ub.hashCode()).isEqualTo(Objects.hash(value));
    }

}
