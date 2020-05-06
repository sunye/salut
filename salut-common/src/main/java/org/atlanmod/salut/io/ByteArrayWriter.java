package org.atlanmod.salut.io;

import static org.atlanmod.commons.Preconditions.checkArgument;
import static org.atlanmod.commons.Preconditions.checkNotNull;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.atlanmod.commons.primitive.Bytes;
import org.atlanmod.salut.domains.IPAddress;
import org.atlanmod.salut.domains.IPv4Address;
import org.atlanmod.salut.domains.IPv6Address;
import org.atlanmod.salut.labels.Label;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.mdns.QClass;
import org.atlanmod.salut.mdns.RecordType;

public class ByteArrayWriter {

    List<Byte> bytes = new ArrayList<>();

    /**
     * Writes a {@code UnsignedShort} instance to this ByteArray, following the big endian order.
     *
     * @param value a UnsignedShort
     * @return this ByteArrayWriter
     */
    public ByteArrayWriter writeUnsignedShort(UnsignedShort value) {
        checkNotNull(value, "value");

        bytes.add((byte) (value.shortValue() >> 8));
        bytes.add((byte) value.shortValue());
        return this;
    }

    /**
     * Writes a {@code UnsignedByte} instance to this ByteArray.
     *
     * @param value a UnsignedByte
     * @return this ByteArrayWriter
     */
    public ByteArrayWriter writeUnsignedByte(UnsignedByte value) {
        checkNotNull(value, "value");

        bytes.add(value.byteValue());
        return this;
    }

    /**
     * Writes a {@code UnsignedInt} instance to this ByteArray, following the big endian order.
     *
     * @param value a UnsignedInt
     * @return this ByteArrayWriter
     */
    public ByteArrayWriter writeUnsignedInt(UnsignedInt value) {
        checkNotNull(value, "value");

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
    public ByteArrayWriter writeLabel(String label) {
        checkNotNull(label, "label");

        UnsignedByte length = UnsignedByte.fromInt(label.length());
        this.writeUnsignedByte(length);
        byte[] arrayLabel = label.getBytes(StandardCharsets.UTF_8);
        for (byte each : arrayLabel) {
            bytes.add(each);
        }
        return this;
    }

    /**
     * Writes an array of names  encoded in UTF-8
     *
     * @param names an array of names
     * @return This ByteArrayWriter
     */
    public ByteArrayWriter writeLabels(Labels names) {
        for (Label each : names.getLabels()) {
            this.writeLabel(each.toString());
        }

        this.writeUnsignedByte(UnsignedByte.fromInt(0));
        return this;
    }

    /**
     * Writes a 4 or 16 bytes value representing an IP address, v4 or v6.
     *
     * @param address
     * @return
     */
    public ByteArrayWriter writeIPAddress(IPAddress address) {
        checkNotNull(address);
        int length = address.address().length;
        checkArgument(length == IPv4Address.SIZE || length == IPv6Address.SIZE,
            "Invalid IP Address");

        bytes.addAll(Bytes.asList(address.address()));
        return this;
    }

    /**
     * Writes an 4-bytes value representing an IPv4 address
     *
     * @param address an IPv4 address
     * @return This ByteArrayWriter
     */
    public ByteArrayWriter writeIPv4Address(IPv4Address address) {
        checkNotNull(address, "address");
        checkArgument(address.address().length == IPv4Address.SIZE);

        bytes.addAll(Bytes.asList(address.address()));
        return this;
    }

    /**
     * Writes an 16-bytes value representing an IPv6 address
     *
     * @param address an IPv6 address
     * @return This ByteArrayWriter
     */
    public ByteArrayWriter writeIPv6Address(IPv6Address address) {
        checkNotNull(address, "address");
        checkArgument(address.address().length == IPv6Address.SIZE);

        bytes.addAll(Bytes.asList(address.address()));
        return this;
    }

    /**
     * Writes a 2-byte value representing a Resource Record (RR) Type.
     *
     * @param type the Resource Record Type.
     * @return This ByteArrayWriter
     */
    public ByteArrayWriter writeRecordType(RecordType type) {
        this.writeUnsignedShort(type.unsignedShortValue());

        return this;
    }

    /**
     * Writes a 2-byte value representing a DNS Question Class (QClass) Type.
     *
     * @param qClass the DNS Question Class.
     * @return This ByteArrayWriter
     */
    public ByteArrayWriter writeQClass(QClass qClass) {
        this.writeUnsignedShort(qClass.unsignedShortValue());

        return this;
    }


    public byte[] array() {
        return Bytes.toArray(bytes);
    }


    public ByteArrayReader getByteArrayReader() {
        return ByteArrayReader.wrap(this.array());
    }
}
