package org.atlanmod.salut.mdns;

import org.atlanmod.commons.Preconditions;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.MulticastPackage;
import org.atlanmod.salut.record.Question;

public class Responder {

    public void accept(MulticastPackage pack) {
        Preconditions.checkArgument(pack.isQuery(), "Responder only answers to queries");
        for (Question each: pack.questions()) {
            this.handleQuestion(each);
        }
    }

    private void handleQuestion(Question question) {
        Log.info("Handling Question {0}", question);
    }

}
