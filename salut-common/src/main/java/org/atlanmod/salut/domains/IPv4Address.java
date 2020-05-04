package org.atlanmod.salut.domains;

import java.util.Arrays;
import org.atlanmod.commons.Preconditions;

public class IPv4Address extends IPAddress {

    public static final int SIZE = 4;

    IPv4Address(byte[] bytes) {
        Preconditions.checkArgument(bytes.length == SIZE, "IPv4 addresses must have %d bytes", SIZE);

        address = bytes;
    }

}
