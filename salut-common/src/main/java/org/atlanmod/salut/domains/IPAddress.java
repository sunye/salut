package org.atlanmod.salut.domains;

import java.util.Arrays;

public abstract class IPAddress {

    byte[] address;

    public byte[] address() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IPAddress ipAddress = (IPAddress) o;

        return Arrays.equals(address, ipAddress.address);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(address);
    }
}
