package org.atlanmod.salut.cache;

import org.atlanmod.salut.names.ServiceInstanceName;

import java.util.ArrayList;
import java.util.List;

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
