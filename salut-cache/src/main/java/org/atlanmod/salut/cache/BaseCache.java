package org.atlanmod.salut.cache;

import org.atlanmod.commons.Preconditions;
import org.atlanmod.commons.annotation.VisibleForTesting;
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
 * <p>
 * - {@see ServerSelectionRecord} (SRV)
 * - {@see PointerRecord} (PTR)
 * - {@see AAAARecord} (AAAARecord)
 * - {@see ARecord} (ARecord)
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
    private Map<ServiceType, ServiceEntry> services = new HashMap<>();
    private Map<Domain, ServerEntry> servers = new HashMap<>();
    private Map<InetAddress, AddressEntry> addresses = new HashMap<>();
    private SortedSet<Link> links = new TreeSet<>(Links.comparator());
    private Thread thread;

    public BaseCache() {
        this.thread = new Thread(() -> maintenanceThread());

    }

    @Override
    public synchronized void cache(ServerSelectionRecord srv) {
        ServiceInstanceName name = srv.getServiceInstanceName();
        Domain domain = srv.getServerName();

        InstanceEntry instanceEntry = instances.computeIfAbsent(name, k -> new InstanceEntry(k));
        ServerEntry serverEntry = servers.computeIfAbsent(domain, k -> new ServerEntry(k));

        Link newLink = Links.link(srv.getTtl(), instanceEntry, serverEntry, srv.getWeight(), srv.getPriority(),
            srv.getPort());
        links.add(newLink);
    }

    @Override
    public synchronized void cache(PointerRecord ptr) {
        ServiceType type = ptr.getServiceType();
        ServiceName name = ptr.getServiceName();
        ServiceInstanceName instanceName = ptr.getServiceInstanceName();

        ServiceEntry service = services.computeIfAbsent(type, k -> new ServiceEntry(name));
        InstanceEntry instance = instances.computeIfAbsent(instanceName, k -> new InstanceEntry(k));

        Link newLink = Links.link(ptr.getTtl(), service, instance);
        links.add(newLink);
    }

    @Override
    public synchronized void cache(ARecord aRecord) throws ParseException {
        Inet4Address address = aRecord.getAddress();
        Domain name = aRecord.getServerName();

        AddressEntry entry = addresses.computeIfAbsent(address, k -> new Inet4AddressEntry(address));
        ServerEntry server = servers.computeIfAbsent(name, k -> new ServerEntry(k));
        Link newLink = Links.link(aRecord.getTtl(), entry, server);

        links.add(newLink);
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public synchronized List<Domain> getServersForInstance(ServiceInstanceName instance) {
        Preconditions.checkNotNull(instance);

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
    protected Map<ServiceType, ServiceEntry> services() {
        return Collections.unmodifiableMap(services);
    }

    @VisibleForTesting
    protected Map<InetAddress, AddressEntry> addresses() {
        return Collections.unmodifiableMap(addresses);
    }

    @VisibleForTesting
    protected void doMaintenance() {
        this.maintenance();
    }
}
