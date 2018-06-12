package org.atlanmod.salut.io;

import java.util.Objects;

/**
 * The `UnsignedShort` class allows the representation of unsigned 16-bit values.
 * It wraps a value of the primitive getType `int` in an object.
 * An object of getType `UnsignedShort` contains a single field whose getType is `int`.
 */
public class UnsignedShort extends Number implements Comparable<UnsignedShort> {
    private final static int UNSIGNED_SHORT_MASK = 0xFFFF;

    protected final int value;

    public UnsignedShort(int value) {
        this.value = value;
    }

    public int intValue() {
        return value;
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

    /**
     *  Returns the value of this `UnsignedShort` as a `short`.
     *  May result in a negative value.
     */
    @Override
    public short shortValue() {
        return (short) value;
    }

    @Override
    public String toString() {
        return "us" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnsignedShort that = (UnsignedShort) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }

    public static UnsignedShort fromShort(short value) {
        return new UnsignedShort(value & UNSIGNED_SHORT_MASK);
    }

    @Override
    public int compareTo(UnsignedShort other) {
        return (this.value < other.value ? -1 : (this.value == other.value ? 0 : 1));
    }
}
