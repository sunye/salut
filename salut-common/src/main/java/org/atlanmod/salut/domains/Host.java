package org.atlanmod.salut.domains;

import org.atlanmod.commons.Preconditions;

import javax.annotation.ParametersAreNonnullByDefault;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class Host {

    private final Domain name;
    private final IPAddress address;


    /**
     * Creates an instance of a Host.
     *
     * @param domain    the name of the Host
     * @param ipAddress the IP address of this Host
     */
    public Host(Domain domain, IPAddress ipAddress) {
        Preconditions.checkNotNull(domain);
        Preconditions.checkNotNull(ipAddress);

        this.name = domain;
        this.address = ipAddress;
    }

    /**
     * Creates a new Host instance using a simple name for a local host and the local IP address.
     *
     * @param name the name of the local host
     * @return a new Host instance
     * @throws UnknownHostException if the Local Host can not be found
     */
    public static Host localHost(String name) throws UnknownHostException {
        IPAddress address = IPAddressBuilder.fromBytes(InetAddress.getLocalHost().getAddress());
        return new Host(
            LocalHostName.fromString(name),
            address
        );
    }

    /**
     * Creates a new Host instance using the local name and the local IP address.
     *
     * @return a new Host instance
     * @throws UnknownHostException if the Local Host can not be found
     */
    public static Host localHost() throws UnknownHostException {
        InetAddress localInetAddress = InetAddress.getLocalHost();

        IPAddress address = IPAddressBuilder.fromBytes(localInetAddress.getAddress());

        String[] labels = localInetAddress.getHostName().split("\\.");
        String firstName = labels.length > 0 ? labels[0] : "unknown";

        return new Host(
            LocalHostName.fromString(firstName),
            address
        );
    }

    /**
     * Creates an array containing all routable addresses from the local interfaces. By 'routable',
     * we mean address that are not link local, loopback, nor multi-cast.
     *
     * @return An array of {@code InetAdress} objects.
     * @throws SocketException
     */
    public static InetAddress[] localRoutableAdresses() throws SocketException {

        return NetworkInterface.networkInterfaces()
            .flatMap(NetworkInterface::inetAddresses)
            .filter(each -> !each.isLoopbackAddress())
            .filter(each -> !each.isLinkLocalAddress())
            .filter(each -> !each.isMulticastAddress())
            .toArray(InetAddress[]::new);
    }

    /**
     * Returns the domain name of this host.
     *
     * @return
     */
    public Domain name() {
        return name;
    }

    /**
     * Returns a byte array representing the address of this host.
     *
     * @return
     */
    public IPAddress address() {
        return address;
    }

    /**
     * Compares two Host objects. Returns true if they are the same
     * @param o the other Host object
     * @return true if the two Host objects are equal. False otherwise.
     */
    @Override
    public boolean equals(Object o) {
        //@formatter:off
        if (this == o) {return true;}
        if (!(o instanceof Host)) {return false;}
        //@formatter:on
        Host other = (Host) o;

        return name.equals(other.name)
            && address.equals(other.address);
    }

    /**
     * Calculates the hash code for this object
     * @return an int representing a hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    /**
     * Converts this Host to a String.
     *
     * @return a string representation of this Host.
     */
    @Override
    public String toString() {
        return name + "/" + address;
    }
}
