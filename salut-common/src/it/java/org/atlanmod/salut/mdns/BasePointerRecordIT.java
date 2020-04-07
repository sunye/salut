package org.atlanmod.salut.mdns;

import static com.google.common.truth.Truth.assertThat;

import java.text.ParseException;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.names.PointerName;
import org.atlanmod.salut.names.ServiceInstanceName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BasePointerRecordIT {

    private ByteArrayWriter writer;

    @BeforeEach
    void setup() {
        writer = new ByteArrayWriter();
    }


    @Test
    void test() throws ParseException {
        Labels labels = Labels.fromList("aaa", "bbb");

        ServiceInstanceName instance = ServiceInstanceName.parseString("My wifes library.daap.tcp.macintosh.local");
        PointerName server = PointerName.fromLabels(Labels.fromList("daap", "tcp", "local"));


        BasePointerRecord ptr = new BasePointerRecord(labels,
            QClass.IN,
            UnsignedInt.fromInt(160),
            instance,
            server
            );

        BasePointerRecord expected = BasePointerRecord.create(labels,
            QClass.IN,
            UnsignedInt.fromInt(160),
            instance,
            server);

        ptr.writeOne(writer);
        ByteArrayReader reader = writer.getByteArrayReader();
        AbstractRecord actual = AbstractRecord.fromByteBuffer(reader);

        assertThat(actual).isEqualTo(expected);


    }

}