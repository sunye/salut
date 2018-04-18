package org.atlanmod.salut.builders;

public interface IPublish {

    void publish();

    IPublish subtype(String str);

    IPublish persistent();
}
