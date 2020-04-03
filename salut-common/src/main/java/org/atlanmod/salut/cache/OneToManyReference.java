package org.atlanmod.salut.cache;

import java.util.ArrayList;
import java.util.List;

public abstract class OneToManyReference<C,T> {
    protected C container;
    private List<T> references = new ArrayList<T>();

    public OneToManyReference(C container) {
        this.container = container;
    }

    public void add(T reference) {
        opposite(reference).basicSet(container);
        this.basicAdd(reference);
    }

    public void basicAdd(T reference) {
        this.references.add(reference);
    }

    public void remove(T reference) {
        opposite(reference).unset();
    }

    public void basicRemove(T reference) {
        this.references.remove(reference);
    }

    public boolean contains(T reference) {
        return references.contains(reference);
    }

    public abstract ManyToOneReference<T, C> opposite(T opposite);
}
