package org.atlanmod.salut.cache;

import org.atlanmod.salut.names.ServiceInstanceName;

import java.util.ArrayList;
import java.util.List;

/**
 * The `InstanceEntry` class represents Service Instancen Names.
 */
class InstanceEntry {

    private ServiceInstanceName name;
    public List<InstanceToServerLink> servers = new ArrayList<>();
    public List<Links.ServiceToInstanceLink> services = new ArrayList<>();


    public InstanceEntry(ServiceInstanceName name) {
        this.name = name;
    }

    public ServiceInstanceName getName() {
        return name;
    }
}
