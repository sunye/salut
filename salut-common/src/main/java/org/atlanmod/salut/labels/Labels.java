package org.atlanmod.salut.labels;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.atlanmod.commons.Preconditions;
import org.atlanmod.salut.io.ByteArrayReader;

/**
 * List of labels
 */
public class Labels {

    private final List<Label> values;

    private Labels(List<Label> labels) {
        this.values = labels;
    }

    public int size() {
        return values.size();
    }

    @Override
    public boolean equals(Object o) {
        //@formatter:off
        if (this == o) {return true;}
        if (!(o instanceof Labels)) {return false;}
        //@formatter:on

        Labels labels = (Labels) o;
        return Objects.equals(this.values, labels.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    public boolean contains(Label aLabel) {
        return values.contains(aLabel);
    }

    @Override
    public String toString() {
        return "la"+ values.toString();
    }

    /**
     * Adds a name (label) to this object.
     *
     * @param name the name (label) to add.
     */
    public Labels add(@Nonnull Label name) {
        Preconditions.checkNotNull(name);

        this.values.add(name);
        return this;
    }

    /**
     * Adds (concatenates) all labels of another object to this one.
     *
     * @param labels the Label list containing the labels to add.
     */
    public Labels addAll(Labels labels) {
        Preconditions.checkNotNull(labels);
        this.values.addAll(labels.getLabels());
        return this;
    }

    public Label get(int index ) {
        Preconditions.checkElementIndex(index, values.size());

        return values.get(index);
    }

    public Label last() {
        Preconditions.checkArgument(!values.isEmpty());

        return values.get(values.size() - 1);
    }

    public Label first() {
        Preconditions.checkArgument(!values.isEmpty());

        return values.get(0);
    }

    public List<Label> getLabels() {
        return values;
    }

    public Labels subArray(int fromIndex, int toIndex) {
        return new Labels(values.subList(fromIndex, toIndex));
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
            labels.add(DNSLabel.create(each));
        }
        return new Labels(labels);
    }

    public static Labels fromArray(DNSLabel[] strings) {
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
        for (Label each : values) {
            length += each.dataLength();
        }

        return length;
    }
}
