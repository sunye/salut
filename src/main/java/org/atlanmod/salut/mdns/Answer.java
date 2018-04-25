package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.ByteArrayBuffer;

import java.text.ParseException;

public class Answer  {
    private final AbstractRecord record;

    public Answer(AbstractRecord record) {
        this.record = record;
    }

    public static Answer fromByteBuffer(ByteArrayBuffer buffer) throws ParseException {
        AbstractRecord record = AbstractRecord.fromByteBuffer(buffer);

        return new Answer(record);
    }
}
