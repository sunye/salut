package org.atlanmod.salut.cache;

import org.atlanmod.salut.io.UnsignedInt;

/**
 * The TimeToLive class represents an expiration time.
 *
 */
public class TimeToLive {

    private long expireTimeMillis;

    private TimeToLive(long expirationInMillis) {
        this.expireTimeMillis = expirationInMillis;
    }

    /**
     * Checks if this time to live has expired.
     *
     * @return True, if this time to live has expired.
     */
    public boolean hasExpired() {
        return System.currentTimeMillis() > this.expireTimeMillis;
    }

    /**
     * Creates an instance of TimeToLive from a long integer representing seconds.
     * @param seconds the time to live in seconds
     * @return a TimeToLive instance.
     */
    public static TimeToLive fromSeconds(long seconds) {
        long now = System.currentTimeMillis();
        long term = now + seconds * 1000;

        return new TimeToLive(term);
    }

    public static TimeToLive fromSeconds(UnsignedInt seconds) {
        return fromSeconds(seconds.toLong());
    }
}
