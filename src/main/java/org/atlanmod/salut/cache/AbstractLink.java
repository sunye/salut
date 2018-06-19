package org.atlanmod.salut.cache;

/**
 * Abstract class for representing links between entries.
 *
 * Each link has a time to live. Once it is expired, the link is no longer valid.
 */
public abstract class AbstractLink {
    private TimeToLive timeToLive;

    public AbstractLink(TimeToLive ttl) {
        this.timeToLive = ttl;
    }

    public TimeToLive getTimeToLive() {
        return timeToLive;
    }

    public boolean hasExpired() {
        return this.timeToLive.hasExpired();
    }
}
