package org.atlanmod.salut.record;

import java.util.Objects;
import org.atlanmod.salut.cache.TimeToLive;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;

/**
 * @formatter:off
 * All RRs have the same top level format shown below:
 *
 *                                     1  1  1  1  1  1
 *       0  1  2  3  4  5  6  7  8  9  0  1  2  3  4  5
 *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *     |                                               |
 *     /                                               /
 *     /                      NAME                     /
 *     |                                               |
 *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *     |                      TYPE                     |
 *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *     |                     CLASS                     |
 *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *     |                      TTL                      |
 *     |                                               |
 *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 *     |                   RDLENGTH                    |
 *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--|
 *     /                     RDATA                     /
 *     /                                               /
 *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * @formatter:off
 */
public abstract class AbstractNormalRecord extends WritableRecord implements NormalRecord {

    protected final QClass qclass;
    protected final TimeToLive ttl;

    AbstractNormalRecord(QClass qclass, UnsignedInt ttl) {
        this.qclass = qclass;
        this.ttl = TimeToLive.fromSeconds(ttl);
    }

    @Override
    public TimeToLive ttl() {
        return this.ttl;
    }

    @Override
    public QClass qclass() {
        return qclass;
    }

    @Override
    public String toString() {
        return "{" + qclass + ", " + ttl + '}';
    }

    @Override
    public boolean equals(Object other) {
        //@formatter:off
        if (this == other) {return true;}
        if (!(other instanceof AbstractNormalRecord)) {return false;}
        //@formatter:off

        AbstractNormalRecord that = (AbstractNormalRecord) other;
        return
            qclass.equals(that.qclass) &&
            ttl.equals(that.ttl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qclass, ttl);
    }

    }
