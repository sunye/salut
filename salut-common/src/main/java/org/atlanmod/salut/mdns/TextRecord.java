package org.atlanmod.salut.mdns;

import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;

import java.text.ParseException;
import java.util.List;

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
    private List<String> properties;

    private TextRecord( QClass qclass, UnsignedInt ttl, List<String> properties) {
        super(qclass, ttl);
        this.properties = properties;
    }

    public static RecordParser<TextRecord> parser() {
        return new TextRecordParser();
    }

    @Override
    public void writeOn(ByteArrayWriter writer) {
        // TODO
        throw new UnsupportedOperationException();
    }

    private static class TextRecordParser extends NormalRecordParser<TextRecord> {

        private List<String> properties;

        protected void parseVariablePart(ByteArrayReader buffer) throws ParseException {
            Log.info("Data length = {0}", dataLength);
            properties = buffer.readTextDataStrings(dataLength);
        }

        @Override
        protected TextRecord build() {
            return new TextRecord(qclass, ttl, properties);
        }
    }
}
