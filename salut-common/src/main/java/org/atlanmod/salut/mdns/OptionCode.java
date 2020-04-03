package org.atlanmod.salut.mdns;

import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

/**
 * Option Code for OPT record.
 *
 * @see <a href="https://tools.ietf.org/html/rfc6891">RFC 6891</a>
 * @see <a href="https://www.iana.org/assignments/dns-parameters/dns-parameters.xhtml>Domain Label System (DNS) Parameters</a>
 *
 */
public enum OptionCode {

    /**
     * Long-Lived Query
     *
     * @see <a href="http://files.dns-sd.org/draft-sekar-dns-llq.txt">DNS Long-Lived Queries</a>
     */
    LLQ(1, "LLQ"),

    /**
     * Dynamic DNS Update Leases
     *
     * @see <a href="http://files.dns-sd.org/draft-sekar-dns-ul.txt">Dynamic DNS Update Leases</a>
     */
    UL(2,"DDNS-UL"),

    /**
     * DNS Label Server Identifier
     *
     * @see <a href="http://www.iana.org/go/rfc5001>RFC 5001</a>
     */
    NSID(3, "NSID"),

    /**
     *
     * @see <a href="https://tools.ietf.org/html/draft-cheshire-edns0-owner-option-01">EDNS0 OWNER Option</a>
     */
    Owner(4, "Owner"),

    /**
     * DNSSEC Algorithm Understood
     * @see <a href="https://tools.ietf.org/html/rfc6975">DNS Security Extensions (DNSSEC)</a>
     */
    DAU(5, "DAU"),

    /**
     * DS Hash Understood
     * @see <a href="https://tools.ietf.org/html/rfc6975">DNS Security Extensions (DNSSEC)</a>
     */
    DHU(6, "DHU"),

    /**
     * NSEC3 Hash Understood
     * @see <a href="https://tools.ietf.org/html/rfc6975">DNS Security Extensions (DNSSEC)</a>
     */
    N3U(7,"N3U"),

    /**
     * Client Subnet
     * @see <a href="https://tools.ietf.org/html/rfc7871">Client Subnet in DNS Queries</a>
     */
    Subnet(8,"Subnet"),

    /**
     * Expire
     * <a href="https://tools.ietf.org/html/rfc7314">Extension Mechanisms for DNS (EDNS) EXPIRE Option</a>
     */
    Expire(9, "Expire"),

    /**
     * Cookie
     * <a href="https://tools.ietf.org/html/rfc7873">Domain Label System (DNS) Cookies</a>
     */
    Cookie(10, "Cookie"),

    /**
     * Keep Alive
     * <a href="https://tools.ietf.org/html/rfc7828">The edns-tcp-keepalive EDNS0 Option</a>
     */
    KeepAlive(11, "KeepAlive"),

    /**
     * Padding
     * <a href="https://tools.ietf.org/html/rfc7830">The EDNS(0) Padding Option</a>
     */
    Padding(12, "Padding"),

    /**
     * Chain
     * <a href="https://tools.ietf.org/html/rfc7901">CHAIN Query Requests in DNS</a>
     */
    Chain(13, "Chain"),

    /**
     * KeyTag
     * <a href="https://tools.ietf.org/html/rfc8145">Signaling Trust Anchor Knowledge in DNS Security Extensions (DNSSEC)</a>
     */
    KeyTag(14, "KeyTag")
    ;


    private int code;
    private String label;


    OptionCode(int code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    private final static Map<Integer, OptionCode> MAP = stream(OptionCode.values())
            .collect(toMap(each -> each.code, each -> each));


    public static Optional<OptionCode> fromCode(int code) {
        return Optional.ofNullable(MAP.get(code));
    }

}
