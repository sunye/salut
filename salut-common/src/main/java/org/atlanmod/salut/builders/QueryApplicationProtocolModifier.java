package org.atlanmod.salut.builders;

public interface QueryApplicationProtocolModifier {

    IQuery http();
    IQuery airplay();
    IQuery application(String str);
}
