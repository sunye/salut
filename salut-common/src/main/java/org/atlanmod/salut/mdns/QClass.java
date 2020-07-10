package org.atlanmod.salut.mdns;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

import java.text.ParseException;
import java.util.Map;
import java.util.Objects;
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

public class QClass {

    public static final int CLASS_MASK   = 0x7FFF;
    public static final QClass Any = new QClass(QueryClassType.Any, false);
    public static final QClass IN  = new QClass(QueryClassType.IN, false);

    private final QueryClassType type;
    private final boolean unicastResponseRequired;

    public QClass(QueryClassType type, boolean unicastResponseRequired) {
        this.type = type;
        this.unicastResponseRequired = unicastResponseRequired;
     }

    public static QClass fromByteBuffer(ByteArrayReader buffer) throws ParseException {
        int code = buffer.getUnsignedShort().intValue();
        boolean isUnicast = (code >= 0x8000);
        code = code & CLASS_MASK;

        Optional<QueryClassType> type = QueryClassType.fromCode(code);
        if (! type.isPresent()) {
            throw new ParseException("Parsing error when reading Question Class. Unknown code: " + code, buffer.position());
        } else {
            return new QClass(type.get(), isUnicast);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof QClass)) {
            return false;
        }
        QClass qClass = (QClass) other;
        return unicastResponseRequired == qClass.unicastResponseRequired &&
            type == qClass.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, unicastResponseRequired);
    }

    /**
     * Returns an unsigned short (2-byte) value representing this QClass.
     *
     * @return an UnsignedShort
     */
    public UnsignedShort unsignedShortValue() {
        int code = type.code();
        if (unicastResponseRequired) {
            code += 0x8000;
        }

        return UnsignedShort.fromInt(code);
    }

}
