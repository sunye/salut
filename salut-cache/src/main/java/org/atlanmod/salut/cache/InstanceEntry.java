package org.atlanmod.salut.cache;

import java.util.ArrayList;
import java.util.List;
import org.atlanmod.salut.names.ServiceInstanceName;

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
