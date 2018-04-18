package org.atlanmod.salut.mdns;

import org.atlanmod.salut.io.ByteArrayBuffer;

import java.text.ParseException;

public interface RecordParser<T extends Record> {
    T parse(NameArray name, ByteArrayBuffer buffer) throws ParseException;
}
