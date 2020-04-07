package org.atlanmod.salut.domains;

import org.atlanmod.salut.labels.Label;
import org.atlanmod.salut.labels.Labels;

/**
 * Singleton representing the local domain.
 */
public class LocalDomain implements Domain {

    private final Label label;

    private LocalDomain(Label label) {
        this.label = label;
    }

    /**
     * Returns the instance of this class.
     *
     * @return the instance of this class
     */
    public static LocalDomain getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public Labels toLabels() {
        return Labels.fromList(label);
    }

    @Override
    public String toString() {
        return label.toString();
    }

    private static final class Holder {

        /**
         * The instance of the outer class.
         */
        static final LocalDomain INSTANCE = new LocalDomain(LOCAL_LABEL);
    }
}
