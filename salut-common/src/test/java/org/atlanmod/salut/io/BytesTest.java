package org.atlanmod.salut.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BytesTest {

    @Test
    void stringToBytes() {
        String str = "0000" + "1111" + "0003" + "0005" + "0009" + "0001" + "0f0f";
        byte[] expected = {0x00, 0x00, 0x11, 0x11, 0x00, 0x03, 0x00, 0x05, 0x00, 0x09, 0x00, 0x01,
        0x0F, 0x0F};
        byte[] actual = Bytes.stringToBytes(str);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testShortsToBytes() {
        short[] shorts = {0,  Short.MAX_VALUE};
        byte[] bytes = {0, 0, 127, -1};

        assertThat(bytes).isEqualTo(Bytes.shortsToBytes(shorts));
    }

    @Test
    void testBytesToBoxedList() {
        byte[] bytes = {0, 1, Byte.MIN_VALUE, Byte.MAX_VALUE};
        List<Byte> expected = new ArrayList<>();
        for(byte each: bytes) {
            expected.add(each);
        }

        assertThat(Bytes.bytesToBoxedList(bytes)).isEqualTo(expected);

    }
}
