package org.atlanmod.salut.io;

import fr.inria.atlanmod.commons.log.Log;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class `ByteArrayBuffer` is a wrapper class for the {@code ByteBuffer} class.
 * It adds specific operations for reading unsigned values, labels, compressed labels, etc.
 */
public class ByteArrayBuffer {


    private final static int MAX_DOMAIN_NAME_LENGTH = 64;
    private final ByteBuffer buffer;

    public ByteArrayBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public static ByteArrayBuffer allocate(int capacity) {
        ByteBuffer wrapped = ByteBuffer.allocate(capacity);
        return new ByteArrayBuffer(wrapped);
    }

    public static ByteArrayBuffer wrap(byte[] array) {
        ByteBuffer wrapped = ByteBuffer.wrap(array, 0, array.length);
        return new ByteArrayBuffer(wrapped);
    }

    public static ByteArrayBuffer fromString(String str) {
        return wrap(toByteArray(str));
    }

    private static byte[] toByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public ByteArrayBuffer duplicate() {
        return new ByteArrayBuffer(buffer.duplicate());
    }

    /**
     * The compression scheme allows a domain data in a message to be represented as either:
     * - a sequence of labels ending in a zero octet
     * - a pointer
     * - a sequence of labels ending with a pointer
     *
     * @return a list potentially containing the strings representing a qualified name.
     */
    public List<String> readLabels() throws ParseException {
        //Log.info("Reading label at position: {0}", buffer.position());

        List<String> labels = new ArrayList<>();
        LabelLength lengthOrPointer = this.getLabelLength();

        while (lengthOrPointer.isValidNameLength()) {
            String label = this.getLabel(lengthOrPointer.intValue());
            labels.add(label);
            lengthOrPointer = this.getLabelLength();
        }
        if (lengthOrPointer.isPointer()) {
            UnsignedByte high = UnsignedByte.fromByte(lengthOrPointer.byteValue());
            LabelPointer pointer = LabelPointer.fromBytes(high, this.getUnsignedByte());
            ByteArrayBuffer other = this.duplicate(); // To keep current position in this Buffer.
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

        /*
        if (labels.isEmpty()) {
            int currentPosition = buffer.position();
            String message = MessageFormat.format("Empty NameArray at position {0}. Before: {1}, Read: {2}, After: {3}",
                    currentPosition - 1,
                    this.getUnsignedByte(currentPosition - 2),
                    this.getUnsignedByte(currentPosition - 1),
                    this.getUnsignedByte(currentPosition));
            Log.warn(message);
        }
        */

        return labels;
    }

    public List<String> readTextStrings(int recordLength) {
        List<String> strings = new ArrayList<>();
        int bytesRead = 0;
        do {
            UnsignedByte length = this.getUnsignedByte();
            String label = this.getLabel(length.intValue());
            bytesRead += length.intValue() + 1;
            strings.add(label);
        } while (bytesRead < recordLength);

        return strings;
    }

    /**
     * Reads a label of a given number of characters, encoded in UTF8.
     *
     * @param length the number of characters to be read.
     * @return A String representing the label.
     */
    private String getLabel(int length) {
        byte[] labelBuffer = new byte[length];
        buffer.get(labelBuffer);
        String newLabel = new String(labelBuffer, StandardCharsets.UTF_8);
        //Log.info("Label read: {0}", newLabel);

        return newLabel;
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

    public void putUnsignedShord(UnsignedShort us) {
        buffer.putShort(us.shortValue());
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

    public ByteArrayBuffer get(byte[] dst) {
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
}
