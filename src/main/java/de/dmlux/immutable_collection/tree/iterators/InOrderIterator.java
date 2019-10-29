package de.dmlux.immutable_collection.tree.iterators;

import de.dmlux.immutable_collection.tree.AbstractMutableTree;

import java.util.Stack;

public class InOrderIterator<T> implements TreeIterator<T> {
    Stack<T> elements;

    public InOrderIterator(AbstractMutableTree<T> tree) {
        elements = new Stack<>();
    }

    @Override
    public IteratorNode<T> nextNode() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return elements != null && elements.peek() != null;
    }

    @Override
    public T next() {
        return null;
    }
}
