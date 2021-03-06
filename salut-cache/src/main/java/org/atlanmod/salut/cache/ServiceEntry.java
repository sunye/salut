package org.atlanmod.salut.cache;

import org.atlanmod.salut.names.PointerName;
import org.atlanmod.salut.names.ServiceName;

import java.util.ArrayList;
import java.util.List;

/**
 * The `ServiceEntry` class represents Service Names.
 *
 * A service name may be provided by different service instances.
 */
public class ServiceEntry {
    final List<Links.ServiceToInstanceLink> instances = new ArrayList<>();

    private final ServiceName name;

    protected ServiceEntry(ServiceName name) {
        this.name = name;
    }

    protected ServiceEntry(PointerName name) {
        this(name.service());
    }

    public ServiceName name() {
        return name;
    }
}
