package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.ByteArrayBuffer;
import org.atlanmod.salut.io.UnsignedByte;
import org.atlanmod.salut.io.UnsignedShort;

import java.text.ParseException;

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
 | Field Name | Field Type   | Description                  |
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
public class PseudoRecord extends AbstractRecord {

    private UnsignedShort   payload;
    private UnsignedByte    extendedRCode;
    private UnsignedByte    version;
    private UnsignedShort   rdlen;

    public PseudoRecord(NameArray name, UnsignedShort payload, UnsignedByte extendedRCode,
                        UnsignedByte version, UnsignedShort rdlen) {
        super(name);
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

    public static RecordParser<PseudoRecord> parser() {
        return new PseudoRecordBuilder();
    }

    private static class PseudoRecordBuilder implements RecordParser<PseudoRecord> {

        @Override
        public PseudoRecord parse(NameArray name, ByteArrayBuffer buffer) throws ParseException {
            UnsignedShort optionCode = buffer.getUnsignedShort();
            UnsignedShort payload = buffer.getUnsignedShort();
            UnsignedByte extendedRCode = buffer.getUnsignedByte();
            UnsignedByte version = buffer.getUnsignedByte();
            UnsignedShort doz = buffer.getUnsignedShort();
            UnsignedShort rdlen = buffer.getUnsignedShort();

            for (int i = 0; i < rdlen.intValue(); i++) {
                buffer.getUnsignedByte();
            }

            return new PseudoRecord(name, payload, extendedRCode, version, rdlen);
        }
    }
}
