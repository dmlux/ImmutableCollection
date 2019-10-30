package de.dmlux.immutable_collection.tree;

import com.google.common.collect.ImmutableList;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Stream;

public interface Tree<T, D extends Tree<T, D>> extends Traversable<T>, Serializable {

    T rootElement();

    ImmutableList<D> subtrees(T subtreeRoot);

    ImmutableList<D> subtrees();

    D empty();

    D copy();

    int size();

    boolean isEmpty();

    boolean contains(T element);

    Stream<T> stream();
}
