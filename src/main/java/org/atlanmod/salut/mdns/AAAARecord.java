package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.ByteArrayBuffer;
import org.atlanmod.salut.io.UnsignedInt;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;

public class AAAARecord extends NormalRecord {

    private Inet6Address address;

    private AAAARecord(NameArray name, QClass qclass, UnsignedInt ttl, Inet6Address address) {
        super(name, qclass, ttl);
        this.address = address;
    }

    @Override
    public String toString() {
        return "AAAARecord{" +
                "address=" + address +
                ", data=" + names +
                '}';
    }

    public static RecordParser<AAAARecord> parser() {
        return new AAAARecordParser();
    }

    private static class AAAARecordParser extends NormalRecordParser<AAAARecord> {
        private Inet6Address address;

        protected void parseVariablePart(ByteArrayBuffer buffer) throws ParseException {
            address = (Inet6Address) parseInet6Address(buffer);
        }
        @Override
        protected AAAARecord build() {
            return new AAAARecord(name, qclass, ttl, address);
        }

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

    }
}

