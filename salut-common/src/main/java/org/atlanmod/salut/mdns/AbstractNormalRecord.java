package org.atlanmod.salut.mdns;

import java.text.ParseException;
import java.util.Objects;
import org.atlanmod.salut.cache.TimeToLive;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;

/**
 * @formatter:off
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
 * @formatter:off
 */
public abstract class AbstractNormalRecord extends AbstractRecord implements NormalRecord {

    protected final QClass qclass;
    protected final TimeToLive ttl;

    AbstractNormalRecord(QClass qclass, UnsignedInt ttl) {
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
        return "{" + qclass + ", " + ttl + '}';
    }

    @Override
    public boolean equals(Object other) {
        //@formatter:off
        if (this == other) {return true;}
        if (!(other instanceof AbstractNormalRecord)) {return false;}
        //@formatter:off

        AbstractNormalRecord that = (AbstractNormalRecord) other;
        return
            qclass == that.qclass &&
            ttl.equals(that.ttl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qclass, ttl);
    }

    protected abstract static class NormalRecordParser<T extends NormalRecord> implements RecordParser<T> {

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
}
