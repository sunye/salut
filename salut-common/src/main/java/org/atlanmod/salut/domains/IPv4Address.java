package org.atlanmod.salut.domains;

import org.atlanmod.commons.Preconditions;

public class IPv4Address extends IPAddress {

    public static final int SIZE = 4;

    IPv4Address(byte[] bytes) {
        Preconditions
            .checkArgument(bytes.length == SIZE, "IPv4 addresses must have %d bytes", SIZE);

        address = bytes;
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
        assert address.length == SIZE : "IPv4 addresses must have 4 bytes";

        return (address[0] & 0xff) + "." + (address[1] & 0xff) + "." + (address[2] & 0xff) + "." + (
            address[3] & 0xff);
    }
}
