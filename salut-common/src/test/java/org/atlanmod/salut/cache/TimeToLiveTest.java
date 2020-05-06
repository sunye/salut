package org.atlanmod.salut.cache;

import static org.assertj.core.api.Assertions.assertThat;

import org.atlanmod.salut.io.UnsignedInt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimeToLiveTest {

    private TimeToLive tenSeconds;

    @BeforeEach
    void beforeEach() {
        tenSeconds = TimeToLive.fromSeconds(10);
    }

    @Test
    void testHasNotExpired() {
        TimeToLive ttl = TimeToLive.fromSeconds(100);

        assertThat(ttl.hasExpired()).isFalse();
    }

    @Test
    void testHasExpired() throws InterruptedException {
        TimeToLive ttl = TimeToLive.fromSeconds(1);
        Thread.currentThread().sleep(1100);

        assertThat(ttl.hasExpired()).isTrue();
    }

    @Test
    void testFromSeconds() {
        TimeToLive actual = TimeToLive.fromSeconds(UnsignedInt.fromInt(10));

        assertThat(actual).isEqualTo(tenSeconds);
    }

    @Test
    void testEquals() {
        TimeToLive other = TimeToLive.fromSeconds(10);
        TimeToLive twentySeconds = TimeToLive.fromSeconds(20);

        assertThat(other).isEqualTo(tenSeconds)
            .isNotEqualTo(null)
            .isNotEqualTo(twentySeconds);
    }

    @Test
    void testHashcode() {
        TimeToLive other = TimeToLive.fromSeconds(10);
        TimeToLive twentySeconds = TimeToLive.fromSeconds(20);

        assertThat(other.hashCode()).isEqualTo(tenSeconds.hashCode())
            .isNotEqualTo(twentySeconds.hashCode());
    }

    @Test
    void testCompareTo() {
        TimeToLive fiveSeconds = TimeToLive.fromSeconds(5);
        TimeToLive twentySeconds = TimeToLive.fromSeconds(20);

        assertThat(tenSeconds).isGreaterThan(fiveSeconds)
            .isLessThan(twentySeconds)
            .isEqualByComparingTo(tenSeconds);
    }

    @Test
    void testUnsignedIntValue() {
        assertThat(UnsignedInt.fromInt(10)).isEqualTo(tenSeconds.unsignedIntValue());
    }

    @Test
    void testToString() {
        TimeToLive twentySeconds = TimeToLive.fromSeconds(20);

        assertThat(tenSeconds.toString()).contains("10");
        assertThat(twentySeconds.toString()).contains("20");
    }

    @Test
    void testDelayTimeMillis() {
        TimeToLive oneHundredSeconds = TimeToLive.fromSeconds(100);

        assertThat(oneHundredSeconds.delayTimeMillis())
            .isGreaterThan(0)
            .isLessThan(100 * 1000 + 1);
    }
}
