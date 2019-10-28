package de.dmlux.immutable_collection.tree;

public interface ImmutableTree<T> extends Tree<T, ImmutableTree<T>> {

    MutableTree<T> asMutableTree();
}
