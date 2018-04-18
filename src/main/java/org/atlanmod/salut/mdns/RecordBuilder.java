package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.UnsignedInt;

public interface RecordBuilder<T> {

    T build(NameArray name, RecordType type, QClass qclass, UnsignedInt ttl);

}
