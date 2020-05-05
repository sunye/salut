package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.labels.Labels;

import java.text.ParseException;

public interface RecordParser<T extends Record> {
    T parse(Labels name, ByteArrayReader buffer) throws ParseException;
}
