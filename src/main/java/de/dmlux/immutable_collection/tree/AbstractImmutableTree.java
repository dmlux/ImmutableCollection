package de.dmlux.immutable_collection.tree;

import java.util.NoSuchElementException;

@SuppressWarnings({"WeakerAccess"})
public abstract class AbstractImmutableTree<T> implements ImmutableTree<T> {
    protected final int[] memory;
    protected final Object[] elements;

    protected AbstractImmutableTree() {
        memory = new int[0];
        elements = new Object[0];
    }

    protected AbstractImmutableTree(int[] memory, Object[] elements) {
        this.memory = memory;
        this.elements = elements;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public T rootElement() {
        if (memory.length == 0)
            throw new NoSuchElementException("No root element available");
        assert memory.length >= 2;
        return (T) elements[memory[1]];
    }

    @Override
    public int size() {
        return memory.length;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(T o) {
        for (Object element : elements)
            if (element == o)
                return true;
        return false;
    }
}
