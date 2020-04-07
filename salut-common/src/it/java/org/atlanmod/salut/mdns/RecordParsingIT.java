package org.atlanmod.salut.mdns;

import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.io.UnsignedShort;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.names.ServiceInstanceName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;

import static com.google.common.truth.Truth.*;

/**
 * Integration tests for record parsing and writing.
 *
 */
public class RecordParsingIT {

    private ByteArrayWriter writer;

    @BeforeEach
    void setup() {
        writer = new ByteArrayWriter();
    }

    @Test
    void testARecordReadWrite() throws ParseException, UnknownHostException {

        Labels names = Labels.fromList("MacBook", "local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{72, 16, 8, 4});
        Domain domaine = DomainBuilder.fromLabels(names);
        BaseARecord record = BaseARecord
            .createRecord(QClass.IN, UnsignedInt.fromInt(10), address, domaine);

        record.writeOn(writer);
        ByteArrayReader reader = writer.getByteArrayReader();
        AbstractRecord readRecord = AbstractRecord.fromByteBuffer(reader);

        assertThat(record).isEqualTo(readRecord);
    }

    @Test
    void testSRVRecordReadWrite() throws ParseException {

        BaseServerSelectionRecord record =
        BaseServerSelectionRecord
            .create(QClass.IN,
                UnsignedInt.fromInt(500), UnsignedShort.fromInt(10), UnsignedShort.fromInt(33),
                UnsignedShort.fromInt(80),
                DomainBuilder.fromLabels(Labels.fromList("mac", "local")),
                ServiceInstanceName.fromLabels(Labels.fromList("music", "raop", "tcp", "mac", "local"))
            );

        record.writeOne(writer);

        ByteArrayReader reader = writer.getByteArrayReader();
        AbstractRecord readRecord = AbstractRecord.fromByteBuffer(reader);

        assertThat(record).isEqualTo(readRecord);
    }
}
