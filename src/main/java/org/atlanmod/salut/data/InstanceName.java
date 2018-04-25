package org.atlanmod.salut.data;

/**
 *
 * From [RFC 6763](https://tools.ietf.org/html/rfc6763):
 *
 *    4.1.1.  Instance Names
 *
 * > "The <Instance> portion of the Service Instance Name is a user-
 *    friendly name consisting of arbitrary Net-Unicode text [RFC5198].  It
 *    MUST NOT contain ASCII control characters (byte values 0x00-0x1F and
 *    0x7F) [RFC20] but otherwise is allowed to contain any characters,
 *    without restriction, including spaces, uppercase, lowercase,
 *    punctuation -- including dots -- accented characters, non-Roman text,
 *    and anything else that may be represented using Net-Unicode.  For
 *    discussion of why the <Instance> name should be a user-visible, user-
 *    friendly name rather than an invisible machine-generated opaque
 *    identifier, see Appendix C, "What You See Is What You Get"."
 *
 *    (...)
 *
 *    4.1.3 Domain Names
 *    (...)
 *
 * >  "In addition, because Service Instance Names are not constrained by
 *    the limitations of host data, this document recommends that they be
 *    stored in the DNS, and communicated over the wire, encoded as
 *    straightforward canonical precomposed UTF-8 [RFC3629] "Net-Unicode"
 *    (Unicode Normalization Form C) [RFC5198] text.  In cases where the
 *    DNS server returns a negative response for the name in question,
 *    client software MAY choose to retry the query using the "Punycode"
 *    algorithm [RFC3492] to convert the UTF-8 name to an IDNA "A-label"
 *    [RFC5890], beginning with the top-level label, then issuing the query
 *    repeatedly, with successively more labels translated to IDNA A-labels
 *    each time, and giving up if it has converted all labels to IDNA
 *    A-labels and the query still fails."
 *
 */
public class InstanceName {
    private String name;

    public InstanceName(String name) {
        this.name = name;
    }
}
