package org.atlanmod.salut.cache;

import java.util.ArrayList;
import java.util.List;
import org.atlanmod.salut.domains.IPAddress;

/**
 * Abstract class for representing IP4 or IP6 addresses.
 */
public abstract class AddressEntry {

    List<AddressToServerLink> servers = new ArrayList<>();

    public abstract IPAddress address();

}
