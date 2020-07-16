package org.atlanmod.salut.parser;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import org.atlanmod.commons.annotation.Singleton;
import org.atlanmod.commons.annotation.Static;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.record.WritableRecord;
import org.atlanmod.salut.record.RecordType;

/**
 * The `ParserFactory` class creates instances of {@link Parser} and stores them in a map.
 */
@Singleton
public class ParserFactory {
    /**
     * Null parser, used to skip unsupported QTypes.
     */
    private static final Parser NULL_PARSER = new NullRecordParser();

    /**
     * Map containing all available record parsers.
     */
    private final Map<RecordType, Parser> parsers = new HashMap<>();


    /**
     * Populates the map containing all available parsers.
     * Each parser is associated to a record getType.
     * Record types that are not in this map will be ignored.
     */
    private ParserFactory() {
        parsers.put(RecordType.A, new ARecordParser());
        parsers.put(RecordType.AAAA, new AAAARecordParser());
        parsers.put(RecordType.PTR, new PointerRecordParser());
        parsers.put(RecordType.OPT, new PseudoRecordParser());
        parsers.put(RecordType.SRV, new ServiceRecordParser());
        parsers.put(RecordType.TXT, new TextRecordParser());
    }

    /**
     * Returns the instance of this class.
     *
     * @return the instance of this class
     */
    @Nonnull
    public static ParserFactory getInstance() {
        return Holder.INSTANCE;
    }

    public Parser<WritableRecord> getParser(RecordType type) {
        if (parsers.containsKey(type)) {
            return parsers.get(type);
        } else {
            Log.warn("Ignoring Record of type {0}.", type);
            return NULL_PARSER;
        }
    }

    /**
     * The initialization-on-demand holder of the singleton of this class.
     */
    @Static
    private static final class Holder {

        /**
         * The instance of the outer class.
         */
        private static final ParserFactory INSTANCE = new ParserFactory();
    }
}
