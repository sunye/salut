package org.atlanmod.salut.parser;

import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.record.QClass;
import org.atlanmod.salut.record.RecordType;

public interface RecordBuilder<T> {

    T build(Labels name, RecordType type, QClass qclass, UnsignedInt ttl);

}
