package org.atlanmod.salut.mdns;

import java.text.ParseException;
import java.util.Objects;
import org.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.domains.IPv6Address;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.io.UnsignedShort;

/**
 * The `AAAARecord` class represents DNS IP6 getAddress records (AAAA).
 * The DNS ARecord has the following format:
 *
 * # AAAARecord
 * Server Label| Time to live| QCLASS | IP 6 Address
 * --|--
 * &lowbar;printer._tcp.local.  | 28800 | A | 2001:db8::1
 */
public class AAAARecord extends AbstractNormalRecord {

    private IPv6Address address;
    private Domain serverName;

    private AAAARecord(QClass qclass, UnsignedInt ttl, IPv6Address address,
                       Domain serverName) {
        super(qclass, ttl);
        this.address = address;
        this.serverName = serverName;
    }

    public static RecordParser<AAAARecord> parser() {
        return new AAAARecordParser();
    }

    /**
     * @return the getAddress pointed by this entry.
     */
    public IPv6Address getAddress() {
        return address;
    }

    /**
     * @return the server name.
     */
    public Domain getServerName() {
        return this.serverName;
    }

    @Override
    public String toString() {
        return "AAAARecord{" + address + '}';
    }

    @Override
    public void writeOn(ByteArrayWriter writer) {

        // Fixed part
        writer.writeLabels(serverName.toLabels())
            .writeRecordType(RecordType.AAAA)
            .writeQClass(QClass.IN)
            .writeUnsignedInt(ttl.unsignedIntValue())
            .writeUnsignedShort(UnsignedShort.fromInt(4));

        // Variable part
        writer.writeIPv6Address(this.address);
    }
    private static class AAAARecordParser extends NormalRecordParser<AAAARecord> {
        private IPv6Address address;
        private Domain serverName;

        @Override
        protected void parseVariablePart(ByteArrayReader buffer) throws ParseException {
            address = buffer.readIPv6Address();
            serverName = DomainBuilder.fromLabels(labels);
        }

        @Override
        protected AAAARecord build() {
            return new AAAARecord(qclass, ttl, address, serverName);
        }

    }

    @VisibleForTesting
    public static AAAARecord createRecord(QClass qclass, UnsignedInt ttl, IPv6Address address, Domain serverName) {
        return new AAAARecord(qclass, ttl, address, serverName);
    }

    @Override
    public final boolean equals(Object other) {
        //@formatter:off
        if (this == other) {return true;}
        if (! (other instanceof AAAARecord)) {return false;}
        //@formatter:on

        AAAARecord that = (AAAARecord) other;
        return super.equals(that) &&
            Objects.equals(address, that.address) &&
            Objects.equals(serverName, that.serverName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address, serverName);
    }
}

