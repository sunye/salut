package org.atlanmod.salut.names;

import java.net.Inet4Address;

public class ReverseInet4Address extends HostName {

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
}
