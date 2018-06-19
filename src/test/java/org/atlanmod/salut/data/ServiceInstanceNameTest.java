package org.atlanmod.salut.data;

import org.atlanmod.salut.mdns.NameArray;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

import static  org.assertj.core.api.Assertions.*;

class ServiceInstanceNameTest {

    @Test
    void fromNameArray() throws ParseException {
        ServiceInstanceName name = ServiceInstanceName.fromNameArray(NameArray.fromList("PrintsAlot", "airplay", "tcp", "MacBook", "local"));

        assertThat(new InstanceName("PrintsAlot")).isEqualTo(name.instance());
        assertEquals(ApplicationProtocolBuilder.fromApplicationProtocol(IANAApplicationProtocol.airplay), name.application());
        assertEquals(TransportProtocol.tcp, name.transport());

    }

    @Test
    void testParseString() throws ParseException {
        ServiceInstanceName name = ServiceInstanceName.parseString("PrintsAlot.airplay.tcp.MacBook.local");

        assertThat(name.application()).isEqualTo(ApplicationProtocolBuilder.fromString("airplay"));
        assertThat(name.transport()).isEqualTo(TransportProtocol.tcp);
        assertThat(name.instance()).isEqualTo(new InstanceName("PrintsAlot"));

        assertThat(name.domain()).isEqualTo(new LocalDomainName("MacBook"));
    }
}