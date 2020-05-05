package org.atlanmod.salut.cache;

import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.IPAddress;
import org.atlanmod.salut.domains.IPv4Address;
import org.atlanmod.salut.mdns.ARecord;
import org.atlanmod.salut.mdns.PointerRecord;
import org.atlanmod.salut.mdns.ServerSelectionRecord;
import org.atlanmod.salut.names.ServiceInstanceName;
import org.atlanmod.salut.names.ServiceName;

import java.text.ParseException;
import java.util.List;

public interface Cache {

    /**
     * Updates this `Cache` with a {@see ServerSelectionRecord}.
     *
     * @param srv
     * @throws ParseException
     */
    void cache(ServerSelectionRecord srv);

    /**
     * Updates this cache with the values of a PTR record
     *
     * @param ptr the pointer record
     */
    void cache(PointerRecord ptr);

    /**
     * Updates this cache with a {@see ARecord}.
     *
     * @param aRecord
     * @throws ParseException
     */
    void cache(ARecord aRecord) throws ParseException;

    /**
     * Finds all Inet Addresses associated to a host name.
     *
     * @param name
     * @return a List containing all associated addresses. An empty list, if none.
     */
    List<IPAddress> getAddressesForServer(Domain name);

    /**
     * Finds all Host Names associated to a Inet getAddress.
     *
     * @return a List containing all associated data. An empty list, if none.
     */
    List<Domain> getServersForAddress(IPv4Address address);

    /**
     * Finds all Service Instance Names providing a given {@code ServiceName}.
     *
     * @param serviceName
     * @return
     */
    List<ServiceInstanceName> getInstancesForService(ServiceName serviceName);

    /**
     * Finds all Servers for a given Service Instance Name
     *
     * @param serviceInstanceName
     * @return
     */
    List<Domain> getServersForInstance(ServiceInstanceName serviceInstanceName);
}
