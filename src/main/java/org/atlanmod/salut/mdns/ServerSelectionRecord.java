package org.atlanmod.salut.mdns;

import fr.inria.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.salut.io.ByteArrayBuffer;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.io.UnsignedShort;

import java.text.ParseException;
import java.util.List;

/**
 * The class `ServerSelectionRecord` represents the DNS SRV abstract record.
 * The SRV abstract record has the following format:
 *
 * # SRV AbstractRecord
 *
 * | Service Instance Name | Time To Live | QCLASS | QTYPE | Weight | Priority | Port | Server Name |
 * | --- | ---|---|---|---|---|---|---|---|
 * | PrintsAlot._printer._tcp.local. | 120 | IN | SRV | 0 | 0 | 515 | blackhawk.local. |
 *
 *
 */
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

    /**
     * Returns an instance of `RecordParser` that is able to parse a SRVRecord and create an instance of
     * a `ServerSelectionRecord`.
     *
     */
    public static RecordParser<ServerSelectionRecord> parser() {
        return new SRVRecordParser();
    }

    /**
     * Creates an instance of `ServerSelectionRecord`. Used for testing purposes.
     *
     */
    @VisibleForTesting
    public static ServerSelectionRecord createRecord(NameArray name, QClass qclass, UnsignedInt ttl, UnsignedShort priority,
                                                     UnsignedShort weight, UnsignedShort port, List<String> target) {

        return new ServerSelectionRecord(name, qclass, ttl, priority, weight, port, target);
    }

    /**
     * Returns the port number where the service is running.
     */
    public UnsignedShort port() {
        return this.port;
    }

    /**
     * Returns the service priority.
     */
    public UnsignedShort priority() {
        return this.priority;
    }

    /**
     * Returns the service weight.
     */
    public UnsignedShort weight() {
        return weight;
    }

    /**
     * Returns a `String` object representing this `ServerSelectionRecord`.
     *
     * @return  a string representation of this object.
     */
    @Override
    public String toString() {
        return "SRVRecord{" +
                "data=" + names +
                ", class=" + qclass +
                ", ttl=" + ttl +
                ", priority=" + priority +
                ", weight=" + weight +
                ", port=" + port +
                ", target=" + target +
                '}';
    }

    /**
     * The class `SRVRecordParser` is used to parse the variable part of a DNS SRV record.
     */
    private static class SRVRecordParser extends NormalRecordParser<ServerSelectionRecord> {

        private UnsignedShort priority;
        private UnsignedShort weight;
        private UnsignedShort port;
        private List<String> target;

        /**
         * Parses the variable part of a SRV Record.
         * @param buffer a `ByteArrayBuffer` containing the record to parse.
         * @throws ParseException
         */
        @Override
        protected void parseVariablePart(ByteArrayBuffer buffer) throws ParseException {
            priority = buffer.getUnsignedShort();
            weight = buffer.getUnsignedShort();
            port = buffer.getUnsignedShort();
            target = buffer.readLabels();
        }

        /**
         * Creates a new instance of `ServerSelectionRecord`.
         */
        @Override
        protected ServerSelectionRecord build() {
            return new ServerSelectionRecord(name, qclass, ttl, priority, weight, port, target);
        }
    }
}
