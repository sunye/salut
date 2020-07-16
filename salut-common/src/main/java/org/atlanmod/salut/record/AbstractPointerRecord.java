package org.atlanmod.salut.record;

import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.parser.PointerRecordParser;
import org.atlanmod.salut.parser.Parser;

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

    AbstractPointerRecord(QClass qclass, UnsignedInt ttl) {
        super(qclass, ttl);
    }

    /**
     * Returns an instance of `RecordParser` that is able to parse a PTRRecord and create an instance of
     * a `PointerRecord`.
     *
     */
    public static Parser<PointerRecord> parser() {
        return new PointerRecordParser();
    }

}
