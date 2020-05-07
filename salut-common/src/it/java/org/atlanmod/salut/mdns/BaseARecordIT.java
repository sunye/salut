package org.atlanmod.salut.mdns;

import static com.google.common.truth.Truth.assertThat;

import java.text.ParseException;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.domains.Host;
import org.atlanmod.salut.domains.IPAddressBuilder;
import org.atlanmod.salut.domains.IPv4Address;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BaseARecordIT {

    private ByteArrayWriter writer;

    @BeforeEach
    void setUp() {
        writer = new ByteArrayWriter();
    }

    @Test
    void testARecordReadWrite() throws ParseException {

        Labels names = Labels.fromList("MacBook", "local");
        IPv4Address address = IPAddressBuilder.createIPv4Address(new byte[]{72, 16, 8, 4});
        Domain domain = DomainBuilder.fromLabels(names);
        Host host = new Host(domain, address);

        BaseARecord record = BaseARecord
            .createRecord(QClass.IN, UnsignedInt.fromInt(10), host);

        record.writeOn(writer);
        ByteArrayReader reader = writer.getByteArrayReader();
        AbstractRecord readRecord = AbstractRecord.fromByteBuffer(reader);

        assertThat(record).isEqualTo(readRecord);
    }

}
