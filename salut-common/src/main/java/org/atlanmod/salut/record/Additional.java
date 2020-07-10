package org.atlanmod.salut.record;

import java.text.ParseException;
import org.atlanmod.salut.io.ByteArrayReader;

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
