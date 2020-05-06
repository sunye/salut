package org.atlanmod.salut.mdns;


import static org.assertj.core.api.Assertions.assertThat;

import java.net.UnknownHostException;
import java.text.ParseException;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.domains.IPAddressBuilder;
import org.atlanmod.salut.domains.IPv6Address;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;
import org.atlanmod.salut.labels.Labels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AAAARecordIT {

    private ByteArrayWriter writer;

    @BeforeEach
    void setUp() {
        writer = new ByteArrayWriter();
    }

    @DisplayName("Given a AAAARecord"
        + "When it is serialized and written to a byte array"
        + "The expect the read and deserialized AAAARecord to be the same")
    @Test
    void serialize_and_deserialize_simple_aaaa_record()
        throws ParseException, UnknownHostException {

        byte[] bytes = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

        Labels names = Labels.fromList("mac-pro", "local");
        IPv6Address address = IPAddressBuilder.createIPv6Address(bytes);

        Domain domain = DomainBuilder.fromLabels(names);
        AAAARecord expected = AAAARecord
            .createRecord(QClass.IN, UnsignedInt.fromInt(10), address, domain);

        expected.writeOn(writer);
        ByteArrayReader reader = writer.getByteArrayReader();
        AbstractRecord actual = AbstractRecord.fromByteBuffer(reader);

        assertThat(expected).isEqualTo(actual);
    }

}
