package org.atlanmod.salut.names;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.verifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class PointerNameTest {

    @Test
    void create_simple_pointer_name() throws ParseException {
        ServiceName service = ServiceName.fromStrings("html", "tcp");
        Domain domain = DomainBuilder.fromLabels(Labels.fromList("local"));
        PointerName expected = PointerName.create(service, domain);

        Labels labels = Labels.fromList("html", "tcp", "local");
        PointerName actual = PointerName.fromLabels(labels);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testEquals() throws ParseException {
        Object[] args1 = {
            ServiceName.fromStrings("html", "tcp"),
            DomainBuilder.fromLabels(Labels.fromList("local"))
        };
        Object[] args2 = {
            ServiceName.fromStrings("daap", "tcp"),
            DomainBuilder.fromLabels(Labels.fromList("free","fr"))
        };

        EqualsVerifier.verify(PointerName.class, args1, args2);

    }

}
