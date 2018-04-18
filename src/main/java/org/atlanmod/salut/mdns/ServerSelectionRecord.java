package org.atlanmod.salut.mdns;

import fr.inria.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.salut.io.ByteArrayBuffer;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.io.UnsignedShort;

import java.text.ParseException;
import java.util.List;

public class ServerSelectionRecord extends NormalRecord {

    private UnsignedShort priority;
    private UnsignedShort weight;
    private UnsignedShort port;
    private List<String> target;

    private ServerSelectionRecord(NameArray name, QClass qclass, UnsignedInt ttl, UnsignedShort priority,
                                  UnsignedShort weight, UnsignedShort port, List<String> target) {
        super(name, qclass, ttl);
        this.priority = priority;
        this.weight = weight;
        this.port = port;
        this.target = target;
    }

    public UnsignedShort port() {
        return this.port;
    }

    public UnsignedShort priority() {
        return this.priority;
    }

    public UnsignedShort weight() {
        return weight;
    }

    @Override
    public String toString() {
        return "SRVRecord{" +
                "names=" + names +
                ", class=" + qclass +
                ", ttl=" + ttl +
                ", priority=" + priority +
                ", weight=" + weight +
                ", port=" + port +
                ", target=" + target +
                '}';
    }

    public static RecordParser<ServerSelectionRecord> parser() {
        return new SRVRecordParser();
    }

    private static class SRVRecordParser extends NormalRecordParser<ServerSelectionRecord> {

        private UnsignedShort priority;
        private UnsignedShort weight;
        private UnsignedShort port;
        private List<String> target;

        protected void parseVariablePart(ByteArrayBuffer buffer) throws ParseException {
            priority = buffer.getUnsignedShort();
            weight = buffer.getUnsignedShort();
            port = buffer.getUnsignedShort();
            target = buffer.readLabels();
        }

        @Override
        protected ServerSelectionRecord build() {
            return new ServerSelectionRecord(name, qclass, ttl, priority, weight, port, target);
        }
    }

    @VisibleForTesting
    public static ServerSelectionRecord createRecord(NameArray name, QClass qclass, UnsignedInt ttl, UnsignedShort priority,
                                                     UnsignedShort weight, UnsignedShort port, List<String> target) {

        return new ServerSelectionRecord(name, qclass, ttl, priority, weight, port, target);
    }
}
