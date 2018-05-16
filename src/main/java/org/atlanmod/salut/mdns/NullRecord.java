package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.ByteArrayBuffer;
import org.atlanmod.salut.io.UnsignedInt;

import java.text.ParseException;

/**
 *
 *
 */
public class NullRecord extends AbstractRecord {


    public NullRecord(NameArray name) {
        super(name);
    }

    @Override
    public String toString() {
        return "NullRecord{" +
                "data=" + names +
                '}';
    }

    public static RecordParser<NullRecord> parser() {
        return new NullRecordParser();
    }

    private static class NullRecordParser implements RecordParser<NullRecord> {

        @Override
        public NullRecord parse(NameArray name, ByteArrayBuffer buffer) throws ParseException {
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
