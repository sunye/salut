package org.atlanmod.salut.data;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class AbstractServiceType {

    public static AbstractServiceType fromApplicationProtocol(ApplicationProtocol protocol) {
        return new KnownServiceType(protocol);
    }

    public static AbstractServiceType fromString(@Nonnull String str) {
        ApplicationProtocol application = ApplicationProtocol.fromString(str);
        if (application == ApplicationProtocol.unknown) {
            return new UnknownServiceType(str);
        } else {
            return new KnownServiceType(application);
        }
    }
}

class UnknownServiceType extends AbstractServiceType {

    /**
     * The name of the unknown application protocol.
     */
    private String name;

    public UnknownServiceType(@Nonnull String name) {
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
        UnknownServiceType that = (UnknownServiceType) other;
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
}

class KnownServiceType extends AbstractServiceType {
    /**
     * Application protocol.
     * <p>
     * If the protocol is unknown, creates a new one with the name.
     */
    private ApplicationProtocol application;

    public KnownServiceType(ApplicationProtocol application) {
        this.application = application;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        KnownServiceType that = (KnownServiceType) other;
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
}