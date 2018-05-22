package org.atlanmod.salut.mdns;

import fr.inria.atlanmod.commons.annotation.Singleton;
import fr.inria.atlanmod.commons.annotation.Static;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * The `ParserFactory` class creates instances of {@link RecordParser} and stores them in a map.
 */
@Singleton
public class ParserFactory {
    /**
     * Null parser, used to skip unsupported QTypes.
     */
    private static final RecordParser<NullRecord> NULL_PARSER = NullRecord.parser();

    /**
     * Map containing all available record parsers.
     */
    private final Map<RecordType, RecordParser> parsers = new HashMap<>();


    /**
     * Populates the map containing all available parsers.
     * Each parser is associated to a record type.
     * Record types that are not in this map will be ignored.
     */
    private ParserFactory() {
        parsers.put(RecordType.A, ARecord.parser());
        parsers.put(RecordType.AAAA, AAAARecord.parser());
        parsers.put(RecordType.PTR, PointerRecord.parser());
        parsers.put(RecordType.OPT, PseudoRecord.parser());
        parsers.put(RecordType.SRV, ServerSelectionRecord.parser());
        parsers.put(RecordType.TXT, TextRecord.parser());
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

    public RecordParser<AbstractRecord> getParser(RecordType type) {
        return parsers.getOrDefault(type, NULL_PARSER);
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
