package org.atlanmod.salut.cache;

import java.util.Comparator;
import org.atlanmod.salut.io.UnsignedShort;

/**
 * Utility class to create and maintain links between Services, Addresses, and Servers.
 */
public class Links {

    /**
     * Private constructor to hide the implicit public one.
     */
    private Links() {}

    public static Link link(TimeToLive ttl, InstanceEntry service, ServerEntry server,
        UnsignedShort weight, UnsignedShort priority, UnsignedShort port) {

        InstanceToServerLink link = new InstanceToServerLink(ttl, service, server, weight, priority, port);
        service.servers.add(link);
        server.instances.add(link);
        return link;
    }

    public static Link link(TimeToLive ttl, AddressEntry addressEntry, ServerEntry serviceEntry) {
        AddressToServerLink link = new AddressToServerLink(ttl, addressEntry, serviceEntry);
        addressEntry.servers.add(link);
        serviceEntry.addresses.add(link);
        return link;
    }

    public static Link link(TimeToLive ttl, ServiceEntry serviceEntry, InstanceEntry instanceEntry) {
        ServiceToInstanceLink link = new ServiceToInstanceLink(ttl, serviceEntry, instanceEntry);
        serviceEntry.instances.add(link);
        instanceEntry.services.add(link);
        return link;
    }


    public static class ServiceToInstanceLink extends AbstractLink {
        private ServiceEntry serviceEntry;
        private InstanceEntry instanceEntry;

        public ServiceToInstanceLink(TimeToLive ttl, ServiceEntry serviceEntry, InstanceEntry instanceEntry) {
            super(ttl);
            this.serviceEntry = serviceEntry;
            this.instanceEntry = instanceEntry;
        }

        public ServiceEntry getServiceEntry() {
            return serviceEntry;
        }

        public InstanceEntry getInstanceEntry() {
            return instanceEntry;
        }

        public void unlink() {
            serviceEntry.instances.remove(this);
            instanceEntry.services.remove(this);
        }
    }

    public static Comparator<Link> comparator() {
        return new Comparator<Link>() {
            @Override
            public int compare(Link one, Link other) {
                return one.compareTo(other);
            }
        };
    }
}
