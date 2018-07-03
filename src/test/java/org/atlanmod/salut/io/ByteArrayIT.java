package org.atlanmod.salut.io;

import org.atlanmod.salut.mdns.QClass;
import org.atlanmod.salut.mdns.RecordType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for ByteArrayReader and ByteArrayWriter
 */
public class ByteArrayIT {

    private ByteArrayWriter writer;

    @BeforeEach
    void setup() {
        writer = new ByteArrayWriter();
    }

    @Test
    void testInet4AddressReadWrite() throws UnknownHostException, ParseException {
        Inet4Address address = (Inet4Address) Inet4Address.getByAddress(new byte[]{1,1,1,1});
        writer.putInet4Address(address);
        ByteArrayReader reader = writer.getByteArrayReader();
        Inet4Address readAddress = reader.readInet4Address();

        assertThat(address).isEqualTo(readAddress);
    }

    @Test
    void testRecordTypeReadWrite() throws ParseException {
        RecordType type = RecordType.ALL;
        writer.putRecordType(type);
        ByteArrayReader reader = writer.getByteArrayReader();
        RecordType readType= reader.readRecordType();

        assertThat(type).isEqualTo(readType);
    }

    @Test
    void testQClassReadWrite() throws ParseException {
        QClass qClass = QClass.Any;
        writer.putQClass(qClass);
        ByteArrayReader reader = writer.getByteArrayReader();
        QClass readQClass= reader.readQClass();

        assertThat(qClass).isEqualTo(readQClass);
    }


}
