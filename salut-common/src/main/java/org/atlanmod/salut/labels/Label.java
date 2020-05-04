package org.atlanmod.salut.labels;

import java.io.Serializable;

public interface Label extends Serializable {

    /**
     * The size, in octets, of this label when written on a byte array, including 1 byte for the
     * label size
     *
     * @return The size in octets of the serialized form of this label
     */
    public int dataLength();


    public boolean isValid();
}
