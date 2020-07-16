package org.atlanmod.salut.record;

import java.util.Objects;
import org.atlanmod.commons.Preconditions;
import org.atlanmod.salut.io.UnsignedShort;

/**
 * The QRFlag is a 16-bit value, organized as follows:
 *
 *                                 1  1  1  1  1  1
 *   0  1  2  3  4  5  6  7  8  9  0  1  2  3  4  5
 *  +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *  |QR|   Opcode  |AA|TC|RD|RA| Z|AD|CD|   RCODE   |
 *  +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *
 *
 * 1 - Query/Response bit
 * 4 - OPCode
 * 1 - (AA) Authoritative Answer Bit
 * 1 - (TC) Truncation Bit
 * 1 - (RD) Recursion Desired
 * 1 - (RA) Recursion Available
 * 1 - Z - Zero (reserved bit set to zero)
 * 1 - AD (Authentic Data) Bit
 * 1 - CD (Checking Disabled) Bit
 * 4 - (RCode) - Response Code
 *
 */
public class QRFlag {

    // @formatter:off
    private static final int QR_BIT_MASK            = 0x8000;
    private static final int OPERATION_CODE_MASK    = 0x7800;
    private static final int AA_BIT_MASK            = 0x0400;
    private static final int TRUNCATED_MASK         = 0x0200;
    private static final int RD_BIT_MASK            = 0x0100;
    private static final int RA_BIT_MASK            = 0x0080;
    private static final int RESPONSE_CODE_MASK     = 0x000F;
    // @formatter:on

    private int value;

    private QRFlag(int value) {
        this.value = value;
    }

    /**
     * Copy constructor for QRFlag.
     * @see <a href="https://www.artima.com/intv/bloch.html#part13">Copy Constructor versus Cloning</a>
     *
     * @param source
     */
    public QRFlag(QRFlag source) {
        this.value = source.value;
    }

    @Override
    public String toString() {
        return "{[" + value + "]:" +
                ", Opcode=" + opCode() +
                ", Rcode=" + responseCode() +
                ", " + (isQuery() ? "Query" : "Response") +
                (isTruncated() ? ", Truncated" : "" ) +
                (isAuthoritativeAnswer() ? ", AA" : "") +
                (isRecursionAvailable() ? ", RA" : "") +
                (isRecursionDesired() ? ", RD" : "") +
            "}";
    }

    /**
     * In query messages the Question/Response bit MUST be zero.
     * In response messages the QR bit MUST be one.
     *
     * @return true, if it is a query.
     */
    public boolean isQuery() {
        return (value & QR_BIT_MASK)  == 0x0000;
    }

    /**
     * In responses, the QR bit is set to one.
     *
     * @return true, if it is a response
     */
    public boolean isResponse() {
        return !isQuery();
    }

    /**
     * In both multicast query and multicast response messages, the OPCODE MUST be zero on transmission
     * (only standard queries are currently supported over multicast).
     * Multicast DNS messages received with an OPCODE other than zero MUST be silently ignored.
     * @see [RFC 6762](https://tools.ietf.org/html/rfc6762)
     *
     * In DNS messages, the OPCODE has the following meaning:
     *
     * 0	Query	[RFC1035]
     * 1	IQuery (Inverse Query, OBSOLETE)	[RFC3425]
     * 2	Status	[RFC1035]
     * 3	Unassigned
     * 4	Notify	[RFC1996]
     * 5	Update	[RFC2136]
     * 6-15	Unassigned
     *
     * @see <a href="https://www.iana.org/assignments/dns-parameters/dns-parameters.xhtml#dns-parameters-5">Domain Label System (DNS) Parameters</a>
     *
     * @return an int value representing the operation code.
     */
    public byte opCode() {
        return (byte) ((value & OPERATION_CODE_MASK) >>> 11);
    }

    /**
     * Sets the Operation Code (bits 1-4).
     * @param code A 4-bit value (≥ 0 and ≤ 15)
     */
    public void setOpCode(byte code) {
        Preconditions.checkLessThanOrEqualTo(code, (byte) 15);
        Preconditions.checkGreaterThanOrEqualTo(code, (byte) 0);

        int valueWithoutOpCode = value & (OPERATION_CODE_MASK ^ 0xFFFF);
        this.value = valueWithoutOpCode | (code << 11);
    }


    /**
     * Authoritative Answer flag.
     *
     * In query messages, the Authoritative Answer bit MUST be zero on transmission,
     * and MUST be ignored on reception.
     *
     * In response messages for Multicast domains, the Authoritative Answer bit MUST be set to one (not setting
     * this bit would imply there's some other place where "better" information may be found) and MUST be ignored
     * on reception.
     */
    public boolean isAuthoritativeAnswer() {
        return (value & AA_BIT_MASK) != 0;
    }

    /**
     * Sets the Authoritative Answer value (bit 5).
     *
     * @param aa a boolean.
     */
    public void setAuthoritativeAnswer(boolean aa) {
        if (aa) {
            this.value = this.value | AA_BIT_MASK;
        } else {
            this.value = this.value & (AA_BIT_MASK ^ 0xFFFF);
        }

    }

    /**
     *    In query messages, if the TC bit is set, it means that additional
     *    Known-Answer records may be following shortly.  A responder SHOULD
     *    record this fact, and wait for those additional Known-Answer records,
     *    before deciding whether to respond.  If the TC bit is clear, it means
     *    that the querying host has no additional Known Answers.
     *
     *    In multicast response messages, the TC bit MUST be zero on
     *    transmission, and MUST be ignored on reception.
     *
     *    In legacy unicast response messages, the TC bit has the same meaning
     *    as in conventional Unicast DNS: it means that the response was too
     *    large to fit in a single packet, so the querier SHOULD reissue its
     *    query using TCP in order to receive the larger response.
     *
     * [RFC 6762](https://tools.ietf.org/html/rfc6762)
     *
     * @return
     */
    public boolean isTruncated() {
        return (value & TRUNCATED_MASK) != 0;
    }

    /**
     * Sets the Truncation bit (bit 6);
     *
     * @param tc a boolean
     */
    public void setTruncated(boolean tc) {
        if (tc) {
            this.value = this.value | TRUNCATED_MASK;
        } else {
            this.value = this.value & (TRUNCATED_MASK ^ 0xFFFF);
        }
    }

    /**
     * In both multicast query and multicast response messages, the Recursion Desired bit SHOULD
     * be zero on transmission, and MUST be ignored on reception.
     *
     * @return true, if the RD bit is set to 1
     */
    public boolean isRecursionDesired() {
        return (value & RD_BIT_MASK) != 0;
    }

    /**
     * Sets the Recursion Desired bit (bit 7).
     *
     * @param rd a boolean
     */
    public void setRecursionDesired(boolean rd) {
        if (rd) {
            this.value = this.value | RD_BIT_MASK;
        } else {
            this.value = this.value & (RD_BIT_MASK ^ 0xFFFF);
        }
    }

    /**
     * In both multicast query and multicast response messages, the Recursion Available bit MUST be zero
     * on transmission, and MUST be ignored on reception.
     *
     * @return true, if the RA bit is set to 1
     */
    public boolean isRecursionAvailable() {
        return (value & RA_BIT_MASK) != 0;
    }

    /**
     * Sets the Recursion Available bit (bit 8).
     *
     * @param rd a boolean
     */
    public void setRecursionAvailable(boolean rd) {
        if (rd) {
            this.value = this.value | RA_BIT_MASK;
        } else {
            this.value = this.value & (RA_BIT_MASK ^ 0xFFFF);
        }
    }

    /**
     * In both multicast query and multicast response messages, the Response Code MUST be zero on transmission.
     * Multicast DNS messages received with non-zero Response Codes MUST be silently ignored.
     *
     * @return an int, representing the response code.
     */
    public byte responseCode() {
        return (byte) (value & RESPONSE_CODE_MASK);

    }

    /**
     * Sets the Response Code (bits 12-16).
     * @param code A 4-bit value (≥ 0 and ≤ 15)
     */
    public void setResponseCode(byte code) {
        Preconditions.checkLessThanOrEqualTo(code, (byte) 15);
        Preconditions.checkGreaterThanOrEqualTo(code, (byte) 0);

        int valueWithoutResponseCode = value & (RESPONSE_CODE_MASK ^ 0xFFFF);
        this.value = valueWithoutResponseCode | code;
    }

    public UnsignedShort toUnsignedShort() {
        return UnsignedShort.fromInt(this.value);
    }

    public static QRFlag fromUnsignedShort(UnsignedShort uShort) {
        return new QRFlag(uShort.intValue());
    }

    public static QRFlag fromInt(int value) {
        return new QRFlag(value);
    }



    @Override
    public boolean equals(Object o) {
        //@formatter:off
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        //@formatter:on

        QRFlag qrFlag = (QRFlag) o;
        return value == qrFlag.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
