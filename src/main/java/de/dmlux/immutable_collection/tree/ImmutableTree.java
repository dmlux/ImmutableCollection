package de.dmlux.immutable_collection.tree;

import java.util.function.Function;

public interface ImmutableTree<T> extends Tree<T, ImmutableTree<T>> {

    MutableTree<T> asMutableTree();

    <M extends MutableTree<T>> M asMutableTree(Function<ImmutableTree<T>, M> allocator);
}
