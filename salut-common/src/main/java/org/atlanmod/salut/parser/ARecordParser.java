package org.atlanmod.salut.parser;

import java.text.ParseException;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.domains.Host;
import org.atlanmod.salut.domains.IPAddress;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.record.ARecord;
import org.atlanmod.salut.record.BaseARecord;

public class ARecordParser extends NormalRecordParser<ARecord> {

    private Host host;

    /**
     * Parses the variable part of a ARecord.
     *
     * @param reader a byte array reader containing the record.
     * @throws ParseException if the record is not well-formed.
     */
    @Override
    protected void parseVariablePart(ByteArrayReader reader) throws ParseException {
        IPAddress address = reader.readIPv4Address();
        Domain serverName = DomainBuilder.fromLabels(labels);
        this.host = new Host(serverName, address);
    }

    @Override
    protected ARecord build() {
        return new BaseARecord(qclass, ttl, host);
    }

}
