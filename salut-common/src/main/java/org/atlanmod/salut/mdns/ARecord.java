package org.atlanmod.salut.mdns;

import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.IPv4Address;

public interface ARecord extends NormalRecord {

    /**
     * @return the server name.
     */
    Domain serverName();

    /**
     * Accessor for field getAddress
     *
     * @return a Inet4Address instance
     */
    IPv4Address address();
}
