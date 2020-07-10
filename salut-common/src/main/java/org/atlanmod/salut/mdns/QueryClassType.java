package org.atlanmod.salut.mdns;

public enum QueryClassEnum {

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


    private int code;
    private String label;

    QueryClassEnum(int value, String str) {
        this.code = value;
        this.label = str;
    }
}
