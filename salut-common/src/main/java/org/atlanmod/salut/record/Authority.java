package org.atlanmod.salut.record;

import java.text.ParseException;
import org.atlanmod.salut.io.ByteArrayReader;

public class Authority {
    private final AbstractRecord record;

    public Authority(AbstractRecord record) {
        this.record = record;
    }

    public static Authority fromByteBuffer(ByteArrayReader buffer) throws ParseException {
        AbstractRecord record = AbstractRecord.fromByteBuffer(buffer);

        return new Authority(record);
    }
}
