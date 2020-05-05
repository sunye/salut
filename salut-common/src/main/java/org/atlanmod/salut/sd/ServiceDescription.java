package org.atlanmod.salut.sd;

import org.atlanmod.salut.data.ApplicationProtocol;
import org.atlanmod.salut.data.TransportProtocol;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.io.UnsignedShort;
import org.atlanmod.salut.names.InstanceName;
import org.atlanmod.salut.names.ServiceName;

import java.util.Objects;
import java.util.Optional;

public class ServiceDescription {

    // @formatter:off
    private final UnsignedShort   port;       // Service port
    private final UnsignedShort   weight;     // Service weight
    private final UnsignedShort   priority;   // Service priority
    private final UnsignedInt     ttl;        // Time to live
    private final ServiceName     service;    // Service Name (or type): transport + application protocols
    private final InstanceName    instance;   // Unqualified service instance data ("mac-book", "printer", etc.)
    // @formatter:on

    /**
     * ServiceDescription subtype. For instance, printer (subtype of http or soap).
     */
    private Optional<String> subtype;

    /**
     * Creates a new service.
     *
     * @param instanceName
     * @param port
     * @param transportProtocol
     * @param applicationProtocol
     * @param subtype
     * @param weight
     * @param priority
     */
    public ServiceDescription(InstanceName instanceName, UnsignedShort port,
        TransportProtocol transportProtocol, ApplicationProtocol applicationProtocol,
        Optional<String> subtype, UnsignedShort weight, UnsignedShort priority, UnsignedInt ttl) {

        this.service = transportProtocol.with(applicationProtocol);
        this.instance = instanceName;
        this.port = port;
        this.subtype = subtype;
        this.weight = weight;
        this.priority = priority;
        this.ttl = ttl;
    }

    public UnsignedShort port() {
        return port;
    }

    public UnsignedShort weight() {
        return weight;
    }

    public UnsignedShort priority() {
        return priority;
    }

    public UnsignedInt ttl() {
        return ttl;
    }

    public InstanceName instanceName() {
        return instance;
    }

    public ServiceName serviceName() {
        return service;
    }

    @Override
    public String toString() {
        return "ServiceDescription{" +
            "port=" + port +
            ", weight=" + weight +
            ", priority=" + priority +
            ", ttl=" + ttl +
            ", service=" + service +
            ", instance=" + instance +
            ", subtype=" + subtype +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceDescription that = (ServiceDescription) o;
        return Objects.equals(port, that.port) &&
            Objects.equals(weight, that.weight) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(ttl, that.ttl) &&
            Objects.equals(service, that.service) &&
            Objects.equals(instance, that.instance) &&
            Objects.equals(subtype, that.subtype);
    }

    @Override
    public int hashCode() {
        return Objects
            .hash(port, weight, priority, ttl, service, instance, subtype);
    }
}
