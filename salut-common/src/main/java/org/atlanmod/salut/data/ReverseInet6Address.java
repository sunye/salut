package org.atlanmod.salut.data;

import java.net.Inet6Address;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.labels.Labels;

public class ReverseInet6Address implements Domain {

    private Inet6Address address;

    public ReverseInet6Address(Inet6Address address) {
        this.address = address;
    }

    @Override
    public Labels toLabels() {
        throw new UnsupportedOperationException();
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
        //@formatter:off
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        //@formatter:on

        ReverseInet6Address that = (ReverseInet6Address) o;
        return address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }
}
