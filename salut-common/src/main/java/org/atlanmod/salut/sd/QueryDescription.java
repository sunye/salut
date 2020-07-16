package org.atlanmod.salut.sd;

import org.atlanmod.salut.data.ApplicationProtocol;
import org.atlanmod.salut.data.TransportProtocol;
import org.atlanmod.salut.names.ServiceName;

public class QueryDescription {

    private final ServiceName service;    // Service Name (or type): transport + application protocols

    public QueryDescription(TransportProtocol transport, ApplicationProtocol application) {
        this.service = transport.with(application);
    }

    public ServiceName serviceName() {
        return service;
    }
}
