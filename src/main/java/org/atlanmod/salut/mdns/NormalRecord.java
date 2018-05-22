package org.atlanmod.salut.mdns;

import org.atlanmod.salut.cache.TimeToLive;
import org.atlanmod.salut.io.ByteArrayBuffer;
import org.atlanmod.salut.io.UnsignedInt;

import java.text.ParseException;

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
public abstract class NormalRecord extends AbstractRecord {

    protected final QClass qclass;
    protected final TimeToLive ttl;

    NormalRecord(NameArray name, QClass qclass, UnsignedInt ttl) {
        super(name);
        this.qclass = qclass;
        this.ttl = TimeToLive.fromSeconds(ttl);
    }

    public TimeToLive ttl() {
        return this.ttl;
    }

    public QClass qclass() {
        return qclass;
    }

    @Override
    public String toString() {
        return "{" +
                "data=" + names +
                ", qclass=" + qclass +
                ", ttl=" + ttl +
                '}';
    }

    protected static abstract class NormalRecordParser<T extends NormalRecord> implements RecordParser<T> {

        protected NameArray name;
        protected QClass qclass;
        protected UnsignedInt ttl;
        protected int dataLength;

        @Override
        public T parse(NameArray name, ByteArrayBuffer buffer) throws ParseException {
            this.name = name;
            this.parseFixedPart(buffer);
            this.parseVariablePart(buffer);
            return build();
        }

        protected void parseFixedPart(ByteArrayBuffer buffer) throws ParseException {
            qclass = QClass.fromByteBuffer(buffer);
            ttl = buffer.getUnsignedInt();
            dataLength = buffer.getUnsignedShort().intValue();
        }

        protected void parseVariablePart(ByteArrayBuffer buffer) throws ParseException {

        }

        abstract protected T build();
    }
}
