package org.atlanmod.salut.cache;

import org.atlanmod.salut.mdns.NameArray;
import org.atlanmod.salut.names.HostName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HostEntryTest {
    private NameArray names = NameArray.fromList("MacBook", "local");
    private HostEntry entry;

    @BeforeEach
    void setup() throws ParseException {
        HostName name = HostName.fromNameArray(names);
        this.entry = new HostEntry(0, name);
    }


    @Test
    void name() throws ParseException {
        HostName name = HostName.fromNameArray(names);

        assertEquals(name, entry.name());
    }

    @Test
    void getAddresses() throws UnknownHostException {
        Inet4Address inet4address = (Inet4Address) InetAddress.getByAddress(new byte[]{72, 16, 8, 4});
        AddressEntry ae = new Inet4AddressEntry(0, inet4address);
        entry.getAddresses().add(ae);

        assertTrue(ae.getNames().contains(entry));
        assertTrue(entry.getAddresses().contains(ae));
        assertTrue(entry.getAddresses()
                .references()
                .stream()
                .anyMatch(each -> each.address().equals(inet4address)));
    }

    @Test
    void getService() {
    }
}