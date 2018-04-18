package org.atlanmod.salut.names;

import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

public enum TransportProtocol {
    tcp("tcp"),
    udp("udp"),
    unknown("unkonwn");

    TransportProtocol(String str) {
        this.name = str;
    }

    private String name;

    private final static Map<String, TransportProtocol> map = stream(TransportProtocol.values())
            .collect(toMap(each -> each.name, each -> each));

    public static TransportProtocol fromString(String name) {
        return map.getOrDefault(name, TransportProtocol.unknown);
    }
}
