package org.atlanmod.salut.builders;

public interface IPublish {

    void publish();

    IPublish subtype(String str);

    IPublish persistent();

    IPublish weight(int weight);

    IPublish priority(int priority);

    IPublish ttl(int ttl);
}
