package de.dmlux.immutable_collection.tree;

public class PreOrderIterator<T> implements TreeIterator<T> {

    public PreOrderIterator(MutableTree<T> tree) {

    }

    public PreOrderIterator(ImmutableTree<T> tree) {

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
