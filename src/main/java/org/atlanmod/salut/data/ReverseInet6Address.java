package org.atlanmod.salut.data;

import org.atlanmod.salut.mdns.LabelArray;

import java.net.Inet6Address;

public class ReverseInet6Address implements Domain {
    private Inet6Address address;

    public ReverseInet6Address(Inet6Address address) {
        this.address = address;
    }

    @Override
    public LabelArray toNameArray() {
        throw new RuntimeException();
    }

    public Inet6Address address() {
        return address;
    }

    @Override
    public String toString() {
        return "ReverseInet6Address{" + address + '}';
    }
}
