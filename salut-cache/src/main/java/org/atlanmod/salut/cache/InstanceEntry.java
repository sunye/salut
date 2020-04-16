package org.atlanmod.salut.cache;

import java.util.ArrayList;
import java.util.List;
import org.atlanmod.salut.names.ServiceInstanceName;

/**
 * The `InstanceEntry` class represents Service Instance Names.
 */
class InstanceEntry {

    private ServiceInstanceName name;
    final List<InstanceToServerLink> servers = new ArrayList<>();
    final List<Links.ServiceToInstanceLink> services = new ArrayList<>();

    public InstanceEntry(ServiceInstanceName name) {
        this.name = name;
    }

    public ServiceInstanceName name() {
        return name;
    }
}
