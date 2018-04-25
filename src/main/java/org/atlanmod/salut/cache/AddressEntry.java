package org.atlanmod.salut.cache;

import java.net.InetAddress;

public abstract class AddressEntry extends AbstractCacheEntry {

    private ReferenceToName names = new ReferenceToName(this);

    public AddressEntry(TimeToLive timeToLive) {
        super(timeToLive);
    }

    public abstract InetAddress address();

    public ReferenceToName getNames() {
        return names;
    }


    static class ReferenceToName extends ManyToManyReference<AddressEntry, HostEntry> {

        public ReferenceToName(AddressEntry container) {
            super(container);
        }

        @Override
        public ManyToManyReference<HostEntry,AddressEntry> opposite(HostEntry opposite) {
            return opposite.getAddresses();
        }
    }
}
