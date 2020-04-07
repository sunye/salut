package org.atlanmod.salut.mdns;

import org.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.io.UnsignedShort;

import java.net.Inet4Address;
import java.text.ParseException;
import java.util.Objects;
import org.atlanmod.salut.labels.Labels;

/**
 * The `ARecord` class represents DNS IP4 getAddress records (ARecord).
 *
 * The DNS ARecord has the following format:
 *
 * # ARecord
 * Server Label| Time to live| QCLASS | IP 4 Address
 * --|--
 * &lowbar;printer._tcp.local.  | 28800 | A | 127.16.8.1
 */
public class BaseARecord extends AbstractNormalRecord implements ARecord {

    private final Inet4Address address;
    private final Domain serverName;

    private BaseARecord(Labels name, QClass qclass, UnsignedInt ttl, Inet4Address address, Domain serverName) {
        super(name, qclass, ttl);
        this.address = address;
        this.serverName = serverName;
    }

    @Override
    public Domain serverName() {
        return this.serverName;
    }

    @Override
    public Inet4Address address() {
        return this.address;
    }

    /**
     * Returns a `String` object representing this `ARecord`.
     * @return  a string representation of this object.
     */
    @Override
    public String toString() {
        return "ARecord{" +
                "data=" + labels +
                ", getAddress=" + address +
                ", ttl="+ttl +
                '}';
    }

    public void writeOn(ByteArrayWriter writer) {

        // Fixed part
        writer.putNameArray(labels)
                .putRecordType(RecordType.A)
                .putQClass(QClass.IN)
                .putUnsignedInt(ttl.unsignedIntValue())
                .putUnsignedShort(UnsignedShort.fromInt(4));

        // Variable part
        writer.putInet4Address(this.address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseARecord aRecord = (BaseARecord) o;
        return address.equals(aRecord.address) &&
                serverName.equals(aRecord.serverName) &&
                super.equals(aRecord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, serverName);
    }

    public static RecordParser<ARecord> parser() {
        return new ARecordParser();
    }

    @Override
    public void writeOne(ByteArrayWriter writer) {
        // TODO
        throw new UnsupportedOperationException();
    }

    private static class ARecordParser extends NormalRecordParser<ARecord> {
        private Inet4Address address;
        private Domain serverName;

        /**
         * Parses the variable part of a ARecord.
         *
         * @param reader a byte array reader containing the record.
         *
         * @throws ParseException if the record is not well-formed.
         */
        @Override
        protected void parseVariablePart(ByteArrayReader reader) throws ParseException {
            address = reader.readInet4Address();
            serverName = DomainBuilder.fromLabels(labels);
        }

        @Override
        protected ARecord build() {
            return new BaseARecord(labels, qclass, ttl, address, serverName);
        }

    }

    @VisibleForTesting
    public static BaseARecord createRecord(Labels name, QClass qclass, UnsignedInt ttl, Inet4Address address, Domain serverName) {
        return new BaseARecord(name, qclass, ttl, address, serverName);
    }
}
