package org.atlanmod.salut.io;

import org.atlanmod.salut.mdns.NameArray;
import org.junit.jupiter.api.Test;

import java.nio.BufferUnderflowException;
import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
    void testReadLabelsKO() throws ParseException {
        byte[] bytes = {
                8, 109, 121, 100, 111, 109, 97, 105, 110, 3,
                8, 109, 121, 100, 111, 109, 97, 105, 110, 3,
                8, 109, 121, 100, 111, 109, 97, 105, 110, 3,
                8, 109, 121, 100, 111, 109, 97, 105, 110, 3,
                8, 109, 121, 100, 111, 109, 97, 105, 110, 3,
                8, 109, 121, 100, 111, 109, 97, 105, 110, 3,
                8, 109, 121, 100, 111, 109, 97, 105, 110, 3,
                8, 109, 121, 100, 111, 109, 97, 105, 110, 3};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        assertThrows(ParseException.class, ()->{bb.readLabels();});
    }

    @Test
    void testReadTextString() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        List<String> strings = bb.readTextStrings(10);
        assertTrue(strings.contains("mydomain"));
        assertTrue(strings.contains("com"));
    }

    @Test
    void testGetUnsignedShort() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        assertThat(bb.getUnsignedShort()).isEqualTo(UnsignedShort.fromShort((short) 2157));
    }

    @Test
    void testGetUnsignedByte() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        assertThat(bb.getUnsignedByte()).isEqualTo(UnsignedByte.fromInt(8));
    }

    @Test
    void getGetUnsignedByte1() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        assertThat(bb.getUnsignedByte(0)).isEqualTo(UnsignedByte.fromInt(8));
    }

    @Test
    void getUnsignedInteger() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        assertThat(bb.getUnsignedInt()).isEqualTo(UnsignedInt.fromInt(141392228));
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
        byte[] name = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(name);
        bb.position(10);
        assertEquals(bb.position(), 10);
    }

    @Test
    void get() {
        byte[] bytes = {5};
        assertEquals(5, ByteArrayBuffer.wrap(bytes).get());
    }

    @Test
    void getParamExecpetion() {
        byte[] bytes = {5};
        byte[] bytes2 = {2, 3};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        assertThrows(BufferUnderflowException.class, ()->{bb.get(bytes2);});
    }

    @Test
    void testCheckOffset(){
        byte[] buffer = { 8, 3, 100, 101, 102, -64, 64, 0 };
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(buffer);
        assertThrows(ParseException.class, ()->{NameArray.fromByteBuffer(bb, 1);});
    }

    @Test
    void testToSring(){
        byte[] bytes = {8, 109, 121, 100};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        assertEquals("java.nio.HeapByteBuffer[pos=0 lim=4 cap=4]", bb.toString());
    }

    @Test
    void testToSringVide(){
        byte[] bytes = {};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        assertEquals("java.nio.HeapByteBuffer[pos=0 lim=0 cap=0]", bb.toString());
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
    void testAllocateVide(){
        byte[] bytes = {};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        assertEquals(bb.toString(), ByteArrayBuffer.allocate(0).toString());
    }

    @Test
    void testAllocate(){
        byte[] bytes = {8,8,8,8,8};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        assertEquals(bb.toString(), ByteArrayBuffer.allocate(5).toString());
    }

    @Test
    void testPutUnsignedShort(){
        byte[] bytes = {5, 6, 7, 8, 9};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        UnsignedShort unsignedShort = new UnsignedShort(2);
        bb.putUnsignedShord(unsignedShort);
        assertEquals(7,bb.get());

    }

    @Test
    void testFromString() throws ParseException {
        byte[] bytes = {-17, -49, -6, -17};
        ByteArrayBuffer bbString = ByteArrayBuffer.fromString("mydomain");
        byte[] bytes2 = bbString.array();
        for (int i = 0; i<4; i++) {
            assertEquals(bytes[i], bytes2[i]);
        }
    }

}