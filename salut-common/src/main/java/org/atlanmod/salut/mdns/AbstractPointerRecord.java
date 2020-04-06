package org.atlanmod.salut.mdns;

import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.data.*;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.UnsignedInt;

import java.text.ParseException;

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

    AbstractPointerRecord(LabelArray name, QClass qclass, UnsignedInt ttl) {
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
        protected  ServiceInstanceName pointerName;
        protected  ServiceName serviceName;


        /**
         *
         * @return The type of the service that is being declared.
         */
        public ServiceName getServiceName() {
            return this.serviceName;
        }



        /**
         * Parses the variable part of a PTR record.
         *
         * @param buffer a `ByteArrayReader` containing the record to parse.
         * @throws ParseException
         */
        @Override
        protected void parseVariablePart(ByteArrayReader buffer) throws ParseException {
            LabelArray ptrName = LabelArray.fromByteBuffer(buffer);
            Log.info("PTR(pointerName={0}, serviceName={1})", ptrName, name);

            if (Domain.ARPA.equals(name.last().toString())) {
                // There should be a better way to determine whether the pointer record is a
                // reverse lookup.
                Log.warn("Reverse Pointer: {0} ", name);
                isReverseLookup = true;
            } else if ("_services".equals(name.first().toString())) {
                Log.warn("Meta Query Pointer: {0} ", name);
                isMetaQuery = true;
            }
            else {
                pointerName = ServiceInstanceName.fromNameArray(ptrName);
                Log.info("---");
                // Ignore domain, parse only Service Name
                serviceName = ServiceName.fromLabelArray(name.subArray(0,2));
            }
        }

        /**
         * Creates a new instance of `PointerRecord`.
         */
        @Override
        protected PointerRecord build() {
            if (isReverseLookup) {
                return new ReverseLookupPointerRecord(name, qclass, ttl);
            } else if (isMetaQuery) {
                return new MetaQueryPointerRecord(name, qclass, ttl);
            }
            else {
                return new NormalPointerRecord(name, qclass, ttl, pointerName, serviceName);
            }
        }
    }
}
