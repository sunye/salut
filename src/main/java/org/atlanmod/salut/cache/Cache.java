package org.atlanmod.salut.cache;

import org.atlanmod.salut.mdns.ARecord;
import org.atlanmod.salut.mdns.PointerRecord;
import org.atlanmod.salut.mdns.ServerSelectionRecord;
import org.atlanmod.salut.names.ApplicationProtocol;
import org.atlanmod.salut.names.HostName;
import org.atlanmod.salut.names.ServiceName;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Cache {
    private Map<HostName, HostEntry> hosts = new HashMap<>();
    private Map<ApplicationProtocol, ServiceEntry> services = new HashMap<>();
    private Map<InetAddress, AddressEntry> addresses = new HashMap<>();

/*

# SRV Record
Here is an example of the SRV record (in the standard DNS record format) for a print spooler named
PrintsAlot running on TCP port 515:

PrintsAlot._printer._tcp.local. 120 IN SRV 0 0 515 blackhawk.local.

This record would be created on the Multicast DNS responder of a printer called blackhawk.local. on the local link.
The initial 120 represents the time-to-live (TTL) value which is used for caching.
The two zeros are weight and priority values, used in traditional DNS when choosing between multiple records
that match a given names; for multicast DNS purposes, these values are ignored

# PTR Record
Here is an example of a PTR record for a print spooler named PrintsAlot:

_printer._tcp.local. 28800 PTR PrintsAlot._printer._tcp.local.

Again, the 28800 is the time-to-live (TTL) value, measured in seconds.

    Domain Name x {adress, address, address}

    domain names x ServiceEntry x Port x TTL x

     */

    public void cache(ServerSelectionRecord srv) throws ParseException {
        //_printer._tcp.local. 28800 PTR PrintsAlot._printer._tcp.local.
        ServiceName serviceName = ServiceName.fromNameArray(srv.name());
        long now = System.currentTimeMillis();
        long term = now + srv.ttl().toLong() * 1000;

        new ServiceEntry(term, srv.weight(), srv.priority(), srv.port());



    }

    public void cache(PointerRecord ptr) {
        ptr.name();
        ptr.qclass();


    }

    /**
     * Updates this cache with the values of a ARecord.
     *
     * @param ip4address
     * @throws ParseException
     */
    public void cache(ARecord ip4address) throws ParseException {
        HostName name = HostName.fromNameArray(ip4address.name());
        Inet4Address address = ip4address.address();
        long now = System.currentTimeMillis();
        long term = now + ip4address.ttl().toLong() * 1000;

        HostEntry host = this.hosts.get(name);

        if (Objects.isNull(host)) {
            host = new HostEntry(term, name);
            this.hosts.put(name, host);
        } else {
            host.updateExpireTime(term);
        }

        AddressEntry ae = this.addresses.get(address);

        if(Objects.isNull(ae)) {
            ae = new Inet4AddressEntry(term, address);
            this.addresses.put(address, ae);
        } else {
            ae.updateExpireTime(term);
        }

        host.getAddresses().add(ae);
    }

    /**
     * Finds all Inet Addresses associated to a host name.
     *
     * @param name
     * @return a List containing all associated addresses. An empty list, if none.
     */
    public List<InetAddress> getAddresses(HostName name) {
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
     * @return a List containing all associated names. An empty list, if none.
     */
    public List<HostName> getNames(InetAddress address) {
        return Collections.emptyList();
    }
}
