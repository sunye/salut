package org.atlanmod.salut.cache;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.atlanmod.salut.names.ServiceInstanceName;

/**
 * The `InstanceEntry` class represents Service Instance Names.
 */
@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
class InstanceEntry {

    @Getter
    private ServiceInstanceName name;
    final List<InstanceToServerLink> servers = new ArrayList<>();
    final List<Links.ServiceToInstanceLink> services = new ArrayList<>();

    public InstanceEntry(ServiceInstanceName name) {
        this.name = name;
    }

}
