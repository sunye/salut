package org.atlanmod.salut.labels;

import org.atlanmod.commons.Preconditions;
import org.atlanmod.salut.io.ByteArrayReader;

import javax.annotation.Nonnull;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * List of labels
 */
public class Labels {

    private final List<Label> labels;

    private Labels(List<Label> labels) {
        this.labels = labels;
    }

    public int size() {
        return labels.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Labels)) return false;
        Labels labels = (Labels) o;
        return Objects.equals(this.labels, labels.labels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(labels);
    }

    public boolean contains(Label aLabel) {
        return labels.contains(aLabel);
    }

    @Override
    public String toString() {
        return "la"+labels.toString();
    }

    /**
     * Adds a name (label) to this object.
     *
     * @param name the name (label) to add.
     */
    public Labels add(@Nonnull Label name) {
        Preconditions.checkNotNull(name);

        this.labels.add(name);
        return this;
    }

    /**
     * Adds (concatenates) all labels of another object to this one.
     *
     * @param labels the Label list containing the labels to add.
     */
    public Labels addAll(Labels labels) {
        Preconditions.checkNotNull(labels);
        this.labels.addAll(labels.getLabels());
        return this;
    }

    public Label get(int index ) {
        Preconditions.checkElementIndex(index, labels.size());

        return labels.get(index);
    }

    public Label last() {
        Preconditions.checkArgument(!labels.isEmpty());

        return labels.get(labels.size() - 1);
    }

    public Label first() {
        Preconditions.checkArgument(!labels.isEmpty());

        return labels.get(0);
    }

    public List<Label> getLabels() {
        return labels;
    }

    public Labels subArray(int fromIndex, int toIndex) {
        return new Labels(labels.subList(fromIndex, toIndex));
    }

    /**
     * The compression scheme allows a domain data in a message to be
     * represented as either:
     * <p>
     * - a sequence of data ending in a zero octet
     * - a pointer
     * - a sequence of data ending with a pointer
     *
     * @param buffer a byte array buffer containing an encoded qualified name.
     * @return a new name array instance
     */
    public static Labels fromByteBuffer(ByteArrayReader buffer, int position) throws ParseException {
        buffer.position(position);
        return buffer.readLabels();
    }

    public static Labels fromByteBuffer(ByteArrayReader buffer) throws ParseException {
        return Labels.fromByteBuffer(buffer, buffer.position());
    }

    public static Labels fromArray(String[] strings) {
        List<Label> labels = new ArrayList<>(strings.length);
        for (String each: strings) {
            labels.add(Label.create(each));
        }
        return new Labels(labels);
    }

    public static Labels fromArray(Label[] strings) {
        return new Labels(Arrays.asList(strings));
    }

    /**
     * Creates an empty instance of a Labels.
     *
     * @return a new instance
     */
    public static Labels create() {
        return new Labels(new ArrayList<Label>());
    }

    /**
     * Creates a label list from an array of Labels.
     * @param names one or more labels containing a qualified name.
     *
     * @return a new name array
     */
    public static Labels fromList(Label... names) {
        return new Labels(Arrays.asList(names));
    }

    /**
     * Creates a label list from an array of Strings.
     * @param names one or more strings containing a qualified name.
     *
     * @return a new name array
     */
    public static Labels fromList(String ... names) {
        return  Labels.fromArray(names);
    }


    /**
     * The sum of the sizes, in octets, of all labels when written on a byte array
     */
    public int dataLength() {
        // FIXME: The data length cannot be calculated here, if compression is used

        int length = 0;
        for (Label each : labels) {
            length += each.dataLength();
        }

        return length;
    }
}
