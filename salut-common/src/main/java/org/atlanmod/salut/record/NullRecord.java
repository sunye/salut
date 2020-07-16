package org.atlanmod.salut.record;

import org.atlanmod.commons.Throwables;
import org.atlanmod.salut.cache.TimeToLive;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.parser.NullRecordParser;
import org.atlanmod.salut.parser.Parser;

/**
 *
 *
 */
public class NullRecord extends AbstractNormalRecord {

    public NullRecord(QClass qclass, UnsignedInt ttl) {
        super(qclass, ttl);
    }

    @Override
    public String toString() {
        return "NullRecord";
    }

    public static Parser<NullRecord> parser() {
        return new NullRecordParser();
    }

    @Override
    public void writeOn(ByteArrayWriter writer) {
        // TODO
        Throwables.notImplementedYet("writeOn()");
    }

    /**
     * @return this record's time to live
     */
    @Override
    public TimeToLive ttl() {
        Throwables.notImplementedYet("ttl()");
        return null;
    }

    /**
     * @return The `QClass` of resource records being requested e.g. Internet, CHAOS etc.
     */
    @Override
    public QClass qclass() {
        Throwables.notImplementedYet("qclass()");
        return null;
    }

    @Override
    public Labels name() {
        Throwables.notImplementedYet("name()");
        return null;
    }
}
