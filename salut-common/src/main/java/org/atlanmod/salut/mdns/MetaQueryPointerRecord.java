package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.names.PointerName;
import org.atlanmod.salut.names.ServiceInstanceName;
import org.atlanmod.salut.io.UnsignedInt;

public class MetaQueryPointerRecord extends AbstractPointerRecord {

    public MetaQueryPointerRecord(QClass qclass, UnsignedInt ttl) {
        super(qclass, ttl);
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
    public void writeOn(ByteArrayWriter writer) {
        // TODO
        throw new UnsupportedOperationException();
    }
}
