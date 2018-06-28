package org.atlanmod.salut.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/*
Classe de test pour la classe UnsignedShort
Cette classe a pour but de faire l'ensemble des tests unitaires de UnsignedInt
 */
public class UnsignedShortTest {
    /*
    Test de la méthode IntValue, on vérifie que les valeurs retournées soient égales aux entrées
     */
    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testIntValue(int value){
        UnsignedShort us = new UnsignedShort(value);
        assertEquals((int) value, us.intValue());
    }

    /*
    Test de la méthode LongValue, on vérifie que les valeurs retournées soient égales aux entrées converties en long
     */
    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testLongValue(int value) {
        UnsignedShort us = new UnsignedShort(value);
        assertEquals((long) value, us.longValue());
    }

    /*
    Test de la méthode FloatValue, on vérifie que les valeurs retournées soient égales aux entrées converties en float
     */
    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testFloatValue(int value) {
        UnsignedShort us = new UnsignedShort(value);
        assertEquals((float) value, us.floatValue());
    }

    /*
    Test de la méthode DoubleValue, on vérifie que les valeurs retournées soient égales aux entrées converties en double
     */
    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testDoubleValue(int value) {
        UnsignedShort us = new UnsignedShort(value);
        assertEquals((double) value, us.doubleValue());
    }

    /*
    Test de la méthode byteValue, on vérifie que les valeurs retournées soient égales aux entrées converties en byte
     */
    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testByteValue(int value) {
        UnsignedShort us = new UnsignedShort(value);
        assertEquals((byte) value, us.byteValue());
    }

    /*
    Test de la méthode shortValue, on vérifie que les valeurs retournées soient égales aux entrées converties en short
     */
    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testShortValue(int value) {
        UnsignedShort us = new UnsignedShort(value);
        assertEquals((short) value, us.shortValue());
    }

    /*
    Test de la méthode ToString, on vérifie que les valeurs retournées soient égales aux entrées converties en string
     */
    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testToString(int value) {
        UnsignedShort us = new UnsignedShort(value);
        assertEquals(String.valueOf(value) , us.toString());
    }

    /*
    Test de la méthode equals
    1. On vérifie que bb, construit à partir de la valeur est égal à cc construit à partir de la meme valeur => True
    2. On vérifie que bb, construit à partir de la valeur est égal à ce même bb => True
    3. On vérifie que bb, construit à partir de la valeur est égal à dd instancié à null => False
    4. On vérifie que bb, construit à partir de la valeur est égal à la valeur => False
    5. On vérifie que bb, construit à partir de la valeur est égal à un autre construit à partir d'une autre valeur => False
     */
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

    /*
    Test de la méthode hashCode, on vérifie que le hashcode retourné soit bien le bon
     */
    @Test
    void testHashCode() {
        UnsignedShort bb = new UnsignedShort(5);
        UnsignedShort cc = new UnsignedShort(35);

        assertAll(
                ()->assertEquals(36, bb.hashCode()),
                ()->assertEquals(66, cc.hashCode())
        );
    }

    /*
    Test de la méthode testFromShort, on vérifie que 2 unsigned short construit à partir de la même valeur soient égaux
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testFromShort(int value) {
        UnsignedShort ub = UnsignedShort.fromShort((short) value);
        assertThat(ub).isEqualTo(UnsignedShort.fromShort((short) value));
    }

    /*
    Test de la méthode compare
    1. On vérifie que bb construit à partir de 5 soit égal à un UnsignedShort construit à partir de 5 => 0
    2. On vérifie que bb construit à partir de 5 soit inférieur à un UnsignedShort construit à partir de 10 => -1
    3. On vérifie que bb construit à partir de 5 soit supérieur à un UnsignedShort construit à partir de 1 => 1
     */
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
    void testEqualsBis(int value) {
        final int other = 100;

        UnsignedShort oub = UnsignedShort.fromInt(other);
        UnsignedShort ub = UnsignedShort.fromInt(value);
        assertThat(ub.equals(oub)).isEqualTo(Integer.valueOf(value).equals(Integer.valueOf(other)));
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
        assertThat(ub.hashCode()).isEqualTo(Objects.hash(value));
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
}
