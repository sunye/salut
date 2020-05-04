package org.atlanmod.salut.domains;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.annotation.ParametersAreNonnullByDefault;
import org.atlanmod.commons.Preconditions;

@ParametersAreNonnullByDefault
public class Host {

    private final Domain name;
    private final IPAddress address;


    /**
     * Creates an instance of a Host.
     *
     * @param name    the name of the Host
     * @param address the IP address of this Host
     */
    public Host(Domain name, IPAddress address) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(address);

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

    @Override
    public boolean equals(Object o) {
        //@formatter:off
        if (this == o) return true;
        if (!(o instanceof Host)) return false;
        //@formatter:on
        Host other = (Host) o;

        return name.equals(other.name)
            && address.equals(other.address);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Host{" +
            "name=" + name +
            ", address=" + address +
            '}';
    }
}
