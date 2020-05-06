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

}
