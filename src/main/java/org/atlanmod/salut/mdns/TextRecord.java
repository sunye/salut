package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.UnsignedInt;

import java.text.ParseException;
import java.util.List;

public class TextRecord extends NormalRecord {
    private List<String> labels;

    private TextRecord(NameArray name, QClass qclass, UnsignedInt ttl, List<String> labels) {
        super(name, qclass, ttl);
        this.labels = labels;
    }

    public static RecordParser<TextRecord> parser() {
        return new TextRecordParser();
    }

    private static class TextRecordParser extends NormalRecordParser<TextRecord> {

        private List<String> labels;

        protected void parseVariablePart(ByteArrayReader buffer) throws ParseException {
            labels = buffer.readTextStrings(dataLength);
        }

        @Override
        protected TextRecord build() {
            return new TextRecord(name, qclass, ttl, labels);
        }
    }
}
