package org.atlanmod.salut.data;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

import java.util.Map;

/**
 * IANA-registered application protocol (music, ipp, etc.)
 *
 * @see <a href="http://www.dns-sd.org/servicetypes.html">DNS SRV (RFC 2782) Service Types</a>
 */
@SuppressWarnings({"pmd:FieldNamingConventions","squid:S115"})
public enum IANAApplicationProtocol {

    /**
     * Automatic Disk Discovery
     */
    adisk("adisk"),

    /**
     * Protocol for streaming of audio/video content
     */
    airplay("airplay"),

    /**
     * AirPort Base Station
     */
    airport("airport"),

    /**
     * World Wide Web HTML-over-HTTP
     */
    http("http"),

    /**
     * IPP (Internet Printing Protocol)
     */
    ipp("ipp"),

    /**
     * AirTunes Service
     */
    raop("raop"),

    /**
     * Server Message Block over TCP/IP
     */
    smb("smb"),

    /**
     * The Secure Shell (SSH) Protocol
     */
    ssh("ssh"),

    /**
     * DNS Service Discovery
     */
    dnssd("dns-sd"),

    /**
     * Remote Frame Buffer (used by VNC)
     */
    rfb("rfb"),

    /**
     * Sleep Proxy Server
     */
    sleepProxy("sleep-proxy"),

    /**
     * Secure File Transfer Protocol over SSH
     */
    sfpssh("sfp-ssh"),

    /**
     * Unknown Application Protocol
     */
    unknown("unknown");


    private String label;
    private final static Map<String, IANAApplicationProtocol> MAP = stream(IANAApplicationProtocol.values())
            .collect(toMap(each -> each.label, each -> each));

    IANAApplicationProtocol(String label) {
        this.label = label;
    }


    public static IANAApplicationProtocol fromString(String label) {
        return MAP.getOrDefault(label, IANAApplicationProtocol.unknown);
    }

}
