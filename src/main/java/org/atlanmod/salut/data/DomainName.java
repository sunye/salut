package org.atlanmod.salut.data;

import org.atlanmod.salut.mdns.NameArray;

/**
 *
 * From [RFC 6763](https://tools.ietf.org/html/rfc6763)
 *
 * > "The <Domain> portion of the Service Instance Name specifies the DNS
 *    subdomain within which those data are registered.  It may be
 *    "local.", meaning "link-local Multicast DNS" [RFC6762], or it may be
 *    a conventional Unicast DNS domain name, such as "ietf.org.",
 *    "cs.stanford.edu.", or "eng.us.ibm.com."  Because Service Instance
 *    Names are not host data, they are not constrained by the usual rules
 *    for host data [RFC1033] [RFC1034] [RFC1035], and rich-text service
 *    subdomains are allowed and encouraged, for example:
 *
 *      Building 2, 1st Floor  .  example  .  com  .
 *      Building 2, 2nd Floor  .  example  .  com  .
 *      Building 2, 3rd Floor  .  example  .  com  .
 *      Building 2, 4th Floor  .  example  .  com  ."
 */
public interface DomainName {

    String LOCAL_STR = "local";
    String ARPA = "arpa";

    NameArray toNameArray();
}
