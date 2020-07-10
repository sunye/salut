package org.atlanmod.salut.record;

import org.atlanmod.salut.cache.TimeToLive;

public interface NormalRecord extends Record {

    /**
     * @return this record's time to live
     */
    TimeToLive ttl();

    /**
     *
     * @return The `QClass` of resource records being requested e.g. Internet, CHAOS etc.
     */
    QClass qclass();
}
