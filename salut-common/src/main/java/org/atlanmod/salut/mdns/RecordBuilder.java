package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;

public interface RecordBuilder<T> {

    T build(Labels name, RecordType type, QClass qclass, UnsignedInt ttl);

}
