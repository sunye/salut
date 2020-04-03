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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReverseInet6Address that = (ReverseInet6Address) o;
        return address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }
}
