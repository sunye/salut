package org.atlanmod.salut.builders;

import java.text.ParseException;
import java.util.Optional;
import org.atlanmod.salut.Salut;
import org.atlanmod.salut.data.ApplicationProtocol;
import org.atlanmod.salut.data.ApplicationProtocolBuilder;
import org.atlanmod.salut.data.IANAApplicationProtocol;
import org.atlanmod.salut.data.InstanceName;
import org.atlanmod.salut.data.ServiceInstanceName;
import org.atlanmod.salut.data.TransportProtocol;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.io.UnsignedShort;
import org.atlanmod.salut.sd.ServiceDescription;
import org.atlanmod.salut.sd.ServicePublisher;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.truth.Truth.*;

public class ServiceBuilderIT {


    @Disabled
    @Test
    void test()  {
        MockPublisher publisher = new MockPublisher();
        ServiceBuilder builder = new ServiceBuilder(publisher);
        builder.name("my printer")
                .port(221)
                .udp()
                .airplay()
                .persistent()
                .weight(200)
                .priority(1)
                .publish();

        // ServiceInstanceName instanceName, UnsignedShort port, TransportProtocol transportProtocol, ApplicationProtocol applicationProtocol,
        //                              Optional<String> subtype, UnsignedShort weight, UnsignedShort priority, UnsignedInt ttl
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
        Salut salut = new Salut();
        salut.builder()
                .service()
                .name("apache-someuniqueid")
                .port(80)
                .tcp()
                .http()
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
