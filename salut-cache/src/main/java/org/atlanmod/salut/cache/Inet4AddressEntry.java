package org.atlanmod.salut.cache;

import org.atlanmod.salut.domains.IPv4Address;


public class Inet4AddressEntry extends AddressEntry {

    private IPv4Address address;

    public Inet4AddressEntry(IPv4Address address) {
        this.address = address;
    }

    @Override
    public IPv4Address address() {
        return address;
    }
}
