package org.atlanmod.salut.io;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class UnsignedShortTest {
    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testIntValue(int value){
        UnsignedShort us = new UnsignedShort(value);
        assertEquals((int) value, us.intValue());
    }

    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testLongValue(int value) {
        UnsignedShort us = new UnsignedShort(value);
        assertEquals((long) value, us.longValue());
    }

    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testFloatValue(int value) {
        UnsignedShort us = new UnsignedShort(value);
        assertEquals((float) value, us.floatValue());
    }

    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testDoubleValue(int value) {
        UnsignedShort us = new UnsignedShort(value);
        assertEquals((double) value, us.doubleValue());
    }

    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testByteValue(int value) {
        UnsignedShort us = new UnsignedShort(value);
        assertEquals((byte) value, us.byteValue());
    }

    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testShortValue(int value) {
        UnsignedShort us = new UnsignedShort(value);
        assertEquals((short) value, us.shortValue());
    }

    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testToString(int value) {
        UnsignedShort us = new UnsignedShort(value);
        assertEquals("us" + String.valueOf(value) , us.toString());
    }

    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testEquals(int value) {
        UnsignedShort bb = new UnsignedShort(value);
        UnsignedShort cc = new UnsignedShort(value);
        UnsignedShort dd = null;
        assertAll(
                ()->assertTrue(bb.equals(cc)),
                ()->assertTrue(bb.equals(bb)),
                ()->assertFalse(bb.equals(dd)),
                ()->assertFalse(bb.equals(value)),
                ()->assertFalse(bb.equals(new UnsignedShort(3)))
        );
    }

    @Test
    void testHashCode() {
        UnsignedShort bb = new UnsignedShort(5);
        UnsignedShort cc = new UnsignedShort(35);

        assertAll(
                ()->assertEquals(36, bb.hashCode()),
                ()->assertEquals(66, cc.hashCode())
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testFromShort(int value) {
        UnsignedShort ub = UnsignedShort.fromShort((short) value);
        assertThat(ub).isEqualTo(UnsignedShort.fromShort((short) value));
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
}
