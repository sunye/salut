package org.atlanmod.salut.io;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.atlanmod.commons.Preconditions;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.domains.IPAddressBuilder;
import org.atlanmod.salut.domains.IPv4Address;
import org.atlanmod.salut.domains.IPv6Address;
import org.atlanmod.salut.labels.DNSLabel;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.record.QClass;
import org.atlanmod.salut.record.RecordType;

/**
 * The class `ByteArrayReader` is a wrapper class for the {@code ByteBuffer} class.
 * It adds specific operations for reading unsigned values, labels, compressed labels, etc.
 */
public class ByteArrayReader {


    private final static int MAX_DOMAIN_NAME_LENGTH = 64;
    private final ByteBuffer buffer;

    public ByteArrayReader(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public static ByteArrayReader allocate(int capacity) {
        ByteBuffer wrapped = ByteBuffer.allocate(capacity);
        return new ByteArrayReader(wrapped);
    }

    public static ByteArrayReader wrap(byte[] array) {
        ByteBuffer wrapped = ByteBuffer.wrap(array, 0, array.length);
        return new ByteArrayReader(wrapped);
    }

    public static ByteArrayReader fromString(String str) {
        return wrap(Bytes.stringToBytes(str));
    }

    public RecordType readRecordType() throws ParseException {
        int code = this.getUnsignedShort().intValue();

        Optional<RecordType> type = RecordType.fromCode(code);
        if (! type.isPresent()) {
            throw new ParseException("Parsing error when reading Question Type. Unknown code: " + code, buffer.position());
        } else {
            return type.get();
        }
    }

    public QClass readQClass() throws ParseException {
        return QClass.fromByteBuffer(this);
    }

    public ByteArrayReader duplicate() {
        return new ByteArrayReader(buffer.duplicate());
    }

    /**
     * The compression scheme allows a domain data in a message to be represented as either:
     * - a sequence of labels ending in a zero octet
     * - a pointer
     * - a sequence of labels ending with a pointer
     *
     * @return a list potentially containing the strings representing a qualified name.
     */
    public Labels readLabels() throws ParseException {
        Labels labels = Labels.create();
        LabelLength lengthOrPointer = this.getLabelLength();

        while (lengthOrPointer.isValidNameLength()) {
            DNSLabel label = this.getLabel(lengthOrPointer);
            labels.add(label);
            lengthOrPointer = this.getLabelLength();
        }
        if (lengthOrPointer.isPointer()) {
            UnsignedByte high = UnsignedByte.fromByte(lengthOrPointer.byteValue());
            LabelPointer pointer = LabelPointer.fromBytes(high, this.getUnsignedByte());
            ByteArrayReader other = this.duplicate(); // To keep current position in this Buffer.
            other.position(checkOffset(pointer.offset()));
            labels.addAll(other.readLabels());

        } else if (lengthOrPointer.isExtended() || lengthOrPointer.isUnknown()) {
            int currentPosition = buffer.position();
            Log.error("Extend and Unknown labels are not supported.  Before: {0}, Read: {1}, After: {2}",
                    this.getUnsignedByte(currentPosition - 2),
                    this.getUnsignedByte(currentPosition - 1),
                    this.getUnsignedByte(currentPosition));

            throw new ParseException("Extend and Unknown labels are not supported.", currentPosition - 1);
        }
        return labels;
    }


    public List<String> readTextDataStrings(int recordLength) {
        List<String> labels = new ArrayList<>();
        int bytesRead = 0;
        do {
            LabelLength length = this.getLabelLength();
            String label = this.getString(length);
            bytesRead += length.intValue() + 1;
            labels.add(label);
            Log.info("Read label {0}", label);
        } while (bytesRead < recordLength);

        return labels;
    }

    /**
     * Reads a label of a given number of characters, encoded in UTF8.
     *
     * @param length the number of characters to be read.
     * @return A Label representing the read value.
     */
    protected DNSLabel getLabel(LabelLength length) {
        byte[] labelBuffer = new byte[length.intValue()];
        buffer.get(labelBuffer);

        return DNSLabel.create(labelBuffer);
    }

    /**
     * Reads a string of a given number of characters, encoded in UTF8.
     *
     * @param length the number of characters to be read.
     * @return A String representing the label.
     */
    protected String getString(LabelLength length){
        Preconditions.checkArgument(length.shortValue() <= 255);

        byte[] labelBuffer = new byte[length.intValue()];
        buffer.get(labelBuffer);
        return new String(labelBuffer, StandardCharsets.UTF_8);
    }

    /**
     * Since java does not provide unsigned primitive types, each unsigned
     * value read from the buffer is promoted up to the next bigger primitive
     * data getType.
     *
     * @return a unsigned short instance.
     */
    public UnsignedShort getUnsignedShort() {
        return UnsignedShort.fromShort(buffer.getShort());
    }

    public UnsignedByte getUnsignedByte() {
        return UnsignedByte.fromByte(buffer.get());
    }

    public LabelLength getLabelLength() {
        return  LabelLength.fromUnsignedByte(this.getUnsignedByte());
    }

    public UnsignedByte getUnsignedByte(int position) {
        return UnsignedByte.fromByte(buffer.get(position));
    }

    public UnsignedInt getUnsignedInt() {
        return UnsignedInt.fromInt(buffer.getInt());
    }

    public byte[] array() {
        return buffer.array();
    }

    public int position() {
        return buffer.position();
    }

    public void position(int newPosition) {
        buffer.position(newPosition);
    }

    public ByteArrayReader get(byte[] dst) {
        buffer.get(dst);
        return this;
    }


    public byte get() {
        return buffer.get();
    }

    @Override
    public String toString() {
        return buffer.toString();
    }

    private int checkOffset(int pointer) throws ParseException {
        if (pointer >= buffer.position()) {
            throw new ParseException("pointer overflow", buffer.position());
        }
        return pointer;
    }

    /**
     * Reads a 4-bytes IPv4 address
     * @return a IPv4Address
     */
    public IPv4Address readIPv4Address()  {
        byte[] addressBytes = new byte[4];
        get(addressBytes);

        return IPAddressBuilder.createIPv4Address(addressBytes);
    }

    /**
     * Reads a 16-bytes IPv6 address
     * @return This ByteArrayReader
     */
    public IPv6Address readIPv6Address() {
        byte[] addressBytes = new byte[16];
        get(addressBytes);

        return IPAddressBuilder.createIPv6Address(addressBytes);
    }
}

