package org.atlanmod.salut.data;

import org.atlanmod.commons.Preconditions;
import org.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.mdns.LabelArray;

import java.text.ParseException;
import java.util.Objects;

/**
 * From [RFC 6763](https://tools.ietf.org/html/rfc6763):
 * <p>
 * > "The <ServiceDescription> portion of the ServiceDescription Instance Label consists of a pair
 * of DNS labels, following the convention already established for SRV
 * records [RFC2782].
 * The first label of the pair is an underscore
 * character followed by the ServiceDescription Label [RFC6335].  The ServiceDescription Label
 * identifies what the service does and what application protocol it
 * uses to do it.
 * The second label is either "_tcp" (for application
 * protocols that run over TCP) or "_udp" (for all others).  For more
 * details, see Section 7, "ServiceDescription Names"."
 * <p>
 * The record contains just one piece of information, the name of the service instance (which is the same as the name
 * of the SRV record).
 * PTR records are accordingly named just like SRV records but without the instance name:
 * <p>
 * <ServiceDescription Type>.<Domain>
 * Example: _printer._tcp.local.
 */
public class ServiceName {
    private ApplicationProtocol application;
    private TransportProtocol transport;

    public ServiceName(ApplicationProtocol type, TransportProtocol transportProtocol) {
        this.application = type;
        this.transport = transportProtocol;
    }

    public ApplicationProtocol getApplication() {
        return application;
    }

    public TransportProtocol getTransport() {
        return transport;
    }


    @Override
    public String toString() {
        return application + "." + transport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceName)) return false;
        ServiceName that = (ServiceName) o;
        return application.equals(that.application) &&
                transport == that.transport;
    }

    @Override
    public int hashCode() {
        return Objects.hash(application, transport);
    }

    public static ServiceName fromLabelArray(LabelArray names) throws ParseException {
        Log.info("Parsing ServiceDescription Label: {0}", names);
        Preconditions.checkArgument(names.size() == 2);

        ApplicationProtocol type = ApplicationProtocolBuilder.fromLabel(names.get(0));
        TransportProtocol transport = TransportProtocol.fromLabel(names.get(1));

        return new ServiceName(type, transport);
    }

    @VisibleForTesting
    public static ServiceName fromStrings(String application, String transport) {
        return new ServiceName(ApplicationProtocolBuilder.fromString(application),
                TransportProtocol.fromString(transport));
    }

    @VisibleForTesting
    public static ServiceName create(ApplicationProtocol application, TransportProtocol transport) {
        return new ServiceName(application, transport);
    }

    public static ServiceName parseString(String str) throws ParseException {
        String[] labels = str.split("\\.");
        if (labels.length != 2) {
            throw new ParseException("ServiceDescription Names must have 2 labels", 0);
        }

        return new ServiceName(ApplicationProtocolBuilder.fromString(labels[0]),
                TransportProtocol.fromString(labels[1]));
    }

    public ServiceType getServiceType() {
        return new ServiceType(application, transport);
    }
}
