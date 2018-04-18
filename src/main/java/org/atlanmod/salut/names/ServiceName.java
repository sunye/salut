package org.atlanmod.salut.names;

import fr.inria.atlanmod.commons.Preconditions;
import fr.inria.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.salut.mdns.NameArray;

import java.text.ParseException;

/**
 *
 *
 *
 * Domain names
 *    used by DNS-SD take the following forms:
 *
 *       <Instance> . <sn>._tcp . <servicedomain> . <parentdomain>.
 */
public class ServiceName {

    private String instanceName;
    private ServiceType type;
    private TransportProtocol transportProtocol;
    private HostName host;

    public ServiceName(String instanceName, ServiceType type, TransportProtocol transportProtocol, HostName host) {
        this.instanceName = instanceName;
        this.type = type;
        this.transportProtocol = transportProtocol;
        this.host = host;
    }

    public String instance() {
        return instanceName;
    }

    public ServiceType type() {
        return type;
    }

    public TransportProtocol transport() {
        return transportProtocol;
    }

    @Override
    public String toString() {
        return "ServiceName{" +
                "instanceName='" + instanceName + '\'' +
                ", type=" + type +
                ", transportProtocol=" + transportProtocol +
                ", host=" + host +
                '}';
    }

    public static ServiceName fromNameArray(NameArray names) throws ParseException {
        Preconditions.checkArgument(names.size() >= 5);
        String instanceName = names.get(0);
        ServiceType type = ServiceType.fromString(names.get(1));
        TransportProtocol transport = TransportProtocol.fromString(names.get(2));
        HostName host = HostName.fromNameArray(names.subArray(3,names.size()));

        return new ServiceName(instanceName, type, transport, host);
    }

    @VisibleForTesting
    public static ServiceName createServiceName(String instanceName, ServiceType type, TransportProtocol transport, HostName host) {
        return new ServiceName(instanceName, type, transport, host);
    }
}
