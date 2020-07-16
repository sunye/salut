package org.atlanmod.salut.parser;

import java.text.ParseException;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.domains.Host;
import org.atlanmod.salut.domains.IPAddress;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.record.ARecord;
import org.atlanmod.salut.record.BaseARecord;
import org.atlanmod.salut.record.NullRecord;
import org.atlanmod.salut.record.QClass;

public class NullRecordParser extends NormalRecordParser<NullRecord> {

    protected void parseVariablePart(ByteArrayReader reader) throws ParseException {
        for (int i = 0; i < dataLength; i++) {
            reader.getUnsignedByte(); // RDATA
        }
    }

    @Override
    protected NullRecord build() {
        return new NullRecord(qclass, ttl);
    }
}
