package org.atlanmod.salut.mdns;

import java.util.Map;
import java.util.Optional;
import org.atlanmod.salut.io.UnsignedShort;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

@SuppressWarnings({"pmd:FieldNamingConventions","squid:S115"})
public enum QueryClassType {

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


    private final static Map<Integer, QueryClassType> MAP = stream(QueryClassType.values())
        .collect(toMap(each -> each.code, each -> each));

    private int code;
    private String label;

    QueryClassType(int value, String str) {
        this.code = value;
        this.label = str;
    }

    @Override
    public String toString() {
        return label;
    }

    /**
     * Returns an in value representing this QClass.
     *
     * @return an int
     */
    public int code() {
        return this.code;
    }

    public static Optional<QueryClassType> fromCode(int code) {
        return Optional.ofNullable(MAP.get(code));
    }
}
