package org.atlanmod.salut.cache;

import org.atlanmod.salut.names.HostName;

import java.util.Objects;

public class HostEntry extends CacheEntry {

    private final ReferenceToAddress addresses = new ReferenceToAddress(this);
    private final ReferenceToService service = new ReferenceToService(this);
    private final HostName name;

    protected HostEntry(long timeToLive, HostName name) {
        super(timeToLive);
        this.name = name;
    }

    public HostName name() {
        return this.name;
    }

    public ReferenceToAddress getAddresses() {
        return addresses;
    }

    public ReferenceToService getService() {
        return service;
    }

    static class ReferenceToAddress extends ManyToManyReference<HostEntry, AddressEntry> {

        public ReferenceToAddress(HostEntry container) {
            super(container);
        }

        @Override
        public ManyToManyReference<AddressEntry, HostEntry> opposite(AddressEntry address) {
            assert Objects.nonNull(address);

            return address.getNames();
        }
    }

    static class ReferenceToService extends OneToManyReference<HostEntry, ServiceEntry> {
        public ReferenceToService(HostEntry container) {
            super(container);
        }

        @Override
        public ManyToOneReference<ServiceEntry, HostEntry> opposite(ServiceEntry opposite) {
            return opposite.getHost();
        }
    }
}
