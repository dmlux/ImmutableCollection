package de.dmlux.immutable_collection.tree;

import com.google.common.collect.ImmutableList;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Stream;

public interface Tree<T, D extends Tree<T, D>> extends Traversable<T>, Collection<T>, Serializable {

    T rootElement();

    ImmutableList<D> subtrees(T element);

    ImmutableList<D> subtrees();

    D empty();

    D copy();

    Stream<T> stream();
}
