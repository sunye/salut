package org.atlanmod.salut.io;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
Classe de test pour la classe ByteArrayBuffer
Cette classe a pour but de faire l'ensemble des tests unitaires de ByteArrayBuffer
 */
class ByteArrayBufferTest {

    /*
        Test de la méthode ReadLabels
     */
    @Test
    void testReadLabels() throws ParseException {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};

        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        List<String> labels = bb.readLabels();

        assertTrue(labels.size() == 2);
        assertTrue(labels.contains("mydomain"));
        assertTrue(labels.contains("com"));
    }

    /*
    Test de la méthode ReadLabels en provoquant une exception
     */
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

    /*
    Test de la méthode array, on vérifie si le tableau retourné est bien égal au tableau retourné par la méthode .array()
     */
    @Test
    void array() {
        byte[] name = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(name);
        assertEquals(name, bb.array());
    }

    /*
    Test de la méthode position, on vérifie si le buffer est bien initialement à la position 0
     */
    @Test
    void position() {
        byte[] name = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(name);
        assertEquals(bb.position(), 0);
    }

    /*
    Test de la méthode Get, on vérifie si l'élément retourné est bien celui inséré.
     */
    @Test
    void get() {
        byte[] bytes = {5};

        assertEquals(5, ByteArrayBuffer.wrap(bytes).get());
    }

    /*
    Test de la méthode get, on vérifie que chaque élément est bien à la bonne position
     */
    @Test
    void testGetAtPosition() {
        byte[] bytes = {5, 6, 7, 8, 9};
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
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
        ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
        assertEquals("java.nio.HeapByteBuffer[pos=0 lim=5 cap=5]", bb.toString());
    }

    /*
    Test de readLabels, on vérifie que le cas d'erreur se produise bien
     */
    @Test
    void testCheckOut()  {
        assertAll(
                ()->assertThrows(ParseException.class, ()-> {
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


                    ByteArrayBuffer bb = ByteArrayBuffer.wrap(bytes);
                    List<String> labels = bb.readLabels();
                }));
    }

}