package org.atlanmod.salut.builders;


import org.atlanmod.salut.data.*;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.io.UnsignedShort;
import org.atlanmod.salut.names.InstanceName;
import org.atlanmod.salut.sd.ServiceDescription;
import org.atlanmod.salut.sd.ServicePublisher;

import java.util.Optional;


public class ServiceBuilder implements SetApplicationProtocol, IPublish, IServiceName, IPort,
        SetTransportProtocol, IQuery {


    /**
     * the local getPort on which the service runs
     */
    private UnsignedShort port;

    /**
     * getWeight of the service
     */
    private UnsignedShort weight = UnsignedShort.fromInt(0);

    /**
     * The priority of the service
     */
    private UnsignedShort priority = UnsignedShort.fromInt(0);

    /**
     * The time to live
     * Default value = 1 hour (3,600 seconds).
     */
    private UnsignedInt ttl = UnsignedInt.fromInt(60*60);

    /**
     * Service Transport protocol (TCP or UDP).
     */
    private TransportProtocol protocol = TransportProtocol.tcp;

    /**
     *
     */
    private ApplicationProtocol serviceType;

    /**
     * instance name.
     */
    private InstanceName name;


    /**
     * Service subtype
     *
     * @see <a href="https://tools.ietf.org/html/rfc6763#section-7.1">DNS-Based ServiceDiscovery</a>
     */
    private Optional<String> subtype = Optional.empty();

    /**
     *
     */
    private boolean persistent = false;

    private ServicePublisher publisher;


    public ServiceBuilder(ServicePublisher publisher) {
        this.publisher = publisher;
    }

    public SetTransportProtocol port(int port) {
        this.port = UnsignedShort.fromInt(port);
        return this;
    }


    public ServiceBuilder name(String name) {
        this.name = InstanceName.fromString(name);
        return this;
    }

    public IPublish subtype(String subtype) {
        this.subtype = Optional.ofNullable(subtype);
        return this;
    }


    public IPublish weight(int weight) {
        this.weight = UnsignedShort.fromInt(weight);
        return this;
    }

    public IPublish priority(int priority) {
        this.priority = UnsignedShort.fromInt(priority);
        return this;
    }

    public IPublish ttl(int ttl) {
        this.ttl = UnsignedInt.fromInt(ttl);
        return this;
    }

    public IPublish persistent() {
        this.persistent = true;
        return this;
    }

    public SetApplicationProtocol tcp() {
        this.protocol = TransportProtocol.tcp;
        return this;
    }

    public SetApplicationProtocol udp() {
        this.protocol = TransportProtocol.udp;
        return this;
    }

    public IPublish http() {
        this.serviceType = ApplicationProtocolBuilder.fromApplicationProtocol(IANAApplicationProtocol.http);
        return this;
    }

    public IPublish airplay() {
        this.serviceType = ApplicationProtocolBuilder.fromApplicationProtocol(IANAApplicationProtocol.airplay);
        return this;
    }

    @Override
    public IPublish application(String str) {
        this.serviceType = ApplicationProtocolBuilder.fromString(str);
        return this;
    }

    public void publish() {
        ServiceDescription service = new ServiceDescription(name, port, protocol, serviceType, subtype, weight, priority, ttl);
        publisher.publish(service);
    }

    @Override
    public void run() {

    }
}

