package org.atlanmod.salut.record;

import org.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.io.UnsignedShort;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.names.ServiceInstanceName;
import org.atlanmod.salut.parser.Parser;
import org.atlanmod.salut.parser.ServiceRecordParser;

/**
 * The class `ServerSelectionRecord` represents the DNS SRV abstract record.
 * The SRV abstract record has the following format:
 *
 * # SRV AbstractRecord
 *
 * | Service Instance Label | Time To Live | QCLASS | QTYPE | Weight | Priority | Port | Server Label |
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
 * This points to a server named sipserver.example.com listening on TCP port 5060 for
 * Session Initiation Protocol (SIP) protocol services.
 * The priority given here is 0, and the weight is 5.
 *
 * As in MX records, the target in SRV records must point to hostname with an address record
 * (A or AAAA record).
 * Pointing to a hostname with a CNAME record is not a valid configuration.
 *
 * @see  "[RFC2782](https://tools.ietf.org/html/rfc2782)"
 */
public class BaseServerSelectionRecord extends AbstractNormalRecord implements
    ServerSelectionRecord {

    private UnsignedShort priority;
    private UnsignedShort weight;
    private UnsignedShort port;
    private Domain serverName;
    private ServiceInstanceName serviceInstanceName;

    public BaseServerSelectionRecord(QClass qclass, UnsignedInt ttl, UnsignedShort priority,
                                  UnsignedShort weight, UnsignedShort port,
                                  Domain serverName, ServiceInstanceName serviceInstanceName) {
        super(qclass, ttl);
        this.priority = priority;
        this.weight = weight;
        this.port = port;
        this.serverName = serverName;
        this.serviceInstanceName = serviceInstanceName;
    }

    /**
     * Returns an instance of `RecordParser` that is able to parse a SRVRecord and
     * create an instance of a `ServerSelectionRecord`.
     */
    public static Parser<ServerSelectionRecord> parser() {
        return new ServiceRecordParser();
    }

    /**
     * Creates an instance of `ServerSelectionRecord`. Used for testing purposes.
     *
     */
    @VisibleForTesting
    public static BaseServerSelectionRecord create(QClass qclass, UnsignedInt ttl, UnsignedShort priority,
                                                     UnsignedShort weight, UnsignedShort port,
                                                     Domain serverName, ServiceInstanceName serviceInstanceName) {

        return new BaseServerSelectionRecord(qclass, ttl, priority, weight, port,
                serverName, serviceInstanceName);
    }

    @Override
    public UnsignedShort port() {
        return this.port;
    }

    @Override
    public UnsignedShort priority() {
        return this.priority;
    }

    @Override
    public UnsignedShort weight() {
        return weight;
    }


    @Override
    public ServiceInstanceName serviceInstanceName() {
        return this.serviceInstanceName;
    }

    @Override
    public Domain serverName() {
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
                "class=" + qclass +
                ", ttl=" + ttl +
                ", priority=" + priority +
                ", weight=" + weight +
                ", port=" + port +
                ", server=" + serverName +
                '}';
    }

    public void writeOn(ByteArrayWriter writer) {
        // Fixed part
        writer.writeLabels(serviceInstanceName.toLabels())
                .writeRecordType(RecordType.SRV)
                .writeQClass(qclass)
                .writeUnsignedInt(ttl.unsignedIntValue())
                .writeUnsignedShort(UnsignedShort.fromInt(4));

        // Variable part
        writer.writeUnsignedShort(priority)
                .writeUnsignedShort(weight)
                .writeUnsignedShort(port)
                .writeLabels(serverName.toLabels());
    }

    @Override
    public Labels name() {
        return serviceInstanceName.toLabels();
    }
}
