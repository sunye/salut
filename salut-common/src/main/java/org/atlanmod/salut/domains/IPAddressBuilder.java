package org.atlanmod.salut.domains;

public class IPAddressBuilder {

    public static IPAddress fromBytes(byte[] bytes) {
        if (bytes.length == IPv4Address.SIZE) {
            return createIPv4Address(bytes);
        } else if (bytes.length == IPv6Address.SIZE) {
            return createIPv6Address(bytes);
        } else {
            throw new IllegalArgumentException("Invalid IP address");
        }
    }

    public static IPv6Address createIPv6Address(byte[] bytes) {
        return new IPv6Address(bytes);
    }

    public static IPv4Address createIPv4Address(byte[] bytes) {
        return new IPv4Address(bytes);
    }

}
