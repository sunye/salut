package org.atlanmod.salut.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public abstract class ManyToManyReference<F, T> implements Iterable<T> {
    protected final F container;
    private List<T> references = new ArrayList<T>();

    public ManyToManyReference(F container) {
        this.container = container;
    }

    public void add(T reference) {
        assert Objects.nonNull(reference);

        opposite(reference).basicAdd(container);
        this.basicAdd(reference);
    }

    public void basicAdd(T reference) {
        this.references.add(reference);
    }

    public void remove(T reference) {
        opposite(reference).basicRemove(container);
        this.basicRemove(reference);
    }

    public void basicRemove(T reference) {
        this.references.remove(reference);
    }

    public boolean contains(T reference) {
        return references.contains(reference);
    }

    public int size() {
        return  references.size();
    }

    public Iterator<T> iterator() {
        return references.iterator();
    }

    public List<T> references() {
        return new ArrayList<>(this.references);
    }

    public abstract ManyToManyReference<T, F> opposite(T opposite);
}
