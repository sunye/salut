package org.atlanmod.salut.io;

import java.util.Objects;

public class UnsignedShort {
    private final static int UNSIGNED_SHORT_MASK = 0xFFFF;


    protected final int value;

    public UnsignedShort(int value) {
        this.value = value;
    }

    public int toInt() {
        return value;
    }

    /**
     * Translates unsigned short to short (may result in a negative value).
     *
     * @return
     */
    public short toShort() {
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
}
