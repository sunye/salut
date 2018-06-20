package org.atlanmod.salut.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/*
Classe de test pour la classe UnsignedIntTest
Cette classe a pour but de faire l'ensemble des tests unitaires de UnsignedIntTest
 */
public class UnsignedIntTest {

    /*
    Test de la méthode fromInt, on vérifie que les valeurs retournées soient égales aux entrées
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testFromInt(int value) {
        UnsignedInt bb = UnsignedInt.fromInt(value);
        assertEquals(value, bb.intValue());
    }

    /*
    Test de la méthode byteValue, on vérifie que les valeurs retournées soient égales aux entrées converties en byte
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testByteValue(int value) {
        UnsignedInt ub = UnsignedInt.fromInt( value);
        assertEquals((byte) value ,ub.byteValue());
    }

    /*
    Test de la méthode shortValue, on vérifie que les valeurs retournées soient égales aux entrées converties en short
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testShortValue(int value) {
        UnsignedInt ub = UnsignedInt.fromInt(value);
        assertEquals((short) value ,ub.shortValue());
    }

    /*
    Test de la méthode IntValue, on vérifie que les valeurs retournées soient égales aux entrées
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testIntValue(int value) {
        UnsignedInt ub = UnsignedInt.fromInt(value);
        assertEquals( value ,ub.intValue());
    }

    /*
    Test de la méthode LongValue, on vérifie que les valeurs retournées soient égales aux entrées converties en long
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testLongValue(int value) {
        UnsignedInt bb = UnsignedInt.fromInt(value);
        assertEquals((long) Long.valueOf(value) , bb.longValue());
    }

    /*
    Test de la méthode DoubleValue, on vérifie que les valeurs retournées soient égales aux entrées converties en double
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testDoubleValue(int value) {
        UnsignedInt bb = UnsignedInt.fromInt(value);
        assertEquals((double) Double.valueOf(value) , bb.doubleValue());
    }

    /*
    Test de la méthode FloatValue, on vérifie que les valeurs retournées soient égales aux entrées converties en float
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testFloatValue(int value) {
        UnsignedInt bb = UnsignedInt.fromInt(value);
        assertEquals((float) Float.valueOf(value) , bb.floatValue());
    }

    /*
    Test de la méthode ToString, on vérifie que les valeurs retournées soient égales aux entrées converties en string
     */
    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testToString(int value) {
        UnsignedInt bb = UnsignedInt.fromInt(value);
        assertEquals(String.valueOf(value) , bb.toString());
    }

    /*
    Test de la méthode equals
    1. On vérifie que bb, construit à partir de la valeur est égal à cc construit à partir de la meme valeur => True
    2. On vérifie que bb, construit à partir de la valeur est égal à ce même bb => True
    3. On vérifie que bb, construit à partir de la valeur est égal à null =>False
    4. On vérifie que bb, construit à partir de la valeur est égal à la valeur => False
    5. On vérifie que bb, construit à partir de la valeur est égal à un autre construit à partir d'une autre valeur => False
     */
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

    /*
    Test de la méthode compare
    1. On vérifie que bb construit à partir de 5 soit égal à un UnsignedByte construit à partir de 5 => 0
    2. On vérifie que bb construit à partir de 5 soit inférieur à un UnsignedByte construit à partir de 10 => -1
    3. On vérifie que bb construit à partir de 5 soit supérieur à un UnsignedByte construit à partir de 1 => 1
     */
    @Test
    void testCompare() {
        UnsignedInt bb = UnsignedInt.fromInt(5);
        assertAll(
                ()->assertEquals(0, bb.compareTo(UnsignedInt.fromInt(5))),
                ()->assertEquals(-1, bb.compareTo(UnsignedInt.fromInt(10))),
                ()->assertEquals(1, bb.compareTo(UnsignedInt.fromInt(1)))
        );
    }

    /*
    Test de la méthode hashCode, on vérifie que le hashcode retourné soit bien le bon
     */
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
