package org.atlanmod.salut.question;

import org.atlanmod.salut.labels.Labels;

public class AllQuestion implements Question {

    private Labels name;

    public AllQuestion(Labels name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AllQuestion{" + name + '}';
    }
}
