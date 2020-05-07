package org.atlanmod.salut.names;

import java.text.ParseException;
import java.util.Objects;
import org.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.labels.Labels;

/**
 * From [RFC 6763](https://tools.ietf.org/html/rfc6763):
 * <p>
 * PTR records are accordingly named just like SRV records but without the instance name:
 * <p>
 * <Service Name>.<Domain> Example: _printer._tcp.local.
 */
public class PointerName implements Name {

    private final ServiceName service;
    private final Domain domain;

    private PointerName(ServiceName service, Domain domain) {
        this.service = service;
        this.domain = domain;
    }

    public ServiceName service() {
        return service;
    }

    public static PointerName fromLabels(Labels labels) throws ParseException {
        ServiceName service = ServiceName.fromLabels(labels.subArray(0,2));
        Domain domain = DomainBuilder.fromLabels(labels.subArray(2,3));

        return new PointerName(service, domain);
    }

    @VisibleForTesting
    public static PointerName create(ServiceName service, Domain domain) {
        return new PointerName(service, domain);
    }

    @Override
    public String toString() {
        return service.toString() + "." + domain.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PointerName)) {
            return false;
        }

        PointerName that = (PointerName) other;
        return service.equals(that.service) &&
            domain.equals(that.domain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(service, domain);
    }

    @Override
    public Labels toLabels() {
        return Labels.create()
            .addAll(service.toLabels()
            .addAll(domain.toLabels()));
    }
}
