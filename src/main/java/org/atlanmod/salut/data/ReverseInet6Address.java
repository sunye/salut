package org.atlanmod.salut.data;

import org.atlanmod.salut.mdns.NameArray;

import java.net.Inet6Address;

public class ReverseInet6Address implements DomainName {
    private Inet6Address address;

    public ReverseInet6Address(Inet6Address address) {
        this.address = address;
    }

    @Override
    public NameArray toNameArray() {
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
