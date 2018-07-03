package org.atlanmod.salut.mdns;

import fr.inria.atlanmod.commons.log.Log;
import org.atlanmod.salut.io.ByteArrayReader;

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
                "data=" + name +
                ", getType=" + type +
                ", qclass=" + qclass +
                '}';
    }

    public static Question fromByteBuffer(ByteArrayReader buffer, int position) throws ParseException {

        NameArray qname  = NameArray.fromByteBuffer(buffer);
        RecordType qtype  = buffer.readRecordType();
        QClass qclass = QClass.fromByteBuffer(buffer);

        Question newQuestion = new Question(qname, qtype, qclass);
        Log.info("Question parsed: {0}", newQuestion);
        return newQuestion;
    }

    public static Question fromByteBuffer(ByteArrayReader buffer) throws ParseException {
        return Question.fromByteBuffer(buffer, buffer.position());
    }
}
