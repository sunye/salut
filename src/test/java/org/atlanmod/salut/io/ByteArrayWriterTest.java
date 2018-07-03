package org.atlanmod.salut.io;

import org.atlanmod.salut.mdns.NameArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

class ByteArrayWriterTest {

    private ByteArrayWriter writer;

    @BeforeEach
    void setup() {
        this.writer = new ByteArrayWriter();
    }

    @ParameterizedTest
    @ValueSource(shorts = {0,1,255, 0xC0, 0xFF, 0x10})
    void testPutUnsignedByte(short value) {
        UnsignedByte writtenValue = UnsignedByte.fromShort(value);
        writer.putUnsignedByte(writtenValue);
        byte[] elements = writer.array();
        ByteArrayReader reader = ByteArrayReader.wrap(elements);
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
        ByteArrayReader reader = ByteArrayReader.wrap(elements);
        UnsignedShort readValue = reader.getUnsignedShort();
        assertThat(writtenValue).isEqualTo(readValue);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 0xFFFFFFFFL, 0x0F0C0000L, 0xF0F0F0F0L, 0xF0000000L, 0x0F000000L, 0x00F00000L, 0x000F0000L, 0x10000000L})
    void testPutUnsignedInt(long value) {
        UnsignedInt writtenValue = UnsignedInt.fromLong(value);
        writer.putUnsignedInt(writtenValue);

        ByteArrayReader reader = writer.getByteArrayReader();
        UnsignedInt readValue = reader.getUnsignedInt();
        assertThat(writtenValue).isEqualTo(readValue);
    }

    @Test
    void testSucceedingPuts() throws ParseException {
        writer.putUnsignedByte(UnsignedByte.fromInt(0xF))
                .putUnsignedShort(UnsignedShort.fromInt(0xBB))
                .putUnsignedInt(UnsignedInt.fromInt(0x1111))
                .putLabel("a label")
                .putNameArray(NameArray.fromList("pc", "local"))
                .putUnsignedByte(UnsignedByte.fromInt(0x0));

        ByteArrayReader reader = writer.getByteArrayReader();

        assertThat(reader.getUnsignedByte()).isEqualTo(UnsignedByte.fromInt(0xF));
        assertThat(reader.getUnsignedShort()).isEqualTo(UnsignedShort.fromInt(0xBB));
        assertThat(reader.getUnsignedInt()).isEqualTo(UnsignedInt.fromInt(0x1111));
        assertThat(reader.getLabel(reader.getLabelLength())).isEqualTo("a label");
        assertThat(reader.readLabels()).isEqualTo(NameArray.fromList("pc", "local"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"mac book pro", "aa aa", })
    void testPutLabel(String label) {
        writer.putLabel(label);
        ByteArrayReader reader = writer.getByteArrayReader();
        LabelLength length = reader.getLabelLength();
        String readLabel = reader.getLabel(length);

        assertThat(readLabel).isEqualTo(label);
    }

    @Test
    void testPutNameArray() throws ParseException {
        NameArray names = NameArray.fromList("Itunes", "MacBook", "local");
        writer.putNameArray(names);
        ByteArrayReader reader = writer.getByteArrayReader();

        assertThat(reader.readLabels()).isEqualTo(names);
    }
}