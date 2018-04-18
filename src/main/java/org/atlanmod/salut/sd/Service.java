package org.atlanmod.salut.sd;

import org.atlanmod.salut.names.ServiceType;
import org.atlanmod.salut.names.TransportProtocol;

import java.util.Optional;

public class Service {

    /**
     * Service port
     */
    private int port;

    /**
     * Service weight
     */
    private int weight;

    /**
     * Service priority
     */
    private int priority;

    /**
     * Transport transportProtocol (TCP or UDP)
     */
    private TransportProtocol transportProtocol;

    /**
     * Application transportProtocol (music, ipp, etc.)
     *
     * @see <a href="http://www.dns-sd.org/servicetypes.html">DNS SRV (RFC 2782) Service Types</a>
     */
    private ServiceType serviceType;

    /**
     * Unqualified service instance names ("mac-book", "printer", etc.)
     */
    private String instanceName;

    /**
     * Service subtype. For instance, printer (subtype of http or soap).
     */
    private Optional<String> subtype;

    /**
     * Creates a new service.
     *
     * @param instanceName
     * @param port
     * @param transportProtocol
     * @param serviceType
     * @param subtype
     * @param weight
     * @param priority
     */
    public Service(String instanceName, int port, TransportProtocol transportProtocol, ServiceType serviceType,
                   Optional<String> subtype, int weight, int priority) {

        this.instanceName = instanceName;
        this.port = port;
        this.transportProtocol = transportProtocol;
        this.serviceType = serviceType;
        this.subtype = subtype;
        this.weight = weight;
        this.priority = priority;
    }
}
