package org.atlanmod.salut.data;

import org.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.salut.mdns.LabelArray;

import java.util.Objects;

/**
 * Host name of the form: "single-dns-label.local.".
 *
 */
public class LocalDomain implements Domain {
    private final String name;


    protected LocalDomain(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "." + LOCAL_STR;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        LocalDomain that = (LocalDomain) other;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public LabelArray toNameArray() {
        return LabelArray.fromList(this.name, LOCAL_STR);
    }

    @VisibleForTesting
    public static LocalDomain fromString(String name) {
        return new LocalDomain(name);
    }
}
