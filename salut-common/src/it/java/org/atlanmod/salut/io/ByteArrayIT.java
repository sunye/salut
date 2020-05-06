package org.atlanmod.salut.io;

import static com.google.common.truth.Truth.assertThat;

import java.text.ParseException;
import org.atlanmod.salut.domains.IPAddress;
import org.atlanmod.salut.domains.IPAddressBuilder;
import org.atlanmod.salut.domains.IPv4Address;
import org.atlanmod.salut.domains.IPv6Address;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.mdns.QClass;
import org.atlanmod.salut.mdns.RecordType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Integration tests for ByteArrayReader and ByteArrayWriter
 */
public class ByteArrayIT {

    private ByteArrayWriter writer;
    private ByteArrayReader reader;

    @BeforeEach
    void setUp() {
        writer = new ByteArrayWriter();
    }

    @DisplayName("Given a Inet4 address"
        + "When the address is written to a ByteArray"
        + "Then the same address is read from the ByteArray")
    @Test
    void testInet4AddressReadWrite() {
        IPv4Address address = IPAddressBuilder.createIPv4Address(new byte[]{1, 1, 1, 1});
        writer.writeIPv4Address(address);
        reader = writer.getByteArrayReader();
        IPv4Address readAddress = reader.readIPv4Address();

        assertThat(readAddress).isEqualTo(address);
    }

    @DisplayName("Given a Record Type"
        + "When the type is written to a ByteArray"
        + "Then the same type is read from the ByreArray")
    @Test
    void testRecordTypeReadWrite() throws ParseException {
        RecordType type = RecordType.ALL;
        writer.writeRecordType(type);
        ByteArrayReader reader = writer.getByteArrayReader();
        RecordType readType = reader.readRecordType();

        assertThat(readType).isEqualTo(type);
    }

    @DisplayName("Given a QClass"
        + "When the QClass is written to a ByteArray"
        + "Then the same QClass is read from the ByreArray")
    @Test
    void testQClassReadWrite() throws ParseException {
        QClass qClass = QClass.Any;
        writer.writeQClass(qClass);
        ByteArrayReader reader = writer.getByteArrayReader();
        QClass readQClass = reader.readQClass();

        assertThat(readQClass).isEqualTo(qClass);
    }


    @ParameterizedTest
    @ValueSource(shorts = {UnsignedByte.MIN_VALUE, UnsignedByte.MAX_VALUE, 42})
    void testUnsignedByte(short value) {
        UnsignedByte expected = UnsignedByte.fromShort(value);

        writer.writeUnsignedByte(expected);
        ByteArrayReader reader = writer.getByteArrayReader();
        UnsignedByte actual = reader.getUnsignedByte();

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(longs = {UnsignedInt.MIN_VALUE, UnsignedInt.MAX_VALUE, 2560})
    void testUnsignedInt(long value) {
        UnsignedInt expected = UnsignedInt.fromLong(value);

        writer.writeUnsignedInt(expected);
        reader = writer.getByteArrayReader();
        UnsignedInt actual = reader.getUnsignedInt();

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"pc-info", "John's music"})
    void testLabel(String value) throws ParseException {
        writer.writeLabel(value);

        reader = writer.getByteArrayReader();
        LabelLength length = reader.getLabelLength();
        String actual = reader.getString(length);

        assertThat(actual).isEqualTo(value);
    }

    @Test
    void testLabels() throws ParseException {
        Labels expected = Labels.fromList("John's music", "raop", "tcp", "pc-linux", "local");
        writer.writeLabels(expected);
        reader = writer.getByteArrayReader();
        Labels actual = reader.readLabels();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testIPAddressReadWrite() {
        IPAddress address = IPAddressBuilder.createIPv4Address(new byte[]{1, 1, 1, 1});
        writer.writeIPAddress(address);
        reader = writer.getByteArrayReader();
        IPv4Address readAddress = reader.readIPv4Address();

        assertThat(readAddress).isEqualTo(address);
    }

    @Test
    void testIPv6AddressReadWrite() {
        IPv6Address address = IPAddressBuilder
            .createIPv6Address(new byte[]{1, 1, 1, 1, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
        writer.writeIPv6Address(address);
        reader = writer.getByteArrayReader();
        IPv6Address readAddress = reader.readIPv6Address();

        assertThat(readAddress).isEqualTo(address);
    }
}
