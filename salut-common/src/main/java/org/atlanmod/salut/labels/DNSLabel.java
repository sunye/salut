package org.atlanmod.salut.labels;


import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class DNSLabel implements Label {

    /**
     * The maximum length of labels
     **/
    public static final int MAX_LENGTH = 63;
    /**
     * DNS Labels should contain only letters, digits, and hyphens
     **/
    private static final String VALID_DNSLABEL_EXPRESSION = "[0-9A-Za-z\\-]+";
    private final String label;

    protected DNSLabel(String string) {
        label = string;
    }

    private static void checkLength(String labelToValidate) {
        if (labelToValidate.length() > DNSLabel.MAX_LENGTH) {
            throw new IllegalArgumentException(
                "Invalid label length (too long): " + labelToValidate.length());
        }
    }

    public static DNSLabel create(String label) {
        checkLength(label);
        return new DNSLabel(label);
    }

    public static DNSLabel create(byte[] bytes) {
        String label = new String(bytes, StandardCharsets.UTF_8);
        return create(label);
    }

    public boolean isValid() {
        return this.label.matches(VALID_DNSLABEL_EXPRESSION);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DNSLabel)) {
            return false;
        }
        DNSLabel label1 = (DNSLabel) o;
        return Objects.equals(label, label1.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public String toString() {
        return label;
    }

    /**
     * The size, in octets, of this label when written on a byte array, including 1 byte for the
     * label size
     *
     * @return The size in octets of the serialized form of this label
     */
    @Override
    public int dataLength() {
        return label.length() + 1;
    }
}
