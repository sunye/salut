package org.atlanmod.salut.record;

import java.text.ParseException;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.parser.RecordParser;

public class Authority {
    private final Record record;

    public Authority(Record record) {
        this.record = record;
    }

    public static Authority fromByteBuffer(ByteArrayReader buffer) throws ParseException {
        Record record = RecordParser.fromByteBuffer(buffer);

        return new Authority(record);
    }
}
