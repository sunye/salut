package org.atlanmod.salut.record;

import java.text.ParseException;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.labels.Labels;

public interface RecordParser<T extends Record> {
    T parse(Labels name, ByteArrayReader buffer) throws ParseException;
}
