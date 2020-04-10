package org.atlanmod.salut.mdns;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

import java.text.ParseException;
import java.util.Map;
import java.util.Optional;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.UnsignedShort;

/**
 * The CLASS of resource records being requested e.g. Internet, CHAOS etc.
 * These values are assigned by IANA and a complete list of values may be obtained from them.
 *
 * As noted in [RFC6762], Multicast DNS can only carry DNS records with classes in the
 * range 0-32767.
 * Classes in the range 32768 to 65535 are incompatible with Multicast DNS.
 *
 * From [RFC6762]:
 *
 * 18.12.  Repurposing of Top Bit of qclass in Question Section
 * In the Question Section of a Multicast DNS query, the top bit of the qclass field is used
 * to indicate that unicast responses are preferred for this particular question.  (See Section 5.4.)
 */
public enum QClass {


    /**
     * Internet
     * @see <a href="https://tools.ietf.org/html/rfc1035">DOMAIN NAMES - IMPLEMENTATION AND SPECIFICATION</a>
     */
    IN(1, "in"),

    /**
     * CSNET (Obsolete)
     */
    CS(2, "cs"),

    /**
     * CHAOS
     * [D. Moon, "Chaosnet", A.I. Memo 628,
     * Massachusetts Institute of Technology Artificial Intelligence Laboratory, June 1981.]
     */
    CH(3, "ch"),

    /**
     * Hesiod
     * [Dyer, S., and F. Hsu, "Hesiod", Project Athena Technical Plan - Label Service, April 1987.]
     */
    HS(4, "hs"),

    /**
     * None
     * @see <a href="https://tools.ietf.org/html/rfc2136">Dynamic Updates in the Domain Label System (DNS UPDATE)</a>
     */
    None(254, "None"),

    /**
     * Any Class
     *
     * @see <a href="https://tools.ietf.org/html/rfc1035">DOMAIN NAMES - IMPLEMENTATION AND SPECIFICATION</a>
     */
    Any(255, "any");

    public static final int CLASS_MASK   = 0x7FFF;
    private int code;
    private String label;

    QClass(int value, String str) {
        this.code = value;
        this.label = str;
    }

    @Override
    public String toString() {
        return label;
    }

    /**
     * Returns an unsigned short (2-byte) value representing this QClass.
     *
     * @return an UnsignedShort
     */
    public UnsignedShort unsignedShortValue() {
        return UnsignedShort.fromInt(this.code);
    }

    private final static Map<Integer, QClass> MAP = stream(QClass.values())
            .collect(toMap(each -> each.code, each -> each));

    public static Optional<QClass> fromCode(int code) {
        return Optional.ofNullable(MAP.get(code));
    }

    public static QClass fromByteBuffer(ByteArrayReader buffer) throws ParseException {
        int code = buffer.getUnsignedShort().intValue();
        code = code & CLASS_MASK;

        Optional<QClass> type = fromCode(code);
        if (! type.isPresent()) {
            throw new ParseException("Parsing error when reading Question Class. Unknown code: " + code, buffer.position());
        } else {
            return type.get();
        }
    }

}
