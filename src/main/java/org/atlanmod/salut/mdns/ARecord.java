package org.atlanmod.salut.mdns;

import fr.inria.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.salut.data.DomainName;
import org.atlanmod.salut.data.DomainNameBuilder;
import org.atlanmod.salut.io.ByteArrayBuffer;
import org.atlanmod.salut.io.UnsignedInt;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;

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
                '}';
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
         * @param buffer a byte array buffer containing the record.
         *
         * @throws ParseException if the record is not well-formed.
         */
        @Override
        protected void parseVariablePart(ByteArrayBuffer buffer) throws ParseException {
            address = (Inet4Address) parseInet4Address(buffer);
            serverName = DomainNameBuilder.fromNameArray(name);
        }

        @Override
        protected ARecord build() {
            return new ARecord(name, qclass, ttl, address, serverName);
        }

        private InetAddress parseInet4Address(ByteArrayBuffer buffer) throws ParseException {
            byte[] addressBytes = new byte[4];
            buffer.get(addressBytes);
            try {
                InetAddress address = Inet4Address.getByAddress(addressBytes);
                return address;
            } catch (UnknownHostException e) {
                throw new ParseException("UnknownHostException - Parsing error when reading a A AbstractRecord.", buffer.position());
            }
        }
    }

    @VisibleForTesting
    public static ARecord createRecord(NameArray name, QClass qclass, UnsignedInt ttl, Inet4Address address, DomainName serverName) {
        return new ARecord(name, qclass, ttl, address, serverName);
    }
}
