package de.dmlux.immutable_collection.tree.iterators;

import com.google.common.collect.ImmutableList;

import java.util.Stack;

public class IteratorNode<T> {
    private T element;
    private int level;
    private ImmutableList<T> children;
    private Stack<T> parents;

    public IteratorNode(T element, int level, ImmutableList<T> children, Stack<T> parents) {
        this.element = element;
        this.level = level;
        this.children = children;
        this.parents = parents;
    }

    public T getElement() {
        return element;
    }

    public int getLevel() {
        return level;
    }

    public ImmutableList<T> getChildren() {
        return children;
    }

    public Stack<T> getParents() {
        return parents;
    }
}
