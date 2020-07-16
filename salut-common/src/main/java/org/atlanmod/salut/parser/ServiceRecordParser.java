package org.atlanmod.salut.parser;

import java.text.ParseException;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.UnsignedShort;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.names.ServiceInstanceName;
import org.atlanmod.salut.record.BaseServerSelectionRecord;
import org.atlanmod.salut.record.ServerSelectionRecord;

/**
 * The class `SRVRecordParser` is used to parse the variable part of a DNS SRV record.
 */
public class ServiceRecordParser extends NormalRecordParser<ServerSelectionRecord> {

    private UnsignedShort priority;
    private UnsignedShort weight;
    private UnsignedShort port;
    private Labels target;
    private Domain serverName;
    private ServiceInstanceName serviceInstanceName;

    /**
     * Parses the variable part of a SRV Record.
     * @param buffer a `ByteArrayReader` containing the record to parse.
     * @throws ParseException
     */
    @Override
    protected void parseVariablePart(ByteArrayReader buffer) throws ParseException {
        priority = buffer.getUnsignedShort();
        weight = buffer.getUnsignedShort();
        port = buffer.getUnsignedShort();
        target = Labels.fromByteBuffer(buffer);
        serverName = DomainBuilder.fromLabels(this.target);
        serviceInstanceName = ServiceInstanceName.fromLabels(this.labels);
    }

    /**
     * Creates a new instance of `ServerSelectionRecord`.
     */
    @Override
    protected ServerSelectionRecord build() {
        return new BaseServerSelectionRecord(qclass, ttl, priority, weight, port,
                serverName, serviceInstanceName);
    }
}
