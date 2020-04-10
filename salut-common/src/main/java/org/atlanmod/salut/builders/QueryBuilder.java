package org.atlanmod.salut.builders;

import org.atlanmod.salut.data.ApplicationProtocol;
import org.atlanmod.salut.data.ApplicationProtocolBuilder;
import org.atlanmod.salut.data.IANAApplicationProtocol;
import org.atlanmod.salut.data.TransportProtocol;
import org.atlanmod.salut.domains.Domain;

public class QueryBuilder implements SetServiceTransportProtocol,
    QueryApplicationProtocolModifier, IQuery {

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
    public QueryApplicationProtocolModifier udp() {
        transportProtocol = TransportProtocol.udp;
        return this;
    }

    @Override
    public QueryApplicationProtocolModifier tcp() {
        transportProtocol = TransportProtocol.tcp;
        return this;
    }
}
