package de.dmlux.immutable_collection.tree.iterators;

import java.util.Iterator;

public interface TreeIterator<T> extends Iterator<T> {
    IteratorNode<T> nextNode();
}
