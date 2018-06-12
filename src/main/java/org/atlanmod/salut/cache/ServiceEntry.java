package org.atlanmod.salut.cache;

import org.atlanmod.salut.data.ServiceName;

import java.util.ArrayList;
import java.util.List;

/**
 * The `ServiceEntry` class represents Service Types.
 *
 * A service type may be provided by different service instances.
 */
public class ServiceEntry {
    public final List<Links.ServiceToInstanceLink> instances = new ArrayList<>();

    private final ServiceName name;

    protected ServiceEntry(ServiceName name) {
        this.name = name;
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
