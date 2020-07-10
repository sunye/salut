package org.atlanmod.salut.record;

import java.text.ParseException;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.labels.Labels;

public abstract class AbstractRecord implements Record {

    public abstract void writeOn(ByteArrayWriter writer);


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
    public static AbstractRecord fromByteBuffer(ByteArrayReader reader) throws ParseException {
        ParserFactory factory =  ParserFactory.getInstance();
        Labels qname = Labels.fromByteBuffer(reader);
        RecordType qtype = reader.readRecordType();
        RecordParser<AbstractRecord> parser = factory.getParser(qtype);

        Log.info("Start parsing {0} record", qtype);
        AbstractRecord newRecord = parser.parse(qname, reader);
        Log.info("Record parsed: {0}", newRecord);
        return newRecord;
    }
}
