package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.UnsignedShort;

import java.text.ParseException;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

/**
 *
 * @see <a href="https://en.wikipedia.org/wiki/List_of_DNS_record_types">Wikipedia - List of DNS record types</a>
 */
public enum RecordType {

    /**
     * IP4 Host AddressEntry
     */
    A(      1, "a"),

    /**
     * Authoritative data server
     */
    NS(     2, "ns"),

    /**
     * Mail destination
     */
    MD(     3, "md"),

    /**
     * Mail forwarder
     */
    MF(     4, "mf"),

    /**
     * Canonical data for an alias
     */
    CNAME(  5, "cname"),

    /**
     * Start of Authority
     */
    SOA(    6, "soa"),

    /**
     * Mailbox domain data
     */
    MB(     7, "mb"),

    /**
     * Mail Group
     */
    MG(     8, "mg"),

    /**
     * Mail Rename
     */
    MR(     9, "mr"),

    /**
     * Null record
     */
    NULL(   10, "null"),

    /**
     * Well-Known Services supported by a host
     */
    WKS(    11, "wks"),

    /**
     * Domain data pointer
     */
    PTR(    12, "ptr"),

    /**
     * Host Information
     */
    HINFO(  13, "hinfo"),

    /**
     * Mailbox information
     */
    MINFO(  14, "minfo"),

    /**
     * Mail exchange
     */
    MX(     15, "mx"),

    /**
     * Text strings
     */
    TXT(    16, "txt"),

    /**
     * Responsible Person
     */
    RP(     17, "tp"),

    /**
     * AFS Data Base location
     */
    AFSDB(  18,"afsdb"),

    /**
     * X.25 PSDN getAddress
     */
    X25(    19, "x25"),

    /**
     * ISDN getAddress
     */
    ISDN(   20, "isdn"),

    /**
     * Route Through
     */
    RT(     21, "rt"),

    /**
     * NSAP getAddress
     *
     * @see <a href="https://tools.ietf.org/html/rfc1706">DNS NSAP Resource Records</a>
     */
    NSAP(   22, "nsap"),

    /**
     * NSAP getAddress pointer
     */
    NSAP_PTR(23, "nsap-ptr"),

    /**
     * Security Signature
     */
    SIG(    24, "sig"),

    /**
     * Security Key
     */
    KEY(    25, "key"),

    /**
     * X.400 mail mapping information
     */
    PX(     26, "px"),

    /**
     * Geographical Position
     */
    GPOS(   27, "gpos"),

    /**
     * IP6 Host AddressEntry
     */
    AAAA(   28, "aaaa"),

    /**
     * Location Information
     */
    LOC(    29, "loc"),

    /**
     * Next Domain
     */
    NXT(    30, "nxt"),

    /**
     * Endpoint Identifier
     */
    EID(    31, "eid"),

    /**
     * Nimrod Locator
     */
    NIMLOC( 32, "nimloc"),

    /**
     * Server Selection
     *
     * @see <a href="https://tools.ietf.org/html/rfc6335">Service Name and Port Number Procedures</a>
     */
    SRV(    33, "srv"),

    /**
     * ATM AddressEntry
     */
    ATMA(   34, "atma"),

    /**
     * Naming Authority Pointer
     */
    NAPTR(  35, "naptr"),

    /**
     * Key Exchanger
     */
    KX(     36, "kx"),

    /**
     * Certificate record
     */
    CERT(   37, "cert"),

    /**
     * Defined as part of early IPv6 but downgraded to experimental by RFC 3363: A6(38),
     * Later downgraded to historic in RFC 6563.
     */
    A6(     38, "a6"),

    /**
     * 	Alias for a data and all its subnames, unlike CNAME, which is an alias for only the exact data.
     * 	Like a CNAME record, the DNS lookup will continue by retrying the lookup with the new data.
     */
    DNAME(  39, "dname"),

    /**
     * Defined by the Kitchen Sink internet draft, but never made it to RFC status: SINK(40)
     */
    SINK(   40, "sink"),       // SINK [Eastlake]

    /**
     * From rfc 6891:
     *
     * An OPT is called a pseudo-RR because it pertains to a particular transport level message
     * and not to any actual DNS data.  OPT RRs shall never be cached, forwarded, or stored
     * in or loaded from master files.  The quantity of OPT pseudo-RRs per message shall be
     * either zero or one, but not greater.
     *
     * @see <a href="https://tools.ietf.org/html/rfc6891">Extension Mechanisms for DNS (EDNS(0))</a>
     */
    OPT(    41, "opt"),

    /**
     * AddressEntry Prefix List
     */
    APL(    42, "apl"),

    /**
     * Delegation Signer
     */
    DS(     43, "ds"),

    /**
     * SSH Key Fingerprint
     */
    SSHFP(  44, "sshfp"),

    /**
     * Signature for a DNSSEC-secured record set.
     * Uses the same format as the SIG record.
     */
    RRSIG(  46, "rrsig"),     // RRSIG [RFC3755]

    /**
     * Next Secure NormalRecord.
     * @see <a href="https://tools.ietf.org/html/rfc8198">Aggressive Use of DNSSEC-Validated Cache</a>
     */
    NSEC(    47, "nsec"),

    /**
     *
     * The key record used in DNSSEC.
     * Uses the same format as the KEY record.
     */
    DNSKEY(  48, "dnskey"),

    /**
     * IANA reserved, no RFC documented them [1] and support was removed from BIND in the early 90s:
     * UINFO(100), UID(101), GID(102), UNSPEC(103)
     */
    UINFO(  100, "uinfo"),

    /**
     * IANA reserved, no RFC documented them [1] and support was removed from BIND in the early 90s:
     * UINFO(100), UID(101), GID(102), UNSPEC(103)
     */
    UID(    101, "uid"),

    /**
     * IANA reserved, no RFC documented them [1] and support was removed from BIND in the early 90s:
     * UINFO(100), UID(101), GID(102), UNSPEC(103)
     */
    GID(    102, "gid"),

    /**
     * IANA reserved, no RFC documented them [1] and support was removed from BIND in the early 90s:
     * UINFO(100), UID(101), GID(102), UNSPEC(103)
     */
    UNSPEC( 103, "unspec"),

    /**
     * Transaction Key
     */
    TKEY(   249, "tkey"),

    /**
     * Transaction Signature
     */
    TSIG(   250, "tsig"),

    /**
     * Incremental transfer
     */
    IXFR(   251, "ixfr"),

    /**
     * A request for a transfer of an entire zone
     */
    AXFR(   252, "axfr"),

    /**
     * A request for mailbox-related records (MB, MG or MR)
     */
    MAILB(  253, "mailb"),

    /**
     *  A request for mail agent RRs (Obsolete - see MX)
     */
    MAILA(  254, "maila"),

    /**
     * All records (request for)
     */
    ALL(    255, "*");


    private int code;
    private String label;

    RecordType(int value, String str) {
        this.code = value;
        this.label = str;
    }

    @Override
    public String toString() {
        return "RecordType{" +
                "code=" + code +
                ", label='" + label + '\'' +
                '}';
    }

    public UnsignedShort unsignedShortValue() {
        return UnsignedShort.fromInt(this.code);
    }

    private final static Map<Integer, RecordType> MAP = stream(RecordType.values())
            .collect(toMap(each -> each.code, each -> each));

    public static Optional<RecordType> fromCode(int code) {
        return Optional.ofNullable(MAP.get(code));
    }

}
