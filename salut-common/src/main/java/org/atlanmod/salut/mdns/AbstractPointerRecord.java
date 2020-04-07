package org.atlanmod.salut.mdns;

import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.UnsignedInt;

import java.text.ParseException;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.names.PointerName;
import org.atlanmod.salut.names.ServiceInstanceName;

/**
 * The `PointerRecord` class represents DNS Domain name pointer record (PTR).
 * PTR records enable service discovery by mapping the type of the service to a list of name of specific
 * instances of that type of service.
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
public abstract class AbstractPointerRecord extends AbstractNormalRecord implements PointerRecord {

    AbstractPointerRecord(Labels name, QClass qclass, UnsignedInt ttl) {
        super(name, qclass, ttl);
    }

    /**
     * Returns an instance of `RecordParser` that is able to parse a PTRRecord and create an instance of
     * a `PointerRecord`.
     *
     */
    public static RecordParser<PointerRecord> parser() {
        return new PTRRecordParser();
    }

    /**
     * The class `PTRRecordParser` is used to parse the variable part of a DNS PTR record.
     */
    private static class PTRRecordParser extends NormalRecordParser<PointerRecord> {
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
                return new ReverseLookupPointerRecord(labels, qclass, ttl);
            } else if (isMetaQuery) {
                return new MetaQueryPointerRecord(labels, qclass, ttl);
            }
            else {
                return new BasePointerRecord(labels, qclass, ttl, instance, service);
            }
        }
    }
}
