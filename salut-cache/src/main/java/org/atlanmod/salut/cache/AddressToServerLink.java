package org.atlanmod.salut.cache;

/**
 * Represents a link between an Address and a Server.
 */
public class AddressToServerLink extends AbstractLink {
    private AddressEntry address;
    private ServerEntry server;

    public AddressToServerLink(TimeToLive ttl, AddressEntry address, ServerEntry server) {
        super(ttl);
        this.address = address;
        this.server = server;
    }

    public AddressEntry getAddress() {
        return  address;
    }

    public ServerEntry getServer() {
        return server;
    }
}
