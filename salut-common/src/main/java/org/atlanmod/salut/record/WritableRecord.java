package org.atlanmod.salut.record;

import java.text.ParseException;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.io.ByteArrayReader;
import org.atlanmod.salut.io.ByteArrayWriter;
import org.atlanmod.salut.labels.Labels;

public abstract class WritableRecord implements Record {

    public abstract void writeOn(ByteArrayWriter writer);

}
