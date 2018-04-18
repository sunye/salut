package org.atlanmod.salut.names;

import java.net.Inet6Address;

public class ReverseInet6Address extends HostName {
    private Inet6Address address;

    public ReverseInet6Address(Inet6Address address) {
        this.address = address;
    }

    public Inet6Address address() {
        return address;
    }

    @Override
    public String toString() {
        return "ReverseInet6Address{" +
                address +
                '}';
    }
}
