package org.atlanmod.salut.record;

import org.atlanmod.commons.Throwables;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.names.PointerName;
import org.atlanmod.salut.names.ServiceInstanceName;

public class MetaQueryPointerRecord extends AbstractPointerRecord {

    public MetaQueryPointerRecord(QClass qclass, UnsignedInt ttl) {
        super(qclass, ttl);
    }

    @Override
    public ServiceInstanceName serviceInstanceName() {
        Throwables.notImplementedYet("MetaQueryPointerRecord");
        return null;
    }

    @Override
    public PointerName pointerName() {
        Throwables.notImplementedYet("MetaQueryPointerRecord");
        return null;
    }

    @Override
    public void writeOn(ByteArrayWriter writer) {
        // TODO
        Throwables.notImplementedYet("MetaQueryPointerRecord");
    }

    @Override
    public Labels name() {
        Throwables.notImplementedYet("MetaQueryPointerRecord");
        return null;
    }
}
