package org.atlanmod.salut.parser;

import java.text.ParseException;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.names.PointerName;
import org.atlanmod.salut.names.ServiceInstanceName;
import org.atlanmod.salut.record.BasePointerRecord;
import org.atlanmod.salut.record.MetaQueryPointerRecord;
import org.atlanmod.salut.record.PointerRecord;
import org.atlanmod.salut.record.ReverseLookupPointerRecord;

/**
 * The class `PTRRecordParser` is used to parse the variable part of a DNS PTR record.
 */
public class PointerRecordParser extends NormalRecordParser<PointerRecord> {
    private boolean isReverseLookup = false;
    private boolean isMetaQuery = false;
    protected ServiceInstanceName instance;
    protected PointerName service;

    /**
     * Parses the variable part of a PTR record.
     *
     * @param buffer a `ByteArrayReader` containing the record to parse.
     * @throws ParseException
     */
    @Override
    protected void parseVariablePart(ByteArrayReader buffer) throws ParseException {
        // TODO Consider dataLength

        Labels instanceLabels = Labels.fromByteBuffer(buffer);
        Log.info("PTR(pointerName={0}, serviceName={1})", instanceLabels, labels);

        if (Domain.ARPA.equals(labels.last().toString())) {
            // There should be a better way to determine whether the pointer record is a
            // reverse lookup.
            Log.warn("Reverse Pointer: {0} ", labels);
            isReverseLookup = true;
        } else if ("_services".equals(labels.first().toString())) {
            Log.warn("Meta Query Pointer: {0} ", labels);
            isMetaQuery = true;
        }
        else {
            service = PointerName.fromLabels(labels);
            instance = ServiceInstanceName.fromLabels(instanceLabels);
        }
    }

    /**
     * Creates a new instance of `PointerRecord`.
     */
    @Override
    protected PointerRecord build() {
        if (isReverseLookup) {
            return new ReverseLookupPointerRecord(qclass, ttl);
        } else if (isMetaQuery) {
            return new MetaQueryPointerRecord(qclass, ttl);
        }
        else {
            return new BasePointerRecord(qclass, ttl, instance, service);
        }
    }
}
