package org.atlanmod.salut.cache;

import java.net.Inet4Address;
import java.net.InetAddress;

public class Inet4AddressEntry extends AddressEntry {
    private Inet4Address address;

    public Inet4AddressEntry(long timeToLive, Inet4Address address) {
        super(timeToLive);
        this.address = address;
    }

    @Override
    public InetAddress address() {
        return address;
    }
}
