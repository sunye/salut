package org.atlanmod.salut.mdns;

import static org.atlanmod.testing.Verifier.verifyEqualsOf;

import java.text.ParseException;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.domains.Host;
import org.atlanmod.salut.domains.IPAddressBuilder;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
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

        verifyEqualsOf(BaseARecord.class)
            .withArguments(args1)
            .andVariants(args2)
            .check();
    }
}