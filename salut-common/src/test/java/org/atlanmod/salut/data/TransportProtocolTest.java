package org.atlanmod.salut.data;

import static org.assertj.core.api.Assertions.assertThat;

import org.atlanmod.salut.names.ServiceName;
import org.junit.jupiter.api.Test;

class TransportProtocolTest {


    @Test
    public void withApplicationProtocol() {
        ApplicationProtocol ap = ApplicationProtocolBuilder.fromString("airplay");
        ServiceName name = TransportProtocol.tcp.with(ap);

        assertThat(name.getApplication()).isEqualTo(ap);
        assertThat(name.getTransport()).isEqualTo(TransportProtocol.tcp);
    }

}
