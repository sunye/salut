package org.atlanmod.salut.cache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeToLiveTest {

    @Test
    void testHasNotExpired() {
        TimeToLive ttl = TimeToLive.fromSeconds(100);
        assertFalse(ttl.hasExpired());
    }

    @Test
    void testeHasExpired() throws InterruptedException {
        TimeToLive ttl = TimeToLive.fromSeconds(1);
        Thread.currentThread().sleep(1000);
        assertTrue(ttl.hasExpired());
    }

}