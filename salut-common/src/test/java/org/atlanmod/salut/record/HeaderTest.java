package org.atlanmod.salut.record;

import static org.assertj.core.api.Assertions.assertThat;
import static org.atlanmod.testing.Verifier.verifyEqualsOf;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedShort;
import org.junit.jupiter.api.Test;

public class HeaderTest {

    private String packetStr = "0000" + // Transaction ID
        "1111" + // Flags
        "0003" + // #Questions
        "0005" + // #Answers
        "0009" + // #Authority resource records
        "0001";  // #Additionals

    @Test
    void writeOn() {
        ByteArrayReader from = ByteArrayReader.fromString(packetStr);
        Header header = Header.fromByteBuffer(from);

        ByteArrayWriter to = new ByteArrayWriter();
        header.writeOn(to);

        assertTrue(Arrays.equals(from.array(), to.array()));
    }

    @Test
    void testFromByteBuffer() {
        byte[] headerArray = {0x00, 0x00, 0x00, 0x00, 0x00, 0x03,
            0x00, 0x00, 0x01, 0x00, 0x00, 0x01};

        ByteArrayReader buffer = ByteArrayReader.wrap(headerArray);
        Header header = Header.fromByteBuffer(buffer);

        assertEquals(0, header.id());
        assertEquals(UnsignedShort.fromInt(0), header.flags().toUnsignedShort());
        assertEquals(3, header.questionRecordCount());
        assertEquals(0, header.answerRecordCount());
        assertEquals(256, header.authorityRecordCount());
        assertEquals(1, header.additionalRecordCount());
    }

    @Test
    void testEqualsFromByteArray() {
        byte[] headerArray = {0x00, 0x00, 0x00, 0x00, 0x00, 0x03,
            0x00, 0x00, 0x01, 0x00, 0x00, 0x01};
        ByteArrayReader buffer = ByteArrayReader.wrap(headerArray);
        Header header = Header.fromByteBuffer(buffer);
        buffer.position(0);
        Header same = Header.fromByteBuffer(buffer);

        assertAll(
            () -> assertThat(header).isEqualTo(header),
            () -> assertThat(header).isEqualTo(same),
            () -> assertThat(header).isNotEqualTo(null)
        );
    }

    @Test
    void testEquals() {
        Object[] args1 = {UnsignedShort.fromInt(13),
            QRFlag.fromInt(1),
            UnsignedShort.fromInt(9),
            UnsignedShort.fromInt(10),
            UnsignedShort.fromInt(11),
            UnsignedShort.fromInt(12)};

        Object[] args2 = {UnsignedShort.fromInt(10),
            QRFlag.fromInt(2),
            UnsignedShort.fromInt(15),
            UnsignedShort.fromInt(21),
            UnsignedShort.fromInt(27),
            UnsignedShort.fromInt(42)};

        verifyEqualsOf(Header.class)
            .withArguments(args1)
            .andVariants(args2)
            .check();
    }


}
