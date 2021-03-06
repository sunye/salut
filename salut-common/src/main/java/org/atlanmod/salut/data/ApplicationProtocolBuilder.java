package org.atlanmod.salut.data;

import java.util.Objects;
import javax.annotation.Nonnull;
import org.atlanmod.salut.labels.Label;

public class ApplicationProtocolBuilder {

    private ApplicationProtocolBuilder() {}

    public static ApplicationProtocol fromApplicationProtocol(IANAApplicationProtocol protocol) {
        return new KnownApplicationProtocol(protocol);
    }

    public static ApplicationProtocol fromString(@Nonnull String str) {
        IANAApplicationProtocol application = IANAApplicationProtocol.fromString(str);
        if (application == IANAApplicationProtocol.unknown) {
            return new UnknownApplicationProtocol(str);
        } else {
            return new KnownApplicationProtocol(application);
        }
    }

    public static ApplicationProtocol fromLabel(@Nonnull Label label) {
        return fromString(label.toString());
    }
}

class UnknownApplicationProtocol implements ApplicationProtocol {

    /**
     * The name of the unknown application protocol.
     */
    private String name;

    public UnknownApplicationProtocol(@Nonnull String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        UnknownApplicationProtocol that = (UnknownApplicationProtocol) other;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String name() {
        return name;
    }
}

