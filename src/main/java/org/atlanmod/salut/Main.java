package org.atlanmod.salut;

import org.atlanmod.salut.sd.Service;

import java.util.HashMap;
import java.util.Map;

public class Main {


    public static void main(String[] args) {

        System.out.println(Byte.parseByte("b", 16));

        Service service;

        String serviceKey = "srvname";
        String text = "Test hypothetical web server";
        Map<String, byte[]> properties = new HashMap<String, byte[]>();
        properties.put(serviceKey, text.getBytes());

        Salut salut = new Salut();

        salut.builder()
                .service()
                .name("apache-someuniqueid")
                .port(80)
                .tcp()
                .http()
                .publish();

        salut.builder()
                .query()
                .tcp()
                .airplay()
                .run();

    }
}
