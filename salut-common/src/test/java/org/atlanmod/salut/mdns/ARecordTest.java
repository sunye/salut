package org.atlanmod.salut.mdns;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.text.ParseException;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.domains.IPv4Address;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ARecordTest {

    private ARecord record;

    @BeforeEach
    void setUp() throws ParseException {
        Labels names = Labels.fromList("MacBook", "local");
        IPv4Address address = new IPv4Address(new byte[]{72, 16, 8, 4});
        Domain domaine = DomainBuilder.fromLabels(names);
        record = BaseARecord
            .createRecord(QClass.IN, UnsignedInt.fromInt(10), address, domaine);
    }

    @Test
    void testGetServerName() throws ParseException {
        Domain expected = DomainBuilder.parseString("MacBook.local");
        assertThat(record.serverName()).isEqualTo(expected);
    }

    @Test
    void getAddress() {
        IPv4Address expected = new IPv4Address(new byte[]{72, 16, 8, 4});
        assertThat(record.address()).isEqualTo(expected);
    }

    @Test
    void testEquals() throws ParseException {
        Labels names = Labels.fromList("MacBook", "local");
        IPv4Address address = new IPv4Address(new byte[]{72, 16, 8, 4});
        Domain domaine = DomainBuilder.fromLabels(names);
        ARecord other = BaseARecord
            .createRecord(QClass.IN, UnsignedInt.fromInt(10), address, domaine);

        assertAll(
            () -> assertThat(other).isEqualTo(record),
            () -> assertThat(record).isEqualTo(record),
            () -> assertThat(record).isNotEqualTo(null)
        );
    }

}
