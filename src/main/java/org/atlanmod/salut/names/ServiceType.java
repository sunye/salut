package org.atlanmod.salut.names;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class ServiceType {

    public static ServiceType fromApplicationProtocol(ApplicationProtocol protocol) {
        return new KnownServiceType(protocol);
    }

    public static ServiceType fromString(@Nonnull String str) {
        ApplicationProtocol application = ApplicationProtocol.fromString(str);
        if (application == ApplicationProtocol.unknown) {
            return new UnknownServiceType(str);
        } else {
            return new KnownServiceType(application);
        }
    }
}

class UnknownServiceType extends ServiceType {

    /**
     * The name of the unknown application protocol.
     */
    private String name;

    public UnknownServiceType(@Nonnull String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnknownServiceType that = (UnknownServiceType) o;
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

class KnownServiceType extends ServiceType {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KnownServiceType that = (KnownServiceType) o;
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