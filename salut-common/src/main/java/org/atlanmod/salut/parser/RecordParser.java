package org.atlanmod.salut.parser;

import java.text.ParseException;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.record.WritableRecord;
import org.atlanmod.salut.record.Record;
import org.atlanmod.salut.record.RecordType;

public class RecordParser {

    /**
     * Creates a sub-instance of AbstractRecord from a ByteArrayReader.
     * The class of the AbstractRecord is determined by the RecordType read from the buffer.
     *
     * @see <a href= "https://tools.ietf.org/html/rfc6762">Multicast DNS</a>
     *
     * @param reader a Byte Array Reader
     * @return A sub-instance of AbstractRecord.
     * @throws ParseException if a parsing error occurs.
     */
    public static Record fromByteBuffer(ByteArrayReader reader) throws ParseException {
        ParserFactory factory =  ParserFactory.getInstance();
        Labels name = Labels.fromByteBuffer(reader);
        RecordType type = reader.readRecordType();
        Parser<WritableRecord> parser = factory.getParser(type);

        Log.trace("Start parsing {0} record", type);
        Record newRecord = parser.parse(name, reader);
        Log.trace("Record parsed: {0}", newRecord);
        return newRecord;
    }
}
