package org.atlanmod.salut.data;

import org.atlanmod.salut.mdns.NameArray;

import java.net.Inet4Address;

public class ReverseInet4Address implements DomainName {

    private Inet4Address address;

    public ReverseInet4Address(Inet4Address address) {
        this.address = address;
    }

    public Inet4Address address() {
        return address;
    }

    @Override
    public String toString() {
        return "ReverseInet4Address{" +
                address +
                '}';
    }

    @Override
    public NameArray toNameArray() {
        throw new RuntimeException();
    }
}
