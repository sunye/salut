package org.atlanmod.salut.answer;

import java.text.ParseException;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.parser.RecordParser;
import org.atlanmod.salut.record.Record;

public class AnswerBuilder {

    public static Answer fromByteBuffer(ByteArrayReader buffer) throws ParseException {
        Record record = RecordParser.fromByteBuffer(buffer);

        return new Answer(record);
    }
}
