package org.atlanmod.salut.cache;

import org.atlanmod.salut.io.UnsignedShort;

/**
 * Represents a link between a service instance and a server.
 * <p>
 * The link specifies the port where the service is running, the priority of the target host
 * and the weight.
 */
public class InstanceToServerLink extends AbstractLink {
    private InstanceEntry instance;
    private ServerEntry server;
    private UnsignedShort weight;
    private UnsignedShort priority;
    private UnsignedShort port;

    public InstanceToServerLink(TimeToLive ttl, InstanceEntry instance, ServerEntry server,
                                UnsignedShort weight, UnsignedShort priority, UnsignedShort port) {
        super(ttl);
        this.instance = instance;
        this.server = server;
        this.weight = weight;
        this.priority = priority;
        this.port = port;
    }

    public InstanceEntry getInstance() {
        return instance;
    }

    public ServerEntry getServer() {
        return server;
    }

    public UnsignedShort getWeight() {
        return weight;
    }

    public UnsignedShort getPriority() {
        return priority;
    }

    public UnsignedShort getPort() {
        return port;
    }
}
