package org.atlanmod.salut.record;

import java.text.ParseException;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.parser.RecordParser;

public class Additional {
    private final Record record;

    public Additional(Record record) {
        this.record = record;
    }

    public static Additional fromByteBuffer(ByteArrayReader buffer) throws ParseException {
        Record record = RecordParser.fromByteBuffer(buffer);

        return new Additional(record);
    }
}
