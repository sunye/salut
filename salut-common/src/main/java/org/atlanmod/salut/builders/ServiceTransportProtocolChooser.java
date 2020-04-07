package org.atlanmod.salut.builders;

public interface ServiceTransportProtocolChooser {
    ServicePortModifier udp();
    ServicePortModifier tcp();
}
