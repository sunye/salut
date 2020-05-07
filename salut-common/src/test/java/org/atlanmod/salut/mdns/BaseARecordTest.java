package org.atlanmod.salut.mdns;

import java.text.ParseException;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.domains.Host;
import org.atlanmod.salut.domains.IPAddressBuilder;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.verifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class BaseARecordTest {

    @Test
    void testEquals() throws ParseException {
        Object[] args1 = {
            QClass.IN,
            UnsignedInt.fromInt(10),
            new Host(DomainBuilder.fromLabels(Labels.fromList("MacBook", "local")),
                IPAddressBuilder.createIPv4Address(72, 16, 8, 4))
        };
        Object[] args2 = {
            QClass.Any,
            UnsignedInt.fromInt(11),
            new Host(DomainBuilder.fromLabels(Labels.fromList("Dell", "local")),
                IPAddressBuilder.createIPv4Address(72, 16, 8, 4))
        };

        EqualsVerifier.verify(BaseARecord.class, args1, args2);
    }
}