package org.atlanmod.salut.mdns;

import org.atlanmod.salut.data.DomainName;
import org.atlanmod.salut.data.DomainNameBuilder;
import org.atlanmod.salut.io.UnsignedInt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class ARecordTest {

    private ARecord record;

    @BeforeEach
    void setup() throws UnknownHostException, ParseException {
        NameArray names = NameArray.fromList("MacBook", "local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{72, 16, 8, 4});
        DomainName domaine = DomainNameBuilder.fromNameArray(names);
        record = ARecord.createRecord(names, QClass.IN, UnsignedInt.fromInt(10), address, domaine);
    }

    @Test
    void testGetServerName() throws ParseException {
        DomainName expected = DomainNameBuilder.parseString("MacBook.local");
        assertThat(record.getServerName()).isEqualTo(expected);
    }

    @Test
    void getAddress() throws UnknownHostException {
        Inet4Address expected = (Inet4Address) InetAddress.getByAddress(new byte[]{72, 16, 8, 4});
        assertThat(record.getAddress()).isEqualTo(expected);
    }

    @Test
    void testEquals() throws ParseException, UnknownHostException {
        NameArray names = NameArray.fromList("MacBook", "local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{72, 16, 8, 4});
        DomainName domaine = DomainNameBuilder.fromNameArray(names);
        ARecord other = ARecord.createRecord(names, QClass.IN, UnsignedInt.fromInt(10), address, domaine);

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