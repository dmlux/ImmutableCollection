package de.dmlux.immutable_collection.tree;

import javax.annotation.Nonnull;
import java.util.Collection;

@SuppressWarnings({"WeakerAccess"})
public abstract class AbstractImmutableTree<T> implements ImmutableTree<T> {
    protected final int[] memory;
    protected final Object[] elements;

    protected AbstractImmutableTree() {
        memory = new int[0];
        elements = new Object[0];
    }

    protected AbstractImmutableTree(int[] memory, Object[] elements) {
        this.memory = memory;
        this.elements = elements;
    }

    @Override
    public T rootElement() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Deprecated
    @Override
    public boolean add(T t) {
        throw new UnsupportedOperationException("Cannot modify immutable object");
    }

    @Deprecated
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Cannot modify immutable object");
    }

    @Override
    public boolean containsAll(@Nonnull Collection<?> collection) {
        return false;
    }

    @Deprecated
    @Override
    public boolean addAll(@Nonnull Collection<? extends T> collection) {
        throw new UnsupportedOperationException("Cannot modify immutable object");
    }

    @Deprecated
    @Override
    public boolean removeAll(@Nonnull Collection<?> collection) {
        throw new UnsupportedOperationException("Cannot modify immutable object");
    }

    @Deprecated
    @Override
    public boolean retainAll(@Nonnull Collection<?> collection) {
        throw new UnsupportedOperationException("Cannot modify immutable object");
    }

    @Deprecated
    @Override
    public void clear() {
        throw new UnsupportedOperationException("Cannot modify immutable object");
    }
}
