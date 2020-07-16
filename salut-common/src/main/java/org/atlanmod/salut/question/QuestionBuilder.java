package org.atlanmod.salut.question;

import java.text.ParseException;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.record.QClass;
import org.atlanmod.salut.record.RecordType;

/**
 * From [RFC1035]:
 *
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
public class QuestionBuilder {

    /**
     * Label of the node to which the query pertains
     */
    private final Labels name;
    private final RecordType type;
    private QClass qclass;

    private QuestionBuilder(Labels name, RecordType type, QClass qclass) {
        this.name = name;
        this.type = type;
        this.qclass = qclass;
    }

    @Override
    public String toString() {
        return "Question{" +
                "name=" + name +
                ", type=" + type +
                ", class=" + qclass +
                '}';
    }


    public static Question fromByteBuffer(ByteArrayReader buffer, int position) throws ParseException {
        /*
        From RFC6762 (section 18.12):
        In the Question Section of a Multicast DNS query, the top bit of the qclass field is used
        to indicate that unicast responses are preferred for this particular question.  (See Section 5.4.)
         */

        Labels qname  = Labels.fromByteBuffer(buffer);
        RecordType type  = buffer.readRecordType();
        QClass qClass = QClass.fromByteBuffer(buffer);
        Question newQuestion;
        switch (type) {
            case A:
                newQuestion = new IPv4AddressQuestion();
                break;
            case AAAA:
                newQuestion = new IPv6AddressQuestion();
                break;
            case PTR:
                newQuestion = PointerQuestionBuilder.createPointerQuestion(qname);
                break;
            case SRV:
                newQuestion = new ServiceQuestion();
                break;
            case TXT:
                newQuestion = new TextQuestion(qname);
                break;
            case ALL:
                newQuestion = new AllQuestion(qname);
                break;
            default:
                throw new ParseException("Could not create Question. Unknown Record Type: " + type, position);
        }

        Log.info("Question parsed: {0}", newQuestion);
        return newQuestion;
    }

    public static Question fromByteBuffer(ByteArrayReader buffer) throws ParseException {
        return QuestionBuilder.fromByteBuffer(buffer, buffer.position());
    }

}
