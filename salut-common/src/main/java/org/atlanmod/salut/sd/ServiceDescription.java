package org.atlanmod.salut.sd;

import java.util.Objects;
import java.util.Optional;
import org.atlanmod.salut.data.ApplicationProtocol;
import org.atlanmod.salut.data.TransportProtocol;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.io.UnsignedShort;
import org.atlanmod.salut.names.InstanceName;

public class ServiceDescription {

    /**
     * ServiceDescription getPort
     */
    private UnsignedShort port;

    public UnsignedShort port() {
        return port;
    }

    /**
     * ServiceDescription getWeight
     */
    private UnsignedShort weight;

    public UnsignedShort weight() {
        return weight;
    }

    /**
     * ServiceDescription getPriority
     */
    private UnsignedShort priority;

    public UnsignedShort priority() {
        return priority;
    }

    /**
     * Time to live
     */
    private UnsignedInt ttl;

    public UnsignedInt ttl() {
        return ttl;
    }

    /**
     * Transport transportProtocol (TCP or UDP)
     */
    private TransportProtocol transportProtocol;

    public TransportProtocol transportProtocol() {
        return transportProtocol;
    }

    /**
     * Application transportProtocol (music, ipp, etc.)
     *
     * @see <a href="http://www.dns-sd.org/servicetypes.html">DNS SRV (RFC 2782) ServiceDescription Types</a>
     */
    private ApplicationProtocol applicationProtocol;

    /**
     * Unqualified service instance data ("mac-book", "printer", etc.)
     */
    private InstanceName instanceName;

    public InstanceName name() {
        return instanceName;
    }

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
    public ServiceDescription(InstanceName instanceName, UnsignedShort port, TransportProtocol transportProtocol, ApplicationProtocol applicationProtocol,
                              Optional<String> subtype, UnsignedShort weight, UnsignedShort priority, UnsignedInt ttl) {

        this.instanceName = instanceName;
        this.port = port;
        this.transportProtocol = transportProtocol;
        this.applicationProtocol = applicationProtocol;
        this.subtype = subtype;
        this.weight = weight;
        this.priority = priority;
        this.ttl = ttl;
    }

    @Override
    public String toString() {
        return "ServiceDescription{" +
            "port=" + port +
            ", weight=" + weight +
            ", priority=" + priority +
            ", ttl=" + ttl +
            ", transportProtocol=" + transportProtocol +
            ", applicationProtocol=" + applicationProtocol +
            ", instanceName=" + instanceName +
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
            transportProtocol == that.transportProtocol &&
            Objects.equals(applicationProtocol, that.applicationProtocol) &&
            Objects.equals(instanceName, that.instanceName) &&
            Objects.equals(subtype, that.subtype);
    }

    @Override
    public int hashCode() {
        return Objects
            .hash(port, weight, priority, ttl, transportProtocol, applicationProtocol, instanceName,
                subtype);
    }
}