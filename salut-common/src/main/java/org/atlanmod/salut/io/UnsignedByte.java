package org.atlanmod.salut.io;

import org.atlanmod.commons.Preconditions;

import java.util.Objects;

/**
 * The `UnsignedByte` class allows the representation of unsigned 8-bit values.
 * It wraps a value of the primitive getType `short` in an object.
 * An object of getType `UnsignedByte` contains a single field whose getType is `short`.
 */
public class UnsignedByte extends Number implements Comparable<UnsignedByte> {

    public final static short MIN_VALUE = 0;
    public final static short MAX_VALUE = 255;
    public final static int UNSIGNED_BYTE_MASK = 0xFF;

    private final static UnsignedByte[] cache = new UnsignedByte[MAX_VALUE + 1];

    protected final short value;

    protected UnsignedByte(short value) {
        Preconditions.checkGreaterThanOrEqualTo(value, MIN_VALUE);
        Preconditions.checkLessThanOrEqualTo(value, MAX_VALUE);

        this.value = value;
    }

    /**
     * Returns an <code>UnsignedByte</code> object wrapping the value.
     *
     * In contrast to the `UnsignedByte` constructor, this method will
     * cache some values.
     *
     * @param value the value to wrap
     * @return the <code>UnsignedByte</code>
     */
    private static UnsignedByte valueOf(short value) {
        assert value >= MIN_VALUE && value <= MAX_VALUE;

        if (cache[value] == null) {
            cache[value] = new UnsignedByte(value);
        }
        return cache[value];
    }

    public static UnsignedByte fromByte(byte value) {
        short unsigned = (short) (value & UNSIGNED_BYTE_MASK);
        return valueOf(unsigned);
    }

    public static UnsignedByte fromShort(short value) {
        Preconditions.checkArgument(value >= MIN_VALUE && value <= MAX_VALUE);
        short unsigned = (short) (value & UNSIGNED_BYTE_MASK);

        return valueOf(unsigned);
    }

    public static UnsignedByte fromInt(int value) {
        Preconditions.checkArgument(value >= MIN_VALUE && value <= MAX_VALUE);
        short unsigned = (short) (value & UNSIGNED_BYTE_MASK);

        return valueOf(unsigned);
    }

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    /**
     * Compares two unsigned bytes numerically.
     * @param other The UnsignedByte object to be compared with
     * @return {@code true} is this value is less than the other, {@cde false} otherwise.
     */
    public boolean isLessThan(UnsignedByte other) {
        return value < other.value;
    }

    public boolean isZero() {
        return value == 0;
    }

    /**
     * Joins two unsigned short values and creates an unsigned short.
     * This value is the higher part and the parameter is the lower part.
     *
     * @param lowByte the value to be joined to this one;
     * @return an unsigned short representing the two joined values.
     */
    public UnsignedShort withLowByte(UnsignedByte lowByte) {
        return UnsignedShort.fromInt((this.value << 8) + (lowByte.value & 0xff));
    }


    /**
     * Returns a `String` object representing this
     * `UnsignedByte`'s value. The value is converted to signed
     * decimal representation and returned as a string.
     *
     * @return  a string representation of the value of this object in
     *          base&nbsp;10.
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Compares this object to the specified object.
     * The result is `true` if and only if the argument is not null and is an `UnsignedByte` object that contains
     * the same `short` value as this object.
     *
     * @param obj the object to compare with.
     *
     * @return `true` if the objects are the same; `false` otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UnsignedByte that = (UnsignedByte) obj;
        return value == that.value;
    }

    /**
     * Returns a hash code for this `UnsignedByte`.
     * @return the hash code for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    /**
     * Compares two `UnsignedByte` objects numerically.
     * @param other the other object to be compared with.
     * @return the value 0 if this `UnsignedByte` is equal to the argument `UnsignedByte`;
     * a value less than 0 if this `UnsignedByte` is numerically less than the argument
     * `UnsignedByte`; and a value greater than 0 if this `UnsignedByte` is numerically greater
     * than the argument `UnsignedByte`.
     */
    @Override
    public int compareTo(UnsignedByte other) {
        return Short.compare(this.value, other.value);
    }
}
