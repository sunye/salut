package org.atlanmod.salut.domains;

import org.atlanmod.commons.Preconditions;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.data.ReverseInet4Address;
import org.atlanmod.salut.data.ReverseInet6Address;
import org.atlanmod.salut.labels.Labels;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;

public abstract class DomainBuilder {

    /**
     * Creates a Domain instance after parsing a {@code LabelArray}. <p> The Domain may either be a
     * local host data, e.g. "appletv.local", a IP4 address, a IP6 address, or Internet host name,
     * e.g. "data.domain.com".
     *
     * @param labels a LabelArray containing the strings that form a domain data.
     * @return a new sub-instance of DomainBuilder.
     */
    public static Domain fromLabels(Labels labels) throws ParseException {
        Preconditions.checkArgument(labels.size() > 0, "Domains have at least one label");

        if (labels.size() == 1) {
            return new LocalHostName(labels.first());
        } else if (labels.size() == 2 && Domain.LOCAL_LABEL.equals(labels.last())) {
            return new LocalHostName(labels.first());
        } else if (labels.size() == 6 && Domain.ARPA.equals(labels.last().toString()) && Domain.IP4
            .equals(labels.get(labels.size() - 2).toString())) {
            return parseInet4Address(labels);
        } else if (labels.size() == 34 && Domain.ARPA.equals(labels.last().toString()) && Domain.IP6
            .equals(labels.get(labels.size() - 2).toString())) {
            return parseInet6Address(labels);
        } else {
            return new InternetDomain(labels);
        }
    }

    /**
     * Creates a Domain instance after parsing a String
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Domain parseString(String str) throws ParseException {
        String[] labels = str.split("\\.");
        return fromLabels(Labels.fromArray(labels));
    }

    private static ReverseInet4Address parseInet4Address(Labels names) throws ParseException {
        byte[] array = new byte[4];
        for (int i = 0; i < 4; i++) {
            array[3 - i] = (byte) Integer.parseInt(names.get(i).toString());
        }
        try {
            Inet4Address address = (Inet4Address) InetAddress.getByAddress(array);
            return new ReverseInet4Address(address);
        } catch (UnknownHostException | ClassCastException e) {
            throw new ParseException("Invalid Inet4Address format", 0);
        }
    }

    private static ReverseInet6Address parseInet6Address(Labels names) throws ParseException {
        byte[] array = new byte[16];
        for (int i = 0; i < 16; i++) {
            String concat = names.get(i * 2 + 1).toString() + names.get(i * 2).toString();
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
