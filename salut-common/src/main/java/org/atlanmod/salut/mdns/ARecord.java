package org.atlanmod.salut.mdns;

import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.Host;
import org.atlanmod.salut.domains.IPv4Address;

public interface ARecord extends NormalRecord {

    /**
     * @return the host for this record.
     */
    Host host();

}
