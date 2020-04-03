package org.atlanmod.salut.builders;

public interface SetApplicationProtocol {

    IPublish http();
    IPublish airplay();
    IPublish application(String str);
}
