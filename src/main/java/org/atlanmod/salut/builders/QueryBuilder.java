package org.atlanmod.salut.builders;

public class QueryBuilder implements SetServiceTransportProtocol,
        SetServiceApplicationProtocol, IQuery {

    @Override
    public IQuery http() {
        return null;
    }

    @Override
    public IQuery airplay() {
        return null;
    }

    @Override
    public IQuery application(String str) {
        return null;
    }

    @Override
    public void run() {

    }

    @Override
    public SetServiceApplicationProtocol udp() {
        return null;
    }

    @Override
    public SetServiceApplicationProtocol tcp() {
        return null;
    }
}
