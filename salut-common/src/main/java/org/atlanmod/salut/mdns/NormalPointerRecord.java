package org.atlanmod.salut.mdns;

import org.atlanmod.salut.data.ServiceInstanceName;
import org.atlanmod.salut.data.ServiceName;
import org.atlanmod.salut.data.ServiceType;
import org.atlanmod.salut.io.UnsignedInt;

class NormalPointerRecord extends PointerRecord {
    /**
     * A <domain-name> which points to some location in the domain name space.
     *
     * The name of the service instance.
     */
    private ServiceInstanceName  pointerName;
    private ServiceName serviceName;

    NormalPointerRecord(LabelArray name, QClass qclass, UnsignedInt ttl, ServiceInstanceName pointerName, ServiceName serverName) {
        super(name, qclass, ttl);
        this.pointerName = pointerName;
        this.serviceName = serverName;
    }

    public ServiceInstanceName getServiceInstanceName() {
        return this.pointerName;
    }

    public ServiceName getServiceName() {
        return this.serviceName;
    }

    @Override
    public ServiceType getServiceType() {
        return serviceName.getServiceType();
    }

    /**
     * Returns a `String` object representing this `NormalPointerRecord`.
     * @return  a string representation of this object.
     */
    @Override
    public String toString() {
        return "NormalPointerRecord{" +
                "pointerName=" + pointerName +
                ", serviceName=" + serviceName +
                '}';
    }
}
