package org.atlanmod.salut.names;

import java.text.ParseException;
import java.util.Objects;
import javax.annotation.ParametersAreNonnullByDefault;
import org.atlanmod.commons.Preconditions;
import org.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.data.ApplicationProtocol;
import org.atlanmod.salut.data.ApplicationProtocolBuilder;
import org.atlanmod.salut.data.TransportProtocol;
import org.atlanmod.salut.labels.Labels;

/**
 * From [RFC 6763](https://tools.ietf.org/html/rfc6763):
 * <p>
 * "The <Service> portion of the Service Instance Label consists of a pair of DNS labels,
 * following the convention already established for SRV records [RFC2782]. The first label of the
 * pair is an underscore character followed by the Service Name [RFC6335].  The Service Name
 * identifies what the service does and what application protocol it uses to do it. The second label
 * is either "_tcp" (for application protocols that run over TCP) or "_udp" (for all others).  For
 * more details, see Section 7, "Service Names"."
 * <p>
 * The record contains just one piece of information, the name of the service instance (which is the
 * same as the name of the SRV record).
 */
@ParametersAreNonnullByDefault
public class ServiceName implements Name {

    private final ApplicationProtocol application;
    private final TransportProtocol transport;

    public ServiceName(ApplicationProtocol applicationProtocol,
        TransportProtocol transportProtocol) {
        Preconditions.checkNotNull(applicationProtocol);
        Preconditions.checkNotNull(transportProtocol);

        this.application = applicationProtocol;
        this.transport = transportProtocol;
    }

    /**
     * Creates an instance of {@code ServiceName} from a Label array.
     *
     * @param names an array of Labels
     * @return an instance of ServiceName corresponding to the input
     */
    public static ServiceName fromLabels(Labels names) {
        Log.info("Parsing Service Name: {0}", names);
        Preconditions.checkArgument(names.size() == 2, "Expecting 2 names, found " + names.size());

        ApplicationProtocol type = ApplicationProtocolBuilder.fromLabel(names.get(0));
        TransportProtocol transport = TransportProtocol.fromLabel(names.get(1));

        return new ServiceName(type, transport);
    }

    @VisibleForTesting
    public static ServiceName fromStrings(String application, String transport) {
        return new ServiceName(ApplicationProtocolBuilder.fromString(application),
            TransportProtocol.fromString(transport));
    }

    @VisibleForTesting
    public static ServiceName create(ApplicationProtocol application, TransportProtocol transport) {
        return new ServiceName(application, transport);
    }

    public static ServiceName parseString(String dottedString) throws ParseException {
        String[] labels = dottedString.split("\\.");
        if (labels.length != 2) {
            throw new ParseException("Service Names must have 2 labels", 0);
        }

        return new ServiceName(ApplicationProtocolBuilder.fromString(labels[0]),
            TransportProtocol.fromString(labels[1]));
    }

    public ApplicationProtocol getApplication() {
        return application;
    }

    public TransportProtocol getTransport() {
        return transport;
    }

    @Override
    public String toString() {
        return application + "." + transport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceName)) {
            return false;
        }
        ServiceName that = (ServiceName) o;
        return application.equals(that.application) &&
            transport == that.transport;
    }

    @Override
    public int hashCode() {
        return Objects.hash(application, transport);
    }

    @Override
    public Labels toLabels() {
        return Labels.fromList(application.name(), transport.name());
    }
}
