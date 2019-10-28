package de.dmlux.immutable_collection.tree;

public class DFSIterator<T> implements TreeIterator<T> {

    public DFSIterator(MutableTree<T> tree) {

    }

    public DFSIterator(ImmutableTree<T> tree) {

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
