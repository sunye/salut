package org.atlanmod.salut.parser;

import java.text.ParseException;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.domains.IPv6Address;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.record.AAAARecord;

public class AAAARecordParser extends NormalRecordParser<AAAARecord> {
    private IPv6Address address;
    private Domain serverName;

    @Override
    protected void parseVariablePart(ByteArrayReader buffer) throws ParseException {
        address = buffer.readIPv6Address();
        serverName = DomainBuilder.fromLabels(labels);
    }

    @Override
    protected AAAARecord build() {
        return new AAAARecord(qclass, ttl, address, serverName);
    }

}
