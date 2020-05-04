package org.atlanmod.salut.data;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import org.atlanmod.salut.labels.DNSLabel;
import org.atlanmod.salut.labels.Label;
import org.atlanmod.salut.names.ServiceName;

@SuppressWarnings({"pmd:FieldNamingConventions", "squid:S115"})
public enum TransportProtocol {
    tcp("tcp"),
    udp("udp"),
    unknown("unkonwn");

    private final static Map<String, TransportProtocol> MAP = stream(TransportProtocol.values())
        .collect(toMap(each -> each.name, each -> each));
    private String name;

    TransportProtocol(String str) {
        this.name = str;
    }

    public static TransportProtocol fromString(String name) {
        return MAP.getOrDefault(name, TransportProtocol.unknown);
    }

    public static TransportProtocol fromLabel(Label label) {
        return fromString(label.toString());
    }

    public ServiceName with(ApplicationProtocol applicationProtocol) {
        return new ServiceName(applicationProtocol, this);
    }
}
