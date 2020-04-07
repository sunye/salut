package org.atlanmod.salut.mdns;

import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.names.ServiceInstanceName;
import org.atlanmod.salut.io.UnsignedShort;

public interface ServerSelectionRecord extends NormalRecord {

    /**
     * Returns the getPort number where the service is running.
     */
    UnsignedShort port();

    /**
     * Returns the service getPriority.
     */
    UnsignedShort priority();

    /**
     * Returns the service getWeight.
     */
    UnsignedShort weight();

    ServiceInstanceName serviceInstanceName();

    Domain serverName();
}
