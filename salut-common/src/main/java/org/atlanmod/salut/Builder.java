package org.atlanmod.salut;

import org.atlanmod.salut.builders.IServiceName;
import org.atlanmod.salut.builders.QueryBuilder;
import org.atlanmod.salut.builders.ServiceBuilder;

public class Builder {

    private final Salut salut;

    public Builder(Salut salut) {
        this.salut = salut;
    }

    /**
     * Creates a new instance of ServiceBuilder to create and publish Services.
     *
     * @return A ServiceBuilder
     */
    public IServiceName service() {
        return new ServiceBuilder(salut);
    }

    public QueryBuilder query() {
        return new QueryBuilder();
    }
}
