package org.atlanmod.salut.labels;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LabelTest {

    @Test
    void equals()  {
        DNSLabel one = DNSLabel.create("abcdefg-hijklmnop");
        DNSLabel other = DNSLabel.create("abcdefg-"+ "hijklmnop");

        assertThat(one).isEqualTo(other);

    }

    @Test
    void notEquals() throws Exception {
        DNSLabel one = DNSLabel.create("abcdefg-hijklmnop");
        DNSLabel other = DNSLabel.create("123456789");

        assertThat(one).isNotEqualTo(other);

    }


    @ParameterizedTest
    @ValueSource(strings = {"a b", "=/*-+", "aaa.bbb"})
    void create(String value) throws Exception {
        DNSLabel newLabel = DNSLabel.create(value);
        assertThat(newLabel.isValid()).isFalse();
    }

    @Test
    void labelTooLong() throws Exception {
        char[] bytes = new char[DNSLabel.MAX_LENGTH + 1];
        Arrays.fill(bytes, 'a');
        String str = new String(bytes);
        assertThrows(RuntimeException.class, () -> DNSLabel.create(str));

    }
}
