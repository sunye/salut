package org.atlanmod.salut.data;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import org.atlanmod.salut.labels.Label;

public enum TransportProtocol {
    tcp("tcp"),
    udp("udp"),
    unknown("unkonwn");

    TransportProtocol(String str) {
        this.name = str;
    }

    private String name;

    private final static Map<String, TransportProtocol> MAP = stream(TransportProtocol.values())
            .collect(toMap(each -> each.name, each -> each));

    public static TransportProtocol fromString(String name) {
        return MAP.getOrDefault(name, TransportProtocol.unknown);
    }

    public static TransportProtocol fromLabel(Label label) {
        return fromString(label.toString());
    }
}
