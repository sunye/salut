package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.ByteArrayBuffer;

import java.text.ParseException;

public class Authority {
    private final AbstractRecord record;

    public Authority(AbstractRecord record) {
        this.record = record;
    }

    public static Authority fromByteBuffer(ByteArrayBuffer buffer) throws ParseException {
        AbstractRecord record = AbstractRecord.fromByteBuffer(buffer);

        return new Authority(record);
    }
}
