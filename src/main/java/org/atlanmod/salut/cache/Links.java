package org.atlanmod.salut.cache;

import org.atlanmod.salut.io.UnsignedShort;

public class Links {

    public static void link(TimeToLive ttl, InstanceEntry service, ServerEntry server,
                            UnsignedShort weight, UnsignedShort priority, UnsignedShort port) {

        InstanceToServerLink link = new InstanceToServerLink(ttl, service, server, weight, priority, port);
        service.servers.add(link);
        server.instances.add(link);
    }

    public static void link(TimeToLive ttl, AddressEntry addressEntry, ServerEntry serviceEntry) {
        AddressToServerLink link = new AddressToServerLink(ttl, addressEntry, serviceEntry);
        addressEntry.servers.add(link);
        serviceEntry.addresses.add(link);
    }

    public static void link(TimeToLive ttl, ServiceEntry serviceEntry, InstanceEntry instanceEntry) {
        ServiceToInstanceLink link = new ServiceToInstanceLink(ttl, serviceEntry, instanceEntry);
        serviceEntry.instances.add(link);
        instanceEntry.services.add(link);
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
    }

}
