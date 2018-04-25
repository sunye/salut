package org.atlanmod.salut.data;

import fr.inria.atlanmod.commons.Preconditions;
import fr.inria.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.salut.mdns.NameArray;

import java.text.ParseException;

/**
 * Service Instance Name = <Instance> . <Service> . <Domain>
 *
 * Domain data
 *    used by DNS-SD take the following forms:
 *
 *       <Instance> . <sn>._tcp . <servicedomain> . <parentdomain>.
 */
public class ServiceInstanceName {

    private InstanceName instanceName;
    private AbstractServiceType type;
    private TransportProtocol transportProtocol;
    private DomainName host;

    public ServiceInstanceName(InstanceName instanceName, AbstractServiceType type, TransportProtocol transportProtocol, DomainName host) {
        this.instanceName = instanceName;
        this.type = type;
        this.transportProtocol = transportProtocol;
        this.host = host;
    }

    public InstanceName instance() {
        return instanceName;
    }

    public AbstractServiceType type() {
        return type;
    }

    public TransportProtocol transport() {
        return transportProtocol;
    }

    /**
     * 4.3.  Internal Handling of Names
     *
     * > "If client software takes the <Instance>, <Service>, and <Domain>
     *    portions of a Service Instance Name and internally concatenates them
     *    together into a single string, then because the <Instance> portion is
     *    allowed to contain any characters, including dots, appropriate
     *    precautions MUST be taken to ensure that DNS label boundaries are
     *    properly preserved.  Client software can do this in a variety of
     *    ways, such as character escaping.
     *
     *    This document RECOMMENDS that if concatenating the three portions of
     *    a Service Instance Name, any dots in the <Instance> portion be
     *    escaped following the customary DNS convention for text files: by
     *    preceding literal dots with a backslash (so "." becomes "\.").
     *    Likewise, any backslashes in the <Instance> portion should also be
     *    escaped by preceding them with a backslash (so "\" becomes "\\")."
     *
     *    Having done this, the three components of the name may be safely
     *    concatenated.  The backslash-escaping allows literal dots in the name
     *    (escaped) to be distinguished from label-separator dots (not
     *    escaped), and the resulting concatenated string may be safely passed
     *    to standard DNS APIs like res_query(), which will interpret the
     *    backslash-escaped string as intended.
     *
     * @return A String concatenating the three parts of a service instance name.
     */
    @Override
    public String toString() {
        return "ServiceInstanceName{" +
                "instanceName='" + instanceName + '\'' +
                ", type=" + type +
                ", transportProtocol=" + transportProtocol +
                ", host=" + host +
                '}';
    }

    public static ServiceInstanceName fromNameArray(NameArray names) throws ParseException {
        Preconditions.checkArgument(names.size() >= 5);

        InstanceName instanceName = new InstanceName(names.get(0));
        AbstractServiceType type = AbstractServiceType.fromString(names.get(1));
        TransportProtocol transport = TransportProtocol.fromString(names.get(2));
        DomainName host = DomainNameBuilder.fromNameArray(names.subArray(3,names.size()));

        return new ServiceInstanceName(instanceName, type, transport, host);
    }

    @VisibleForTesting
    public static ServiceInstanceName createServiceName(InstanceName instanceName, AbstractServiceType type, TransportProtocol transport, DomainName host) {
        return new ServiceInstanceName(instanceName, type, transport, host);
    }
}
