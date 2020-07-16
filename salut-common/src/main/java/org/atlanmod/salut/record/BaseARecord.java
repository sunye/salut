package org.atlanmod.salut.record;

import java.util.Objects;
import org.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.salut.domains.Host;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.io.UnsignedShort;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.parser.ARecordParser;
import org.atlanmod.salut.parser.Parser;

/**
 * @formatter:off
 * The `ARecord` class represents DNS IP4 address records (ARecord).
 *
 * The DNS ARecord has the following format:
 *
 * # ARecord
 * Server Label| Time to live| QCLASS | IP 4 Address
 * --|--
 * &lowbar;printer._tcp.local.  | 28800 | A | 127.16.8.1
 * @formatter:on
 */
public class BaseARecord extends AbstractNormalRecord implements ARecord {

    private final Host host;

    public BaseARecord(QClass qclass, UnsignedInt ttl, Host host) {
        super(qclass, ttl);
        this.host = host;
    }

    public static Parser<ARecord> parser() {
        return new ARecordParser();
    }

    @VisibleForTesting
    public static BaseARecord createRecord(QClass qclass, UnsignedInt ttl, Host host) {

        return new BaseARecord(qclass, ttl, host);
    }

    @Override
    public Host host() {
        return this.host;
    }

    /**
     * Returns a `String` object representing this `ARecord`.
     *
     * @return a string representation of this object.
     */
    @Override
    public String toString() {
        return "ARecord{" + host +
            ", ttl=" + ttl +
            '}';
    }

    public void writeOn(ByteArrayWriter writer) {

        // Fixed part
        writer.writeLabels(host.name().toLabels())
            .writeRecordType(RecordType.A)
            .writeQClass(QClass.IN)
            .writeUnsignedInt(ttl.unsignedIntValue())
            .writeUnsignedShort(UnsignedShort.fromInt(4));

        // Variable part
        writer.writeIPAddress(host.address());
    }

    @Override
    public boolean equals(Object o) {
        //@formatter:off
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        //@formatter:on

        BaseARecord aRecord = (BaseARecord) o;
        return host.equals(aRecord.host) &&
            super.equals(aRecord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), host);
    }

    @Override
    public Labels name() {
        return host.name().toLabels();
    }
}
