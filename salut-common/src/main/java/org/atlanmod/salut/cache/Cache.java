package org.atlanmod.salut.cache;

import java.net.InetAddress;
import java.text.ParseException;
import java.util.List;
import org.atlanmod.salut.data.Domain;
import org.atlanmod.salut.data.ServiceInstanceName;
import org.atlanmod.salut.data.ServiceType;
import org.atlanmod.salut.mdns.ARecord;
import org.atlanmod.salut.mdns.PointerRecord;
import org.atlanmod.salut.mdns.ServerSelectionRecord;

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
    List<InetAddress> getAddressesForServer(Domain name);

    /**
     * Finds all Host Names associated to a Inet getAddress.
     *
     * @return a List containing all associated data. An empty list, if none.
     */
    List<Domain> getServersForAddress(InetAddress address);

    /**
     * Finds all ServiceDescription Instances providing a given `ServiceType`.
     *
     * @param serviceType
     * @return
     */
    List<ServiceInstanceName> getInstancesForService(ServiceType serviceType);

    /**
     * Finds all Servers for a given ServiceDescription Instance
     * @param instance
     * @return
     */
    List<Domain> getServersForInstance(ServiceInstanceName instance);
}
