package org.atlanmod.salut.mdns;

import fr.inria.atlanmod.commons.Preconditions;
import fr.inria.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.salut.io.ByteArrayBuffer;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class NameArray {

    private final List<String> names;

    private NameArray(List<String> names) {
        this.names = names;
    }


    public int size() {
        return names.size();
    }

    public boolean contains(String aLabel) {
        return names.contains(aLabel);
    }

    @Override
    public String toString() {
        return "NameArray{" +
                "names=" + names +
                '}';
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

    public NameArray subArray(int fromIndex, int toIndex) {
        return new NameArray(names.subList(fromIndex, toIndex));
    }

    /**
     * The compression scheme allows a domain names in a message to be
     * represented as either:
     * <p>
     * - a sequence of names ending in a zero octet
     * - a pointer
     * - a sequence of names ending with a pointer
     *
     * @param buffer
     * @return
     */
    public static NameArray fromByteBuffer(ByteArrayBuffer buffer, int position) throws ParseException {
        buffer.position(position);
        List<String> labels = buffer.readLabels();
        return new NameArray(labels);
    }

    public static NameArray fromByteBuffer(ByteArrayBuffer buffer) throws ParseException {
        return NameArray.fromByteBuffer(buffer, buffer.position());
    }

    /**
     * For unit testing
     *
     * @param
     * @return a new name array
     */
    @VisibleForTesting
    public static NameArray fromList(String ... names) {
        return new NameArray(Arrays.asList(names));
    }

}
