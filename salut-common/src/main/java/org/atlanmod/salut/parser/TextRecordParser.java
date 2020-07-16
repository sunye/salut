package org.atlanmod.salut.parser;

import java.text.ParseException;
import java.util.List;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.record.TextRecord;

public class TextRecordParser extends NormalRecordParser<TextRecord> {

    private List<String> properties;


    @Override
    protected void parseVariablePart(ByteArrayReader buffer) throws ParseException {
        Log.info("Data length = {0}", dataLength);
        properties = buffer.readTextDataStrings(dataLength);
    }

    @Override
    protected TextRecord build() {
        return new TextRecord(labels, qclass, ttl, properties);
    }
}
