package org.atlanmod.salut.mdns;

import org.atlanmod.salut.data.ServiceInstanceName;
import org.atlanmod.salut.data.ServiceName;

public interface PointerRecord extends NormalRecord {

    ServiceInstanceName serviceInstanceName();

    ServiceName serviceName();
}
