package org.atlanmod.salut.data;

import static com.google.common.truth.Truth.assertThat;

import java.text.ParseException;
import org.atlanmod.salut.mdns.LabelArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class ServiceInstanceNameTest {

    ServiceInstanceName printer;
    ServiceInstanceName music;

    @BeforeEach
    void beforeEach() throws ParseException {
        printer = ServiceInstanceName
            .fromNameArray(LabelArray.fromList("PrintsAlot", "airplay", "tcp", "MacBook", "local"));

        music = ServiceInstanceName
            .parseString("iTunes.airplay.tcp.MacBook.local");
    }

    @DisplayName("Given: a host name 'PrintsAlot.airplay.tcp.MacBook.local' "
        + "When: a Service Instance name is created with fromString()"
        + "Then: the instance name is 'PrintsAlot' ")
    @Test
    void create_service_instance_name_from_names_check_name() {
        InstanceName expected = InstanceName.fromString("PrintsAlot");
        assertThat(expected).isEqualTo(printer.instance());
    }

    @DisplayName("Given: a host name 'PrintsAlot.airplay.tcp.MacBook.local' "
        + "When: a Service Instance name is created with fromString()"
        + "Then: the service name is 'airplay.tcp' ")
    @Test
    void create_service_instance_name_from_names_check_service() {
        ApplicationProtocol applicationProtocol = ApplicationProtocolBuilder
            .fromApplicationProtocol(IANAApplicationProtocol.airplay);
        TransportProtocol transportProtocol = TransportProtocol.tcp;
        ServiceName expected = ServiceName.create(applicationProtocol, transportProtocol);

        assertThat(expected).isEqualTo(printer.service());
    }

    @DisplayName("Given: a host name 'PrintsAlot.airplay.tcp.MacBook.local' "
        + "When: a Service Instance name is created with fromString()"
        + "Then: the domain name is 'MacBook.local' ")
    @Test
    void create_service_instance_name_from_names_check_domain() throws ParseException {
        Domain expected = DomainBuilder.parseString("MacBook.local");

        assertThat(expected).isEqualTo(printer.domain());
    }

    @DisplayName("Given a host name 'iTunes.airplay.tcp.MacBook.local' "
        + "When a Service Instance Name is created with parseString()"
        + "Then the expected Instance Name is 'iTunes' ")
    @Test
    void create_service_instance_name_from_string_check_instance_name() throws ParseException {
        assertThat(music.instance()).isEqualTo(InstanceName.fromString("iTunes"));
    }

    @DisplayName("Given a host name 'iTunes.airplay.tcp.MacBook.local' "
        + "When a Service Instance Name is created with parseString()"
        + "Then the expected Service Name is 'airplay.tcp' ")
    @Test
    void create_service_instance_name_from_string_check_service_name() throws ParseException {
        assertThat(music.service())
            .isEqualTo(ServiceName.fromStrings("airplay", "tcp"));
    }

    @DisplayName("Given a host name 'iTunes.airplay.tcp.MacBook.local' "
        + "When a Service Instance Name is created with parseString()"
        + "Then the expected Domain Name is 'MacBook' ")
    @Test
    void create_service_instance_name_from_string_check_domain_name() throws ParseException {
        assertThat(music.domain()).isEqualTo(new LocalDomain("MacBook"));
    }
}
