package org.atlanmod.salut.io;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/*
Classe de test pour la classe LabelLength
Cette classe a pour but de faire l'ensemble des tests unitaires de LabelLength
 */
class LabelLengthTest {

    /*
    Test de la méthode IsValidNameLength, on vérifie qu'un nombre acceptable fasse retourner true
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 64, 32})
    void testIsValidNameLength(int value) {
        LabelLength ub = LabelLength.fromInt(value);
        assertThat(ub.isValidNameLength()).isTrue();
    }

    /*
    Test de la méthode IsValidNameLength, on vérifie qu'un nombre non acceptable fasse retourner false
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 65, 124})
    void testIsNotValidNameLength(int value) {
        LabelLength ub = LabelLength.fromInt(value);
        assertThat(ub.isValidNameLength()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, LabelLength.MAX_VALUE + 1})
    void testInvalidFromInt(int value) {
        assertThrows(IllegalArgumentException.class, () -> LabelLength.fromInt(value));
    }

    /*
    Test de la méthode IsPointer, on vérifie qu'avec des données acceptables, la méthode retourne true
     */
    @ParameterizedTest
    @ValueSource(strings = {"11000000", "11111111"})
    void testIsPointer(String value) {
        int pointer = Integer.parseInt(value, 2);
        LabelLength ub = LabelLength.fromInt(pointer);

        assertThat(ub.isPointer()).isTrue();
    }

    /*
    Test de la méthode IsPointer, on vérifie qu'avec des données non acceptables, la méthode retourne false
     */
    @ParameterizedTest
    @ValueSource(strings = {"00000000", "00111111", "10101010", "01010101"})
    void testNotPointer(String value) {
        int pointer = Integer.parseInt(value, 2);
        LabelLength ub = LabelLength.fromInt(pointer);

        assertThat(ub.isPointer()).isFalse();
    }

    /*
    Test de la méthode IsExtended , on vérifie qu'avec des données acceptables, la méthode retourne true
     */
    @ParameterizedTest
    @ValueSource(strings = {"01000000", "01111111"})
    void testIsExtended(String value) {
        int extended = Integer.parseInt(value, 2);
        LabelLength ub = LabelLength.fromInt(extended);

        assertThat(ub.isExtended()).isTrue();
    }

    /*
    Test de la méthode IsExtended , on vérifie qu'avec des données non acceptables, la méthode retourne false
     */
    @ParameterizedTest
    @ValueSource(strings = {"00000000", "10111111", "11101010"})
    void testIsNotExtended(String value) {
        int extended = Integer.parseInt(value, 2);
        LabelLength ub = LabelLength.fromInt(extended);

        assertThat(ub.isExtended()).isFalse();
    }

    /*
    Test de la méthode IsUnknown , on vérifie qu'avec des données acceptables, la méthode retourne true
     */
    @ParameterizedTest
    @ValueSource(strings = {"10000000", "10111111"})
    void testIsUnknown(String value) {
        int extended = Integer.parseInt(value, 2);
        LabelLength ub = LabelLength.fromInt(extended);

        assertThat(ub.isUnknown()).isTrue();
    }

    /*
    Test de la méthode IsUnknown , on vérifie qu'avec des données non acceptables, la méthode retourne false
     */
    @ParameterizedTest
    @ValueSource(strings = {"01000000", "01111111", "11000000"})
    void testIsNotUnknown(String value) {
        int extended = Integer.parseInt(value, 2);
        LabelLength ub = LabelLength.fromInt(extended);

        assertThat(ub.isUnknown()).isFalse();
    }


}