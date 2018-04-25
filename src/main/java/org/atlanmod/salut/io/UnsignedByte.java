package org.atlanmod.salut.io;

public class UnsignedByte {
    private final static int UNSIGNED_BYTE_MASK = 0xFF;
    private final static int MAX_DOMAIN_NAME_LENGTH = 64;

    private final int value;

    private UnsignedByte(int value) {
        this.value = value;
    }

    public int toInt() {
        return value;
    }

    public boolean isLessThan(int other) {
        return value < other;
    }

    public boolean isZero() {
        return value == 0;
    }

    /**
     * A data length is valid if it is greater than 0 and lower than 64,
     * the maximum accepted length for a data.
     *
     * @return true if it is a valid length.
     */
    public boolean isValidNameLength() {
        return value != 0 && value <= MAX_DOMAIN_NAME_LENGTH;
    }

    /**
     * A pointer is an unsigned 16-bit value with the top two bits of 11 indicate the pointer format
     * @return true if it is a pointer.
     */
    public boolean isPointer() {
        return (value & 0xC0) == 0xC0;
    }

    public boolean isExtended() {
        return (value & 0x40) == 0x40;
    }

    public boolean isUnknown() {
        return (value & 0x80) == 0x80;
    }

    public UnsignedShort withByte(byte value) {
        return new UnsignedShort((this.value << 8) + value);
    }

    @Override
    public String toString() {
        return "ub" + value;
    }

    public static UnsignedByte fromByte(byte value) {
        return new UnsignedByte(value & UNSIGNED_BYTE_MASK);
    }
}
