package org.atlanmod.salut.mdns;

import java.net.Inet4Address;
import org.atlanmod.salut.domains.Domain;

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
    Inet4Address address();
}
