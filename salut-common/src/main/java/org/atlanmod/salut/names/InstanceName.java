package org.atlanmod.salut.names;

import org.atlanmod.salut.labels.DNSLabel;
import org.atlanmod.salut.labels.Label;
import org.atlanmod.salut.labels.Labels;

import java.util.Objects;

/**
 *
 * From [RFC 6763](https://tools.ietf.org/html/rfc6763):
 *
 *    4.1.1.  Instance Names
 *
 * > "The <Instance> portion of the Service Instance Label is a user-
 *    friendly label consisting of arbitrary Net-Unicode text [RFC5198].  It
 *    MUST NOT contain ASCII control characters (byte values 0x00-0x1F and
 *    0x7F) [RFC20] but otherwise is allowed to contain any characters,
 *    without restriction, including spaces, uppercase, lowercase,
 *    punctuation -- including dots -- accented characters, non-Roman text,
 *    and anything else that may be represented using Net-Unicode.  For
 *    discussion of why the <Instance> label should be a user-visible, user-
 *    friendly label rather than an invisible machine-generated opaque
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
 *    DNS server returns a negative response for the label in question,
 *    client software MAY choose to retry the query using the "Punycode"
 *    algorithm [RFC3492] to convert the UTF-8 label to an IDNA "A-label"
 *    [RFC5890], beginning with the top-level label, then issuing the query
 *    repeatedly, with successively more labels translated to IDNA A-labels
 *    each time, and giving up if it has converted all labels to IDNA
 *    A-labels and the query still fails."
 *
 */
public class InstanceName implements Name {
    private final Label label;

    private InstanceName(Label label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstanceName that = (InstanceName) o;
        return Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    public static InstanceName fromString(String str) {
        return fromLabel(DNSLabel.create(str));
    }

    public static InstanceName fromLabel(Label label) {
        return new InstanceName(label);
    }

    @Override
    public Labels toLabels() {
        return Labels.fromList(label);
    }
}
