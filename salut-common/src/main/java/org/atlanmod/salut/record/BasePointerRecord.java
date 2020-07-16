package org.atlanmod.salut.record;

import java.util.Objects;
import org.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.io.UnsignedShort;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.names.PointerName;
import org.atlanmod.salut.names.ServiceInstanceName;

/**
 * The `PointerRecord` class represents DNS Domain name pointer record (PTR).
 * PTR records enable service discovery by mapping the type of the service to a list of name of specific
 * instances of that type of service.
 * This record adds yet another layer of indirection so services can be found just by looking up
 * PTR records labeled with the service type.
 *
 * The record contains just one piece of information, the name of the service (which is the same
 * as the name of the SRV record). PTR records are accordingly named just like SRV records but
 * without the instance name:
 *
 *  - <Service Type>.<Domain>
 *
 * The PTR  record has the following format:
 *
 * # PTR Record
 *
 * Server Name | Time to live| QCLASS | QTYPE | Service Instance Label
 * --|--
 * _printer._tcp.local.  | 28800 | IN | PTR | PrintsAlot._printer._tcp.local.
 *
 */
public class BasePointerRecord extends AbstractPointerRecord {
    /**
     * A <domain-name> which points to some location in the domain name space.
     *
     * The name of the service instance.
     */
    private ServiceInstanceName instance;

    private PointerName server;

    public BasePointerRecord(QClass qclass, UnsignedInt ttl, ServiceInstanceName instance, PointerName server) {
        super(qclass, ttl);
        this.instance = instance;
        this.server = server;
    }

    @Override
    public boolean equals(Object other) {
        //@formatter:off
        if (this == other) {return true;}
        if (!(other instanceof BasePointerRecord)) {return false;}
        //@formatter:on

        BasePointerRecord that = (BasePointerRecord) other;
        return
            super.equals(other) &&
            instance.equals(that.instance) &&
            server.equals(that.server);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), instance, server);
    }

    public ServiceInstanceName serviceInstanceName() {
        return this.instance;
    }

    public PointerName pointerName() {
        return this.server;
    }

    /**
     * Returns a `String` object representing this `NormalPointerRecord`.
     * @return  a string representation of this object.
     */
    @Override
    public String toString() {
        return "BasePointerRecord{" + server
            + ", " + qclass
            + ", " + ttl
            +", "+ instance + "}";
    }

    @Override
    public void writeOn(ByteArrayWriter writer) {
        // FIXME The Data length cannot be calculated here, because labels can be compressed.

        UnsignedShort dataLength = UnsignedShort.fromInt(instance.toLabels().dataLength());

        writer.writeLabels(server.toLabels())
            .writeRecordType(RecordType.PTR)
            .writeQClass(qclass)
            .writeUnsignedInt(ttl.unsignedIntValue())
            .writeUnsignedShort(dataLength)
            .writeLabels(instance.toLabels());
    }

    @VisibleForTesting
    public static BasePointerRecord create(QClass qclass, UnsignedInt ttl,
        ServiceInstanceName instance, PointerName server) {

        return new BasePointerRecord(qclass, ttl, instance, server);
    }

    @Override
    public Labels name() {
        return server.toLabels();
    }
}
