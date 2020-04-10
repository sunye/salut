package org.atlanmod.salut.cache;

import java.util.ArrayList;
import java.util.List;
import org.atlanmod.salut.domains.Domain;

/**
 * The `ServerEntry` represents Servers (Hosts).
 *
 * A Server may have different IP Addresses and may host different service instances.
 *
 */
public class ServerEntry {


    public final List<AddressToServerLink> addresses = new ArrayList<>();
    public final List<InstanceToServerLink> instances = new ArrayList<>();
    private Domain name;

    public ServerEntry(Domain name) {
        this.name = name;
    }

    public Domain getName() {
        return name;
    }
}
