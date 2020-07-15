package org.atlanmod.salut.question;

import org.atlanmod.commons.Preconditions;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.names.ServiceName;

public class ServiceDiscoveryQuestion implements Question {

    private final Labels name;

    private ServiceDiscoveryQuestion(Labels name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SRVQuestion{" + name + '}';
    }

    public static ServiceDiscoveryQuestion fromLabels(Labels labels) {
        // TODO: Handle Services with subtypes
        if (labels.size() == 5) {
            Log.error("Could not handle correctly Pointer query for: " + labels.toString());
        }

        //ServiceName name = ServiceName.fromLabels(labels.subArray(0,2));
        return new ServiceDiscoveryQuestion(labels);
    }
}
