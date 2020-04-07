package org.atlanmod.salut.mdns;

import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.io.ByteArrayReader;

import java.text.ParseException;
import java.util.Optional;
import org.atlanmod.salut.labels.Labels;

/**
 * The question section is used to carry the "question" in most queries,
 * i.e., the parameters that define what is being asked.  The section
 * contains QDCOUNT (usually 1) entries, each of the following format:
 *
 *                                     1  1  1  1  1  1
 *       0  1  2  3  4  5  6  7  8  9  0  1  2  3  4  5
 *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *     |                                               |
 *     /                     QNAME                     /
 *     /                                               /
 *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *     |                     QTYPE                     |
 *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *     |                     QCLASS                    |
 *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *
 * where:
 *
 * QNAME           a domain name represented as a sequence of labels, where
 *                 each label consists of a length octet followed by that
 *                 number of octets.  The domain name terminates with the
 *                 zero length octet for the null label of the root.  Note
 *                 that this field may be an odd number of octets; no
 *                 padding is used.
 *
 * QTYPE           a two octet code which specifies the type of the query.
 *                 The values for this field include all codes valid for a
 *                 TYPE field, together with some more general codes which
 *                 can match more than one type of RR.
 *
 * QCLASS          a two octet code that specifies the class of the query.
 *                 For example, the QCLASS field is IN for the Internet.
 */
public class Question {

    /**
     * Label of the node to which the query pertains
     */
    private final Labels name;

    /**
     * unicast-response bit
     */
    private boolean requiresUnicastResponse;
    private final RecordType type;
    private QClass qclass;

    private Question(Labels name, RecordType type, QClass qclass, boolean isQU) {
        this.name = name;
        this.type = type;
        this.qclass = qclass;
        this.requiresUnicastResponse = isQU;
    }

    @Override
    public String toString() {
        return "Question{" +
                "data=" + name +
                ", getType=" + type +
                ", qclass=" + qclass +
                '}';
    }


    public static Question fromByteBuffer(ByteArrayReader buffer, int position) throws ParseException {
        /*
        From RFC6762 (section 18.12):
        In the Question Section of a Multicast DNS query, the top bit of the qclass field is used
        to indicate that unicast responses are preferred for this particular question.  (See Section 5.4.)
         */

        Labels qname  = Labels.fromByteBuffer(buffer);
        RecordType qtype  = buffer.readRecordType();
        int code          = buffer.getUnsignedShort().intValue();
        boolean requiresUnicast = code >= 0x8000;

        code = code & QClass.CLASS_MASK;
        Optional<QClass> qclass = QClass.fromCode(code);

        if (!qclass.isPresent()) {
            throw new ParseException("Parsing error when reading Question Class. Unknown code: " + code, buffer.position());
        }

        Question newQuestion = new Question(qname, qtype, qclass.get(), requiresUnicast);
        Log.info("Question parsed: {0}", newQuestion);

        return newQuestion;
    }

    public static Question fromByteBuffer(ByteArrayReader buffer) throws ParseException {
        return Question.fromByteBuffer(buffer, buffer.position());
    }
}
