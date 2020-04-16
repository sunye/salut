package org.atlanmod.salut.cache;

import java.net.Inet4Address;


public class Inet4AddressEntry extends AddressEntry {

    private Inet4Address address;

    public Inet4AddressEntry(Inet4Address address) {
        this.address = address;
    }

    @Override
    public Inet4Address address() {
        return address;
    }
}
