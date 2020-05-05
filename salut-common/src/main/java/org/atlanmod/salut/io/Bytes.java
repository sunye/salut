package org.atlanmod.salut.io;

/**
 * Utility class for handling byte array conversion
 */
public class Bytes {

    private Bytes() {}

    public static byte[] stringToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static byte[] shortsToBytes(short[] shorts) {
        byte[] bytes = new byte[shorts.length*2];
        for (int i = 0; i < shorts.length; i++) {
            bytes[i*2] = (byte) ((shorts[i] >> 8 & 0xff));
            bytes[i*2+1] = (byte) (shorts[i] & 0xff);
        }
        return bytes;
    }
}
