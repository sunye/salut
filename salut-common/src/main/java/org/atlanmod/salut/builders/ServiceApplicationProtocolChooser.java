package org.atlanmod.salut.builders;

public interface ServiceApplicationProtocolChooser {

    ServiceTransportProtocolChooser http();
    ServiceTransportProtocolChooser airplay();
    ServiceTransportProtocolChooser application(String str);
}
