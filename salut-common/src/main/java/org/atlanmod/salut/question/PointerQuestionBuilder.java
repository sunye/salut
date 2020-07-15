package org.atlanmod.salut.question;

import org.atlanmod.commons.annotation.Builder;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.labels.Labels;

@Builder
public class PointerQuestionBuilder {

    private static final Labels META_QUERY = Labels.fromList("_services","_dns-sd","_udp", "local");
    private static final Labels ARPA_IPV4 = Labels.fromList(Domain.IP4, Domain.ARPA);
    private static final Labels ARPA_IPV6 = Labels.fromList(Domain.IP6, Domain.ARPA);


    public static Question createPointerQuestion(Labels labels) {
        if ((labels.size() == 3) || (labels.size() == 5)) {
            return ServiceDiscoveryQuestion.fromLabels(labels);
        } else if (labels.equals(META_QUERY)) {
            return new ServiceDiscoveryMetaQuestion();
        } else if (labels.size() == 6 && labels.subArray(4,6).equals(ARPA_IPV4)) {
            return new IPv4ReverseLookup();
        } else if (labels.size() == 34 && labels.subArray(32,34).equals(ARPA_IPV6)) {
            return new IPv6ReverseLookup();
        } else  {
            throw new IllegalArgumentException("Could not find Pointer question for name: "+ labels.toString());
        }
    }
}
