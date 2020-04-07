package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.io.UnsignedInt;

import java.text.ParseException;
import org.atlanmod.salut.labels.Labels;

/**
 *
 *
 */
public class NullRecord extends AbstractRecord {


    public NullRecord(Labels name) {
        super(name);
    }

    @Override
    public String toString() {
        return "NullRecord{" +
                "data=" + labels +
                '}';
    }

    public static RecordParser<NullRecord> parser() {
        return new NullRecordParser();
    }

    @Override
    public void writeOne(ByteArrayWriter writer) {
        // TODO
        throw new UnsupportedOperationException();
    }

    private static class NullRecordParser implements RecordParser<NullRecord> {

        @Override
        public NullRecord parse(Labels name, ByteArrayReader buffer) throws ParseException {
            QClass qclass = QClass.fromByteBuffer(buffer);
            UnsignedInt ttl = buffer.getUnsignedInt();
            int dataLength = buffer.getUnsignedShort().intValue();
            for (int i = 0; i < dataLength; i++) {
                buffer.getUnsignedByte(); // RDATA
            }

            return new NullRecord(name);
        }
    }
}
