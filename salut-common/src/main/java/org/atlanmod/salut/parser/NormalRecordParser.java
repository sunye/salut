package org.atlanmod.salut.parser;

import java.text.ParseException;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.record.NormalRecord;
import org.atlanmod.salut.record.QClass;

public abstract class NormalRecordParser<T extends NormalRecord> implements Parser<T> {

    //@formatter:off
    protected Labels        labels;
    protected QClass        qclass;
    protected UnsignedInt   ttl;
    protected int           dataLength;
    //@formatter:on

    @Override
    public T parse(Labels name, ByteArrayReader buffer) throws ParseException {
        this.labels = name;
        this.parseFixedPart(buffer);
        this.parseVariablePart(buffer);
        return build();
    }

    /**
     * Parses the fixed part of a record. The fixed part is composed of: - The QClass - The time
     * to live - The length of the RData part (the variable part).
     *
     * @param reader a ByteArrayReader containing the record to be parsed.
     * @throws ParseException if there is a parsing error.
     */
    protected void parseFixedPart(ByteArrayReader reader) throws ParseException {
        qclass = QClass.fromByteBuffer(reader);
        ttl = reader.getUnsignedInt();
        dataLength = reader.getUnsignedShort().intValue();
    }

    /**
     * Common behavior for the variable part parsing (does nothing). The specific behavior will
     * be handled by the subclasses.
     *
     * @param reader a ByteArrayReader containing the record to be parsed.
     * @throws ParseException if there is a parsing error.
     */
    protected void parseVariablePart(ByteArrayReader reader) throws ParseException {

    }

    protected abstract T build();
}
