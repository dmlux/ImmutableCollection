package de.dmlux.immutable_collection.tree;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.function.Function;

public interface ImmutableTree<T> extends Tree<T, ImmutableTree<T>> {

    MutableTree<T> asMutableTree();

    <M extends MutableTree<T>> M asMutableTree(Function<ImmutableTree<T>, M> allocator);

    @Deprecated
    @Override
    boolean add(T t);

    @Deprecated
    @Override
    boolean remove(Object o);

    @Deprecated
    @Override
    boolean addAll(@Nonnull Collection<? extends T> collection);

    @Deprecated
    @Override
    boolean removeAll(@Nonnull Collection<?> collection);

    @Deprecated
    @Override
    boolean retainAll(@Nonnull Collection<?> collection);

    @Deprecated
    @Override
    void clear();
}
