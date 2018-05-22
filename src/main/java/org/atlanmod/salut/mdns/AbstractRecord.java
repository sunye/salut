package org.atlanmod.salut.mdns;

import fr.inria.atlanmod.commons.log.Log;
import org.atlanmod.salut.io.ByteArrayBuffer;

import java.text.ParseException;

public abstract class AbstractRecord {

    protected final NameArray names;

    public AbstractRecord(NameArray names) {
        this.names = names;
    }

    public NameArray name() {
        return names;
    }


    /**
     * Creates a sub-instance of AbstractRecord from a ByteArrayBuffer.
     * The class of the AbstractRecord is determined by the RecordType read from the buffer.
     *
     * @see <a href= "https://tools.ietf.org/html/rfc6762">Multicast DNS</a>
     *
     * @param buffer
     * @return A sub-instance of AbstractRecord.
     * @throws ParseException if a parsing error occurs.
     */
    public static AbstractRecord fromByteBuffer(ByteArrayBuffer buffer) throws ParseException {
        ParserFactory factory =  ParserFactory.getInstance();
        NameArray qname = NameArray.fromByteBuffer(buffer);
        RecordType qtype = RecordType.fromByteBuffer(buffer);
        RecordParser<AbstractRecord> parser = factory.getParser(qtype);

        AbstractRecord newRecord = parser.parse(qname, buffer);
        return newRecord;
    }
}
