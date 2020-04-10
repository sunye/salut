package org.atlanmod.salut.mdns;

import java.net.Inet6Address;
import java.text.ParseException;
import org.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
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

    private Inet6Address address;
    private Domain serverName;

    private AAAARecord(QClass qclass, UnsignedInt ttl, Inet6Address address,
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
    public Inet6Address getAddress() {
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
        writer.putNameArray(serverName.toLabels())
            .putRecordType(RecordType.A)
            .putQClass(QClass.IN)
            .putUnsignedInt(ttl.unsignedIntValue())
            .putUnsignedShort(UnsignedShort.fromInt(4));

        // Variable part
        writer.putInet6Address(this.address);
    }
    private static class AAAARecordParser extends NormalRecordParser<AAAARecord> {
        private Inet6Address address;
        private Domain serverName;

        protected void parseVariablePart(ByteArrayReader buffer) throws ParseException {
            address = buffer.readInet6Address();
            serverName = DomainBuilder.fromLabels(labels);
        }

        @Override
        protected AAAARecord build() {
            return new AAAARecord(qclass, ttl, address, serverName);
        }

    }

    @VisibleForTesting
    public static AAAARecord createRecord(QClass qclass, UnsignedInt ttl, Inet6Address address, Domain serverName) {
        return new AAAARecord(qclass, ttl, address, serverName);
    }
}

