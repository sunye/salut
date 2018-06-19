package org.atlanmod.salut.data;

import fr.inria.atlanmod.commons.log.Log;
import org.atlanmod.salut.mdns.NameArray;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;

public abstract class DomainNameBuilder {

    /**
     * Creates a DomainNameBuilder instance after parsing a NameArray.
     * <p>
     * The DomainNameBuilder may either be a local host data, e.g. "appletv.local" or an internet
     * host data, e.g. "data.domain.com".
     *
     * @param names a NameArray containing the strings that form a domain data.
     * @return a new sub-instance of DomainNameBuilder.
     */
    public static DomainName fromNameArray(NameArray names) throws ParseException {
        if (names.size() == 2 && DomainName.LOCAL_STR.equals(names.lastName())) {
            return new LocalDomainName(names.get(0));
        } else if (names.size() == 6 && DomainName.ARPA.equals(names.lastName()) && "in-addr".equals(names.get(names.size() - 2))) {
            return parseInet4Address(names);
        } else if (names.size() == 34 && DomainName.ARPA.equals(names.lastName()) && "ip6".equals(names.get(names.size() - 2))) {
            return parseInet6Address(names);
        } else {
            return new InternetDomainName(names);
        }
    }

    public static DomainName parseString(String str) throws ParseException {
        String[] labels = str.split("\\.");
        return fromNameArray(NameArray.fromArray(labels));
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
