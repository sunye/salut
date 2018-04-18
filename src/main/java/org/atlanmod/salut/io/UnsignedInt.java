package org.atlanmod.salut.io;

public class UnsignedInt {
    private final static long UNSIGNED_INT_MASK = 0xFFFFFFFFL;
    private final long value;

    private UnsignedInt(long value) {
        this.value = value;
    }

    public long toLong() {
        return value;
    }

    public String toString() {
        return "ui" + value;
    }

    public static UnsignedInt fromInt(int value) {
        return new UnsignedInt(value & UNSIGNED_INT_MASK);
    }

}
