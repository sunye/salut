package org.atlanmod.salut.io;

import fr.inria.atlanmod.commons.Preconditions;

/**
 * A label length is an unsigned 8-bit value, representing the size of a label (in bytes), between 1 and 64 (the
 * maximum length). The two first bits are actually flags, "11" indicates a pointer {@see LabelPointer}, "01" an extended
 * label, and "10" an unknown label.
 *
 * If the value represents a pointer, then it should be combined with another byte to form a 14-bit value corresponding
 * to the pointer offset.
 *
 */
public class LabelLength extends UnsignedByte {

    private final static int MAX_DOMAIN_NAME_LENGTH = 64;

    protected LabelLength(short value) {
        super(value);
    }

    public LabelLength(UnsignedByte ub) {
        super(ub.value);
    }

    public static LabelLength fromInt(int value) {
        Preconditions.checkArgument(value >= MIN_VALUE && value <= MAX_VALUE);
        short unsigned = (short) (value & UNSIGNED_BYTE_MASK);
        return new LabelLength(unsigned);
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
     * A pointer is an unsigned 16-bit value with the top two bits of 11 indicate the pointer format.
     * Here, we check that the higher byte starts with "11", for instance 11001010.
     *
     * @return true if it is a pointer.
     */
    public boolean isPointer() {
        return (value & 0xC0) == 0xC0;
    }

    /**
     * A label is extended if it starts with "01, for instance "01001010".
     *
     * @return true if it is an extended label.
     */
    public boolean isExtended() {
        return (value & 0xC0) == 0x40;
    }

    /**
     * A label is unknown if it starts with "10", for instance "1010101010".
     *
     * @return true if it is an unknown label.
     */
    public boolean isUnknown() {
        return (value & 0xC0) == 0x80;
    }
}
