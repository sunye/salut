package org.atlanmod.salut.mdns;

import org.atlanmod.salut.domains.*;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;
import java.text.ParseException;

import static com.google.common.truth.Truth.assertThat;

class BaseARecordIT {

    private ByteArrayWriter writer;

    @BeforeEach
    void setUp() {
        writer = new ByteArrayWriter();
    }

    @Test
    void testARecordReadWrite() throws ParseException, UnknownHostException {

        Labels names = Labels.fromList("MacBook", "local");
        IPv4Address address = IPAddressBuilder.createIPv4Address(new byte[]{72, 16, 8, 4});
        Domain domain = DomainBuilder.fromLabels(names);
        Host host = new Host (domain, address);


        BaseARecord record = BaseARecord
            .createRecord(QClass.IN, UnsignedInt.fromInt(10), host);

        record.writeOn(writer);
        ByteArrayReader reader = writer.getByteArrayReader();
        AbstractRecord readRecord = AbstractRecord.fromByteBuffer(reader);

        assertThat(record).isEqualTo(readRecord);
    }

}
