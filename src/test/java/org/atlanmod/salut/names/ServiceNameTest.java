package org.atlanmod.salut.names;

import org.atlanmod.salut.mdns.NameArray;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class ServiceNameTest {

    @Test
    void fromNameArray() throws ParseException {
        ServiceName name = ServiceName.fromNameArray(NameArray.fromList("PrintsAlot", "airplay", "tcp", "MacBook", "local"));

        assertEquals("PrintsAlot", name.instance());
        assertEquals(ServiceType.fromApplicationProtocol(ApplicationProtocol.airplay), name.type());
        assertEquals(TransportProtocol.tcp, name.transport());

    }

    @Test
    void createServiceName() {
    }
}