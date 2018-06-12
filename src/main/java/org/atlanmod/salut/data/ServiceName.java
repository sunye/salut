package org.atlanmod.salut.data;

import fr.inria.atlanmod.commons.Preconditions;
import fr.inria.atlanmod.commons.annotation.VisibleForTesting;
import fr.inria.atlanmod.commons.log.Log;
import org.atlanmod.salut.mdns.NameArray;

import java.text.ParseException;

/**
 * From [RFC 6763](https://tools.ietf.org/html/rfc6763):
 * <p>
 * > "The <Service> portion of the Service Instance Name consists of a pair
 * of DNS labels, following the convention already established for SRV
 * records [RFC2782].  The first label of the pair is an underscore
 * character followed by the Service Name [RFC6335].  The Service Name
 * identifies what the service does and what application protocol it
 * uses to do it.  The second label is either "_tcp" (for application
 * protocols that run over TCP) or "_udp" (for all others).  For more
 * details, see Section 7, "Service Names"."
 * <p>
 * The record contains just one piece of information, the name of the service instance (which is the same as the name
 * of the SRV record).
 * PTR records are accordingly named just like SRV records but without the instance name:
 * <p>
 * <Service Type>.<Domain>
 * Example: _printer._tcp.local.
 */
public class ServiceName {
    private ApplicationProtocol application;
    private TransportProtocol transport;
    private DomainName domain;

    public ServiceName(ApplicationProtocol type, TransportProtocol transportProtocol, DomainName domain) {
        this.application = type;
        this.transport = transportProtocol;
        this.domain = domain;
    }

    public ApplicationProtocol getApplication() {
        return application;
    }

    public TransportProtocol getTransport() {
        return transport;
    }

    public DomainName getDomain() {
        return domain;
    }

    @Override
    public String toString() {
        return application + "." + transport + "." + domain;
    }

    public static ServiceName fromNameArray(NameArray names) throws ParseException {
        Log.info("Parsing Service  Name: {0}", names);
        Preconditions.checkArgument(names.size() >= 3);

        ApplicationProtocol type = ApplicationProtocolBuilder.fromString(names.get(0));
        TransportProtocol transport = TransportProtocol.fromString(names.get(1));
        DomainName host = DomainNameBuilder.fromNameArray(names.subArray(2, names.size()));

        return new ServiceName(type, transport, host);
    }

    @VisibleForTesting
    public static ServiceName fromStrings(String application, String transport, String name) {
        return new ServiceName(ApplicationProtocolBuilder.fromString(application),
                TransportProtocol.fromString(transport), new LocalDomainName(name));
    }

    public static ServiceName parseString(String str) throws ParseException {
        String[] labels = str.split("\\.");
        if (labels.length != 3) {
            throw new ParseException("Service Names must have 3 labels", 0);
        }

        return new ServiceName(ApplicationProtocolBuilder.fromString(labels[0]),
                TransportProtocol.fromString(labels[1]),
                new LocalDomainName(labels[2]));
    }

    public ServiceType getServiceType() {
        return new ServiceType(application, transport);
    }
}
