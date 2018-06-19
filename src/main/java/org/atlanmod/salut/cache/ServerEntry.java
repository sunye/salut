package org.atlanmod.salut.cache;

import org.atlanmod.salut.data.DomainName;

import java.util.ArrayList;
import java.util.List;

/**
 * The `ServerEntry` represents Servers (Hosts).
 *
 * A Server may have different IP Addresses and may host different service instances.
 *
 */
public class ServerEntry {


    public final List<AddressToServerLink> addresses = new ArrayList<>();
    public final List<InstanceToServerLink> instances = new ArrayList<>();
    private DomainName name;

    public ServerEntry(DomainName name) {
        this.name = name;
    }

    public DomainName getName() {
        return name;
    }
}
