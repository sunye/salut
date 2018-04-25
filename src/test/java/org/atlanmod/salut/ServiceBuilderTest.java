package org.atlanmod.salut;

import org.atlanmod.salut.sd.Service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class ServiceBuilderTest {


    @Test
    void test() {
        String serviceKey = "srvname";
        String text = "Test hypothetical web server";
        Map<String, byte[]> properties = new HashMap<String, byte[]>();
        properties.put(serviceKey, text.getBytes());

        /*
        Service service = Service.builder()
                .type("_html._tcp.local.")
                .data("apache-someuniqueid")
                .port(80)
                .persistent()
                .properties(properties)
                .build();
         */

        /*

        assertEquals("tcp", service.getProtocol());
        assertEquals("apache-someuniqueid", service.getName());
        assertEquals(80, service.getPort());
        assertEquals("local", service.getDomain());
        assertArrayEquals(text.getBytes(), service.getPropertyBytes(serviceKey));
*/
    }
}
