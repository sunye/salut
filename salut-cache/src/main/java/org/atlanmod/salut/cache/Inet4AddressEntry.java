package org.atlanmod.salut.cache;

import java.net.Inet4Address;
import java.net.InetAddress;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;


public class Inet4AddressEntry extends AddressEntry {

    @Getter
    private Inet4Address address;

    public Inet4AddressEntry(Inet4Address address) {
        this.address = address;
    }

    @Override
    public Inet4Address address() {
        return address;
    }
}
