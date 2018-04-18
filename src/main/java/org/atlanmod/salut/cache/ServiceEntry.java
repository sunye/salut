package org.atlanmod.salut.cache;

import org.atlanmod.salut.io.UnsignedShort;

class ServiceEntry extends CacheEntry {

    private UnsignedShort weight;
    private UnsignedShort priority;
    private UnsignedShort port;

    private ReferenceToHost host = new ReferenceToHost(this);
/*
 Ed’s Party Mix._music._tcp.local. service.

 domain = "local"

 Service type = {transport protocol, application protocol}

 Human readable service instance names = "Ed’s Party Mix"

 */

    public ServiceEntry(long timeToLive, UnsignedShort weight, UnsignedShort priority, UnsignedShort port) {
        super(timeToLive);
        this.weight = weight;
        this.priority = priority;
        this.port = port;
    }

    public ReferenceToHost getHost() {
        return host;
    }

    static class ReferenceToHost extends ManyToOneReference<ServiceEntry, HostEntry> {

        public ReferenceToHost(ServiceEntry container) {
            super(container);
        }

        @Override
        public OneToManyReference<HostEntry, ServiceEntry> opposite(HostEntry opposite) {
            return opposite.getService();
        }
    }
}
