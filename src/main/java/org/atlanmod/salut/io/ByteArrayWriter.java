package org.atlanmod.salut.io;

import fr.inria.atlanmod.commons.collect.MoreArrays;
import fr.inria.atlanmod.commons.primitive.Bytes;

import java.util.ArrayList;
import java.util.List;

public class ByteArrayWriter {

    List<Byte> bytes = new ArrayList<>();

    public ByteArrayWriter putUnsignedShort(UnsignedShort value) {
        bytes.add((byte)(value.shortValue() >> 8));
        bytes.add((byte) value.shortValue());
        return this;
    }

    public ByteArrayWriter putUnsignedByte(UnsignedByte value) {
        bytes.add(value.byteValue());
        return this;
    }

    public ByteArrayWriter putUnsignedInt(UnsignedInt value) {
        bytes.add((byte) (value.intValue() >> 24));
        bytes.add((byte) (value.intValue() >> 16));
        bytes.add((byte) (value.intValue() >> 8));
        bytes.add((byte) value.intValue());
        return this;
    }

    public byte[] array() {
        return Bytes.toArray(bytes);
    }
}
