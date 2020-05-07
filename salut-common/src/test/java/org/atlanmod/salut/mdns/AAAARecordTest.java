package org.atlanmod.salut.mdns;

import java.text.ParseException;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.domains.IPAddressBuilder;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.verifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class AAAARecordTest {

    @Test
    void testEquals() throws ParseException {
        Object[] args1 = {
            QClass.IN,
            UnsignedInt.fromInt(10),
            IPAddressBuilder.createIPv6Address(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
            DomainBuilder.fromLabels(Labels.fromList("mac-pro", "local"))
        };

        Object[] args2 = {
            QClass.Any,
            UnsignedInt.fromInt(11),
            IPAddressBuilder.createIPv6Address(1, 2, 3, 4, 5, 6, 4, 8, 9, 10, 11, 12, 13, 14, 15, 16),
            DomainBuilder.fromLabels(Labels.fromList("pc-pro", "local"))
        };

        EqualsVerifier.verify(AAAARecord.class, args1, args2);
    }

}