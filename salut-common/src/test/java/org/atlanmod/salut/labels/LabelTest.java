package org.atlanmod.salut.labels;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LabelTest {

    @Test
    void equals()  {
        Label one = Label.create("abcdefg-hijklmnop");
        Label other = Label.create("abcdefg-"+ "hijklmnop");

        assertThat(one).isEqualTo(other);

    }

    @Test
    void notEquals() throws Exception {
        Label one = Label.create("abcdefg-hijklmnop");
        Label other = Label.create("123456789");

        assertThat(one).isNotEqualTo(other);

    }


    @ParameterizedTest
    @ValueSource(strings = {"a b", "=/*-+", "aaa.bbb"})
    void create(String value) throws Exception {
        Label newLabel = Label.create(value);
        assertThat(newLabel.isValidDNSLabel()).isFalse();
    }

    @Test
    void labelTooLong() throws Exception {
        char[] bytes = new char[Label.MAX_LENGTH + 1];
        Arrays.fill(bytes, 'a');
        String str = new String(bytes);
        assertThrows(RuntimeException.class, () -> Label.create(str));

    }
}