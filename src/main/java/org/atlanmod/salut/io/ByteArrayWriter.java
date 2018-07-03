package org.atlanmod.salut.io;

import fr.inria.atlanmod.commons.primitive.Bytes;
import org.atlanmod.salut.mdns.NameArray;
import org.atlanmod.salut.mdns.QClass;
import org.atlanmod.salut.mdns.RecordType;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
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
        bytes.add((byte) (value.intValue() >>> 24));
        bytes.add((byte) (value.intValue() >>> 16));
        bytes.add((byte) (value.intValue() >>> 8));
        bytes.add((byte) value.intValue());
        return this;
    }

    /**
     * Writes a label encoded in UTF8.
     *
     * @param label the label to write
     * @return This ByteArrayWriter
     */
    public ByteArrayWriter putLabel(String label) {
        UnsignedByte length = UnsignedByte.fromInt(label.length());
        this.putUnsignedByte(length);
        byte[] arrayLabel = label.getBytes(StandardCharsets.UTF_8);
        for (byte each : arrayLabel) {
            bytes.add(each);
        }
        return this;
    }

    /**
     * Writes an array of names  encoded in UFF-8
     * @param names an array of names
     * @return This ByteArrayWriter
     */
    public ByteArrayWriter putNameArray(NameArray names) {
        for (String each : names.getNames()) {
            this.putLabel(each);
        }

        this.putUnsignedByte(UnsignedByte.fromInt(0));
        return this;
    }

    /**
     * Writes an 4-bytes value representing an IPv4 address
     *
     * @param address an IPv4 address
     * @return This ByteArrayWriter
     */
    public  ByteArrayWriter putInet4Address(Inet4Address address) {
        for (byte each : address.getAddress()) {
            bytes.add(each);
        }
        return this;
    }

    /**
     * Writes a 2-byte value representing a Resource Record (RR) Type.
     *
     * @param type the Resource Record Type.
     * @return This ByteArrayWriter
     */
    public ByteArrayWriter putRecordType(RecordType type) {
        this.putUnsignedShort(type.unsignedShortValue());

        return this;
    }

    public ByteArrayWriter putQClass(QClass qClass) {
        this.putUnsignedShort(qClass.unsignedShortValue());

        return this;
    }


    public byte[] array() {
        return Bytes.toArray(bytes);
    }


    public ByteArrayReader getByteArrayReader() {
        return ByteArrayReader.wrap(this.array());
    }
}
