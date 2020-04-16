package org.atlanmod.salut.cache;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for representing IP4 or IP6 addresses.
 *
 */
public abstract class AddressEntry {

    List<AddressToServerLink> servers = new ArrayList<>();

    public abstract InetAddress address();

}
