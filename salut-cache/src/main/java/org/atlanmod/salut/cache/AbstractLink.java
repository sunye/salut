package org.atlanmod.salut.cache;

/**
 * Abstract class for representing links between entries.
 *
 * Each link has a time to live. Once it is expired, the link is no longer valid.
 */
public abstract class AbstractLink implements Link {
    private TimeToLive timeToLive;

    public AbstractLink(TimeToLive ttl) {
        this.timeToLive = ttl;
    }

    @Override
    public TimeToLive getTimeToLive() {
        return timeToLive;
    }

    @Override
    public boolean hasExpired() {
        return this.timeToLive.hasExpired();
    }

    @Override
    public int compareTo(Link other) {
        return timeToLive.compareTo(other.getTimeToLive());
    }
}
