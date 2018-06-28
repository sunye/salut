package org.atlanmod.salut.io;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Objects;
import java.util.stream.Stream;

/*
Classe de test pour la classe UnsignedByte
Cette classe a pour but de faire l'ensemble des tests unitaires de UnsignedByte
 */
class UnsignedByteTest {

    /*
    Test de la méthode intValue(), on vérifie que la méhode retourne bien l'entier en entrée
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 255, UnsignedByte.MAX_VALUE})
    void testToInt(int value) {
        UnsignedByte ub = UnsignedByte.fromByte((byte) value);
        assertThat(ub.intValue()).isEqualTo(value);
    }

    /*
    Test de la méthode isLessTha
     */
    @Test
    void isLessThan() {
        UnsignedByte ub0 = UnsignedByte.fromInt(0);
        UnsignedByte ub1 = UnsignedByte.fromInt(1);

        assertThat(ub0.isLessThan(ub1)).isTrue();//On vérifie que 0<1 => true
        assertThat(ub1.isLessThan(ub0)).isFalse();//On vérifie que 1<0 => False
        assertThat(ub1.isLessThan(ub1)).isFalse();//On vérfifie que 1<1 => False
    }

    /*
    Test de la méthode isZero
     */
    @Test
    void isZero() {
        UnsignedByte ub = UnsignedByte.fromInt(0);
        UnsignedByte ub1 = UnsignedByte.fromInt(1);

        assertThat(ub.isZero()).isTrue();//on vérifie que 0 est 0 => true
        assertThat(ub1.isZero()).isFalse();//on vérifie que 1 est 0 => false
    }

    /*
    Test de la méthode joinedBytes, on vérifie que la méthode retourne ce qui est attendu en fonction de l'entrée
     */
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

        /*
    Test de la méthode FromByte, on vérifie que les valeurs retournées soient égales aux entrées
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 255, Integer.MIN_VALUE, Integer.MAX_VALUE})
    void testFromByte(int value) {
        UnsignedByte ub = UnsignedByte.fromByte((byte) value);
        assertThat(ub).isEqualTo(UnsignedByte.fromByte((byte) value));
    }

    /*
    Test de la méthode FromInteger, on vérifie que les valeurs retournées soient égales aux entrées
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testFromInteger(int value) {
        UnsignedByte ub = UnsignedByte.fromInt(value);
        assertThat(ub).isEqualTo(UnsignedByte.fromInt(value));
    }

    /*
    Test de la méthode FromShort, on vérifie que les valeurs retournées soient égales aux entrées
     */
    @ParameterizedTest
    @ValueSource(shorts = {0, 1, UnsignedByte.MAX_VALUE, UnsignedByte.MAX_VALUE -1})
    void testFromShort(short value) {
        UnsignedByte ub = UnsignedByte.fromShort(value);
        assertThat(ub.shortValue()).isEqualTo(value);
    }

    /*
    Test de la méthode FromShort, on vérifie que la méthode lance une exception pour des entrées illégales
     */
    @ParameterizedTest
    @ValueSource(shorts = {-1, 256, Short.MAX_VALUE, Short.MIN_VALUE})
    void testInvalidFromShort(short value) {
        assertThrows(IllegalArgumentException.class, () -> UnsignedByte.fromShort((short) value));
    }

    /*
    Test de la méthode FromInt, on vérifie que la méthode lance une exception pour des entrées illégales
     */
    @ParameterizedTest
    @ValueSource(ints = {-1, 256, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void testInvalidFromInt(int value) {
        assertThrows(IllegalArgumentException.class, () -> UnsignedByte.fromInt(value));
    }

    /*
    Test de la méthode LongValue, on vérifie que les valeurs retournées soient égales aux entrées
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testLongValue(int value) {
        UnsignedByte bb = UnsignedByte.fromInt(value);
        assertEquals((long) Long.valueOf(value) , bb.longValue());
    }

    /*
    Test de la méthode DoubleValue, on vérifie que les valeurs retournées soient égales aux entrées
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testDoubleValue(int value) {
        UnsignedByte bb = UnsignedByte.fromInt(value);
        assertEquals((double) Double.valueOf(value) , bb.doubleValue());
    }

    /*
    Test de la méthode FloatValue, on vérifie que les valeurs retournées soient égales aux entrées
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 255, 1, 254})
    void testFloatValue(int value) {
        UnsignedByte bb = UnsignedByte.fromInt(value);
        assertEquals((float) Float.valueOf(value) , bb.floatValue());
    }

    /*
    Test de la méthode toString, on vérifie bien que les entrées conveties en string soient bien égales à ce qui es retourné par la méthode
     */
    @ParameterizedTest
    @ValueSource( ints = {0, 255, 1, 254})
    void testToStringBis(int value) {
        UnsignedByte bb = UnsignedByte.fromInt(value);
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

    /*
    Test de la méthode hashCode, on vérifie que le hashcode retourné soit bien le bon
     */
    @Test
    void testHash() {
        UnsignedByte bb = UnsignedByte.fromInt(5);
        UnsignedByte cc = UnsignedByte.fromInt(35);

        assertAll(
                ()->assertEquals(36, bb.hashCode()),
                ()->assertEquals(66, cc.hashCode())
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
        UnsignedByte bb = UnsignedByte.fromInt(5);
        assertAll(
                () -> assertEquals(0, bb.compareTo(UnsignedByte.fromInt(5))),
                () -> assertEquals(-1, bb.compareTo(UnsignedByte.fromInt(10))),
                () -> assertEquals(1, bb.compareTo(UnsignedByte.fromInt(1)))
        );
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(ints = {-1, 256, Short.MAX_VALUE, Short.MIN_VALUE})
    /**
     * FIXME
     */
    void testInvalidFromUnsignedByte(int value) {
        assertThrows(AssertionError.class, () -> new UnsignedByte((short) value));
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
    @ValueSource(ints = {0, 1, 100, UnsignedByte.MAX_VALUE, UnsignedByte.MIN_VALUE})
    void testEquals(int value) {
        final int other = 100;
        UnsignedByte oub = UnsignedByte.fromInt(other);
        UnsignedByte ub = UnsignedByte.fromInt(value);
        assertThat(ub.equals(oub)).isEqualTo(Integer.valueOf(value).equals(Integer.valueOf(other)));
    }
    
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 100, UnsignedByte.MIN_VALUE, UnsignedByte.MAX_VALUE})
    void testCompareTo(int value) {
        final int other = 100;

        UnsignedByte ub = UnsignedByte.fromInt(value);
        UnsignedByte oub = UnsignedByte.fromInt(other);

        assertThat(ub.compareTo(oub)).isEqualTo(Integer.compare(value, other));
    }
    
    @ParameterizedTest
    @ValueSource(ints = {0, 255, UnsignedByte.MIN_VALUE, UnsignedByte.MAX_VALUE})
    void testHashCode(int value) {
        UnsignedByte ub = UnsignedByte.fromInt(value);
        assertThat(ub.hashCode()).isEqualTo(Objects.hash(value));
    }

}