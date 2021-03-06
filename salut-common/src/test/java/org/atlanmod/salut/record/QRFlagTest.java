package org.atlanmod.salut.record;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QRFlagTest {

    @ParameterizedTest
    @ValueSource(ints = {0x0000, 0x7FFF})
    void testIsQuery(int value) {
        QRFlag queryFlag = QRFlag.fromInt(value);

        assertAll(
            () -> assertThat(queryFlag.isQuery()).isTrue(),
            () -> assertThat(queryFlag.isResponse()).isFalse()
        );

    }

    @ParameterizedTest
    @ValueSource(ints = {0x8000, 0xFFFF})
    void testNotQuery(int value) {
        QRFlag queryFlag = QRFlag.fromInt(value);

        assertAll(
            () -> assertThat(queryFlag.isQuery()).isFalse(),
            () -> assertThat(queryFlag.isResponse()).isTrue()
        );

    }

    @ParameterizedTest
    @ValueSource(bytes = {0, 1, 8, 14, 15})
    void testSetOpCode(byte code) {
        QRFlag queryFlag = QRFlag.fromInt(0);
        queryFlag.setOpCode(code);
        assertThat(queryFlag.opCode()).isEqualTo(code);
    }

    @Test
    void testSetEmptyOpCode() {
        QRFlag queryFlag = QRFlag.fromInt(0);
        assertThat(queryFlag.opCode()).isEqualTo(Byte.valueOf((byte) 0));
    }

    @ParameterizedTest
    @ValueSource(bytes = {0, 1, 8, 14, 15})
    void testSetOpCodeAgain(byte code) {
        QRFlag queryFlag = QRFlag.fromInt(0xFFFF);
        queryFlag.setOpCode(code);
        assertThat(queryFlag.opCode()).isEqualTo(code);
    }

    @Test
    void testIsAA() {
        QRFlag queryFlag = QRFlag.fromInt(1024);

        assertThat(queryFlag.isAuthoritativeAnswer()).isTrue();
    }

    @Test
    void testSetAA() {
        QRFlag queryFlag = QRFlag.fromInt(0);

        assertThat(queryFlag.isAuthoritativeAnswer()).isFalse();
        queryFlag.setAuthoritativeAnswer(true);
        assertThat(queryFlag.isAuthoritativeAnswer()).isTrue();
        queryFlag.setAuthoritativeAnswer(false);
        assertThat(queryFlag.isAuthoritativeAnswer()).isFalse();
    }

    @Test
    void testIsTruncated() {
        QRFlag queryFlag = QRFlag.fromInt(512);
        assertThat(queryFlag.isTruncated()).isTrue();
    }

    @Test
    void testSetTruncated() {
        QRFlag queryFlag = QRFlag.fromInt(0);
        assertThat(queryFlag.isTruncated()).isFalse();
        queryFlag.setTruncated(true);
        assertThat(queryFlag.isTruncated()).isTrue();
        queryFlag.setTruncated(false);
        assertThat(queryFlag.isTruncated()).isFalse();
    }

    @Test
    void testSetTruncatedAgain() {
        QRFlag queryFlag = QRFlag.fromInt(0xFFFF);
        assertThat(queryFlag.isTruncated()).isTrue();
        queryFlag.setTruncated(false);
        assertThat(queryFlag.isTruncated()).isFalse();
        queryFlag.setTruncated(true);
        assertThat(queryFlag.isTruncated()).isTrue();
    }

    @Test
    void testIsRecursionDesired() {
        QRFlag queryFlag = QRFlag.fromInt(256);

        assertThat(queryFlag.isRecursionDesired()).isTrue();
    }

    @Test
    void testSetRecursionDesired() {
        QRFlag clean = QRFlag.fromInt(0);
        QRFlag dirt = QRFlag.fromInt(0xFFFF);

        assertAll(
            () -> assertThat(clean.isRecursionDesired()).isFalse(),
            () -> assertThat(dirt.isRecursionDesired()).isTrue()
        );

        clean.setRecursionDesired(true);
        dirt.setRecursionDesired(true);

        assertAll(
            () -> assertThat(clean.isRecursionDesired()).isTrue(),
            () -> assertThat(dirt.isRecursionDesired()).isTrue()
        );

        clean.setRecursionDesired(false);
        dirt.setRecursionDesired(false);
        assertAll(
            () -> assertThat(clean.isRecursionDesired()).isFalse(),
            () -> assertThat(dirt.isRecursionDesired()).isFalse()
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 0xFFFF})
    void testNoSideEffect(int value) throws CloneNotSupportedException {
        QRFlag flag = QRFlag.fromInt(value);
        QRFlag other = new QRFlag(flag);

        boolean previousRecursionDesired = flag.isRecursionDesired();
        boolean previousAuthoritativeAnswer = flag.isAuthoritativeAnswer();
        boolean previousRecursionAvailable = flag.isRecursionAvailable();
        boolean previousTruncated = flag.isTruncated();

        flag.setRecursionDesired(true);
        flag.setRecursionDesired(false);
        flag.setRecursionDesired(previousRecursionDesired);

        flag.setAuthoritativeAnswer(true);
        flag.setAuthoritativeAnswer(false);
        flag.setAuthoritativeAnswer(previousAuthoritativeAnswer);

        flag.setRecursionAvailable(true);
        flag.setRecursionAvailable(false);
        flag.setRecursionAvailable(previousRecursionAvailable);

        flag.setTruncated(true);
        flag.setTruncated(false);
        flag.setTruncated(previousTruncated);

        assertThat(flag).isEqualTo(other);
    }

    @Test
    void testIsRecursionAvailable() {
        QRFlag queryFlag = QRFlag.fromInt(128);
        assertThat(queryFlag.isRecursionAvailable()).isTrue();
    }

    @Test
    void testSetRecursionAvailable() {
        QRFlag queryFlag = QRFlag.fromInt(0);

        assertThat(queryFlag.isRecursionAvailable()).isFalse();
        queryFlag.setRecursionAvailable(true);
        assertThat(queryFlag.isRecursionAvailable()).isTrue();
        queryFlag.setRecursionAvailable(false);
        assertThat(queryFlag.isRecursionAvailable()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(bytes = {0, 1, 8, 14, 15})
    void testSetResponseCode(byte code) {
        QRFlag clean = QRFlag.fromInt(0);
        QRFlag dirt = QRFlag.fromInt(0xFFFF);
        assertAll(
            () -> assertThat(clean.responseCode()).isEqualTo(Byte.valueOf((byte) 0)),
            () -> assertThat(dirt.responseCode() == 15).isTrue()
        );

        clean.setResponseCode(code);
        dirt.setResponseCode(code);
        assertAll(
            () -> assertThat(clean.responseCode()).isEqualTo(code),
            () -> assertThat(dirt.responseCode()).isEqualTo(code)
        );

    }


    @ParameterizedTest
    @ValueSource(ints = {0x0000, 0xFFFF})
    void testEquals(int value) {
        QRFlag flag = QRFlag.fromInt(value);
        QRFlag same = QRFlag.fromInt(value);
        QRFlag other = QRFlag.fromInt(0xCCCC);

        assertAll(
            () -> assertThat(flag).isEqualTo(flag),
            () -> assertThat(flag).isEqualTo(same),
            () -> assertThat(flag).isNotEqualTo(other),
            () -> assertThat(flag).isNotEqualTo(null)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0x0000, 0xFFFF})
    void testHashCode(int value) {
        QRFlag flag = QRFlag.fromInt(value);
        QRFlag same = QRFlag.fromInt(value);
        QRFlag other = QRFlag.fromInt(0xCCCC);

        assertAll(
            () -> assertThat(flag.hashCode()).isEqualTo(flag.hashCode()),
            () -> assertThat(flag.hashCode()).isEqualTo(same.hashCode()),
            () -> assertThat(flag.hashCode()).isNotEqualTo(other.hashCode())
        );
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
        assertThat(flag.opCode()).isEqualTo((byte) 3);
    }

    @Test
    void testToString() {
        QRFlag clean = QRFlag.fromInt(0x0000);
        QRFlag dirt = QRFlag.fromInt(0xFFFF);

        assertThat(clean.toString())
            .contains("Opcode=0")
            .contains("Rcode=0")
            .contains("Query")
            .doesNotContain("AA")
            .doesNotContain("RA")
            .doesNotContain("RD");

        assertThat(dirt.toString())
            .contains("Opcode=15")
            .contains("Rcode=15")
            .contains("Response")
            .contains("Truncated")
            .contains("AA")
            .contains("RA")
            .contains("RD");
    }

}
