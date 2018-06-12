package org.atlanmod.salut.data;

import fr.inria.atlanmod.commons.annotation.VisibleForTesting;

import javax.annotation.Nonnull;
import java.text.ParseException;
import java.util.Objects;

public class ServiceType {
    private ApplicationProtocol applicationProtocol;
    private TransportProtocol transportProtocol;

    public ServiceType(ApplicationProtocol applicationProtocol, TransportProtocol transportProtocol) {
        this.applicationProtocol = applicationProtocol;
        this.transportProtocol = transportProtocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceType that = (ServiceType) o;
        return Objects.equals(applicationProtocol, that.applicationProtocol) &&
                transportProtocol == that.transportProtocol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationProtocol, transportProtocol);
    }

    @VisibleForTesting
    public static ServiceType fromStrings(@Nonnull String applicationName, @Nonnull String transportName) {
        ApplicationProtocol ap = ApplicationProtocolBuilder.fromString(applicationName);
        TransportProtocol tp = TransportProtocol.fromString(transportName);

        return new ServiceType(ap, tp);
    }
}
