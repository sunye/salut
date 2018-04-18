package org.atlanmod.salut.cache;

public abstract class CacheEntry {

    private long expireTimeMillis;

    public CacheEntry(long timeToLive) {
        this.expireTimeMillis = timeToLive;
    }

    public void updateExpireTime(long posponedExpireTime) {
        if (posponedExpireTime > this.expireTimeMillis) {
            this.expireTimeMillis = posponedExpireTime;
        }
    }

    public boolean hasExpired() {
        return System.currentTimeMillis() > this.expireTimeMillis;
    }
}
