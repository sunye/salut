package org.atlanmod.salut.builders;

import static com.google.common.truth.Truth.assertThat;

import java.util.Optional;
import org.atlanmod.salut.Salut;
import org.atlanmod.salut.data.ApplicationProtocolBuilder;
import org.atlanmod.salut.data.TransportProtocol;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.io.UnsignedShort;
import org.atlanmod.salut.names.InstanceName;
import org.atlanmod.salut.sd.ServiceDescription;
import org.atlanmod.salut.sd.ServicePublisher;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ServiceBuilderIT {

    @Test
    void test()  {
        MockPublisher publisher = new MockPublisher();
        ServiceBuilder builder = new ServiceBuilder(publisher);
        builder.instance("my printer")
                .airplay()
                .udp()
                .port(221)
                .persistent()
                .weight(200)
                .priority(1)
                .publish();

        ServiceDescription expected = new ServiceDescription(InstanceName.fromString("my printer"),
            UnsignedShort.fromInt(221),
            TransportProtocol.udp,
            ApplicationProtocolBuilder.fromString("airplay"),
            Optional.empty(),
            UnsignedShort.fromInt(200),
            UnsignedShort.fromInt(1),
            UnsignedInt.fromInt(60*60));

        assertThat(publisher.description).isEqualTo(expected);
    }


    @Disabled
    @Test
    void servicePublishingTest() {
        Salut.getInstance()
            .builder()
            .service()
            .instance("apache-someuniqueid")
            .http()
            .tcp()
            .port(80)
            .nonpersistent()
            .publish();
    }

    static class MockPublisher implements ServicePublisher {
        ServiceDescription description;
        @Override
        public void publish(ServiceDescription description) {
            this.description = description;
        }
    }

}
