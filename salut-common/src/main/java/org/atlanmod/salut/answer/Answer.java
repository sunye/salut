package org.atlanmod.salut.answer;

import org.atlanmod.salut.record.Record;

public class Answer {

    private final Record record;

    public Answer(Record record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "Answer{" + record + '}';
    }
}
