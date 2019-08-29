package org.atlanmod.salut.mdns;

import org.atlanmod.salut.data.ServiceInstanceName;
import org.atlanmod.salut.data.ServiceName;
import org.atlanmod.salut.data.ServiceType;
import org.atlanmod.salut.io.UnsignedInt;

public class MetaQueryPointerRecord extends PointerRecord {

    public MetaQueryPointerRecord(LabelArray name, QClass qclass, UnsignedInt ttl) {
        super(name, qclass, ttl);
    }

    @Override
    public ServiceInstanceName getServiceInstanceName() {
        return null;
    }

    @Override
    public ServiceName getServiceName() {
        return null;
    }

    @Override
    public ServiceType getServiceType() {
        return null;
    }
}
