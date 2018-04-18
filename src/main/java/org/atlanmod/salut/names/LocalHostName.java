package org.atlanmod.salut.names;

import java.util.Objects;

/**
 * Host name of the form: "single-dns-label.local.".
 *
 */
public class LocalHostName extends HostName {
    private final String name;


    public LocalHostName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "." + LOCAL_STR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalHostName that = (LocalHostName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
