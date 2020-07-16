package org.atlanmod.salut.record;

import static com.google.common.truth.Truth.assertThat;

import java.text.ParseException;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.io.UnsignedShort;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.names.ServiceInstanceName;
import org.atlanmod.salut.parser.RecordParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for record parsing and writing.
 *
 */
public class RecordParsingIT {

    private ByteArrayWriter writer;

    @BeforeEach
    void setUp() {
        writer = new ByteArrayWriter();
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

        record.writeOn(writer);

        ByteArrayReader reader = writer.getByteArrayReader();
        Record readRecord = RecordParser.fromByteBuffer(reader);

        assertThat(record).isEqualTo(readRecord);
    }
}
