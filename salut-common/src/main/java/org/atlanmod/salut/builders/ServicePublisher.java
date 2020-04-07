package org.atlanmod.salut.builders;

public interface ServicePublisher {

    void publish();

    ServicePublisher subtype(String str);



    ServicePublisher weight(int weight);

    ServicePublisher priority(int priority);

    ServicePublisher ttl(int ttl);
}
