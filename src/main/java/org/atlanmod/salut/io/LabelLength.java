package org.atlanmod.salut.io;

import fr.inria.atlanmod.commons.Preconditions;

import java.util.Objects;

/**
 * A label length is an unsigned 8-bit value, representing the size of a label (in bytes), between 1 and 64 (the
 * maximum length). The two first bits are actually flags, "11" indicates a pointer {@see LabelPointer}, "01" an extended
 * label, and "10" an unknown label.
 *
 * If the value represents a pointer, then it should be combined with another byte to form a 14-bit value corresponding
 * to the pointer offset.
 *
 */
public class LabelLength extends Number {

    private final static int MAX_DOMAIN_NAME_LENGTH = 64;

    private final short value;

    private LabelLength(short value) {
        this.value = value;
    }


    public static LabelLength fromInt(int value) {
        Preconditions.checkArgument(value >= UnsignedByte.MIN_VALUE && value <= UnsignedByte.MAX_VALUE);
        short unsigned = (short) (value & UnsignedByte.UNSIGNED_BYTE_MASK);
        return new LabelLength(unsigned);
    }

    public static LabelLength fromUnsignedByte(UnsignedByte ub) {
        return fromInt(ub.intValue());
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

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return (long) value;
    }

    @Override
    public float floatValue() {
        return (float) value;
    }

    @Override
    public double doubleValue() {
        return (double) value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabelLength that = (LabelLength) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }
}
