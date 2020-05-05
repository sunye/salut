package org.atlanmod.salut.names;

import org.atlanmod.commons.Preconditions;
import org.atlanmod.commons.annotation.VisibleForTesting;
import org.atlanmod.commons.log.Log;
import org.atlanmod.salut.domains.Domain;
import org.atlanmod.salut.domains.DomainBuilder;
import org.atlanmod.salut.labels.DNSLabel;
import org.atlanmod.salut.labels.Labels;

import java.text.ParseException;

/**
 * Service Instance Name = <Instance> . <Service> . <Domain>
 * <p>
 * Domain data
 * used by DNS-SD take the following forms:
 *
 * <Instance> . <sn>._tcp . <servicedomain> . <parentdomain>.
 */
public class ServiceInstanceName implements Name {

    private InstanceName instanceName;
    private ServiceName serviceName;
    private Domain domain;

    public ServiceInstanceName(InstanceName instanceName, ServiceName serviceName, Domain domain) {
        this.instanceName = instanceName;
        this.serviceName  = serviceName;
        this.domain = domain;
    }


    public InstanceName instance() {
        return instanceName;
    }

    public ServiceName service() {
        return serviceName;
    }

    public Domain domain() {
        return this.domain;
    }


    /**
     * 4.3.  Internal Handling of Names
     * <p>
     * > "If client software takes the <Instance>, <Service>, and <Domain>
     * portions of a Service Instance Label and internally concatenates them
     * together into a single string, then because the <Instance> portion is
     * allowed to contain any characters, including dots, appropriate
     * precautions MUST be taken to ensure that DNS label boundaries are
     * properly preserved.  Client software can do this in a variety of
     * ways, such as character escaping.
     * <p>
     * This document RECOMMENDS that if concatenating the three portions of
     * a Service Instance Label, any dots in the <Instance> portion be
     * escaped following the customary DNS convention for text files: by
     * preceding literal dots with a backslash (so "." becomes "\.").
     * Likewise, any backslashes in the <Instance> portion should also be
     * escaped by preceding them with a backslash (so "\" becomes "\\")."
     * <p>
     * Having done this, the three components of the name may be safely
     * concatenated.  The backslash-escaping allows literal dots in the name
     * (escaped) to be distinguished from label-separator dots (not
     * escaped), and the resulting concatenated string may be safely passed
     * to standard DNS APIs like res_query(), which will interpret the
     * backslash-escaped string as intended.
     *
     * @return A String concatenating the three parts of a service instance name.
     */
    @Override
    public String toString() {
        return "{" + instanceName +
                ", " + serviceName +
                ", " + domain +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceInstanceName that = (ServiceInstanceName) o;
        return instanceName.equals(that.instanceName) &&
            serviceName.equals(that.serviceName) &&
            domain.equals(that.domain);

    }

    @Override
    public int hashCode() {
        int result = instanceName != null ? instanceName.hashCode() : 0;
        result = 31 * result + (serviceName != null ? serviceName.hashCode() : 0);
        result = 31 * result + (domain != null ? domain.hashCode() : 0);
        return result;
    }

    public static ServiceInstanceName fromLabels(Labels labels) throws ParseException {
        Log.info("Parsing Service Instance Name: {0}", labels);
        Preconditions.checkArgument(labels.size() >= 4, "Expecting at least 4 labels, found " + labels.size());

        if (labels.contains(DNSLabel.create("_sub"))) {
            Log.warn("Service subtype !!! : {0}", labels);
        }

        InstanceName instanceName = InstanceName.fromLabel(labels.get(0));
        ServiceName serviceName = ServiceName.fromLabels(labels.subArray(1,3));
        Domain host = DomainBuilder.fromLabels(labels.subArray(3, labels.size()));

        return new ServiceInstanceName(instanceName, serviceName, host);
    }

    @VisibleForTesting
    public static ServiceInstanceName createServiceInstanceName(InstanceName instanceName, ServiceName serviceName,
                                                        Domain host) {
        return new ServiceInstanceName(instanceName, serviceName, host);
    }

    public static ServiceInstanceName parseString(String str) throws ParseException {
        String[] labels = str.split("\\.");
        if (labels.length != 5) {
            throw new ParseException("Service Instance Names must have 5 labels", 0);
        }

        return new ServiceInstanceName(InstanceName.fromString(labels[0]),
                ServiceName.fromLabels(Labels.fromList(labels[1], labels[2])),
                DomainBuilder.fromLabels(Labels.fromList(labels[3], labels[4])));
    }

    @Override
    public Labels toLabels() {
        return Labels.create()
            .addAll(instanceName.toLabels())
            .addAll(serviceName.toLabels()
            .addAll(domain.toLabels()));
    }
}
