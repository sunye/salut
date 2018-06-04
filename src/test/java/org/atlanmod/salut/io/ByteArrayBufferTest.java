package org.atlanmod.salut.io;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ByteArrayBufferTest {

    @Test
    void wrap() {
    }

    @Test
    void testReadLabels() throws ParseException {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};

        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        List<String> labels = bb.readLabels();

        assertTrue(labels.size() == 2);
        assertTrue(labels.contains("mydomain"));
        assertTrue(labels.contains("com"));
    }

    @Test
    void testReadLabelsException()  {

        assertAll(
            ()->assertThrows(ParseException.class,
                ()->{
                    byte[] bytes = {3, 12, 34, 56,
                            65, 109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110, 64};

                    ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
                    List<String> labels = bb.readLabels();
            }),
            ()->assertThrows(ParseException.class,
                ()->{
                    byte[] bytes = {3, 12, 34, 56,
                            (byte) 128, 109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110, 64};

                    ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
                    List<String> labels = bb.readLabels();
                })
        );



    }


    @Test
    void getUnsignedShort() {
    }

    @Test
    void getUnsignedByte() {
    }

    @Test
    void getUnsignedByte1() {
    }

    @Test
    void getUnsignedInteger() {
    }

    @Test
    void array() {
        byte[] name = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(name);
        assertEquals(name, bb.array());
    }

    @Test
    void position() {
        byte[] name = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(name);
        assertEquals(bb.position(), 0);
    }

    @Test
    void position1() {
    }

    @Test
    void get() {
        byte[] bytes = {5};

        assertEquals(5, ByteArrayBuffer.wrap(bytes).get());
    }

    @Test
    void testGetAtPosition() {
        byte[] bytes = {5, 6, 7, 8, 9};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        for (int i = 0; i < bytes.length; i++) {
            assertEquals(bytes[i], bb.get());
        }
    }

    @Test
    void testToString(){
        byte[] bytes = {5, 6, 7, 8, 9};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        assertEquals("java.nio.HeapByteBuffer[pos=0 lim=5 cap=5]", bb.toString());
    }

}