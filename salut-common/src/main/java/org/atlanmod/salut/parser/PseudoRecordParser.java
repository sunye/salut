package org.atlanmod.salut.parser;

import java.text.ParseException;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.UnsignedByte;
import org.atlanmod.salut.io.UnsignedShort;
import org.atlanmod.salut.labels.Labels;
import org.atlanmod.salut.record.PseudoRecord;

public class PseudoRecordParser implements Parser<PseudoRecord> {

    @Override
    public PseudoRecord parse(Labels name, ByteArrayReader buffer) throws ParseException {
        UnsignedShort optionCode = buffer.getUnsignedShort();
        UnsignedShort payload = buffer.getUnsignedShort();
        UnsignedByte extendedRCode = buffer.getUnsignedByte();
        UnsignedByte version = buffer.getUnsignedByte();
        UnsignedShort doz = buffer.getUnsignedShort();
        UnsignedShort rdlen = buffer.getUnsignedShort();

        for (int i = 0; i < rdlen.intValue(); i++) {
            buffer.getUnsignedByte();
        }

        return new PseudoRecord(payload, extendedRCode, version, rdlen);
    }
}
