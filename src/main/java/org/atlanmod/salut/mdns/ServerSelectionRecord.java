package org.atlanmod.salut.mdns;

import fr.inria.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.salut.data.DomainName;
import org.atlanmod.salut.data.DomainNameBuilder;
import org.atlanmod.salut.data.ServiceInstanceName;
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
 * A SRV record has the form:
 *
 *   ```
 *   _service._proto.name. TTL class SRV priority weight port target.
 *   ```
 *
 *   - service: the symbolic name of the desired service.
 *   - proto: the transport protocol of the desired service; this is usually either TCP or UDP.
 *   - name: the domain name for which this record is valid, ending in a dot.
 *   - TTL: standard DNS time to live field.
 *   - class: standard DNS class field (this is always IN).
 *   - priority: the priority of the target host, lower value means more preferred.
 *   - weight: A relative weight for records with the same priority, higher value means more preferred.
 *   - port: the TCP or UDP port on which the service is to be found.
 *   - target: the canonical hostname of the machine providing the service, ending in a dot.
 *
 * An example SRV record in textual form that might be found in a zone file might be the following:
 *
 *  ```
 * _sip._tcp.example.com. 86400 IN SRV 0 5 5060 sipserver.example.com.
 * ```
 *
 * This points to a server named sipserver.example.com listening on TCP port 5060 for Session Initiation Protocol (SIP) protocol services. The priority given here is 0, and the weight is 5.
 *
 * As in MX records, the target in SRV records must point to hostname with an address record (A or AAAA record). Pointing to a hostname with a CNAME record is not a valid configuration.
 */
public class ServerSelectionRecord extends NormalRecord {

    private UnsignedShort priority;
    private UnsignedShort weight;
    private UnsignedShort port;
    private NameArray target;
    private DomainName serverName;
    private ServiceInstanceName serviceInstanceName;

    private ServerSelectionRecord(NameArray name, QClass qclass, UnsignedInt ttl, UnsignedShort priority,
                                  UnsignedShort weight, UnsignedShort port, NameArray target,
                                  DomainName serverName, ServiceInstanceName serviceInstanceName) {
        super(name, qclass, ttl);
        this.priority = priority;
        this.weight = weight;
        this.port = port;
        this.target = target;
        this.serverName = serverName;
        this.serviceInstanceName = serviceInstanceName;
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
                                                     UnsignedShort weight, UnsignedShort port, NameArray target,
                                                     DomainName serverName, ServiceInstanceName serviceInstanceName) {

        return new ServerSelectionRecord(name, qclass, ttl, priority, weight, port, target,
                serverName, serviceInstanceName);
    }

    /**
     * Returns the getPort number where the service is running.
     */
    public UnsignedShort getPort() {
        return this.port;
    }

    /**
     * Returns the service getPriority.
     */
    public UnsignedShort getPriority() {
        return this.priority;
    }

    /**
     * Returns the service getWeight.
     */
    public UnsignedShort getWeight() {
        return weight;
    }


    public ServiceInstanceName getServiceInstanceName() {
        return this.serviceInstanceName;
    }

    public DomainName getServerName() {
        return this.serverName;
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
                ", getTtl=" + ttl +
                ", getPriority=" + priority +
                ", getWeight=" + weight +
                ", getPort=" + port +
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
        private NameArray target;
        private DomainName serverName;
        private ServiceInstanceName serviceInstanceName;

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
            target = NameArray.fromByteBuffer(buffer);
            serverName = DomainNameBuilder.fromNameArray(this.target);
            serviceInstanceName = ServiceInstanceName.fromNameArray(this.name);
        }

        /**
         * Creates a new instance of `ServerSelectionRecord`.
         */
        @Override
        protected ServerSelectionRecord build() {
            return new ServerSelectionRecord(name, qclass, ttl, priority, weight, port, target,
                    serverName, serviceInstanceName);
        }
    }
}
