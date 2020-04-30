package org.atlanmod.salut.domains;

import org.atlanmod.commons.Preconditions;

public class IPv6Address extends IPAddress {

    public static final int SIZE = 16;


    public IPv6Address(byte[] bytes) {
        Preconditions.checkArgument(bytes.length == SIZE, "IPv6 addresses must have %d bytes", SIZE);
        address = bytes;
    }

}
