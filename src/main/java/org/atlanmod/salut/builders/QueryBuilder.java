package org.atlanmod.salut.builders;

import org.atlanmod.salut.data.*;

public class QueryBuilder implements SetServiceTransportProtocol,
        SetServiceApplicationProtocol, IQuery {

    private ApplicationProtocol applicationProtocol;
    private TransportProtocol transportProtocol;
    private Domain domain;

    @Override
    public IQuery http() {
        applicationProtocol = ApplicationProtocolBuilder.fromApplicationProtocol(IANAApplicationProtocol.http);
        return this;
    }

    @Override
    public IQuery airplay() {
        applicationProtocol = ApplicationProtocolBuilder.fromApplicationProtocol(IANAApplicationProtocol.airplay);
        return this;
    }

    @Override
    public IQuery application(String str) {
        applicationProtocol = ApplicationProtocolBuilder.fromString(str);
        return this;
    }

    @Override
    public void run() {
        //domain = DomainBuilder.
    }

    @Override
    public SetServiceApplicationProtocol udp() {
        transportProtocol = TransportProtocol.udp;
        return this;
    }

    @Override
    public SetServiceApplicationProtocol tcp() {
        transportProtocol = TransportProtocol.tcp;
        return this;
    }
}
