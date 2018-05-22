package org.atlanmod.salut.mdns;

import org.atlanmod.salut.data.DomainName;
import org.atlanmod.salut.data.DomainNameBuilder;
import org.atlanmod.salut.io.ByteArrayBuffer;
import org.atlanmod.salut.io.UnsignedInt;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;

/**
 * The `AAAARecord` class represents DNS IP6 address records (AAAA).
 * The DNS ARecord has the following format:
 *
 * # AAAARecord
 * Server Name| Time to live| QCLASS | IP 6 Address
 * --|--
 * &lowbar;printer._tcp.local.  | 28800 | A | 2001:db8::1
 */
public class AAAARecord extends NormalRecord {

    private Inet6Address address;
    private DomainName serverName;

    private AAAARecord(NameArray name, QClass qclass, UnsignedInt ttl, Inet6Address address,
                       DomainName serverName) {
        super(name, qclass, ttl);
        this.address = address;
        this.serverName = serverName;
    }

    public static RecordParser<AAAARecord> parser() {
        return new AAAARecordParser();
    }

    /**
     * @return the address pointed by this entry.
     */
    public Inet6Address getAddress() {
        return address;
    }

    /**
     * @return the server name.
     */
    public DomainName getServerName() {
        return this.serverName;
    }

    @Override
    public String toString() {
        return "AAAARecord{" +
                "address=" + address +
                ", data=" + names +
                '}';
    }

    private static class AAAARecordParser extends NormalRecordParser<AAAARecord> {
        private Inet6Address address;
        private DomainName serverName;

        private static InetAddress parseInet6Address(ByteArrayBuffer buffer) throws ParseException {
            byte[] addressBytes = new byte[16];
            buffer.get(addressBytes);
            try {
                InetAddress address = Inet6Address.getByAddress(addressBytes);
                return address;
            } catch (UnknownHostException e) {
                throw new ParseException("UnknownHostException - Parsing error when reading a AAAA AbstractRecord.", buffer.position());
            }
        }

        protected void parseVariablePart(ByteArrayBuffer buffer) throws ParseException {
            address = (Inet6Address) parseInet6Address(buffer);
             serverName = DomainNameBuilder.fromNameArray(name);
        }

        @Override
        protected AAAARecord build() {
            return new AAAARecord(name, qclass, ttl, address, serverName);
        }

    }
}

