package org.atlanmod.salut.parser;

import java.text.ParseException;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.record.Record;

public interface Parser<T extends Record> {
    T parse(Labels name, ByteArrayReader buffer) throws ParseException;
}
