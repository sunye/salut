package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.ByteArrayBuffer;
import org.atlanmod.salut.io.UnsignedShort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class HeaderTest {

    private String packetStr = "0000" + // Transaction ID
            "1111" + // Flags
            "0003" + // #Questions
            "0005" + // #Answers
            "0009" + // #Authority resource records
            "0001";  // #Additionals

    @Test
    void writeOn() {
        ByteArrayBuffer from = ByteArrayBuffer.fromString(packetStr);
        Header header = Header.fromByteBuffer(from);

        ByteArrayBuffer to = ByteArrayBuffer.allocate(12);
        header.writeOn(to);

        assertTrue(Arrays.equals(from.array(), to.array()));
    }

    @Test
    void testFromByteBuffer() {
        byte[] headerArray = {0x00, 0x00, 0x00, 0x00, 0x00, 0x03,
                0x00, 0x00, 0x01, 0x00, 0x00, 0x01};

        ByteArrayBuffer buffer = ByteArrayBuffer.wrap(headerArray);
        Header header = Header.fromByteBuffer(buffer);

        assertEquals(0, header.id());
        assertEquals(new UnsignedShort(0), header.flags().toUnsignedShort());
        assertEquals(3, header.questionRecordCount());
        assertEquals(0, header.answerRecordCount());
        assertEquals(256, header.authorityRecordCount());
        assertEquals(1, header.additionalRecordCount());

    }
}