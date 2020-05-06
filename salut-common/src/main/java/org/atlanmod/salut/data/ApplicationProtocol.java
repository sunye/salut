package org.atlanmod.salut.data;

import org.atlanmod.salut.names.ServiceName;

public interface ApplicationProtocol {

    /**
     * The name of this Application Protocol
     *
     * @return a String representing the name of this Application Protocol
     */
    String name();

    /**
     * Convenience method that creates an instance of a Service Name with this Application Protocol
     * and a Transport Protocol
     *
     * @param transportProtocol The Transport Protocol that will be joined to this Application
     *                          Protocol to form a Service Name.
     * @return An instance of Service Name
     */
    public default ServiceName with(TransportProtocol transportProtocol) {
        return new ServiceName(this, transportProtocol);
    }
}
