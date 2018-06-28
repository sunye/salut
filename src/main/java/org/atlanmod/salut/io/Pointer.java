package org.atlanmod.salut.io;

/**
 * A label pointer is an unsigned 16-bit value, where the top two bits of 11 indicate the pointer format,
 * and the 14 other bits represent a (previous) position containing one or more labels (in the same dns message).
 *
 * For instance, if a message contains several times the labels "salut.atlanmod.org", only the first occurrence
 * will be written to the message, the others will use pointers to this first occurrence.
 *
 * While inb theory the position may be between 0 and 2^14 (16,384), it is limited between the position the label
 * pointer was read and the mdns messages maximum size.
 */
public class Pointer extends UnsignedShort {
    private static final short POINTER_MASK = 0x3FFF;

    public Pointer(int value) {
        super(value);
    }

    /**
     * Removes pointer mask from this unsigned short
     * @return an int value
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

        int pointer = (high.intValue() << 8) + low.intValue();
        return new Pointer(pointer);
    }
}
