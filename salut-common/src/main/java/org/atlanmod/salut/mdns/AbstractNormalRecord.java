package org.atlanmod.salut.mdns;

import org.atlanmod.salut.cache.TimeToLive;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.UnsignedInt;

import java.text.ParseException;
import java.util.Objects;

/**
 *
 * All RRs have the same top level format shown below:
 *
 *                                     1  1  1  1  1  1
 *       0  1  2  3  4  5  6  7  8  9  0  1  2  3  4  5
 *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *     |                                               |
 *     /                                               /
 *     /                      NAME                     /
 *     |                                               |
 *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *     |                      TYPE                     |
 *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *     |                     CLASS                     |
 *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *     |                      TTL                      |
 *     |                                               |
 *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *     |                   RDLENGTH                    |
 *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--|
 *     /                     RDATA                     /
 *     /                                               /
 *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *
 */
public abstract class AbstractNormalRecord extends AbstractRecord implements NormalRecord {

    protected final QClass qclass;
    protected final TimeToLive ttl;

    AbstractNormalRecord(LabelArray name, QClass qclass, UnsignedInt ttl) {
        super(name);
        this.qclass = qclass;
        this.ttl = TimeToLive.fromSeconds(ttl);
    }

    @Override
    public TimeToLive ttl() {
        return this.ttl;
    }

    @Override
    public QClass qclass() {
        return qclass;
    }

    @Override
    public String toString() {
        return "{" +
                "data=" + names +
                ", qclass=" + qclass +
                ", getTtl=" + ttl +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractNormalRecord)) return false;
        AbstractNormalRecord that = (AbstractNormalRecord) o;
        return qclass == that.qclass;

    }

    @Override
    public int hashCode() {

        return Objects.hash(qclass, ttl);
    }

    protected static abstract class NormalRecordParser<T extends NormalRecord> implements RecordParser<T> {

        protected LabelArray name;
        protected QClass qclass;
        protected UnsignedInt ttl;
        protected int dataLength;

        @Override
        public T parse(LabelArray name, ByteArrayReader buffer) throws ParseException {
            this.name = name;
            this.parseFixedPart(buffer);
            this.parseVariablePart(buffer);
            return build();
        }

        /**
         * Parses the fixed part of a record. The fixed part is composed of:
         *     - The QClass
         *     - The time to live
         *     - The length of the RData part (the variable part).
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
         * Common behavior for the variable part parsing (does nothing).
         * The specific behavior will be handled by the subclasses.
         *
         * @param reader a ByteArrayReader containing the record to be parsed.
         * @throws ParseException if there is a parsing error.
         */
        protected void parseVariablePart(ByteArrayReader reader) throws ParseException {

        }

        abstract protected T build();
    }
}
