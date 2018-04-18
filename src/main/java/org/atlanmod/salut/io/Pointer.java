package org.atlanmod.salut.io;

public class Pointer extends UnsignedShort {
    private static final short POINTER_MASK = 0x3FFF;

    public Pointer(int value) {
        super(value);
    }

    /**
     * Removes pointer mask from this unsigned short
     * @return
     */
    public int offset() {
        return value & POINTER_MASK;
    }

    /**
     *
     * Creates a new Pointer from two unsigned bytes.
     *
     * @param high The higher byte of the created Pointer
     * @param low The lower byte of the created Pointer
     * @return a Pointer instance
     */
    public static Pointer fromBytes(UnsignedByte high, UnsignedByte low) {

        int pointer = (high.toInt() << 8) + low.toInt();
        return new Pointer(pointer);
    }
}
