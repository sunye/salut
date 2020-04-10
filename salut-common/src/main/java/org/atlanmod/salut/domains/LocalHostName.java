package org.atlanmod.salut.domains;

import java.util.Objects;
import org.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.salut.labels.Label;
import org.atlanmod.salut.labels.Labels;

/**
 * Host name of the form: "single-dns-label.local.".
 *
 */
public class LocalHostName implements Domain {
    private final Label label;


    protected LocalHostName(String label) {
        this(Label.create(label));
    }

    protected LocalHostName(Label label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label + "." + LOCAL_LABEL;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        LocalHostName that = (LocalHostName) other;
        return Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public Labels toLabels() {
        return Labels.fromList(this.label, LOCAL_LABEL);
    }

    @VisibleForTesting
    public static LocalHostName fromString(String name) {
        return new LocalHostName(name);
    }
}
