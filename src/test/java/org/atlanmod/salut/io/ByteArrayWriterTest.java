package org.atlanmod.salut.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ByteArrayWriterTest {

    private ByteArrayWriter writer;

    @BeforeEach
    void setup() {
        this.writer = new ByteArrayWriter();
    }

    @ParameterizedTest
    @ValueSource(shorts = {0,1,255})
    void testPutUnsignedByte(short value) {
        UnsignedByte writtenValue = UnsignedByte.fromShort(value);
        writer.putUnsignedByte(writtenValue);
        byte[] elements = writer.array();
        ByteArrayBuffer reader = ByteArrayBuffer.wrap(elements);
        UnsignedByte readValue = reader.getUnsignedByte();
        assertThat(elements.length).isEqualTo(1);
        assertThat(readValue).isEqualTo(writtenValue);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,255, 0xFFFF})
    void testPutUnsignedShort(int value) {
        UnsignedShort writtenValue = UnsignedShort.fromInt(value);
        writer.putUnsignedShort(writtenValue);
        byte[] elements = writer.array();
        ByteArrayBuffer reader = ByteArrayBuffer.wrap(elements);
        UnsignedShort readValue = reader.getUnsignedShort();
        assertThat(writtenValue).isEqualTo(readValue);
    }

    @ParameterizedTest
    @ValueSource(longs = {1})
    void testPutUnsignedInt(long value) {
        UnsignedInt writtenValue = UnsignedInt.fromLong(1);
        writer.putUnsignedInt(writtenValue);
        byte[] elements = writer.array();
        ByteArrayBuffer reader = ByteArrayBuffer.wrap(elements);
        UnsignedInt readValue = reader.getUnsignedInt();
        assertThat(writtenValue).isEqualTo(readValue);
    }

    @Test
    void array() {
    }
}