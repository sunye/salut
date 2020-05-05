package org.atlanmod.salut.cache;

import org.atlanmod.salut.domains.IPAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for representing IP4 or IP6 addresses.
 */
public abstract class AddressEntry {

    List<AddressToServerLink> servers = new ArrayList<>();

    public abstract IPAddress address();

}
