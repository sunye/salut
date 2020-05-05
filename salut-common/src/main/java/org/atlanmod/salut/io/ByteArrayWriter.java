package org.atlanmod.salut.io;

import org.atlanmod.commons.primitive.Bytes;
import org.atlanmod.salut.domains.IPAddress;
import org.atlanmod.salut.domains.IPv4Address;
import org.atlanmod.salut.domains.IPv6Address;
import org.atlanmod.salut.labels.Label;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.mdns.QClass;
import org.atlanmod.salut.mdns.RecordType;

import java.net.Inet6Address;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ByteArrayWriter {

    List<Byte> bytes = new ArrayList<>();

    public ByteArrayWriter putUnsignedShort(UnsignedShort value) {
        bytes.add((byte) (value.shortValue() >> 8));
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
     *
     * @param names an array of names
     * @return This ByteArrayWriter
     */
    public ByteArrayWriter putNameArray(Labels names) {
        for (Label each : names.getLabels()) {
            this.putLabel(each.toString());
        }

        this.putUnsignedByte(UnsignedByte.fromInt(0));
        return this;
    }

    /**
     * Writes a 4 or 16 bytes value representing an IP address, v4 or v6.
     *
     * @param address
     * @return
     */
    public ByteArrayWriter putIPAddress(IPAddress address) {
        int length = address.address().length;
        assert length == IPv4Address.SIZE
            || length == IPv6Address.SIZE
            : "Invalid IP Address";

        for (byte each : address.address()) {
            bytes.add(each);
        }
        return this;
    }

    /**
     * Writes an 4-bytes value representing an IPv4 address
     *
     * @param address an IPv4 address
     * @return This ByteArrayWriter
     */
    public ByteArrayWriter putIPv4Address(IPv4Address address) {
        for (byte each : address.address()) {
            bytes.add(each);
        }
        return this;
    }

    /**
     * Writes an 16-bytes value representing an IPv6 address
     *
     * @param address an IPv6 address
     * @return This ByteArrayWriter
     */
    public ByteArrayWriter putInet6Address(Inet6Address address) {
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
