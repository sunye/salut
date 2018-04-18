package org.atlanmod.salut.names;

import fr.inria.atlanmod.commons.log.Log;
import org.atlanmod.salut.mdns.NameArray;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;

public abstract class HostName {

    public final static String LOCAL_STR = "local";
    public static final String ARPA = "arpa";

    /**
     * Creates a HostName instance after parsing a NameArray.
     * <p>
     * The HostName may either be a local host names, e.g. "appletv.local" or an internet
     * host names, e.g. "names.domain.com".
     *
     * @param names a NameArray containing the strings that form a domain names.
     * @return
     */
    public static HostName fromNameArray(NameArray names) throws ParseException {
        if (names.size() == 2 && LOCAL_STR.equals(names.lastName())) {
            return new LocalHostName(names.get(0));
        } else if (names.size() == 6 && ARPA.equals(names.lastName()) && "in-addr".equals(names.get(names.size() - 2))) {
            return parseInet4Address(names);
        } else if (names.size() == 34 && ARPA.equals(names.lastName()) && "ip6".equals(names.get(names.size() - 2))) {
            return parseInet6Address(names);
        } else {
            return new InternetHostName(names);
        }
    }

    private static ReverseInet4Address parseInet4Address(NameArray names) throws ParseException {
        byte[] array = new byte[4];
        for (int i = 0; i < 4; i++) {
            array[3 - i] = (byte) Integer.parseInt(names.get(i));
        }
        try {
            Inet4Address address = (Inet4Address) InetAddress.getByAddress(array);
            return new ReverseInet4Address(address);
        } catch (UnknownHostException | ClassCastException e) {
            throw new ParseException("Invalid Inet4Address format", 0);
        }
    }

    private static ReverseInet6Address parseInet6Address(NameArray names) throws ParseException {
        byte[] array = new byte[16];
        for (int i = 0; i < 16; i++) {
            String concat = names.get(i*2+1) + names.get(i*2);
            array[15 - i] = (byte) Integer.parseInt(concat, 16);
        }
        try {
            Inet6Address address = (Inet6Address) InetAddress.getByAddress(array);
            return new ReverseInet6Address(address);
        } catch (UnknownHostException | ClassCastException e) {
            Log.error(e);
            throw new ParseException("Invalid Inet6Address format", 0);
        }
    }
}
