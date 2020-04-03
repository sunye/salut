package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.ByteArrayReader;

import java.text.ParseException;

public interface RecordParser<T extends Record> {
    T parse(LabelArray name, ByteArrayReader buffer) throws ParseException;
}
