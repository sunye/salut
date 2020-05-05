package org.atlanmod.salut.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class UnsignedShortTest {

    @DisplayName("Tests method intValue()")
    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testIntValue(int value){
        UnsignedShort us = new UnsignedShort(value);
        assertEquals(value, us.intValue());
    }

    @DisplayName("Tests method longValue()")
    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testLongValue(int value) {
        UnsignedShort us = new UnsignedShort(value);
        assertEquals((long) value, us.longValue());
    }

    @DisplayName("Tests method longValue()")
    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testFloatValue(int value) {
        UnsignedShort us = new UnsignedShort(value);
        assertEquals((float) value, us.floatValue());
    }


    @DisplayName("Tests method doubleValue()")
    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testDoubleValue(int value) {
        UnsignedShort us = new UnsignedShort(value);
        assertEquals((double) value, us.doubleValue());
    }


    @DisplayName("Tests method byteValue()")
    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testByteValue(int value) {
        UnsignedShort us = new UnsignedShort(value);
        assertEquals((byte) value, us.byteValue());
    }


    @DisplayName("Tests method shortValue()")
    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testShortValue(int value) {
        UnsignedShort us = new UnsignedShort(value);
        assertEquals((short) value, us.shortValue());
    }

    @DisplayName("Tests method toString()")
    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testToString(int value) {
        UnsignedShort us = new UnsignedShort(value);
        assertEquals(String.valueOf(value) , us.toString());
    }

    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254, 100})
    void testEquals(int value) {
        UnsignedShort us = UnsignedShort.fromInt(value);
        UnsignedShort same =  UnsignedShort.fromInt(value);
        UnsignedShort other = UnsignedShort.fromInt(0xFFFF);

        assertAll(
                () -> assertThat(us).isEqualTo(same),
                () -> assertThat(us).isEqualTo(us),
                () -> assertThat(us).isNotEqualTo(other),
                () -> assertThat(us).isNotEqualTo(null)
        );
    }

    @DisplayName("Tests method hashCode()")
    @Test
    void testHashCode() {
        UnsignedShort a = new UnsignedShort(5);
        UnsignedShort b = new UnsignedShort(35);

        assertAll(
                () -> assertThat(a.hashCode()).isNotEqualTo(b.hashCode()),
                () -> assertThat(a.hashCode()).isEqualTo(new UnsignedShort(5).hashCode()),
                () -> assertThat(b.hashCode()).isEqualTo(new UnsignedShort(35).hashCode())
        );
    }


    @Test
    void testCompareTo() {
        UnsignedShort bb = new UnsignedShort(5);
        assertAll(
                ()->assertEquals(0, bb.compareTo(new UnsignedShort(5))),
                ()->assertEquals(-1, bb.compareTo(new UnsignedShort(10))),
                ()->assertEquals(1, bb.compareTo(new UnsignedShort(1)))
        );
    }

    @Test
    void isLessThan() {
        UnsignedShort ub0 = UnsignedShort.fromInt(0);
        UnsignedShort ub1 = UnsignedShort.fromInt(1);

        assertThat(ub0.isLessThan(ub1)).isTrue();
        assertThat(ub1.isLessThan(ub0)).isFalse();
        assertThat(ub1.isLessThan(ub1)).isFalse();
    }

    @Test
    void isZero() {
        UnsignedShort ub = UnsignedShort.fromInt(0);
        UnsignedShort ub1 = UnsignedShort.fromInt(1);

        assertThat(ub.isZero()).isTrue();
        assertThat(ub1.isZero()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 100, 254, 255})
    void testToStringBis(int value) {
        UnsignedShort ub = UnsignedShort.fromInt(value);
        assertThat(ub.toString()).isEqualTo(Integer.toString(value));
    }



    @ParameterizedTest
    @ValueSource(ints = {0, 1, 100, 254, 255})
    void testCompareTo(int value) {
        final int other = 100;

        UnsignedShort ub = UnsignedShort.fromInt(value);
        UnsignedShort oub = UnsignedShort.fromInt(other);

        assertThat(ub.compareTo(oub)).isEqualTo(Integer.compare(value, other));
    }
    @ParameterizedTest
    @ValueSource(ints = {UnsignedShort.MIN_VALUE, UnsignedShort.MAX_VALUE, 1, 254})
    void testHashCode(int value) {
        UnsignedShort ub = UnsignedShort.fromInt(value);
        assertThat(ub.hashCode()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(shorts = {0, Short.MAX_VALUE})
    void testToInt(short value) {
        UnsignedShort ub = UnsignedShort.fromShort(value);
        assertThat(ub.intValue()).isEqualTo(value);
    }
    
    @ParameterizedTest
    @ValueSource(floats = {UnsignedShort.MIN_VALUE, UnsignedShort.MAX_VALUE, 1, 254})
    void testToFloat(float value) {
        UnsignedShort ub = UnsignedShort.fromShort((short) value);
        assertThat(ub.floatValue()).isEqualTo(value);
    }
    
    @ParameterizedTest
    @ValueSource(doubles = {UnsignedShort.MIN_VALUE, UnsignedShort.MAX_VALUE, 1, 254})
    void testToDouble(double value) {
        UnsignedShort ub = UnsignedShort.fromShort((short) value);
        assertThat(ub.doubleValue()).isEqualTo(value);
    }
    @ParameterizedTest
    @ValueSource(shorts = {UnsignedShort.MIN_VALUE, (short) UnsignedShort.MAX_VALUE, 1, 254})
    void testToShorts(short value) {
        UnsignedShort ub = UnsignedShort.fromShort(value);
        assertThat(ub.shortValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(ints = {UnsignedShort.MIN_VALUE, UnsignedShort.MAX_VALUE, 1, 254})
    void testFromByte(int value) {
        UnsignedShort ub = UnsignedShort.fromShort((byte) value);
        assertThat(ub).isEqualTo(UnsignedShort.fromShort((byte) value));
    }

    @ParameterizedTest
    @ValueSource(shorts = {(short) UnsignedShort.MIN_VALUE, (short) UnsignedShort.MAX_VALUE, 1,
            (short) UnsignedShort.MAX_VALUE -1})
    void testFromShort(short value) {
        UnsignedShort ub = UnsignedShort.fromShort(value);
        assertThat(ub).isEqualTo(UnsignedShort.fromShort(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {UnsignedShort.MIN_VALUE, UnsignedShort.MAX_VALUE, 1, UnsignedShort.MAX_VALUE -1})
    void testFromInt(int value) {
        UnsignedShort ub = UnsignedShort.fromInt(value);
        assertThat(ub.intValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -1, UnsignedShort.MAX_VALUE +1, Integer.MAX_VALUE})
    void testInvalidFromInt(int value) {
        assertThrows(IllegalArgumentException.class, () -> UnsignedShort.fromInt(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -1, UnsignedShort.MAX_VALUE +1, Integer.MAX_VALUE})
    void constructor_with_invalid_values_throws_exception(int value) {
        assertThrows(IllegalArgumentException.class, () -> new  UnsignedShort(value));
    }
}
