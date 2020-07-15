package org.atlanmod.salut.question;

import org.atlanmod.salut.labels.Labels;

public class TextQuestion implements Question {

    private Labels name;

    public TextQuestion(Labels name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TextQuestion{" + name + '}';
    }
}
