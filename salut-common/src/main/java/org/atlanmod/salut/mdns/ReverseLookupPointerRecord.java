package org.atlanmod.salut.mdns;

import org.atlanmod.salut.data.ServiceInstanceName;
import org.atlanmod.salut.data.ServiceName;
import org.atlanmod.salut.io.UnsignedInt;

/**
 * The `ReverseLookupPointerRecord` represents pointer record (PTR) used for reverse lookups.
 *
 */
class ReverseLookupPointerRecord extends AbstractPointerRecord {

    ReverseLookupPointerRecord(LabelArray name, QClass qclass, UnsignedInt ttl) {
        super(name, qclass, ttl);
    }

    @Override
    public ServiceInstanceName serviceInstanceName() {
        return null;
    }

    @Override
    public ServiceName serviceName() {
        return null;
    }

}
