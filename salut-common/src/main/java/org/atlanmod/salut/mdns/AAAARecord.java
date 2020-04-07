package org.atlanmod.salut.mdns;

import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import org.atlanmod.salut.labels.Labels;

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
    public void writeOne(ByteArrayWriter writer) {
        // TODO
        throw new UnsupportedOperationException();
    }

    private static class AAAARecordParser extends NormalRecordParser<AAAARecord> {
        private Inet6Address address;
        private Domain serverName;

        private static InetAddress parseInet6Address(ByteArrayReader buffer) throws ParseException {
            byte[] addressBytes = new byte[16];
            buffer.get(addressBytes);
            try {
                InetAddress address = Inet6Address.getByAddress(addressBytes);
                return address;
            } catch (UnknownHostException e) {
                throw new ParseException("UnknownHostException - Parsing error when reading a AAAA AbstractRecord.", buffer.position());
            }
        }

        protected void parseVariablePart(ByteArrayReader buffer) throws ParseException {
            address = (Inet6Address) parseInet6Address(buffer);
            serverName = DomainBuilder.fromLabels(labels);
        }

        @Override
        protected AAAARecord build() {
            return new AAAARecord(qclass, ttl, address, serverName);
        }

    }
}

