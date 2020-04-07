package org.atlanmod.salut.domains;

import org.atlanmod.salut.labels.Labels;

/**
 * Conventional Unicast DNS domain name.
 * Host name in the strict sense to mean a fully qualified domain name that
 * has an IPv4 or IPv6 getAddress record.
 *
 */
public class InternetDomain implements Domain {

    private final Labels names;

    public InternetDomain(Labels names) {
        this.names = names;
    }

    @Override
    public String toString() {
        return "InternetDomain{" +
                "data=" + names +
                '}';
    }

    @Override
    public Labels toLabels() {
        return names;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InternetDomain that = (InternetDomain) o;
        return names.equals(that.names);
    }

    @Override
    public int hashCode() {
        return names.hashCode();
    }
}
