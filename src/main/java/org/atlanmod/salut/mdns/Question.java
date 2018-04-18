package org.atlanmod.salut.mdns;

import fr.inria.atlanmod.commons.log.Log;
import org.atlanmod.salut.io.ByteArrayBuffer;

import java.text.ParseException;

public class Question {

    /**
     * Name of the node to which the query pertains
     */
    private final NameArray name;
    private final RecordType type;
    private QClass qclass;

    private Question(NameArray name, RecordType type, QClass qclass) {
        this.name = name;
        this.type = type;
        this.qclass = qclass;
    }

    @Override
    public String toString() {
        return "Question{" +
                "names=" + name +
                ", type=" + type +
                ", qclass=" + qclass +
                '}';
    }

    public static Question fromByteBuffer(ByteArrayBuffer buffer, int position) throws ParseException {

        NameArray qname  = NameArray.fromByteBuffer(buffer);
        RecordType qtype  = RecordType.fromByteBuffer(buffer);
        QClass qclass = QClass.fromByteBuffer(buffer);

        Question newQuestion = new Question(qname, qtype, qclass);
        Log.info("Question parsed: {0}", newQuestion);
        return newQuestion;
    }

    public static Question fromByteBuffer(ByteArrayBuffer buffer) throws ParseException {
        return Question.fromByteBuffer(buffer, buffer.position());
    }
}
