package org.atlanmod.salut.io;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class LabelLengthTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 64, 32})
    void testIsValidNameLength(int value) {
        LabelLength ub = LabelLength.fromInt(value);
        assertThat(ub.isValidNameLength()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 65, 124})
    void testIsNotValidNameLength(int value) {
        LabelLength ub = LabelLength.fromInt(value);
        assertThat(ub.isValidNameLength()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"11000000", "11111111"})
    void testIsPointer(String value) {
        int pointer = Integer.parseInt(value, 2);
        LabelLength ub = LabelLength.fromInt(pointer);

        assertThat(ub.isPointer()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"00000000", "00111111", "10101010", "01010101"})
    void testNotPointer(String value) {
        int pointer = Integer.parseInt(value, 2);
        LabelLength ub = LabelLength.fromInt(pointer);

        assertThat(ub.isPointer()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"01000000", "01111111"})
    void testIsExtended(String value) {
        int extended = Integer.parseInt(value, 2);
        LabelLength ub = LabelLength.fromInt(extended);

        assertThat(ub.isExtended()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"00000000", "10111111", "11101010"})
    void testIsNotExtended(String value) {
        int extended = Integer.parseInt(value, 2);
        LabelLength ub = LabelLength.fromInt(extended);

        assertThat(ub.isExtended()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"10000000", "10111111"})
    void testIsUnknown(String value) {
        int extended = Integer.parseInt(value, 2);
        LabelLength ub = LabelLength.fromInt(extended);

        assertThat(ub.isUnknown()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"01000000", "01111111", "11000000"})
    void testIsNotUnknown(String value) {
        int extended = Integer.parseInt(value, 2);
        LabelLength ub = LabelLength.fromInt(extended);

        assertThat(ub.isUnknown()).isFalse();
    }


}