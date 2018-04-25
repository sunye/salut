package org.atlanmod.salut.cache;

public abstract class AbstractCacheEntry {

    private TimeToLive expireTime;

    public AbstractCacheEntry(TimeToLive timeToLive) {
        this.expireTime = timeToLive;
    }

    public void updateExpireTime(TimeToLive posponedExpireTime) {
        /*
        if (posponedExpireTime > this.expireTimeMillis) {
            this.expireTimeMillis = posponedExpireTime;
        }*/
    }

    public boolean hasExpired() {
        return this.expireTime.hasExpired();
    }
}
