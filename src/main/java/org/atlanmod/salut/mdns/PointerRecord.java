package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.ByteArrayBuffer;
import org.atlanmod.salut.io.UnsignedInt;

import java.text.ParseException;

/**
 * The `PointerRecord` class represents DNS Domain name pointer record (PTR).
 * PTR records enable service discovery by mapping the type of the service to a list of name of specific
 * instances of that type of service.
 *
 * The PTR abstract record has the following format:
 *
 * # PTR Record
 *
 * Server Name| Time to live| QCLASS | QTYPE | Service Instance Name
 * --|--
 * &lowbar;printer._tcp.local.  | 28800 | IN | PTR | PrintsAlot._printer._tcp.local.
 *
 */
public class PointerRecord extends NormalRecord {
    /**
     * A <domain-name> which points to some location in the domain name space.
     *
     * The name of the service instance.
     */
    private NameArray pointerName;

    PointerRecord(NameArray name, QClass qclass, UnsignedInt ttl, NameArray pointerName) {
        super(name, qclass, ttl);
        this.pointerName = pointerName;
    }

    /**
     * Returns an instance of `RecordParser` that is able to parse a PTRRecord and create an instance of
     * a `PointerRecord`.
     *
     */
    public static RecordParser<PointerRecord> parser() {
        return new PTRRecordParser();
    }

    /**
     * Returns a `String` object representing this `PointerRecord`.
     * @return  a string representation of this object.
     */
    @Override
    public String toString() {
        return "PTRRecord{" +
                "name=" + names +
                ", class=" + qclass +
                ", ttl="+ttl +
                ", pointerName=" + pointerName +
                '}';
    }

    /**
     * The class `PTRRecordParser` is used to parse the variable part of a DNS PTR record.
     */
    private static class PTRRecordParser extends NormalRecordParser<PointerRecord> {
        protected NameArray ptrName;

        /**
         * Parses the variable part of a PTR record.
         *
         * @param buffer a `ByteArrayBuffer` containing the record to parse.
         * @throws ParseException
         */
        @Override
        protected void parseVariablePart(ByteArrayBuffer buffer) throws ParseException {
            ptrName = NameArray.fromByteBuffer(buffer);
        }

        /**
         * Creates a new instance of `PointerRecord`.
         */
        @Override
        protected PointerRecord build() {
            return new PointerRecord(name, qclass, ttl, ptrName);
        }
    }
}
