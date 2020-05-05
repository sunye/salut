package org.atlanmod.salut.cache;

import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.labels.Labels;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

class ServerEntryTest {


    @Test
    void testConstructor() throws ParseException {
        Domain name = DomainBuilder.fromLabels(Labels.fromList("macbook","local"));
        ServerEntry entry = new ServerEntry(name);

        assertThat(entry.name()).isEqualTo(name);
    }

}
