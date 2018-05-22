package org.atlanmod.salut.cache;

import org.atlanmod.salut.mdns.ARecord;
import org.atlanmod.salut.mdns.PointerRecord;
import org.atlanmod.salut.mdns.ServerSelectionRecord;
import org.atlanmod.salut.data.DomainName;
import org.atlanmod.salut.data.DomainNameBuilder;
import org.atlanmod.salut.data.ApplicationProtocol;
import org.atlanmod.salut.data.ServiceInstanceName;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Caches locally Multicast DNS entries.
 * The following kinds of entry are accepted:
 *
 *   - {@see ServerSelectionRecord} (SRV)
 *   - {@see PointerRecord} (PTR)
 *   - {@see AAAARecord} (AAAARecord)
 *   - {@see ARecord} (ARecord)
 *
 *
 * Each entry has its own Time To Live (TTL) value, which determines the validity of a entry.
 * When a validity is reached, the entry is no longer valid.
 * If the entry already exists, its TTL is updated.
 *
 *
 *
 *  ![UML Diagram describing cache contents](cache.png)
 *
 *  @startuml cache.png
 *  class AddressEntry{
 *      address : InetAddress
 *  }
 *
 *  class HostEntry {
 *      name : HostName
 *  }
 *
 *  class ServiceEntry {
 *      weight : UnsignedShort
 *      priority :  UnsignedShort
 *      port : UnsignedShort
 *  }
 *  AddressEntry "*" -- "*" HostEntry
 *  ServiceEntry "*" -- "1" HostEntry
 *  @enduml
 *
 */
public class Cache {
    private Map<DomainName, HostEntry> hosts = new HashMap<>();
    private Map<ApplicationProtocol, ServiceEntry> services = new HashMap<>();
    private Map<InetAddress, AddressEntry> addresses = new HashMap<>();

    /**
     * Updates this `Cache` with a {@see ServerSelectionRecord}.
     *
     * @param srv
     * @throws ParseException
     */
    public void cache(ServerSelectionRecord srv) throws ParseException {
        //_printer._tcp.local. 28800 PTR PrintsAlot._printer._tcp.local.
        ServiceInstanceName serviceInstanceName = ServiceInstanceName.fromNameArray(srv.name());

        new ServiceEntry(srv.ttl(), srv.weight(), srv.priority(), srv.port());
    }

    /**
     * Updates this cache with the values of a PTR record
     * @param ptr the pointer record
     */
    public void cache(PointerRecord ptr) {
        ptr.name();
        ptr.qclass();
    }

    /**
     * Updates this cache with a {@see ARecord}.
     *
     * @param ip4address
     * @throws ParseException
     */
    public void cache(ARecord ip4address) throws ParseException {
        DomainName name = DomainNameBuilder.fromNameArray(ip4address.name());
        Inet4Address address = ip4address.address();
        TimeToLive ttl = ip4address.ttl();

        HostEntry host = this.hosts.get(name);

        if (Objects.isNull(host)) {
            host = new HostEntry(ttl, name);
            this.hosts.put(name, host);
        } else {
            host.updateExpireTime(ttl);
        }

        AddressEntry ae = this.addresses.get(address);

        if(Objects.isNull(ae)) {
            ae = new Inet4AddressEntry(ttl, address);
            this.addresses.put(address, ae);
        } else {
            ae.updateExpireTime(ttl);
        }

        host.getAddresses().add(ae);
    }

    /**
     * Finds all Inet Addresses associated to a host name.
     *
     * @param name
     * @return a List containing all associated addresses. An empty list, if none.
     */
    public List<InetAddress> getAddresses(DomainName name) {
        HostEntry entry = this.hosts.get(name);
        if (Objects.isNull(entry)) {
            return Collections.emptyList();
        } else {
            return entry.getAddresses().references()
                    .stream()
                    .map(each -> each.address())
                    .collect(Collectors.toList());
        }
    }

    /**
     * Finds all Host Names associated to a Inet address.
     *
     * @return a List containing all associated data. An empty list, if none.
     */
    public List<DomainNameBuilder> getNames(InetAddress address) {
        return Collections.emptyList();
    }
}
