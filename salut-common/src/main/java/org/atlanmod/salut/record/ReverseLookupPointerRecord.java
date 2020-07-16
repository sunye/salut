package org.atlanmod.salut.record;

import org.atlanmod.commons.Throwables;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.names.PointerName;
import org.atlanmod.salut.names.ServiceInstanceName;

/**
 * The `ReverseLookupPointerRecord` represents pointer record (PTR) used for reverse lookups.
 *
 */
public class ReverseLookupPointerRecord extends AbstractPointerRecord {

    public ReverseLookupPointerRecord(QClass qclass, UnsignedInt ttl) {
        super(qclass, ttl);
    }

    @Override
    public ServiceInstanceName serviceInstanceName() {
        Throwables.notImplementedYet("ReverseLookupPointerRecord");
        return null;
    }

    @Override
    public PointerName pointerName() {
        Throwables.notImplementedYet("ReverseLookupPointerRecord");
        return null;
    }

    @Override
    public Labels name() {
        Throwables.notImplementedYet("ReverseLookupPointerRecord");
        return null;
    }

    @Override
    public void writeOn(ByteArrayWriter writer) {
        // TODO
        Throwables.notImplementedYet("writeOn()");
    }
}
