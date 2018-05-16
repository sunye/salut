package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.UnsignedShort;

/**
 *
 *
 * 0    1                   5    6    7    8    9   10    11  12                   16
 * +----+----+----+----+----+----+----+----+----+----+----+----+----+----+----+----+
 * | QR |        OPCode     | AA | TC | RD | RA |     Zero     |         RCode     |
 * +----+----+----+----+----+----+----+----+----+----+----+----+----+----+----+----+
 *
 * 1 - Query/Response flag
 * 4 - OPCode
 * 1 - (AA) Authoritative Answer Flag
 * 1 - (TC) Truncation Flag:
 * 1 - (RD) Recursion Desired:
 * 1 - (RA) Recursion Available
 * 3 - Zero - Three reserved bits set to zero.
 * 4 - (RCode) - Response Code
 *
 */
public class QRFlag {

    private final static int OPERATION_CODE_MASK = 0x7800;
    private final static  int TRUNCATED_MASK = 0x0200;
    private final static  int QR_BIT_MASK = 0x8000;

    private final int value;

    private QRFlag(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "QRFlag{" +
                ", isQuery=" + isQuery() +
                ", isResponse=" + isResponse() +
                ", isValid=" + isValid() +
                ", isTruncated=" + isTruncated() +
                "value=" + value +
                '}';
    }

    public boolean isQuery() {
        return (value & QR_BIT_MASK)  == 0x0000;
    }

    public boolean isResponse() {
        return !isQuery();
    }

    public boolean isValid() {
        return (value | 0x8400) == 0x8400;
    }

    public boolean isTruncated() {
        return (value & TRUNCATED_MASK) != 0;
    }

    public UnsignedShort toUnsignedShort() {
        return new UnsignedShort(this.value);
    }

    public static QRFlag fromUnsignedShort(UnsignedShort uShort) {
        return new QRFlag(uShort.intValue());
    }

    public static QRFlag fromInt(int value) {
        return new QRFlag(value);
    }

    /**
     * 0	Query	[RFC1035]
     * 1	IQuery (Inverse Query, OBSOLETE)	[RFC3425]
     * 2	Status	[RFC1035]
     * 3	Unassigned
     * 4	Notify	[RFC1996]
     * 5	Update	[RFC2136]
     * 6-15	Unassigned
     *
     * @see <a href="https://www.iana.org/assignments/dns-parameters/dns-parameters.xhtml#dns-parameters-5">Domain Name System (DNS) Parameters</a>
     * @return an int value representing the operation code.
     */
    public int opCode() {
        int code = (value & OPERATION_CODE_MASK) >>> 11 ;

        return code;
    }
}
