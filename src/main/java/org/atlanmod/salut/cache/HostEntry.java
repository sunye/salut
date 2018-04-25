package org.atlanmod.salut.cache;

import org.atlanmod.salut.data.DomainName;

import java.util.Objects;

public class HostEntry extends AbstractCacheEntry {

    private final ReferenceToAddress addresses = new ReferenceToAddress(this);
    private final ReferenceToService service = new ReferenceToService(this);
    private final DomainName name;

    protected HostEntry(TimeToLive timeToLive, DomainName name) {
        super(timeToLive);
        this.name = name;
    }

    public DomainName name() {
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
