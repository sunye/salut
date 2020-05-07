package org.atlanmod.salut.domains;

public class IPAddressBuilder {

    private IPAddressBuilder(){}

    /**
     * Creates an sub-instance of IPAddress, from an array of {@code byte}.
     * The instance type depends on the length of the argument. If the length is 4, a
     * {@link IPv4Address} is created, if it is 16, a {@link IPv6Address} is created.
     * An exception is thrown otherwise.
     *
     * @param bytes a byte array representing a IP address.
     * @throws IllegalArgumentException if the argument's length is different from 4 or 16.
     * @return a sub-instance of IPAddress.
     */
    public static IPAddress fromBytes(byte... bytes) {
        if (bytes.length == IPv4Address.SIZE) {
            return createIPv4Address(bytes);
        } else if (bytes.length == IPv6Address.SIZE) {
            return createIPv6Address(bytes);
        } else {
            throw new IllegalArgumentException("Invalid IP address");
        }
    }

    public static IPv6Address createIPv6Address(byte... bytes) {
        return new IPv6Address(bytes);
    }

    public static IPv6Address createIPv6Address(int... ints) {
        return new IPv6Address(intsToBytes(ints));
    }

    public static IPv4Address createIPv4Address(byte... bytes) {
        return new IPv4Address(bytes);
    }

    public static IPv4Address createIPv4Address(int... ints) {
        return new IPv4Address(intsToBytes(ints));
    }

    private static byte[] intsToBytes(int... ints) {
        byte[] bytes = new byte[ints.length];
        for (int i = 0; i < ints.length; i++) {
            bytes[i] = (byte) ints[i];
        }
        return bytes;
    }

}
