package org.atlanmod.salut.domains;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import javax.annotation.ParametersAreNonnullByDefault;
import org.atlanmod.commons.Preconditions;

@ParametersAreNonnullByDefault
public class Host {

    private final Domain name;
    private final byte[] address;


    /**
     * Creates an instance of a Host.
     * @param name the name of the Host
     * @param address the IP address of this Host
     */
    public Host(Domain name, byte[] address) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(address);
        Preconditions.checkArgument(address.length == 4, "IPv4 Addresses must have 4 bytes");

        this.name = name;
        this.address = address;
    }

    /**
     * Creates a new Host instance using a simple name for a local host and the local IP address.
     *
     * @param name the name of the local host
     * @return a new Host instance
     * @throws UnknownHostException if the Local Host can not be found
     */
    public static Host localHost(String name) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();

        return new Host(
            LocalHostName.fromString(name),
            inetAddress.getAddress()
        );
    }

    /**
     * Creates a new Host instance using the local name and the local IP address.
     *
     * @return a new Host instance
     * @throws UnknownHostException if the Local Host can not be found
     */
    public static Host localHost() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String[] labels = inetAddress.getHostName().split("\\.");
        String firstName = labels.length > 1 ? labels[0] : "unknown";

        return new Host(
            LocalHostName.fromString(firstName),
            inetAddress.getAddress()
        );
    }

    /**
     * Returns the domain name of this host.
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
    public byte[] address() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        //@formatter:off
        if (this == o) return true;
        if (!(o instanceof Host)) return false;
        //@formatter:on
        Host other = (Host) o;

        return name.equals(other.name)
            && Arrays.equals(address, other.address);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + Arrays.hashCode(address);
        return result;
    }
}
