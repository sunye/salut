package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.names.PointerName;
import org.atlanmod.salut.names.ServiceInstanceName;

/**
 * The `ReverseLookupPointerRecord` represents pointer record (PTR) used for reverse lookups.
 *
 */
class ReverseLookupPointerRecord extends AbstractPointerRecord {

    ReverseLookupPointerRecord(Labels name, QClass qclass, UnsignedInt ttl) {
        super(name, qclass, ttl);
    }

    @Override
    public ServiceInstanceName serviceInstanceName() {
        return null;
    }

    @Override
    public PointerName pointerName() {
        return null;
    }

    @Override
    public void writeOne(ByteArrayWriter writer) {
        // TODO
        throw new UnsupportedOperationException();
    }
}
