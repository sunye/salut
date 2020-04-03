package org.atlanmod.salut.cache;

import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.io.UnsignedShort;

import java.util.Objects;

/**
 * The TimeToLive class represents an expiration time.
 */
public class TimeToLive implements Comparable<TimeToLive>{

    private long valueInSeconds;
    private long expireTimeMillis;

    private TimeToLive(long valueInSeconds, long expirationInMillis) {
        this.valueInSeconds = valueInSeconds;
        this.expireTimeMillis = expirationInMillis;
    }

    /**
     * Creates an instance of TimeToLive from a long integer representing the time delay, in seconds.
     *
     * @param seconds the time to live in seconds
     * @return a TimeToLive instance.
     */
    public static TimeToLive fromSeconds(long seconds) {
        long now = now();
        long expireTime = now + seconds * 1000;

        return new TimeToLive(seconds, expireTime);
    }

    /**
     * Creates an instance of `TimeToLive` from an UnsignedInt representing the expiration time in seconds.
     *
     * @param seconds the delay until expiration, in seconds
     * @return an instance of `TimeToLIve`
     */
    public static TimeToLive fromSeconds(UnsignedInt seconds) {
        return fromSeconds(seconds.longValue());
    }

    @Override
    public String toString() {
        return "TTL {value=" +  valueInSeconds +
            "s, expire=" + expireTimeMillis +
            "}";
    }

    /**
     * Checks if this time to live has expired.
     *
     * @return True, if this time to live has expired.
     */
    public boolean hasExpired() {
        return now() > this.expireTimeMillis;
    }


    /**
     * Calculates the remaining time to this TTL expiration.
     *
     * @return The remaining time, in milliseconds.
     */
    public long delayTimeMillis() {
        return this.expireTimeMillis - now();
    }

    private static long now() {
        return System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeToLive)) return false;
        TimeToLive that = (TimeToLive) o;
        return valueInSeconds == that.valueInSeconds;
    }

    @Override
    public int hashCode() {
        return Objects.hash(expireTimeMillis);
    }

    /**
     * Returns a 4-byte unsigned value representing this time to live, in seconds.
     * @return
     */
    public UnsignedInt unsignedIntValue() {
        return UnsignedInt.fromLong(this.valueInSeconds);
    }

    /**
     * Compares two TTLs in function of their expiration time.
     * @param other the other TTL to compare
     * @return 0, if both TTLs are the same, -1 if current TTL is inferior to other's TTL,
     * +1 otherwise.
     */
    @Override
    public int compareTo(TimeToLive other) {
        if (this.expireTimeMillis < other.expireTimeMillis) {
            return -1;
        } else if (this.expireTimeMillis > other.expireTimeMillis) {
            return +1;
        } else {
            return 0;
        }
    }
}
