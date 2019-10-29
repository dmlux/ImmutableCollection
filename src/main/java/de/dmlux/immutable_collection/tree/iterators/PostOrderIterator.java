package de.dmlux.immutable_collection.tree.iterators;

import de.dmlux.immutable_collection.tree.ImmutableTree;
import de.dmlux.immutable_collection.tree.MutableTree;

public class PostOrderIterator<T> implements TreeIterator<T> {

    public PostOrderIterator(MutableTree<T> tree) {

    }

    public PostOrderIterator(ImmutableTree<T> tree) {

    }

    @Override
    public IteratorNode<T> nextNode() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        return null;
    }
}
