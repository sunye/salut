package org.atlanmod.salut.cache;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.names.ServiceInstanceName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InstanceEntryTest {
    private Labels names = Labels.fromList("adisk", "tcp", "MacBook", "local");
    private InstanceEntry entry;
    private ServiceInstanceName instance;


    @BeforeEach
    void setUp() throws ParseException {
        instance = ServiceInstanceName.fromLabels(names);
        entry = new InstanceEntry(instance);
    }


    @Test
    void name() throws ParseException {
        ServiceInstanceName expected = ServiceInstanceName.fromLabels(names);
        assertThat(entry.name()).isEqualTo(expected);
    }

}
