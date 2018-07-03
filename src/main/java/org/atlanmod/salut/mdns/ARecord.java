package org.atlanmod.salut.mdns;

import fr.inria.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.salut.data.DomainName;
import org.atlanmod.salut.data.DomainNameBuilder;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.io.UnsignedShort;

import java.net.Inet4Address;
import java.text.ParseException;
import java.util.Objects;

/**
 * The `ARecord` class represents DNS IP4 getAddress records (ARecord).
 *
 * The DNS ARecord has the following format:
 *
 * # ARecord
 * Server Name| Time to live| QCLASS | IP 4 Address
 * --|--
 * &lowbar;printer._tcp.local.  | 28800 | A | 127.16.8.1
 */
public class ARecord extends NormalRecord {

    private final Inet4Address address;
    private final DomainName serverName;

    private ARecord(NameArray name, QClass qclass, UnsignedInt ttl, Inet4Address address, DomainName serverName) {
        super(name, qclass, ttl);
        this.address = address;
        this.serverName = serverName;
    }

    /**
     * @return the server name.
     */
    public DomainName getServerName() {
        return this.serverName;
    }

    /**
     * Accessor for field getAddress
     *
     * @return a Inet4Address instance
     */
    public Inet4Address getAddress() {
        return this.address;
    }

    /**
     * Returns a `String` object representing this `ARecord`.
     * @return  a string representation of this object.
     */
    @Override
    public String toString() {
        return "ARecord{" +
                "data=" + names +
                ", getAddress=" + address +
                ", ttl="+ttl +
                '}';
    }

    public void writeOn(ByteArrayWriter writer) {

        // Fixed part
        writer.putNameArray(names)
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
        ARecord aRecord = (ARecord) o;
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

    private static class ARecordParser extends NormalRecordParser<ARecord> {
        private Inet4Address address;
        private DomainName serverName;

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
            serverName = DomainNameBuilder.fromNameArray(name);
        }

        @Override
        protected ARecord build() {
            return new ARecord(name, qclass, ttl, address, serverName);
        }

    }

    @VisibleForTesting
    public static ARecord createRecord(NameArray name, QClass qclass, UnsignedInt ttl, Inet4Address address, DomainName serverName) {
        return new ARecord(name, qclass, ttl, address, serverName);
    }
}
