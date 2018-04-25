package org.atlanmod.salut;

import javax.annotation.ParametersAreNonnullByDefault;

public class QualifiedName {

    public final String domain;
    public final String protocol;
    public final String application;
    public final String instance;
    public final String subtype;

    @ParametersAreNonnullByDefault
    public QualifiedName(String domain, String protocol, String application, String instance, String subtype) {
        this.domain = domain;
        this.protocol = protocol;
        this.application = application;
        this.instance = instance;
        this.subtype = subtype;
    }

    public String getType() {
        return (application.length() > 0 ? "_" + application + "." : "") + (protocol.length() > 0 ? "_" + protocol + "." : "") + domain + ".";
    }


    public String getKey() {
        return  ((instance.length() > 0 ? instance + "." : "") + this.getType()).toLowerCase();
    }

    public String getQualifiedName() {
        return (instance.length() > 0 ? instance + "." : "") + (application.length() > 0 ? "_" + application + "." : "")
                + (protocol.length() > 0 ? "_" + protocol + "." : "") + domain + ".";
    }


    public static QualifiedName decodeQualifiedNameMapForType(String type) {
        int index;

        String casePreservedType = type;

        String aType = type.toLowerCase();
        String application = aType;
        String protocol = "";
        String subtype = "";
        String name = "";
        String domain = "";

        if (aType.contains("in-addr.arpa") || aType.contains("ip6.arpa")) {
            index = (aType.contains("in-addr.arpa") ? aType.indexOf("in-addr.arpa") : aType.indexOf("ip6.arpa"));
            name = removeSeparators(casePreservedType.substring(0, index));
            domain = casePreservedType.substring(index);
            application = "";
        } else if ((!aType.contains("_")) && aType.contains(".")) {
            index = aType.indexOf('.');
            name = removeSeparators(casePreservedType.substring(0, index));
            domain = removeSeparators(casePreservedType.substring(index));
            application = "";
        } else {
            // First remove the data if it there.
            if (!aType.startsWith("_") || aType.startsWith("_services")) {
                index = aType.indexOf("._");
                if (index > 0) {
                    // We need to preserve the case for the user readable data.
                    name = casePreservedType.substring(0, index);
                    if (index + 1 < aType.length()) {
                        aType = aType.substring(index + 1);
                        casePreservedType = casePreservedType.substring(index + 1);
                    }
                }
            }

            index = aType.lastIndexOf("._");
            if (index > 0) {
                int start = index + 2;
                int end = aType.indexOf('.', start);
                protocol = casePreservedType.substring(start, end);
            }
            if (protocol.length() > 0) {
                index = aType.indexOf("_" + protocol.toLowerCase() + ".");
                int start = index + protocol.length() + 2;
                int end = aType.length() - (aType.endsWith(".") ? 1 : 0);
                if (end > start) {
                    domain = casePreservedType.substring(start, end);
                }
                if (index > 0) {
                    application = casePreservedType.substring(0, index - 1);
                } else {
                    application = "";
                }
            }
            index = application.toLowerCase().indexOf("._sub");
            if (index > 0) {
                int start = index + 5;
                subtype = removeSeparators(application.substring(0, index));
                application = application.substring(start);
            }
        }

        return new QualifiedName(removeSeparators(domain), protocol, removeSeparators(application), name, subtype);
    }

    private static String removeSeparators(String name) {
        if (name == null) {
            return "";
        }
        String newName = name.trim();
        if (newName.startsWith(".")) {
            newName = newName.substring(1);
        }
        if (newName.startsWith("_")) {
            newName = newName.substring(1);
        }
        if (newName.endsWith(".")) {
            newName = newName.substring(0, newName.length() - 1);
        }
        return newName;
    }

    /**
     * Fields for the fully qualified map.
     */
    public enum Fields {
        /**
         * Domain Field.
         */
        Domain,
        /**
         * Protocol Field.
         */
        Protocol,
        /**
         * Application Field.
         */
        Application,
        /**
         * Instance Field.
         */
        Instance,
        /**
         * Subtype Field.
         */
        Subtype
    }
}
