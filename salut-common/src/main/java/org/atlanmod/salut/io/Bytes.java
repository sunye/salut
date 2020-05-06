package org.atlanmod.salut.io;

import static org.atlanmod.commons.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Utility class for handling byte array conversion
 */
@ParametersAreNonnullByDefault
public class Bytes {

    private Bytes() {
    }

    /**
     * Convenience method that coverts a {@code String} representing a collection of bytes into an
     * array of {@code byte}. For instance, "12af" will be converted to [0x12,0xAF].
     *
     * @param str the {@code String} to be converted
     * @return an array of {@code byte}, containing the converted bytes, in the same order.
     */
    public static byte[] stringToBytes(String str) {
        checkNotNull(str, "str");

        int len = str.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4)
                + Character.digit(str.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * Converts an array of primitive types {@code short} to an array of {@link byte}. Each {@code
     * short} is converted to two bytes, using the big endian order. For instance, [0x1234] will be
     * converted to [0x12, 0x34].
     *
     * @param shorts the array of {@code short} to convert.
     * @return an array of {@link byte} containing twice of the elements as {@code primitiveArray}.
     */
    public static byte[] shortsToBytes(final short... shorts) {
        checkNotNull(shorts, "shorts");

        byte[] bytes = new byte[shorts.length * 2];
        for (int i = 0; i < shorts.length; i++) {
            bytes[i * 2] = (byte) ((shorts[i] >> 8 & 0xff));
            bytes[i * 2 + 1] = (byte) (shorts[i] & 0xff);
        }
        return bytes;
    }

    /**
     * Converts an array of primitive types {@code byte} to a {@List} of {@link Byte}.
     *
     * @param primitiveArray the array of {@code byte} to convert.
     * @return an array of {@link Byte} containing the same elements as {@code primitiveArray}, in
     * the same order, converted to boxed types.
     */
    public static List<Byte> bytesToBoxedList(final byte... primitiveArray) {
        checkNotNull(primitiveArray, "primitiveArray");

        final int size = primitiveArray.length;
        final List<Byte> boxedList = new ArrayList<>(size);

        for (byte each : primitiveArray) {
            boxedList.add(each);
        }

        return boxedList;
    }
}
