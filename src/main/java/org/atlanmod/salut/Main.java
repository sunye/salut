package org.atlanmod.salut;

import org.atlanmod.salut.sd.ServiceDescription;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class Main {


    public static void main(String[] args) throws UnknownHostException {

        System.out.println(Byte.parseByte("b", 16));

        ServiceDescription service;

        String serviceKey = "srvname";
        String text = "Test hypothetical web server";
        Map<String, byte[]> properties = new HashMap<String, byte[]>();
        properties.put(serviceKey, text.getBytes());


        InetAddress local = InetAddress.getLocalHost();

        System.out.println(local);

        Salut salut = new Salut();



        salut.builder().query().tcp().airplay().run();

        salut.builder().service().name("").port(55).udp().application("rpc").publish();







    }
}
