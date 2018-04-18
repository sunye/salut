package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.UnsignedShort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class QRFlagTest {

    @ParameterizedTest
    @ValueSource(ints = {0x0000, 0x7FFF})
    void testIsQuery(int value) {
        QRFlag queryFlag = QRFlag.fromInt(value);
        assertThat(queryFlag.isQuery()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {0x8000, 0xFFFF})
    void testNotQuery(int value) {
        QRFlag queryFlag = QRFlag.fromInt(value);
        assertThat(queryFlag.isQuery()).isFalse();
    }

    @Test
    void isResponse() {
    }

    @Test
    void isValid() {
    }

    @ParameterizedTest
    @ValueSource(ints = {0x0200, 0xFFFF})
    void isTruncated(int value) {
        QRFlag truncatedFlag = QRFlag.fromInt(value);
        assertThat(truncatedFlag.isTruncated()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {0x1800, 0x9FFF})
    void testOpCode(int value) {
        QRFlag flag = QRFlag.fromInt(value);
        assertThat(flag.opCode()).isEqualTo(3);
    }

}