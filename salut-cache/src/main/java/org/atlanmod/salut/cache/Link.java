package org.atlanmod.salut.cache;

public interface Link extends Comparable<Link> {
    TimeToLive getTimeToLive();

    boolean hasExpired();

    /**
     * Deletes this link's references.
     * This method should be called when the TTL expires.
     */
    void unlink();
}
