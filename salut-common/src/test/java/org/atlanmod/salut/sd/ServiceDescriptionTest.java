package org.atlanmod.salut.sd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.atlanmod.salut.data.ApplicationProtocol;
import org.atlanmod.salut.data.ApplicationProtocolBuilder;
import org.atlanmod.salut.data.TransportProtocol;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.io.UnsignedShort;
import org.atlanmod.salut.names.InstanceName;
import org.atlanmod.salut.names.ServiceName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceDescriptionTest {

    private ServiceDescription music;

    @BeforeEach
    void setUp() {
        InstanceName instance = InstanceName.fromString("Mario's music");
        ApplicationProtocol application = ApplicationProtocolBuilder.fromString("daap");
        TransportProtocol transport = TransportProtocol.tcp;
        UnsignedShort port = UnsignedShort.fromInt(3689);
        UnsignedShort weight = UnsignedShort.fromInt(100);
        UnsignedShort priority = UnsignedShort.fromInt(0);
        UnsignedInt ttl = UnsignedInt.fromInt(3600);

        music = new ServiceDescription(instance, port, transport, application, Optional.empty(),
            weight, priority, ttl);
    }

    @Test
    void port() {
        assertThat(music.port()).isEqualTo(UnsignedShort.fromInt(3689));
    }

    @Test
    void weight() {
        assertThat(music.weight()).isEqualTo(UnsignedShort.fromInt(100));
    }

    @Test
    void priority() {
        assertThat(music.priority()).isEqualTo(UnsignedShort.fromInt(0));
    }

    @Test
    void ttl() {
        assertThat(music.ttl()).isEqualTo(UnsignedInt.fromInt(3600));
    }

    @Test
    void instanceName() {
        assertThat(music.instanceName()).isEqualTo(InstanceName.fromString("Mario's music"));
    }

    @Test
    void serviceName() {
        ServiceName expected = ServiceName.fromStrings("daap", "tcp");

        assertThat(music.serviceName()).isEqualTo(expected);
    }
}