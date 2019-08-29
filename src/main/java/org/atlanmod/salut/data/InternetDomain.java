package org.atlanmod.salut.data;

import org.atlanmod.salut.mdns.LabelArray;

/**
 * Conventional Unicast DNS domain name.
 * Host name in the strict sense to mean a fully qualified domain name that
 * has an IPv4 or IPv6 getAddress record.
 *
 */
public class InternetDomain implements Domain {

    private final LabelArray names;

    public InternetDomain(LabelArray names) {
        this.names = names;
    }

    @Override
    public String toString() {
        return "InternetDomain{" +
                "data=" + names +
                '}';
    }

    @Override
    public LabelArray toNameArray() {
        return names;
    }
}
