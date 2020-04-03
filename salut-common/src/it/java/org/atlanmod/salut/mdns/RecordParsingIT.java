package org.atlanmod.salut.mdns;

import org.atlanmod.salut.data.Domain;
import org.atlanmod.salut.data.DomainBuilder;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.io.UnsignedShort;
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

        LabelArray names = LabelArray.fromList("MacBook", "local");
        Inet4Address address = (Inet4Address) InetAddress.getByAddress(new byte[]{72, 16, 8, 4});
        Domain domaine = DomainBuilder.fromLabels(names);
        BaseARecord record = BaseARecord
            .createRecord(names, QClass.IN, UnsignedInt.fromInt(10), address, domaine);

        record.writeOn(writer);
        ByteArrayReader reader = writer.getByteArrayReader();
        AbstractRecord readRecord = AbstractRecord.fromByteBuffer(reader);

        assertThat(record).isEqualTo(readRecord);
    }

    @Test
    void testSRVRecordReadWrite() throws ParseException {

        BaseServerSelectionRecord record =
        BaseServerSelectionRecord
            .createRecord(LabelArray.fromList("music", "raop", "tcp", "mac", "local"), QClass.IN,
                UnsignedInt.fromInt(500), UnsignedShort.fromInt(10), UnsignedShort.fromInt(33),
                UnsignedShort.fromInt(80), LabelArray.fromList("mac", "local") ,
                null, null);

        record.writeOne(writer);

        ByteArrayReader reader = writer.getByteArrayReader();
        AbstractRecord readRecord = AbstractRecord.fromByteBuffer(reader);

        assertThat(record).isEqualTo(readRecord);
    }
}
