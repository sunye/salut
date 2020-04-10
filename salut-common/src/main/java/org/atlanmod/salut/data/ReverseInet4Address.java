package org.atlanmod.salut.data;

import java.net.Inet4Address;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.labels.Labels;

public class ReverseInet4Address implements Domain {

    private final Inet4Address address;

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
    public Labels toLabels() {
        throw new RuntimeException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReverseInet4Address that = (ReverseInet4Address) o;

        return address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }
}
