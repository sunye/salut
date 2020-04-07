package org.atlanmod.salut.builders;

public interface ServicePersistenceChooser {

    ServicePublisher persistent();
    ServicePublisher nonpersistent();
}
