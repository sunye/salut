package org.atlanmod.salut.names;

import static com.google.common.truth.Truth.*;

import java.text.ParseException;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.labels.Labels;
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

}