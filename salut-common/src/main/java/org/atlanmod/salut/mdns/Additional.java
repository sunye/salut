package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.ByteArrayReader;

import java.text.ParseException;

public class Additional {
    private final AbstractRecord record;

    public Additional(AbstractRecord record) {
        this.record = record;
    }

    public static Additional fromByteBuffer(ByteArrayReader buffer) throws ParseException {
        AbstractRecord record = AbstractRecord.fromByteBuffer(buffer);

        return new Additional(record);
    }
}
