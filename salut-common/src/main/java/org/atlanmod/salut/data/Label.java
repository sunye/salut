package org.atlanmod.salut.data;


import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Label implements Serializable {
    /**
     * DNS Labels should contain only letters, digits, and hyphens
     **/
    private static final String VALID_DNSLABEL_EXPRESSION = "[0-9A-Za-z\\-]+";
    /**
     * The maximum length of labels
     **/
    public static final int MAX_LENGTH = 63;

    private final String label;

    protected Label(String string) {
        label = string;
    }

    private static void checkLength(String labelToValidate) {
        if (labelToValidate.length() > Label.MAX_LENGTH) {
            throw new IllegalArgumentException("Invalid label length (too long): " + labelToValidate.length());
        }
    }

    public static Label create(String label) {
        checkLength(label);
        return new Label(label);
    }

    public static Label create(byte[] bytes) {
        String label = new String(bytes, StandardCharsets.UTF_8);
        return create(label);
    }

    public boolean isValidDNSLabel() {
        return this.label.matches(VALID_DNSLABEL_EXPRESSION);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Label)) return false;
        Label label1 = (Label) o;
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
}
