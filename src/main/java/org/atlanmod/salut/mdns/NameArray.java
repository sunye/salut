package org.atlanmod.salut.mdns;

import fr.inria.atlanmod.commons.Preconditions;
import fr.inria.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.salut.io.ByteArrayReader;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class NameArray {

    private final List<String> names;

    private NameArray(List<String> names) {
        this.names = names;
    }


    public int size() {
        return names.size();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NameArray)) return false;
        NameArray nameArray = (NameArray) o;
        return Objects.equals(names, nameArray.names);
    }

    @Override
    public int hashCode() {
        return Objects.hash(names);
    }

    public boolean contains(String aLabel) {
        return names.contains(aLabel);
    }

    @Override
    public String toString() {
        return "NameArray{" +
                "data=" + names +
                '}';
    }

    /**
     * Adds a name (label) to this NameArray.
     *
     * @param name the name (label) to add.
     */
    public void add(String name) {
        Preconditions.checkNotNull(name);

        this.names.add(name);
    }

    /**
     * Adds (concatenates) all labels of another NameArray to this one.
     *
     * @param nameArray the NameArray containing the labels to add.
     */
    public void addAll(NameArray nameArray) {
        Preconditions.checkNotNull(nameArray);

        this.names.addAll(nameArray.getNames());
    }

    public String get(int index ) {
        Preconditions.checkElementIndex(index, names.size());

        return names.get(index);
    }

    public String lastName() {
        Preconditions.checkArgument(!names.isEmpty());

        return names.get(names.size() - 1);
    }

    public String firstName() {
        Preconditions.checkArgument(!names.isEmpty());

        return names.get(0);
    }

    public List<String> getNames() {
        return names;
    }

    public NameArray subArray(int fromIndex, int toIndex) {
        return new NameArray(names.subList(fromIndex, toIndex));
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
    public static NameArray fromByteBuffer(ByteArrayReader buffer, int position) throws ParseException {
        buffer.position(position);
        return buffer.readLabels();
    }

    public static NameArray fromByteBuffer(ByteArrayReader buffer) throws ParseException {
        return NameArray.fromByteBuffer(buffer, buffer.position());
    }

    public static NameArray fromArray(String[] strings) {
        return new NameArray(Arrays.asList(strings));
    }

    /**
     * Creates an empty instance of a NameArray.
     *
     * @return a NameArray instance
     */
    public static NameArray create() {
        return new NameArray(new ArrayList<String>());
    }

    /**
     * For unit testing
     *
     * @param names one or more strings containing a qualified name.
     *
     * @return a new name array
     */
    @VisibleForTesting
    public static NameArray fromList(String ... names) {
        return new NameArray(Arrays.asList(names));
    }

}
