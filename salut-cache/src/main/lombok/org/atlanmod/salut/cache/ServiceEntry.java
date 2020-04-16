package org.atlanmod.salut.cache;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.atlanmod.salut.names.PointerName;
import org.atlanmod.salut.names.ServiceName;

/**
 * The `ServiceEntry` class represents Service Names.
 *
 * A service name may be provided by different service instances.
 */
@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
public class ServiceEntry {
    final List<Links.ServiceToInstanceLink> instances = new ArrayList<>();

    @Getter
    private final ServiceName name;

    protected ServiceEntry(ServiceName name) {
        this.name = name;
    }

    protected ServiceEntry(PointerName name) {
        this(name.service());
    }
}
