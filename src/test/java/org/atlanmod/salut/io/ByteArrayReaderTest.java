package org.atlanmod.salut.io;

import org.atlanmod.salut.mdns.NameArray;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.nio.ByteBuffer;
import java.nio.BufferUnderflowException;
import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/*
Classe de test pour la classe ByteArrayReader
Cette classe a pour but de faire l'ensemble des tests unitaires de ByteArrayReader
 */
class ByteArrayReaderTest {

    @Test
    void wrap() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};

        ByteArrayReader bb = ByteArrayReader.wrap(bytes);

        byte[] baf = new ByteArrayReader(ByteBuffer.wrap(bytes, 0, bytes.length)).array();

        assertTrue(baf.equals(bytes));
    }

    /*
        Test de la méthode ReadLabels
     */

    @Test
    void testReadLabels() throws ParseException {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};

        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        List<String> labels = bb.readLabels().getNames();

        assertTrue(labels.size() == 2);
        assertTrue(labels.contains("mydomain"));
        assertTrue(labels.contains("com"));
    }

    /*
    Test de la méthode ReadLabels en provoquant une exception
     */
    @Test
    void testReadLabelsException() {

        assertAll(
                () -> assertThrows(ParseException.class,
                        () -> {
                            byte[] bytes = {3, 12, 34, 56,
                                    65, 109, 121, 100, 111, 109, 97, 105, 110,
                                    109, 121, 100, 111, 109, 97, 105, 110,
                                    109, 121, 100, 111, 109, 97, 105, 110,
                                    109, 121, 100, 111, 109, 97, 105, 110,
                                    109, 121, 100, 111, 109, 97, 105, 110,
                                    109, 121, 100, 111, 109, 97, 105, 110,
                                    109, 121, 100, 111, 109, 97, 105, 110,
                                    109, 121, 100, 111, 109, 97, 105, 110, 64};

                            ByteArrayReader bb = ByteArrayReader.wrap(bytes);
                            NameArray labels = bb.readLabels();
                        }),
                () -> assertThrows(ParseException.class,
                        () -> {
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

                            ByteArrayReader bb = ByteArrayReader.wrap(bytes);
                            NameArray labels = bb.readLabels();
                        })
        );
    }

    @Disabled
    @Test
    void getUnsignedShort() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};

        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        for (int i = 0; i < bb.array().length; i++) {
            UnsignedShort us1 = bb.getUnsignedShort();
            UnsignedShort us2 = UnsignedShort.fromShort(bytes[i]);
            assertTrue(us1==us2);
        }


    }

    @Test
    void getUnsignedByte() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};

        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        for (int i = 0; i < bb.array().length; i++) {
            assertTrue(bb.getUnsignedByte().equals(UnsignedByte.fromByte(bytes[i])));
        }

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
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        assertThrows(ParseException.class, ()->{bb.readLabels();});
    }

    @Test
    void testReadTextString() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        List<String> strings = bb.readTextStrings(10);
        assertTrue(strings.contains("mydomain"));
        assertTrue(strings.contains("com"));
    }

    @Test
    void testGetUnsignedShort() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        assertThat(bb.getUnsignedShort()).isEqualTo(UnsignedShort.fromShort((short) 2157));
    }

    @Test
    void testGetUnsignedByte() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        assertThat(bb.getUnsignedByte()).isEqualTo(UnsignedByte.fromInt(8));
    }

    @Test
    void getGetUnsignedByte1() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        assertThat(bb.getUnsignedByte(0)).isEqualTo(UnsignedByte.fromInt(8));
    }

    @Test
    void getUnsignedInteger() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};

        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        assertThat(bb.getUnsignedInt()).isEqualTo(UnsignedInt.fromInt(141392228));
    }

    /*
    Test de la méthode array, on vérifie si le tableau retourné est bien égal au tableau retourné par la méthode .array()
     */
    @Test
    void array() {
        byte[] name = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayReader bb = ByteArrayReader.wrap(name);
        assertEquals(name, bb.array());
    }

    /*
    Test de la méthode position, on vérifie si le buffer est bien initialement à la position 0
     */
    @Test
    void position() {
        byte[] name = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayReader bb = ByteArrayReader.wrap(name);
        assertEquals(bb.position(), 0);
    }

    @Test
    void position1() {
        byte[] name = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayReader bb = ByteArrayReader.wrap(name);
        bb.position(10);
        assertEquals(bb.position(), 10);
    }


    /*
    Test de la méthode Get, on vérifie si l'élément retourné est bien celui inséré.
     */
    @Test
    void get() {
        byte[] bytes = {5};
        assertEquals(5, ByteArrayReader.wrap(bytes).get());
    }

    /*
    Test de la méthode get, on vérifie que chaque élément est bien à la bonne position
     */
    @Test
    void getParamExecpetion() {
        byte[] bytes = {5};
        byte[] bytes2 = {2, 3};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        assertThrows(BufferUnderflowException.class, ()->{bb.get(bytes2);});
    }

    @Test
    void testCheckOffset(){
        byte[] buffer = { 8, 3, 100, 101, 102, -64, 64, 0 };
        ByteArrayReader bb = ByteArrayReader.wrap(buffer);
        assertThrows(ParseException.class, ()->{NameArray.fromByteBuffer(bb, 1);});
    }

    @Test
    void testToSring(){
        byte[] bytes = {8, 109, 121, 100};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        assertEquals("java.nio.HeapByteBuffer[pos=0 lim=4 cap=4]", bb.toString());
    }

    @Test
    void testToSringVide(){
        byte[] bytes = {};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        assertEquals("java.nio.HeapByteBuffer[pos=0 lim=0 cap=0]", bb.toString());
    }

    @Test
    void testGetAtPosition() {
        byte[] bytes = {5, 6, 7, 8, 9};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        for (int i = 0; i < bytes.length; i++) {
            assertEquals(bytes[i], bb.get());
        }
    }

    /*
    Test de la méthode toString(), on vérifie que la chaine de caractère retournée est celle attendue
     */
    @Test
    void testToString(){
        byte[] bytes = {5, 6, 7, 8, 9};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        assertEquals("java.nio.HeapByteBuffer[pos=0 lim=5 cap=5]", bb.toString());
    }

    /*
    Test de readLabels, on vérifie que le cas d'erreur se produise bien
     */
    @Test
    void testCheckOut() {
        assertAll(
                () -> assertThrows(ParseException.class, () -> {
                    byte[] bytes = {3, 12, 34, 56,
                            (byte) 192, 109, 121, 100, 111, 109, 97, 105, 110,
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
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110,
                            109, 121, 100, 111, 109, 97, 105, 110, 64};


                    ByteArrayReader bb = ByteArrayReader.wrap(bytes);
                    NameArray labels = bb.readLabels();
                }));

    }

    @Test
    void allocate() {
        ByteArrayReader bb = ByteArrayReader.allocate(4);
        assertEquals(bb.array().length, 4);
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                ByteArrayReader bb = ByteArrayReader.allocate(-4);
            }
        });
    }

    @Disabled
    @Test
    void fromString() {
        byte[] bytes = {5, 6, 7, 8, 9};
        ByteArrayReader bb = ByteArrayReader.fromString(bytes.toString());
        ByteArrayReader bb1 = bb.duplicate();
        for (int i = 0; i < bytes.length; i++) {
            assertEquals(bb1.get(), bb.get());
        }
    }

    @Test
    void duplicate() {
        byte[] bytes = {5, 6, 7, 8, 9};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        ByteArrayReader bb1 = bb.duplicate();
        for (int i = 0; i < bytes.length; i++) {
            assertEquals(bb1.get(), bb.get());
        }
    }


    @Disabled
    @Test
    void readTextStrings() throws ParseException {
        byte[] bytes = {5, 6, 7, 8, 9};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        NameArray labels = bb.readLabels();
        List<String> lbb = bb.readTextStrings(2);
        assertTrue(lbb.get(0) == "5");
    }

    @Test
    @Disabled
    void putUnsignedShort() throws ParseException {
        byte[] bytes = {2};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        bb.putUnsignedShort(new UnsignedShort(2));
        assertEquals(2, bb.get());
    }

    @Test
    void getLabelLength() {
        byte[] bytes = {5};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);

        LabelLength read = bb.getLabelLength();
        assertThat(read).isEqualTo(LabelLength.fromInt(5));
    }

    @Disabled
    @Test
    /**
     * FIXME: an unsigned int requires 4 bytes!
     */
    void getUnsignedInt() {

        byte[] bytes = {5};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        assertThat(bb.getUnsignedInt()).isEqualTo(UnsignedInt.fromInt(5));
    }

    @Test
    void toStringTest() {
        byte[] bytes = {5};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        bb.toString().equals(ByteBuffer.wrap(bytes, 0, bytes.length).toString());
    }

    @Test
    void testAllocateVide(){
        byte[] bytes = {};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        assertEquals(bb.toString(), ByteArrayReader.allocate(0).toString());
    }

    @Test
    void testAllocate(){
        byte[] bytes = {8,8,8,8,8};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        assertEquals(bb.toString(), ByteArrayReader.allocate(5).toString());
    }

    @Test
    void testPutUnsignedShort(){
        byte[] bytes = {5, 6, 7, 8, 9};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        UnsignedShort unsignedShort = new UnsignedShort(2);
        bb.putUnsignedShort(unsignedShort);
        assertEquals(7,bb.get());

    }

    @Test
    void testFromString() throws ParseException {
        byte[] bytes = {-17, -49, -6, -17};
        ByteArrayReader bbString = ByteArrayReader.fromString("mydomain");
        byte[] bytes2 = bbString.array();
        for (int i = 0; i<4; i++) {
            assertEquals(bytes[i], bytes2[i]);
        }
    }

}