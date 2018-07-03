package org.atlanmod.salut.data;

import org.atlanmod.salut.mdns.NameArray;

import java.util.Objects;

/**
 * Host name of the form: "single-dns-label.local.".
 *
 */
public class LocalDomainName implements DomainName {
    private final String name;


    public LocalDomainName(String name) {
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
        LocalDomainName that = (LocalDomainName) other;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public NameArray toNameArray() {
        return NameArray.fromList(this.name, LOCAL_STR);
    }
}
