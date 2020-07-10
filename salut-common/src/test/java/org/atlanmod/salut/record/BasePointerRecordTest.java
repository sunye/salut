package org.atlanmod.salut.record;

import static org.atlanmod.testing.Verifier.verifyEqualsOf;

import java.text.ParseException;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.names.PointerName;
import org.atlanmod.salut.names.ServiceInstanceName;
import org.junit.jupiter.api.Test;

class BasePointerRecordTest {


    @Test
    void testEquals() throws ParseException {
        Object[] args1 = {
            QClass.IN,
            UnsignedInt.fromInt(160),
            ServiceInstanceName.parseString("My wifes library.daap.tcp.macintosh.local"),
            PointerName.fromLabels(Labels.fromList("daap", "tcp", "local"))
        };

        Object[] args2 = {
            QClass.Any,
            UnsignedInt.fromInt(320),
            ServiceInstanceName.parseString("My husband's library.daap.tcp.macintosh.local"),
            PointerName.fromLabels(Labels.fromList("daap", "udp", "local"))
        };

        verifyEqualsOf(BasePointerRecord.class)
            .withArguments(args1)
            .andVariants(args2)
            .check();
    }

}