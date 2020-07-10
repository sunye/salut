package org.atlanmod.salut.data;

import java.util.Objects;

public class KnownApplicationProtocol implements ApplicationProtocol {

    /**
     * Application protocol.
     * <p>
     * If the protocol is unknown, creates a new one with the name.
     */
    private IANAApplicationProtocol application;

    public KnownApplicationProtocol(IANAApplicationProtocol application) {
        this.application = application;
    }

    @Override
    public boolean equals(Object other) {
        //@formatter:off
        if (this == other) {return true;}
        if (!(other instanceof KnownApplicationProtocol)) {return false;}
        //@formatter:on

        KnownApplicationProtocol that = (KnownApplicationProtocol) other;
        return application == that.application;
    }

    @Override
    public int hashCode() {
        return Objects.hash(application);
    }

    @Override
    public String toString() {
        return application.toString();
    }

    @Override
    public String name() {
        return application.name();
    }
}
