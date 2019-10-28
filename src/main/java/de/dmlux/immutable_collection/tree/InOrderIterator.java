package de.dmlux.immutable_collection.tree;

public class InOrderIterator<T> implements TreeIterator<T> {

    public InOrderIterator(MutableTree<T> tree) {

    }

    public InOrderIterator(ImmutableTree<T> tree) {

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
