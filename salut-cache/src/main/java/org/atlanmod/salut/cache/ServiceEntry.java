package org.atlanmod.salut.cache;

import java.util.ArrayList;
import java.util.List;
import org.atlanmod.salut.names.PointerName;
import org.atlanmod.salut.names.ServiceName;

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
        return this.name;
    }

    @Override
    public String toString() {
        return "ServiceEntry{" +
                ", name=" + name +
                "instances=" + instances +
                '}';
    }

}
