package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.ByteArrayBuffer;
import org.atlanmod.salut.io.UnsignedInt;

import java.text.ParseException;

/**
 * Domain name pointer record.
 * PTR records enable service discovery by mapping the type of the service to a list of name of specific
 * instances of that type of service.
 *
 */
public class PointerRecord extends NormalRecord {

    /*
    The record contains just one piece of information, the name of the service instance
    (which is the same as the name of the SRV record).
    PTR records are accordingly named just like SRV records but without the instance name:
    <Service Type>.<Domain>

    Here is an example of a PTR record for a print spooler named PrintsAlot:

    _printer._tcp.local. 28800 PTR PrintsAlot._printer._tcp.local.

    Again, the 28800 is the time-to-live (TTL) value, measured in seconds.
     */

    /*
    PTRDNAME
     */

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

    @Override
    public String toString() {
        return "PTRRecord{" +
                "name=" + names +
                ", class=" + qclass +
                ", ttl="+ttl +
                ", pointerName=" + pointerName +
                '}';
    }

    public static RecordParser<PointerRecord> parser() {
        return new PTRRecordParser();
    }

    private static class PTRRecordParser extends NormalRecordParser<PointerRecord> {
        protected NameArray ptrName;

        protected void parseVariablePart(ByteArrayBuffer buffer) throws ParseException {
            ptrName = NameArray.fromByteBuffer(buffer);
        }

        @Override
        protected PointerRecord build() {
            return new PointerRecord(name, qclass, ttl, ptrName);
        }
    }
}
