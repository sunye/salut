package org.atlanmod.salut.mdns;

import org.atlanmod.salut.data.Domain;
import org.atlanmod.salut.data.DomainBuilder;
import org.atlanmod.salut.io.UnsignedInt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static com.google.common.truth.Truth.*;

class ARecordTest {

    private ARecord record;

    @BeforeEach
    void setup() throws UnknownHostException, ParseException {
        LabelArray names = LabelArray.fromList("MacBook", "local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{72, 16, 8, 4});
        Domain domaine = DomainBuilder.fromLabels(names);
        record = BaseARecord
            .createRecord(names, QClass.IN, UnsignedInt.fromInt(10), address, domaine);
    }

    @Test
    void testGetServerName() throws ParseException {
        Domain expected = DomainBuilder.parseString("MacBook.local");
        assertThat(record.serverName()).isEqualTo(expected);
    }

    @Test
    void getAddress() throws UnknownHostException {
        Inet4Address expected = (Inet4Address) InetAddress.getByAddress(new byte[]{72, 16, 8, 4});
        assertThat(record.address()).isEqualTo(expected);
    }

    @Test
    void testEquals() throws ParseException, UnknownHostException {
        LabelArray names = LabelArray.fromList("MacBook", "local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{72, 16, 8, 4});
        Domain domaine = DomainBuilder.fromLabels(names);
        ARecord other = BaseARecord
            .createRecord(names, QClass.IN, UnsignedInt.fromInt(10), address, domaine);

        assertAll(
                () -> assertThat(other).isEqualTo(record),
                () -> assertThat(record).isEqualTo(record),
                () -> assertThat(record).isNotEqualTo(null)
        );
    }

    @Test
    void testToString() {
    }

    @Test
    void parser() {
    }

    @Test
    void createRecord() {
    }
}
