package org.atlanmod.salut.mdns;

import fr.inria.atlanmod.commons.log.Log;
import org.atlanmod.salut.io.ByteArrayBuffer;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRecord {
    private static final Map<RecordType, RecordParser> RECORD_PARSERS = new HashMap<>();

    /**
     * Null parser, used to skip unsupported QTypes.
     */
    private static final RecordParser<NullRecord> NULL_PARSER = NullRecord.parser();

    static {
        RECORD_PARSERS.put(RecordType.A, ARecord.parser());
        RECORD_PARSERS.put(RecordType.AAAA, AAAARecord.parser());
        RECORD_PARSERS.put(RecordType.PTR, PointerRecord.parser());
        RECORD_PARSERS.put(RecordType.OPT, PseudoRecord.parser());
        RECORD_PARSERS.put(RecordType.SRV, ServerSelectionRecord.parser());
        RECORD_PARSERS.put(RecordType.TXT, TextRecord.parser());
    }

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
        NameArray qname = NameArray.fromByteBuffer(buffer);
        RecordType qtype = RecordType.fromByteBuffer(buffer);
        AbstractRecord newRecord;
        if (RECORD_PARSERS.containsKey(qtype)) {
            RecordParser<AbstractRecord> parser = RECORD_PARSERS.get(qtype);
            newRecord = parser.parse(qname, buffer);

        } else {
            newRecord = NULL_PARSER.parse(qname, buffer);
            Log.warn("Ignored Unsupported RecordType {0} ", qtype);
        }
        Log.info("Parsed AbstractRecord {0}", newRecord);
        return newRecord;
    }
}
