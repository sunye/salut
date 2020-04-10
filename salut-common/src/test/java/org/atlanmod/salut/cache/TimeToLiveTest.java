package org.atlanmod.salut.cache;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TimeToLiveTest {

    @Test
    void testHasNotExpired() {
        TimeToLive ttl = TimeToLive.fromSeconds(100);
        assertFalse(ttl.hasExpired());
    }

    @Test
    void testHasExpired() throws InterruptedException {
        TimeToLive ttl = TimeToLive.fromSeconds(1);
        Thread.currentThread().sleep(1100);
        assertTrue(ttl.hasExpired());
    }

}