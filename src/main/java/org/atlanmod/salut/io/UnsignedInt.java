package org.atlanmod.salut.io;

/**
 * The `UnsignedInt` class allows the representation of unsigned 32-bit values.
 * It wraps a value of the primitive type `long` in an object.
 * An object of type `UnsignedInt` contains a single field whose type is `long`.
 */
public class UnsignedInt extends Number implements Comparable<UnsignedInt> {
    private final static long UNSIGNED_INT_MASK = 0xFFFFFFFFL;
    private final long value;

    private UnsignedInt(long value) {
        this.value = value;
    }

    /**
     * Wraps an `int` value into a `UnsignedInt`.
     *
     *
     * @param value
     * @return
     */
    public static UnsignedInt fromInt(int value) {
        return new UnsignedInt(value & UNSIGNED_INT_MASK);
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return value;
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
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int compareTo(UnsignedInt other) {
        return (this.value < other.value ? -1 : (this.value == other.value ? 0 : 1));
    }
}
