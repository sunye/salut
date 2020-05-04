package org.atlanmod.salut.mdns;

import org.atlanmod.salut.domains.Host;
import org.atlanmod.salut.sd.ServiceDescription;

public class RecordFactory {

    private final Host localHost;

    public RecordFactory(Host host) {
        this.localHost = host;
    }

    /**
     * Creates a SRV record from a service description
     *
     * @param service the service description
     * @return an instance of the SRV record
     */
    public ServerSelectionRecord createServerSelectionRecord(ServiceDescription service) {
        // FIXME
        //@formatter:off
        return new BaseServerSelectionRecord(
                QClass.IN,
                service.ttl(),
                service.priority(),
                service.weight(),
                service.port(),
                localHost.name(),
                null);
        //@formatter:on
    }

    /**
     * Creates a PTR record from a service description
     *
     * @param service the service description
     * @return an instance of the PTR record
     */
    public PointerRecord createPointerRecord(ServiceDescription service) {
        // FIXME
        return new BasePointerRecord(
            QClass.IN,
            service.ttl(),
            null,
            null);
    }

    /**
     * Creates an A record from a service description
     *
     * @param service the service description
     * @return an instance of the A record
     */
    public ARecord createAddressRecord(ServiceDescription service) {
        return new BaseARecord(
            QClass.IN,
            service.ttl(),
            localHost
        );
    }
}
