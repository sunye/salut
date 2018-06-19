package org.atlanmod.salut.data;

import fr.inria.atlanmod.commons.Preconditions;
import fr.inria.atlanmod.commons.annotation.VisibleForTesting;
import fr.inria.atlanmod.commons.log.Log;
import org.atlanmod.salut.mdns.NameArray;

import java.text.ParseException;

/**
 * Service Instance Name = <Instance> . <Service> . <Domain>
 * <p>
 * Domain data
 * used by DNS-SD take the following forms:
 *
 * <Instance> . <sn>._tcp . <servicedomain> . <parentdomain>.
 */
public class ServiceInstanceName {

    private InstanceName instanceName;
    private ApplicationProtocol applicationProtocol;
    private TransportProtocol transportProtocol;
    private DomainName domainName;

    public ServiceInstanceName(InstanceName instanceName, ApplicationProtocol applicationProtocol, TransportProtocol transportProtocol, DomainName domainName) {
        this.instanceName = instanceName;
        this.applicationProtocol = applicationProtocol;
        this.transportProtocol = transportProtocol;
        this.domainName = domainName;
    }

    public InstanceName instance() {
        return instanceName;
    }

    public ServiceType getType() {
        return new ServiceType(applicationProtocol, transportProtocol);
    }

    public TransportProtocol transport() {
        return transportProtocol;
    }

    public ApplicationProtocol application() {
        return applicationProtocol;
    }

    public DomainName domain() {
        return this.domainName;
    }

    /**
     * 4.3.  Internal Handling of Names
     * <p>
     * > "If client software takes the <Instance>, <Service>, and <Domain>
     * portions of a Service Instance Name and internally concatenates them
     * together into a single string, then because the <Instance> portion is
     * allowed to contain any characters, including dots, appropriate
     * precautions MUST be taken to ensure that DNS label boundaries are
     * properly preserved.  Client software can do this in a variety of
     * ways, such as character escaping.
     * <p>
     * This document RECOMMENDS that if concatenating the three portions of
     * a Service Instance Name, any dots in the <Instance> portion be
     * escaped following the customary DNS convention for text files: by
     * preceding literal dots with a backslash (so "." becomes "\.").
     * Likewise, any backslashes in the <Instance> portion should also be
     * escaped by preceding them with a backslash (so "\" becomes "\\")."
     * <p>
     * Having done this, the three components of the name may be safely
     * concatenated.  The backslash-escaping allows literal dots in the name
     * (escaped) to be distinguished from label-separator dots (not
     * escaped), and the resulting concatenated string may be safely passed
     * to standard DNS APIs like res_query(), which will interpret the
     * backslash-escaped string as intended.
     *
     * @return A String concatenating the three parts of a service instance name.
     */
    @Override
    public String toString() {
        return "{" + instanceName +
                ", " + applicationProtocol +
                ", " + transportProtocol +
                ", " + domainName +
                '}';
    }

    public static ServiceInstanceName fromNameArray(NameArray names) throws ParseException {
        Log.info("Parsing Service Instance Name: {0}", names);
        Preconditions.checkArgument(names.size() >= 4);

        InstanceName instanceName = new InstanceName(names.get(0));
        ApplicationProtocol type = ApplicationProtocolBuilder.fromString(names.get(1));
        TransportProtocol transport = TransportProtocol.fromString(names.get(2));
        DomainName host = DomainNameBuilder.fromNameArray(names.subArray(3, names.size()));

        return new ServiceInstanceName(instanceName, type, transport, host);
    }

    @VisibleForTesting
    public static ServiceInstanceName createServiceName(InstanceName instanceName, ApplicationProtocol type,
                                                        TransportProtocol transport, DomainName host) {
        return new ServiceInstanceName(instanceName, type, transport, host);
    }

    public static ServiceInstanceName parseString(String str) throws ParseException {
        String[] labels = str.split("\\.");
        if (labels.length != 5) {
            throw new ParseException("Service Instance Names must have 5 labels", 0);
        }

        return new ServiceInstanceName(new InstanceName(labels[0]),
                ApplicationProtocolBuilder.fromString(labels[1]),
                TransportProtocol.fromString(labels[2]),
                DomainNameBuilder.fromNameArray(NameArray.fromList(labels[3], labels[4])));
    }

}
