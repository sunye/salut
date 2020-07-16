package org.atlanmod.salut.mdns;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.MulticastPackage;
import org.atlanmod.salut.names.ServiceInstanceName;
import org.atlanmod.salut.names.ServiceName;
import org.atlanmod.salut.answer.Answer;
import org.atlanmod.salut.sd.QueryDescription;

public class Querier {

    private Map<ServiceName, ResponseHandler> currentQueries = new ConcurrentHashMap<>();

    /**
     * From [RFC6762]:
     * <p>
     * Multicast DNS responses MUST NOT contain any questions in the Question Section. Any questions
     * in the Question Section of a received Multicast DNS response MUST be silently ignored.
     * Multicast DNS queriers receiving Multicast DNS responses do not care what question elicited
     * the response; they care only that the information in the response is true and accurate.
     *
     * @param response
     */
    public void accept(MulticastPackage response) {
        for (Answer each: response.answers()) {
            this.handleAnswer(each);
        }
    }

    private void handleAnswer(Answer answer) {
        Log.warn("Handling Answer {0}", answer);
    }


    public Future<ServiceInstanceName> oneShotQuery(QueryDescription query) {
        return null;
    }

    public void continuousQuery(QueryDescription query, ServiceInstanceListener listener) {
        currentQueries.put(query.serviceName(), new ContinuousResponseHandler(listener));
    }

    private abstract class ResponseHandler {
        public abstract void handle();
    }

    private class OneShotResponseHandler extends ResponseHandler {
        private final Future<ServiceInstanceName> future;

        public OneShotResponseHandler(
            Future<ServiceInstanceName> future) {
            this.future = future;
        }

        public void handle() {

        }
    }

    private class ContinuousResponseHandler extends ResponseHandler {
        private final ServiceInstanceListener listener;

        public ContinuousResponseHandler(ServiceInstanceListener listener) {
            this.listener = listener;
        }

        public void handle() {

        }
    }
}
