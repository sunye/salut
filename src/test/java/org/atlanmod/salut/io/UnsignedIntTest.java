package org.atlanmod.salut.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
}
