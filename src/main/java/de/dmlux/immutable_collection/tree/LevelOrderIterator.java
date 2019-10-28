package de.dmlux.immutable_collection.tree;

public class LevelOrderIterator<T> implements TreeIterator<T> {

    public LevelOrderIterator(MutableTree<T> tree) {

    }

    public LevelOrderIterator(ImmutableTree<T> tree) {

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
