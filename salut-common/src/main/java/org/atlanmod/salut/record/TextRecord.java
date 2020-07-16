package org.atlanmod.salut.record;

import java.util.List;
import org.atlanmod.commons.Throwables;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.parser.Parser;
import org.atlanmod.salut.parser.TextRecordParser;

/**
 * From [RFC6763](https://tools.ietf.org/html/rfc6763#page-31)
 *
 * ```
 * 6.1.  General Format Rules for DNS TXT Records
 *
 *    A DNS TXT record can be up to 65535 (0xFFFF) bytes long.  The total
 *    length is indicated by the length given in the resource record header
 *    in the DNS message.  There is no way to tell directly from the data
 *    alone how long it is (e.g., there is no length count at the start, or
 *    terminating NULL byte at the end).
 *
 *    Note that when using Multicast DNS [RFC6762] the maximum packet size
 *    is 9000 bytes, including the IP header, UDP header, and DNS message
 *    header, which imposes an upper limit on the size of TXT records of
 *    about 8900 bytes.  In practice the maximum sensible size of a DNS-SD
 *    TXT record is smaller even than this, typically at most a few hundred
 *    bytes, as described below in Section 6.2.
 * ```
 */
public class TextRecord extends AbstractNormalRecord {
    private Labels name;
    private List<String> properties;

    public TextRecord(Labels name, QClass qclass, UnsignedInt ttl, List<String> properties) {
        super(qclass, ttl);
        this.properties = properties;
        this.name = name;
    }

    public static Parser<TextRecord> parser() {
        return new TextRecordParser();
    }

    @Override
    public void writeOn(ByteArrayWriter writer) {
        // TODO
        Throwables.notImplementedYet("writeOn()");
    }

    @Override
    public String toString() {
        return "TXTRecord{size="+properties.size()+'}';
    }

    @Override
    public Labels name() {
        return name;
    }
}
