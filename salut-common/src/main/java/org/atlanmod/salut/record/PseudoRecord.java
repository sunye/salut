package org.atlanmod.salut.record;

import org.atlanmod.commons.Throwables;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedByte;
import org.atlanmod.salut.io.UnsignedShort;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.parser.Parser;
import org.atlanmod.salut.parser.PseudoRecordParser;

/**
 * From rfc 6891
 * https://tools.ietf.org/html/rfc6891
 *
 * 6.1.2.  Wire Format

 An OPT RR has a fixed part and a variable set of options expressed as
 {attribute, value} pairs.  The fixed part holds some DNS metadata,
 and also a small collection of basic extension elements that we
 expect to be so popular that it would be a waste of wire space to
 encode them as {attribute, value} pairs.

 The fixed part of an OPT RR is structured as follows:

 +------------+--------------+------------------------------+
 | FieldLabel | Field Type   | Description                  |
 +------------+--------------+------------------------------+
 | NAME       | domain data  | MUST be 0 (root domain)      |
 | TYPE       | u_int16_t    | OPT (41)                     |
 | CLASS      | u_int16_t    | requestor's UDP payload size |
 | TTL        | u_int32_t    | extended RCODE and flags     |
 | RDLEN      | u_int16_t    | length of all RDATA          |
 | RDATA      | octet stream | {attribute,value} pairs      |
 +------------+--------------+------------------------------+

 The variable part of an OPT RR may contain zero or more options in
 the RDATA.  Each option MUST be treated as a bit field.  Each option
 is encoded as:

 +0 (MSB)                            +1 (LSB)
    +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+
 0: |                          OPTION-CODE                          |
    +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+
 2: |                         OPTION-LENGTH                         |
    +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+
 4: |                                                               |
    /                          OPTION-DATA                          /
    /                                                               /
    +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+

 OPTION-CODE
 Assigned by the Expert Review process as defined by the DNSEXT
 working group and the IESG.

 OPTION-LENGTH
 Size (in octets) of OPTION-DATA.

 OPTION-DATA
 Varies per OPTION-CODE.  MUST be treated as a bit field.

 */
public class PseudoRecord implements Record {

    private UnsignedShort   payload;
    private UnsignedByte    extendedRCode;
    private UnsignedByte    version;
    private UnsignedShort   rdlen;

    public PseudoRecord(UnsignedShort payload, UnsignedByte extendedRCode,
                        UnsignedByte version, UnsignedShort rdlen) {
        this.payload = payload;
        this.extendedRCode = extendedRCode;
        this.version = version;
        this.rdlen = rdlen;
    }

    @Override
    public String toString() {
        return "PseudoRecord{" +
                "payload=" + payload +
                ", extendedRCode=" + extendedRCode +
                ", version=" + version +
                ", rdlen=" + rdlen +
                '}';
    }

    public static Parser<PseudoRecord> parser() {
        return new PseudoRecordParser();
    }

    public void writeOn(ByteArrayWriter writer) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public Labels name() {
        Throwables.notImplementedYet("name()");
        return null;
    }

}
