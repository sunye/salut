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
        assertEquals(AbstractServiceType.fromApplicationProtocol(ApplicationProtocol.airplay), name.type());
        assertEquals(TransportProtocol.tcp, name.transport());

    }

    @Test
    void createServiceName() {
    }
}