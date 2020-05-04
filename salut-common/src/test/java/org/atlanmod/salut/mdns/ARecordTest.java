package org.atlanmod.salut.mdns;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.text.ParseException;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.domains.Host;
import org.atlanmod.salut.domains.IPAddress;
import org.atlanmod.salut.domains.IPAddressBuilder;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ARecordTest {

    private ARecord record;

    @BeforeEach
    void setUp() throws ParseException {
        Labels names = Labels.fromList("MacBook", "local");
        Host host = new Host(
            DomainBuilder.fromLabels(names),
            IPAddressBuilder.fromBytes(new byte[]{72, 16, 8, 4})
        );

        record = BaseARecord
            .createRecord(QClass.IN, UnsignedInt.fromInt(10), host);
    }

    @Test
    void testGetHost() throws ParseException {
        Host expected = new Host(
            DomainBuilder.parseString("MacBook.local"),
            IPAddressBuilder.fromBytes(new byte[]{72, 16, 8, 4})
        );
        assertThat(record.host()).isEqualTo(expected);
    }


    @Test
    void testEquals() throws ParseException {
        Labels names = Labels.fromList("MacBook", "local");
        IPAddress address = IPAddressBuilder.fromBytes(new byte[]{72, 16, 8, 4});
        Domain domain = DomainBuilder.fromLabels(names);
        Host host = new Host(domain, address);

        ARecord other = BaseARecord
            .createRecord(QClass.IN, UnsignedInt.fromInt(10), host);

        assertAll(
            () -> assertThat(other).isEqualTo(record),
            () -> assertThat(record).isEqualTo(record),
            () -> assertThat(record).isNotEqualTo(null)
        );
    }

}
