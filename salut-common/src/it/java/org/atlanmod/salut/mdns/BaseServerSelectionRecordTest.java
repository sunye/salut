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

import java.text.ParseException;

import static com.google.common.truth.Truth.assertThat;

class BaseServerSelectionRecordTest {

    private ByteArrayWriter writer;

    @BeforeEach
    void setUp() {
        writer = new ByteArrayWriter();
    }

    @Test
    void serialize_unserialize_svr_record() throws ParseException {
        Labels labels = Labels.fromList("aaa", "bbb");
        QClass qclass = QClass.IN;
        UnsignedInt ttl = UnsignedInt.fromInt(160);
        UnsignedShort priority = UnsignedShort.fromInt(10);
        UnsignedShort weight = UnsignedShort.fromInt(75);
        UnsignedShort port = UnsignedShort.fromInt(8080);

        ServiceInstanceName instance = ServiceInstanceName.parseString("My wifes library.daap.tcp.mac-donalds.local");
        Domain server = DomainBuilder.fromLabels(Labels.fromList("mac-donalds","local"));

        BaseServerSelectionRecord expected = BaseServerSelectionRecord.create(
            qclass,
            ttl,
            priority,
            weight,
            port,
            server,
            instance
        );

        expected.writeOn(writer);
        ByteArrayReader reader = writer.getByteArrayReader();
        AbstractRecord actual = AbstractRecord.fromByteBuffer(reader);

        assertThat(actual).isEqualTo(expected);

    }
}
