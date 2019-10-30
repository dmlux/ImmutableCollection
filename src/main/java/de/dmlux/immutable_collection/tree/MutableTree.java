package de.dmlux.immutable_collection.tree;

import java.util.function.BiFunction;

public interface MutableTree<T> extends Tree<T, MutableTree<T>> {

    void setRoot(T element);

    void addChild(T parent, T child);

    boolean removeAll(T element);

    boolean replaceAll(T element, T newElement);

    void clear();

    ImmutableTree<T> asImmutableTree();

    <I extends ImmutableTree<T>> I asImmutableTree(BiFunction<int[], Object[], I> allocator);
}
