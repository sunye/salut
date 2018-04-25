package org.atlanmod.salut.cache;

import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.mdns.ARecord;
import org.atlanmod.salut.mdns.NameArray;
import org.atlanmod.salut.mdns.QClass;
import org.atlanmod.salut.data.DomainNameBuilder;
import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CacheTest {

    @Test
    void updateFrom() throws UnknownHostException, ParseException {
        NameArray names = NameArray.fromList("MacBook", "local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{72, 16, 8, 4});
        ARecord record = ARecord.createRecord(names, QClass.IN, UnsignedInt.fromInt(10), address);

        Cache cache = new Cache();
        cache.cache(record);

        assertTrue(cache.getAddresses(DomainNameBuilder.fromNameArray(names)).contains(address));

    }

    @Test
    void getAddresses() {
    }
}