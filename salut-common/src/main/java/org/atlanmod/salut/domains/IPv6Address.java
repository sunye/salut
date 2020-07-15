package org.atlanmod.salut.domains;

import java.util.Arrays;
import org.atlanmod.commons.Preconditions;

public class IPv6Address extends IPAddress {

    public static final int SIZE = 16;


    IPv6Address(byte[] bytes) {
        Preconditions
            .checkArgument(bytes.length == SIZE, "IPv6 addresses must have %d bytes", SIZE);

        address = Arrays.copyOf(bytes, SIZE);
    }

    /**
     * Converts this IP address to a {@code String}. The
     * string returned is a literal IP address.
     *
     *
     * @return  a string representation of this IP address.
     */
    @Override
    public String toString() {
        assert address.length == SIZE : "IPv6 addresses must have 16 bytes";

        StringBuilder addressStr = new StringBuilder();
        for ( byte each: address) {
            addressStr.append(each & 0xff)
                .append('.');

        }

        return addressStr.substring(0, addressStr.length() - 1);
    }
}
