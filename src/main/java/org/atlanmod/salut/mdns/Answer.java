package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.ByteArrayBuffer;

import java.text.ParseException;

public class Answer  {
    private final Record record;

    public Answer(Record record) {
        this.record = record;
    }

    public static Answer fromByteBuffer(ByteArrayBuffer buffer) throws ParseException {
        Record record = Record.fromByteBuffer(buffer);

        return new Answer(record);
    }
}
