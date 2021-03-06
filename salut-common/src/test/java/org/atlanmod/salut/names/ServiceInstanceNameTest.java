package org.atlanmod.salut.names;

import static org.assertj.core.api.Assertions.assertThat;
import static org.atlanmod.testing.Verifier.verifyEqualsOf;

import java.text.ParseException;
import org.atlanmod.salut.data.ApplicationProtocol;
import org.atlanmod.salut.data.ApplicationProtocolBuilder;
import org.atlanmod.salut.data.IANAApplicationProtocol;
import org.atlanmod.salut.data.TransportProtocol;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.domains.LocalHostName;
import org.atlanmod.salut.labels.Labels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class ServiceInstanceNameTest {

    ServiceInstanceName printer;
    ServiceInstanceName music;

    @BeforeEach
    void beforeEach() throws ParseException {
        printer = ServiceInstanceName
            .fromLabels(Labels.fromList("PrintsAlot", "airplay", "tcp", "MacBook", "local"));

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
        assertThat(music.domain()).isEqualTo(LocalHostName.fromString("MacBook"));
    }

    @Test
    void test_equals() throws ParseException {
        Object[] args1 = {
            InstanceName.fromString("canon-mg5300"),
            ServiceName.fromStrings("ipp", "tcp"),
            LocalHostName.fromString("mac-pro")
        };
        Object[] args2 = {
            InstanceName.fromString("canon-mg5400"),
            ServiceName.fromStrings("ipp", "udp"),
            LocalHostName.fromString("sun-station")
        };

        verifyEqualsOf(ServiceInstanceName.class)
            .withArguments(args1)
            .andVariants(args2)
            .check();
    }

    @DisplayName("Given a service instance name 'canon-mg500.ipp.tcp.mac-pro.local' "
        + "When 'toLabels()' is called"
        + "Then the expect list of labels must be {'canon-mg500','ipp','tcp','mac-pro','local'}")
    @Test
    void test_toLabels() {
        ServiceInstanceName instance =
            ServiceInstanceName.createServiceInstanceName(InstanceName.fromString("canon-mg5300"),
                ServiceName.fromStrings("ipp", "tcp"),
                LocalHostName.fromString("mac-pro"));

        Labels expected = Labels.fromList("canon-mg5300", "ipp", "tcp", "mac-pro", "local");
        Labels actual = instance.toLabels();

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Given two different instances of the same service instance name"
        + "Expect hash codes to be the same")
    @Test
    void same_object_has_same_hashcode() throws ParseException {
        int expected = ServiceInstanceName
            .fromLabels(Labels.fromList("PrintsAlot", "airplay", "tcp", "MacBook", "local")).hashCode();

        assertThat(printer.hashCode()).isEqualTo(expected);
    }

    @DisplayName("Given two different instances of different service instance names"
        + "Expect hash codes to be different")
    @Test
    void different_objects_have_different_hashcodes() throws ParseException {

        assertThat(printer.hashCode()).isNotEqualTo(music.hashCode());
    }

}
