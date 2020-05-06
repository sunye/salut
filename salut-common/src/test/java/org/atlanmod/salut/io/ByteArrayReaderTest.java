package org.atlanmod.salut.io;

import org.atlanmod.salut.labels.DNSLabel;
import org.atlanmod.salut.labels.Label;
import org.atlanmod.salut.labels.Labels;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class ByteArrayReaderTest {

    private Label mydomain = DNSLabel.create("mydomain");
    private Label com = DNSLabel.create("com");

    @Test
    void wrap() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};

        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        byte[] baf = new ByteArrayReader(ByteBuffer.wrap(bytes, 0, bytes.length)).array();

        assertTrue(baf.equals(bytes));
    }

    @Test
    void testReadLabels() throws ParseException {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};

        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        List<Label> labels = bb.readLabels().getLabels();

        assertTrue(labels.size() == 2);
        assertTrue(labels.contains(mydomain));
        assertTrue(labels.contains(com));
    }

    @Test
    void testReadLabelsException() throws Exception {

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
                            Labels labels = bb.readLabels();
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
                            Labels labels = bb.readLabels();
                        })
        );
    }

    @Test
    void getUnsignedShort() {
        short[] shorts = {1, 0, 121, 100, 111, 109, 6439, 110, 3, 99, 111, 109, Short.MIN_VALUE, Short.MAX_VALUE};
        byte[] bytes = Bytes.shortsToBytes(shorts);
        UnsignedShort[] actual = new UnsignedShort[shorts.length];
        UnsignedShort[] expected = new UnsignedShort[shorts.length];
        for (int i = 0; i < shorts.length; i++) {
            expected[i] = UnsignedShort.fromShort(shorts[i]);
        }

        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        for (int i = 0; i < shorts.length; i++) {
            actual[i] = bb.getUnsignedShort();
        }
        assertThat(actual).isEqualTo(expected);
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
    void testReadLabelsKO() throws Exception {
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
        assertThrows(ParseException.class, () -> {
            bb.readLabels();
        });
    }

    @Test
    void testReadTextString() {
        byte[] bytes = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        List<String> strings = bb.readTextDataStrings(10);
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

    @Test
    void array() {
        byte[] name = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayReader bb = ByteArrayReader.wrap(name);
        assertEquals(name, bb.array());
    }

    @Test
    void position() {
        byte[] name = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayReader bb = ByteArrayReader.wrap(name);

        assertThat(bb.position()).isZero();
    }

    @Test
    void position1() {
        byte[] name = {8, 109, 121, 100, 111, 109, 97, 105, 110, 3, 99, 111, 109, 0};
        ByteArrayReader bb = ByteArrayReader.wrap(name);
        bb.position(10);
        assertThat(bb.position()).isEqualTo(10);
    }

    @Test
    void get() {
        byte[] bytes = {5};
        ByteArrayReader reader = ByteArrayReader.wrap(bytes);

        assertThat(reader.get()).isEqualTo(5);
    }

    @Test
    void getParamExecpetion() {
        byte[] bytes = {5};
        byte[] bytes2 = {2, 3};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        assertThrows(BufferUnderflowException.class, () -> {
            bb.get(bytes2);
        });
    }

    @Test
    void testCheckOffset() {
        byte[] buffer = {8, 3, 100, 101, 102, -64, 64, 0};
        ByteArrayReader bb = ByteArrayReader.wrap(buffer);
        assertThrows(ParseException.class, () -> {
            Labels.fromByteBuffer(bb, 1);
        });
    }

    @Test
    void testToSring() {
        byte[] bytes = {8, 109, 121, 100};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        assertEquals("java.nio.HeapByteBuffer[pos=0 lim=4 cap=4]", bb.toString());
    }

    @Test
    void testToSringVide() {
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

    @Test
    void testToString() {
        byte[] bytes = {5, 6, 7, 8, 9};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        assertEquals("java.nio.HeapByteBuffer[pos=0 lim=5 cap=5]", bb.toString());
    }

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
                    Labels labels = bb.readLabels();
                }));

    }

    @Test
    void allocate() {
        ByteArrayReader bb = ByteArrayReader.allocate(4);

        assertThat(bb.array()).hasSize(4);
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                ByteArrayReader bb = ByteArrayReader.allocate(-4);
            }
        });
    }

    @Test
    void fromString() {
        ByteArrayReader reader = ByteArrayReader.fromString("00" + "10" + "02");
        assertThat(reader.get() == 0).isTrue();
        assertThat(reader.get() == 0x10).isTrue();
        assertThat(reader.get()).isEqualTo((byte) (2));
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


    @Test
    void getLabelLength() {
        byte[] bytes = {5};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);

        LabelLength read = bb.getLabelLength();
        assertThat(read).isEqualTo(LabelLength.fromInt(5));
    }

    @Test
    void getUnsignedInt() {

        byte[] bytes = {0, 0, 0, 5};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        assertThat(bb.getUnsignedInt()).isEqualTo(UnsignedInt.fromInt(5));
    }

    @Test
    void toStringTest() {
        byte[] bytes = {5};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);

        assertThat(bb.toString()).isEqualTo(ByteBuffer.wrap(bytes, 0, bytes.length).toString());
    }

    @Test
    void testAllocateVide() {
        byte[] bytes = {};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        assertEquals(bb.toString(), ByteArrayReader.allocate(0).toString());
    }

    @Test
    void testAllocate() {
        byte[] bytes = {8, 8, 8, 8, 8};
        ByteArrayReader bb = ByteArrayReader.wrap(bytes);
        assertEquals(bb.toString(), ByteArrayReader.allocate(5).toString());
    }

    @Test
    void testFromString() {
        byte[] bytes = {-17, -49, -6, -17};
        ByteArrayReader bbString = ByteArrayReader.fromString("mydomain");
        byte[] bytes2 = bbString.array();
        for (int i = 0; i < 4; i++) {
            assertEquals(bytes[i], bytes2[i]);
        }
    }

}
