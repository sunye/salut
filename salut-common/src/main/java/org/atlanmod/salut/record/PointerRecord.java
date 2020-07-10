package org.atlanmod.salut.record;

import org.atlanmod.salut.names.PointerName;
import org.atlanmod.salut.names.ServiceInstanceName;

public interface PointerRecord extends NormalRecord {

    ServiceInstanceName serviceInstanceName();

    PointerName pointerName();
}
