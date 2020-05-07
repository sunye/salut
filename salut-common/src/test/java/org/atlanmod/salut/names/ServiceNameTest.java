package org.atlanmod.salut.names;

import static com.google.common.truth.Truth.assertThat;

import java.text.ParseException;
import org.atlanmod.salut.data.ApplicationProtocol;
import org.atlanmod.salut.data.ApplicationProtocolBuilder;
import org.atlanmod.salut.data.TransportProtocol;
import org.atlanmod.salut.labels.Labels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServiceNameTest {

    ServiceName ipp;

    @BeforeEach
    void before() throws ParseException {
        ipp = ServiceName.parseString("ipp.tcp");
    }

    @DisplayName("Given the string 'ipp.tcp' "
        + "When parseString() is called"
        + "Then the application protocol is IPP")
    @Test
    void create_service_name_from_string_check_application_protocol() {
        ApplicationProtocol expected = ApplicationProtocolBuilder.fromString("ipp");
        assertThat(ipp.getApplication()).isEqualTo(expected);
    }

    @DisplayName("Given the string 'ipp.tcp' "
        + "When parseString() is called"
        + "Then the transport protocol is TCP")
    @Test
    void create_service_name_from_string_check_transport_protocol() throws ParseException {
        TransportProtocol expected = TransportProtocol.tcp;
        assertThat(ipp.getTransport()).isEqualTo(expected);
    }

    @DisplayName("Given two strings ['atlanmod','udp']"
        + "When fromString() is called"
        + "Then the service name is 'atlanmod.upd' ")
    @Test
    void create_service_name_from_strings() {
        ServiceName actual = ServiceName.fromStrings("atlanmod", "udp");
        ServiceName expected = ServiceName.create(ApplicationProtocolBuilder.fromString("atlanmod"),
            TransportProtocol.udp);

        assertThat(actual).isEqualTo(expected);
    }


    @DisplayName("Given a label array ['ipp','tcp']"
        + "When fromLaleblArray() is called"
        + "Then the service name is 'ipp.tcp' ")
    @Test
    void create_from_labels() throws ParseException {
        ServiceName name = ServiceName.fromLabels(Labels.fromList("ipp", "tcp"));
        ServiceName expected =  ServiceName.create(ApplicationProtocolBuilder.fromString("ipp"), TransportProtocol.tcp);

        assertThat(name).isEqualTo(expected);
    }
}
