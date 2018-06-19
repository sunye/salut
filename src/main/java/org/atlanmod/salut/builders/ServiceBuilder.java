package org.atlanmod.salut.builders;


import org.atlanmod.salut.data.ApplicationProtocol;
import org.atlanmod.salut.data.IANAApplicationProtocol;
import org.atlanmod.salut.data.ApplicationProtocolBuilder;
import org.atlanmod.salut.data.TransportProtocol;
import org.atlanmod.salut.sd.Service;
import org.atlanmod.salut.sd.ServicePublisher;

import java.util.Optional;


public class ServiceBuilder implements SetApplicationProtocol, IPublish, IServiceName, IPort,
        SetTransportProtocol, IQuery {

    /**
     * the local getPort on which the service runs
     */
    private int port;

    /**
     * getWeight of the service
     */
    private int weight = 0;

    /**
     * getPriority of the service
     */
    private int priority = 0;

    /**
     * Service Transport protocol (TCP or UDP).
     */
    private TransportProtocol protocol = TransportProtocol.tcp;

    /**
     *
     */
    private ApplicationProtocol serviceType;

    /**
     * Unqualified service instance name.
     */
    private String name;


    /**
     * Service subtype
     *
     * @see <a href="https://tools.ietf.org/html/rfc6763#section-7.1">DNS-Based Service Discovery</a>
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
        this.port = port;
        return this;
    }


    public ServiceBuilder name(String name) {
        this.name = name;
        return this;
    }

    public IPublish subtype(String subtype) {
        this.subtype = Optional.ofNullable(subtype);
        return this;
    }


    public IPublish weight(int weight) {
        this.weight = weight;
        return this;
    }

    public IPublish priority(int priority) {
        this.priority = priority;
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
        Service service = new Service(name, port, protocol, serviceType, subtype, weight, priority);
        publisher.publish(service);
    }

    @Override
    public void run() {

    }
}

