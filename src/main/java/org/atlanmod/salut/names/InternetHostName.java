package org.atlanmod.salut.names;

import org.atlanmod.salut.mdns.NameArray;

/**
 * Host name in the strict sense to mean a fully qualified domain name that
 * has an IPv4 or IPv6 address record.
 *
 */
public class InternetHostName extends HostName {

    private final NameArray names;

    public InternetHostName(NameArray names) {
        this.names = names;
    }

    @Override
    public String toString() {
        return "InternetHostName{" +
                "names=" + names +
                '}';
    }
}
