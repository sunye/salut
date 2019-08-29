package org.atlanmod.salut.cache;

import org.atlanmod.salut.data.*;
import org.atlanmod.salut.mdns.ARecord;
import org.atlanmod.salut.mdns.PointerRecord;
import org.atlanmod.salut.mdns.ServerSelectionRecord;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Caches locally Multicast DNS records.
 * The following kinds of records are accepted:
 *
 *   - {@see ServerSelectionRecord} (SRV)
 *   - {@see PointerRecord} (PTR)
 *   - {@see AAAARecord} (AAAARecord)
 *   - {@see ARecord} (ARecord)
 *
 *
 * Each record has its own Time To Live (TTL) value, which determines the validity of a entry.
 * When a validity is reached, the record is no longer valid.
 * If the entry already exists, its TTL is updated.
 *
 *
 *  ![UML Diagram describing cache contents](cache.png)
 *
 *  @startuml cache.png
 *  class AddressEntry{
 *      address : InetAddress
 *  }
 *
 *  class ServiceEntry {
 *      name : ServiceName
 *  }
 *
 * class ServerEntry {
 *      name : Domain
 * }
 *
 *  class InstanceEntry {
 *      name : ServiceInstanceName
 *  }
 *
 *  ServiceEntry "1" -- "*" InstanceEntry
 * (ServiceEntry, InstanceEntry) .. ServiceToInstanceLink
 *
 *  InstanceEntry "*" -- "1" ServerEntry
 *  (InstanceEntry, ServerEntry) .. InstanceToServerLink
 *
 *  AddressEntry "*" -- "*" ServerEntry
 *  (AddressEntry, ServerEntry) .. AddressToServerLink
 *
 *  class ServiceToInstanceLink {
 *      ttl : Integer
 *  }
 *
 *  class InstanceToServerLink {
 *      ttl : Integer
 *      weight : Integer
 *      priority : Integer
 *      port : Integer
 *  }
 *
 *  class AddressToServerLink {
 *      ttl : Integer
 *  }
 *  @enduml
 *
 *
 */
public class Cache {
    private Map<ServiceInstanceName, InstanceEntry>  instances = new HashMap<>();
    private Map<ServiceType, ServiceEntry>           services = new HashMap<>();
    private Map<Domain, ServerEntry>             servers = new HashMap<>();
    private Map<InetAddress, AddressEntry>           addresses = new HashMap<>();

    /**
     * Updates this `Cache` with a {@see ServerSelectionRecord}.
     *
     * @param srv
     * @throws ParseException
     */
    public synchronized void cache(ServerSelectionRecord srv) {
        ServiceInstanceName name = srv.getServiceInstanceName();
        InstanceEntry instanceEntry = instances.get(name);
        if (Objects.isNull(instanceEntry)) {
            instanceEntry = new InstanceEntry(srv.getServiceInstanceName());
            instances.put(name, instanceEntry);
        }
        ServerEntry serverEntry = servers.get(srv.getServerName());
        if (Objects.isNull(serverEntry)) {
            serverEntry = new ServerEntry(srv.getServerName());
            servers.put(srv.getServerName(), serverEntry);
        }
        Links.link(srv.getTtl(), instanceEntry, serverEntry, srv.getWeight(), srv.getPriority(),
                srv.getPort());
    }

    /**
     * Updates this cache with the values of a PTR record
     * @param ptr the pointer record
     */
    public synchronized void cache(PointerRecord ptr) {
        ServiceType type = ptr.getServiceType();
        ServiceEntry service = services.get(type);
        if (Objects.isNull(service)) {
            service = new ServiceEntry(ptr.getServiceName());
            services.put(type, service);
        }
        ServiceInstanceName name = ptr.getServiceInstanceName();
        InstanceEntry instance = instances.get(name);
        if (Objects.isNull(instance)) {
            instance = new InstanceEntry(ptr.getServiceInstanceName());
            instances.put(name, instance);
        }
        Links.link(ptr.getTtl(), service, instance);
    }

    /**
     * Updates this cache with a {@see ARecord}.
     *
     * @param aRecord
     * @throws ParseException
     */
    public synchronized void cache(ARecord aRecord) throws ParseException {
        Inet4Address address = aRecord.getAddress();
        Domain name = aRecord.getServerName();

        AddressEntry entry = addresses.get(address);
        if (Objects.isNull(entry)) {
            entry = new Inet4AddressEntry(address);
            addresses.put(address, entry);
        }
        ServerEntry server = servers.get(name);
        if (Objects.isNull(server)) {
            server = new ServerEntry(aRecord.getServerName());
            servers.put(name, server);
        }

        Links.link(aRecord.getTtl(),entry, server);
    }

    /**
     * Finds all Inet Addresses associated to a host name.
     *
     * @param name
     * @return a List containing all associated addresses. An empty list, if none.
     */
    public synchronized List<InetAddress> getAddressesForServer(Domain name) {
        ServerEntry entry = this.servers.get(name);
        if (Objects.isNull(entry)) {
            return Collections.emptyList();
        } else {
            return entry.addresses.stream()
                    .map(each -> each.getAddress())
                    .map(each -> each.address())
                    .collect(Collectors.toList());
        }
    }

    /**
     * Finds all Host Names associated to a Inet getAddress.
     *
     * @return a List containing all associated data. An empty list, if none.
     */
    public synchronized List<Domain> getServersForAddress(InetAddress address) {
        AddressEntry entry = this.addresses.get(address);
        if (Objects.isNull(entry)) {
            return Collections.emptyList();
        } else {
            return entry.servers.stream()
                    .map(each -> each.getServer())
                    .map(each -> each.getName())
                    .collect(Collectors.toList());
        }
    }

    /**
     * Finds all ServiceDescription Instances providing a given `ServiceType`.
     *
     * @param serviceType
     * @return
     */
    public synchronized List<ServiceInstanceName> getInstancesForService(ServiceType serviceType) {
        ServiceEntry service = this.services.get(serviceType);

        if (Objects.isNull(service)) {
            return Collections.emptyList();
        } else {
            return service.instances.stream()
                    .map(each -> each.getInstanceEntry())
                    .map(each -> each.getName())
                    .collect(Collectors.toList());
        }
    }

    /**
     * Finds all Servers for a given ServiceDescription Instance
     * @param instance
     * @return
     */
    public synchronized List<Domain> getServersForInstance(ServiceInstanceName instance) {
        InstanceEntry entry = this.instances.get(instance);

        if (Objects.isNull(entry)) {
            return Collections.emptyList();
        } else {
            return entry.servers.stream()
                    .map(each -> each.getServer())
                    .map(each -> each.getName())
                    .collect(Collectors.toList());
        }
    }
}
