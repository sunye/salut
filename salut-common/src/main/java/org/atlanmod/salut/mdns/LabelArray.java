package org.atlanmod.salut.mdns;

import org.atlanmod.commons.Preconditions;
import org.atlanmod.salut.data.Label;
import org.atlanmod.salut.io.ByteArrayReader;

import javax.annotation.Nonnull;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LabelArray {

    private final List<Label> labels;

    private LabelArray(List<Label> labels) {
        this.labels = labels;
    }

    public int size() {
        return labels.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LabelArray)) return false;
        LabelArray labelArray = (LabelArray) o;
        return Objects.equals(labels, labelArray.labels);
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
     * Adds a name (label) to this LabelArray.
     *
     * @param name the name (label) to add.
     */
    public void add(@Nonnull Label name) {
        Preconditions.checkNotNull(name);

        this.labels.add(name);
    }

    /**
     * Adds (concatenates) all labels of another LabelArray to this one.
     *
     * @param labelArray the LabelArray containing the labels to add.
     */
    public void addAll(LabelArray labelArray) {
        Preconditions.checkNotNull(labelArray);

        this.labels.addAll(labelArray.getLabels());
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

    public LabelArray subArray(int fromIndex, int toIndex) {
        return new LabelArray(labels.subList(fromIndex, toIndex));
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
    public static LabelArray fromByteBuffer(ByteArrayReader buffer, int position) throws ParseException {
        buffer.position(position);
        return buffer.readLabels();
    }

    public static LabelArray fromByteBuffer(ByteArrayReader buffer) throws ParseException {
        return LabelArray.fromByteBuffer(buffer, buffer.position());
    }

    public static LabelArray fromArray(String[] strings) {
        List<Label> labels = new ArrayList<>(strings.length);
        for (String each: strings) {
            labels.add(Label.create(each));
        }
        return new LabelArray(labels);
    }

    public static LabelArray fromArray(Label[] strings) {
        return new LabelArray(Arrays.asList(strings));
    }

    /**
     * Creates an empty instance of a LabelArray.
     *
     * @return a LabelArray instance
     */
    public static LabelArray create() {
        return new LabelArray(new ArrayList<Label>());
    }

    /**
     * Creates a LabelArray from an array of Labels.
     * @param names one or more labels containing a qualified name.
     *
     * @return a new name array
     */
    public static LabelArray fromList(Label... names) {
        return new LabelArray(Arrays.asList(names));
    }

    /**
     * Creates a LabelArray from an array of Strings.
     * @param names one or more strings containing a qualified name.
     *
     * @return a new name array
     */
    public static LabelArray fromList(String ... names) {
        return  LabelArray.fromArray(names);
    }

}
