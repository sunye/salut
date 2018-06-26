package org.atlanmod.salut.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ByteArrayBufferTest {

    @Test
    void wrap() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};

        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);

        byte[] baf = new ByteArrayBuffer(ByteBuffer.wrap(bytes, 0, bytes.length)).array();

        assertTrue(baf.equals(bytes));
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
    void getUnsignedShort() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};

        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        for (int i = 0; i < bb.array().length; i++) {
            UnsignedShort us1 = bb.getUnsignedShort();
            UnsignedShort us2 = UnsignedShort.fromShort(bytes[i]);
            assertTrue(us1==us2);
        }


    }

    @Test
    void getUnsignedByte() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};

        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        for (int i = 0; i < bb.array().length; i++) {
            assertTrue(bb.getUnsignedByte().equals(UnsignedByte.fromByte(bytes[i])));
        }
    }

    @Test
    void getUnsignedInteger() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};

        ByteArrayBuffer byteArrayBuffer = ByteArrayBuffer.wrap(bytes);
        try {
            List<String> labels = byteArrayBuffer.readLabels();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 1; i < byteArrayBuffer.array().length; i++) {
            assertTrue(byteArrayBuffer.getUnsignedInt()==UnsignedInt.fromInt(bytes[i]));
        }
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
    void allocate() {
        ByteArrayBuffer bb = ByteArrayBuffer.allocate(4);
        assertEquals(bb.array().length, 4);
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                ByteArrayBuffer bb = ByteArrayBuffer.allocate(-4);
            }
        });
    }

    @Test
    void fromString() {
        byte[] bytes = {5, 6, 7, 8, 9};
        ByteArrayBuffer bb = ByteArrayBuffer.fromString(bytes.toString());
        ByteArrayBuffer bb1 = bb.duplicate();
        for (int i = 0; i < bytes.length; i++) {
            assertEquals(bb1.get(), bb.get());
        }
    }

    @Test
    void duplicate() {
        byte[] bytes = {5, 6, 7, 8, 9};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        ByteArrayBuffer bb1 = bb.duplicate();
        for (int i = 0; i < bytes.length; i++) {
            assertEquals(bb1.get(), bb.get());
        }
    }


    @Test
    void readTextStrings() throws ParseException {
        byte[] bytes = {5, 6, 7, 8, 9};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        List<String> labels = bb.readLabels();
        List<String> lbb = bb.readTextStrings(2);
        assertTrue(lbb.get(0) == "5");
    }

    @Test
    void putUnsignedShort() throws ParseException {
        byte[] bytes = {2};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        bb.putUnsignedShord(new UnsignedShort(2));
        assertEquals(2, bb.get());
    }

    @Test
    void getLabelLength() {
        byte[] bytes = {5};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        short p = bb.getLabelLength().value;
        assertTrue(p == 5);
    }

    @Test
    void getUnsignedInt() {
        byte[] bytes = {5};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        assertTrue(bb.getUnsignedInt().intValue() ==  UnsignedInt.fromInt(5).intValue());
    }

    @Test
    void toStringTest() {
        byte[] bytes = {5};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        bb.toString().equals(ByteBuffer.wrap(bytes, 0, bytes.length).toString());
    }
}