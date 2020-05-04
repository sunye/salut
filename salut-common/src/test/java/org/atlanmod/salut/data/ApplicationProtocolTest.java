package org.atlanmod.salut.data;

import static org.assertj.core.api.Assertions.*;

import org.atlanmod.salut.names.ServiceName;
import org.junit.jupiter.api.Test;

class ApplicationProtocolTest {

    @Test
    public void withTransportProtocol() {
        ApplicationProtocol ap = ApplicationProtocolBuilder.fromString("smb");
        ServiceName name = ap.with(TransportProtocol.udp);

        assertThat(name.getTransport()).isEqualTo(TransportProtocol.udp);
        assertThat(name.getApplication()).isEqualTo(ap);
    }

}