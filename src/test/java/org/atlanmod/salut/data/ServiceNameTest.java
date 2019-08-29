package org.atlanmod.salut.data;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

class ServiceNameTest {

    @Test
    void parseString() throws ParseException {
        ServiceName name = ServiceName.parseString("ipp.tcp");
        assertThat(name.getApplication()).isEqualTo(ApplicationProtocolBuilder.fromString("ipp"));
        assertThat(name.getTransport()).isEqualTo(TransportProtocol.tcp);
    }
}
