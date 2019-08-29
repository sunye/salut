package org.atlanmod.salut;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ServiceBuilderTest {


    @Test
    void test() {
        String serviceKey = "srvname";
        String text = "Test hypothetical web server";
        Map<String, byte[]> properties = new HashMap<String, byte[]>();
        properties.put(serviceKey, text.getBytes());

        Salut salut = new Salut();
        salut.builder()
                .service()
                .name("my printer")
                .port(221)
                .udp()
                .airplay()
                .persistent()
                .weight(200)
                .priority(1)
                .publish();

    }

    @Test
    void servicePublishingTest() {
        Salut salut = new Salut();
        salut.builder()
                .service()
                .name("apache-someuniqueid")
                .port(80)
                .tcp()
                .http()
                .publish();
    }

}
