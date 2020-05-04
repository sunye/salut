package org.atlanmod.salut.labels;

/**
 * The <Instance> portion of the Service Instance Name is a user- friendly name consisting of
 * arbitrary Net-Unicode text [RFC5198].  It MUST NOT contain ASCII control characters (byte values
 * 0x00-0x1F and 0x7F) [RFC20] but otherwise is allowed to contain any characters, without
 * restriction, including spaces, uppercase, lowercase, punctuation -- including dots -- accented
 * characters, non-Roman text, and anything else that may be represented using Net-Unicode.  For
 * discussion of why the <Instance> name should be a user-visible, user- friendly name rather than
 * an invisible machine-generated opaque identifier, see Appendix C, "What You See Is What You
 * Get".
 * <p>
 * DNS labels are currently limited to 63 octets in length.  UTF-8 encoding can require up to four
 * octets per Unicode character, which means that in the worst case, the <Instance> portion of a
 * name could be limited to fifteen Unicode characters.  However, the Unicode characters with longer
 * octet lengths under UTF-8 encoding tend to be the more rarely used ones, and tend to be the ones
 * that convey greater meaning per character.
 *
 * @see <a href="https://tools.ietf.org/html/rfc6763#section-4.1">RFC 6763</a>
 */
public class InstanceLabel implements Label {

    private static final String VALID_LABEL_EXPRESSION = "[^\\p{Cntrl}]*";

    private final String label;

    public InstanceLabel(String label) {
        this.label = label;
    }

    public boolean isValid() {
        return this.label.matches(VALID_LABEL_EXPRESSION);
    }

    /**
     * The size, in octets, of this label when written on a byte array, including 1 byte for the
     * label size
     *
     * @return The size in octets of the serialized form of this label
     */
    public int dataLength() {
        return label.length() + 1;
    }
}
