package org.atlanmod.salut.mdns;

import org.apache.commons.lang3.ArrayUtils;
import org.atlanmod.salut.io.ByteArrayBuffer;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NameArrayTest {

    @Test
    void testFromByteBufferSingle() throws ParseException {
        // "mydomain.com"
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110,
                3, 99, 111, 109, 0};

        NameArray qname = NameArray.fromByteBuffer(ByteArrayBuffer.wrap(bytes));

        assertTrue(qname.size() == 2);
        assertTrue(qname.contains("mydomain"));
        assertTrue(qname.contains("com"));
    }

    @Test
    void testFromByteBufferDouble() throws ParseException {
        // "mydomain.com"
        byte[] first = {8, 109, 121, 100, 111, 109, 97, 105, 110,
                3, 99, 111, 109, 0};

        // "printer.arpanet.org"
        byte[] second = { 7, 112, 114, 105, 110, 116, 101, 114,
                7, 97, 114, 112, 97, 110, 101, 116,
                3, 111, 114, 103,
                0};

        byte[] bytes = ArrayUtils.addAll(first, second);
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        NameArray qnameFirst = NameArray.fromByteBuffer(bb);
        NameArray qnameSecond = NameArray.fromByteBuffer(bb);

        assertTrue(qnameFirst.size() == 2);
        assertTrue(qnameFirst.contains("mydomain"));
        assertTrue(qnameFirst.contains("com"));

        assertTrue(qnameSecond.size() == 3);
        assertTrue(qnameSecond.contains("printer"));
        assertTrue(qnameSecond.contains("arpanet"));
        assertTrue(qnameSecond.contains("org"));
    }


    @Test
    void testFromByteBufferWithCompression() throws ParseException {
        // "printer.arpanet.org"
        byte[] firstPart = { 7, 112, 114, 105, 110, 116, 101, 114,
                7, 97, 114, 112, 97, 110, 101, 116,
                3, 111, 114, 103,
                0};

        // "www.arpanet.org
        short pointer = (short) (8 | 0xC000);

        byte[] secondPart = { 3, 119, 119, 119,
                (byte)(pointer >> 8), (byte)(pointer), 0 };

        byte[] buffer = ArrayUtils.addAll(firstPart, secondPart);

        ByteArrayBuffer bb = ByteArrayBuffer.wrap(buffer);
        NameArray qname = NameArray.fromByteBuffer(bb, firstPart.length);

        assertTrue(qname.size() == 3);
        assertTrue(qname.contains("www"));
        assertTrue(qname.contains("arpanet"));
        assertTrue(qname.contains("org"));
    }

    @Test
    void testFromByteBufferWithLabelAndPointer() {

    }
}

// F.ISI.ARPA,
//FOO.F.ISI.ARPA, ARPA,

    /*
      +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
    20 |           1           |           F           |
       +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
    22 |           3           |           I           |
       +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
    24 |           S           |           I           |
       +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
    26 |           4           |           A           |
       +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
    28 |           R           |           P           |
       +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
    30 |           A           |           0           |
       +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+

       +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
    40 |           3           |           F           |
       +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
    42 |           O           |           O           |
       +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
    44 | 1  1|                20                       |
       +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+

       +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
    64 | 1  1|                26                       |
       +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+

       +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
    92 |           0           |                       |
       +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+

*/
