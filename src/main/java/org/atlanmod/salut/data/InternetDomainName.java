package org.atlanmod.salut.data;

import org.atlanmod.salut.mdns.NameArray;

/**
 * Host name in the strict sense to mean a fully qualified domain name that
 * has an IPv4 or IPv6 getAddress record.
 *
 */
public class InternetDomainName implements DomainName {

    private final NameArray names;

    public InternetDomainName(NameArray names) {
        this.names = names;
    }

    @Override
    public String toString() {
        return "InternetDomainName{" +
                "data=" + names +
                '}';
    }

    @Override
    public NameArray toNameArray() {
        return names;
    }
}
