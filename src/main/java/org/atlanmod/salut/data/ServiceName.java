package org.atlanmod.salut.data;

/**
 * From [RFC 6763](https://tools.ietf.org/html/rfc6763):
 *
 * > "The <Service> portion of the Service Instance Name consists of a pair
 *    of DNS labels, following the convention already established for SRV
 *    records [RFC2782].  The first label of the pair is an underscore
 *    character followed by the Service Name [RFC6335].  The Service Name
 *    identifies what the service does and what application protocol it
 *    uses to do it.  The second label is either "_tcp" (for application
 *    protocols that run over TCP) or "_udp" (for all others).  For more
 *    details, see Section 7, "Service Names"."
 *
 *
 */
public class ServiceName {
}
