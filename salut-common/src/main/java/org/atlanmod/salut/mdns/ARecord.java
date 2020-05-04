package org.atlanmod.salut.mdns;

import org.atlanmod.salut.domains.Host;

public interface ARecord extends NormalRecord {

    /**
     * @return the host for this record.
     */
    Host host();

}
