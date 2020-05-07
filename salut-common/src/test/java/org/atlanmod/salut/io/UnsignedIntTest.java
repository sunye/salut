package org.atlanmod.salut.io;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/*
Classe de test pour la classe UnsignedInt
Cette classe a pour but de faire l'ensemble des tests unitaires de UnsignedInt
 */
public class UnsignedIntTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testFromInt(int value) {
        UnsignedInt bb = UnsignedInt.fromInt(value);
        assertEquals(value, bb.intValue());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testByteValue(int value) {
        UnsignedInt ub = UnsignedInt.fromInt( value);
        assertEquals((byte) value ,ub.byteValue());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testShortValue(int value) {
        UnsignedInt ub = UnsignedInt.fromInt(value);
        assertEquals((short) value ,ub.shortValue());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testIntValue(int value) {
        UnsignedInt ub = UnsignedInt.fromInt(value);
        assertEquals( value ,ub.intValue());
    }


    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testLongValue(int value) {
        UnsignedInt bb = UnsignedInt.fromInt(value);
        assertEquals((long) Long.valueOf(value) , bb.longValue());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testDoubleValue(int value) {
        UnsignedInt bb = UnsignedInt.fromInt(value);
        assertEquals((double) Double.valueOf(value) , bb.doubleValue());
    }


    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testFloatValue(int value) {
        UnsignedInt bb = UnsignedInt.fromInt(value);
        assertEquals((float) Float.valueOf(value) , bb.floatValue());
    }


    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testToString(int value) {
        UnsignedInt bb = UnsignedInt.fromInt(value);
        assertEquals(String.valueOf(value) , bb.toString());
    }


    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testEqual(int value) {
        UnsignedInt bb = UnsignedInt.fromInt(value);
        UnsignedInt cc = UnsignedInt.fromInt(value);
        UnsignedInt dd = null;
        assertAll(
                ()->assertTrue(bb.equals(cc)),
                ()->assertTrue(bb.equals(bb)),
                ()->assertFalse(bb.equals(dd)),
                ()->assertFalse(bb.equals(value)),
                ()->assertFalse(bb.equals(UnsignedInt.fromInt(3)))
        );
    }

    @Test
    void testCompare() {
        UnsignedInt bb = UnsignedInt.fromInt(5);
        assertAll(
                ()->assertEquals(0, bb.compareTo(UnsignedInt.fromInt(5))),
                ()->assertEquals(-1, bb.compareTo(UnsignedInt.fromInt(10))),
                ()->assertEquals(1, bb.compareTo(UnsignedInt.fromInt(1)))
        );
    }

    @Test
    void testHash() {
        UnsignedInt bb = UnsignedInt.fromInt(5);
        UnsignedInt cc = UnsignedInt.fromInt(35);

        assertAll(
                ()->assertEquals(36, bb.hashCode()),
                ()->assertEquals(66, cc.hashCode())
        );
    }

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
        float expected = (float) value;
        UnsignedInt unsignedInt = UnsignedInt.fromInt(value);

        assertThat(unsignedInt.floatValue()).isEqualTo(expected);
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

    @ParameterizedTest
    @ValueSource(longs = {UnsignedInt.MIN_VALUE, UnsignedInt.MAX_VALUE, UnsignedInt.MIN_VALUE +1, UnsignedInt.MAX_VALUE -1})
    void testFromLong(long value) {
        UnsignedInt ui = UnsignedInt.fromLong(value);
        assertThat(ui.longValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, UnsignedInt.MAX_VALUE + 1, Long.MIN_VALUE, Long.MAX_VALUE})
    void testFromLongInvalid(long value) {
        assertThrows(IllegalArgumentException.class , () -> UnsignedInt.fromLong(value));
    }

}
