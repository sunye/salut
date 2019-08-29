package org.atlanmod.salut.data;

import org.atlanmod.salut.mdns.LabelArray;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static  org.assertj.core.api.Assertions.*;

class ServiceInstanceNameTest {

    @Test
    void fromNameArray() throws ParseException {
        ServiceInstanceName name = ServiceInstanceName.fromNameArray(LabelArray.fromList("PrintsAlot", "airplay", "tcp", "MacBook", "local"));

        assertThat(InstanceName.fromString("PrintsAlot")).isEqualTo(name.instance());
        //assertEquals(ApplicationProtocolBuilder.fromApplicationProtocol(IANAApplicationProtocol.airplay), name.application());
        //assertEquals(TransportProtocol.tcp, name.transport());

    }

    @Test
    void testParseString() throws ParseException {
        ServiceInstanceName serviceInstanceName = ServiceInstanceName.parseString("PrintsAlot.airplay.tcp.MacBook.local");
        assertThat(serviceInstanceName.service()).isEqualTo(ServiceName.fromStrings("airplay", "tcp"));
        assertThat(serviceInstanceName.instance()).isEqualTo(InstanceName.fromString("PrintsAlot"));
        assertThat(serviceInstanceName.domain()).isEqualTo(new LocalDomain("MacBook"));
    }
}
