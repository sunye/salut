package org.atlanmod.salut.cache;

import org.atlanmod.commons.Preconditions;
import org.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.cache.Links.ServiceToInstanceLink;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.Host;
import org.atlanmod.salut.domains.IPAddress;
import org.atlanmod.salut.domains.IPv4Address;
import org.atlanmod.salut.record.ARecord;
import org.atlanmod.salut.record.PointerRecord;
import org.atlanmod.salut.record.ServerSelectionRecord;
import org.atlanmod.salut.names.ServiceInstanceName;
import org.atlanmod.salut.names.ServiceName;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Caches locally Multicast DNS records.
 * The following kinds of records are accepted:
 * <p>
 * - {@link ServerSelectionRecord} (SRV)
 * - {@link PointerRecord} (PTR)
 * - {@link ARecord} (ARecord)
 * <p>
 * <p>
 * Each record has its own Time To Live (TTL) value, which determines the validity of a entry.
 * When a validity is reached, the record is no longer valid.
 * If the entry already exists, its TTL is updated.
 * <p>
 * <p>
 * ![UML Diagram describing cache contents](cache.png)
 *
 * @startuml cache.png
 * class AddressEntry{
 * address : InetAddress
 * }
 * <p>
 * class ServiceEntry {
 * name : ServiceName
 * }
 * <p>
 * class ServerEntry {
 * name : Domain
 * }
 * <p>
 * class InstanceEntry {
 * name : ServiceInstanceName
 * }
 * <p>
 * ServiceEntry "1" -- "*" InstanceEntry
 * (ServiceEntry, InstanceEntry) .. ServiceToInstanceLink
 * <p>
 * InstanceEntry "*" -- "1" ServerEntry
 * (InstanceEntry, ServerEntry) .. InstanceToServerLink
 * <p>
 * AddressEntry "*" -- "*" ServerEntry
 * (AddressEntry, ServerEntry) .. AddressToServerLink
 * <p>
 * class ServiceToInstanceLink {
 * ttl : Integer
 * }
 * <p>
 * class InstanceToServerLink {
 * ttl : Integer
 * weight : Integer
 * priority : Integer
 * port : Integer
 * }
 * <p>
 * class AddressToServerLink {
 * ttl : Integer
 * }
 * @enduml
 */
public class BaseCache implements Cache {
    private Map<ServiceInstanceName, InstanceEntry> instances = new HashMap<>();
    private Map<ServiceName, ServiceEntry> services = new HashMap<>();
    private Map<Domain, ServerEntry> servers = new HashMap<>();
    private Map<IPAddress, AddressEntry> addresses = new HashMap<>();
    private SortedSet<Link> links = new TreeSet<>(Links.comparator());
    private Thread thread;

    public BaseCache() {
        this.thread = new Thread(this::maintenanceThread);

    }

    @Override
    public synchronized void cache(ServerSelectionRecord srv) {
        ServiceInstanceName name = srv.serviceInstanceName();
        Domain domain = srv.serverName();
        InstanceEntry instanceEntry = instances.computeIfAbsent(name, InstanceEntry::new);
        ServerEntry serverEntry = servers.computeIfAbsent(domain, ServerEntry::new);

        Link newLink = Links.link(srv.ttl(), instanceEntry, serverEntry, srv.weight(), srv.priority(),
            srv.port());
        links.add(newLink);
    }

    @Override
    public synchronized void cache(PointerRecord ptr) {
        ServiceName name = ptr.pointerName().service();
        ServiceInstanceName instanceName = ptr.serviceInstanceName();

        ServiceEntry service = services.computeIfAbsent(name, ServiceEntry::new);
        InstanceEntry instance = instances.computeIfAbsent(instanceName, InstanceEntry::new);

        Link newLink = Links.link(ptr.ttl(), service, instance);
        links.add(newLink);
    }

    @Override
    public synchronized void cache(ARecord aRecord) throws ParseException {
        Host host = aRecord.host();
        // TODO: remove this cast
        IPv4Address address = (IPv4Address) host.address();
        Domain name = host.name();

        AddressEntry entry = addresses.computeIfAbsent(address, k -> new Inet4AddressEntry(address));
        ServerEntry server = servers.computeIfAbsent(name, ServerEntry::new);
        Link newLink = Links.link(aRecord.ttl(), entry, server);

        links.add(newLink);
    }

    @Override
    public synchronized List<IPAddress> getAddressesForServer(Domain name) {
        ServerEntry entry = this.servers.get(name);
        if (Objects.isNull(entry)) {
            return Collections.emptyList();
        } else {
            return entry.addresses.stream()
                .map(AddressToServerLink::getAddress)
                .map(AddressEntry::address)
                .collect(Collectors.toList());
        }
    }

    @Override
    public synchronized List<Domain> getServersForAddress(IPv4Address address) {
        AddressEntry entry = this.addresses.get(address);
        if (Objects.isNull(entry)) {
            return Collections.emptyList();
        } else {
            return entry.servers.stream()
                .map(AddressToServerLink::getServer)
                .map(ServerEntry::name)
                .collect(Collectors.toList());
        }
    }

    @Override
    public synchronized List<ServiceInstanceName> getInstancesForService(ServiceName serviceName) {
        ServiceEntry service = this.services.get(serviceName);

        if (Objects.isNull(service)) {
            return Collections.emptyList();
        } else {
            return service.instances.stream()
                .map(ServiceToInstanceLink::getInstanceEntry)
                .map(InstanceEntry::name)
                .collect(Collectors.toList());
        }
    }

    @Override
    public synchronized List<Domain> getServersForInstance(ServiceInstanceName serviceInstanceName) {
        Preconditions.checkNotNull(serviceInstanceName);

        InstanceEntry entry = this.instances.get(serviceInstanceName);

        if (Objects.isNull(entry)) {
            return Collections.emptyList();
        } else {
            return entry.servers.stream()
                .map(InstanceToServerLink::getServer)
                .map(ServerEntry::name)
                .collect(Collectors.toList());
        }
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }

    private void maintenanceThread() {
        try {
            while (!Thread.interrupted()) {
                this.maintenance();
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Log.error(e, "Cache maintenance thread interrupted");
            Thread.currentThread().interrupt();
        }
    }

    private void maintenance() {
        Iterator<Link> it = links.iterator();
        while (it.hasNext()) {
            Link each = it.next();
            if (each.hasExpired()) {
                it.remove();
                each.unlink();
            } else {
                break;
            }
        }
    }

    @VisibleForTesting
    protected Map<Domain, ServerEntry> servers() {
        return Collections.unmodifiableMap(servers);
    }

    @VisibleForTesting
    protected Map<ServiceInstanceName, InstanceEntry> instances() {
        return Collections.unmodifiableMap(instances);
    }

    @VisibleForTesting
    protected Map<ServiceName, ServiceEntry> services() {
        return Collections.unmodifiableMap(services);
    }

    @VisibleForTesting
    protected Map<IPAddress, AddressEntry> addresses() {
        return Collections.unmodifiableMap(addresses);
    }

    @VisibleForTesting
    protected void doMaintenance() {
        this.maintenance();
    }
}
