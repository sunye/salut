package org.atlanmod.salut.record;

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
    void setUp() {
        writer = new ByteArrayWriter();
    }


    @Test
    void serialize_unserialize_ptr_record() throws ParseException {
        ServiceInstanceName instance = ServiceInstanceName.parseString("My wifes library.daap.tcp.macintosh.local");
        PointerName server = PointerName.fromLabels(Labels.fromList("daap", "tcp", "local"));


        BasePointerRecord expected = BasePointerRecord.create(
            QClass.IN,
            UnsignedInt.fromInt(160),
            instance,
            server);

        expected.writeOn(writer);
        ByteArrayReader reader = writer.getByteArrayReader();
        AbstractRecord actual = AbstractRecord.fromByteBuffer(reader);

        assertThat(actual).isEqualTo(expected);

    }

}
