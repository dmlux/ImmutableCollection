package de.dmlux.immutable_collection.tree;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SuppressWarnings({"WeakerAccess", "unused"})
public class NAryTree<T> extends AbstractMutableTree<T> {

    public NAryTree() {
        super();
    }

    @Override
    public ImmutableTree<T> asImmutableTree() {
        return new ImmutableNAryTree<>(extractMemory(), extractElements());
    }

    @Override
    public <I extends ImmutableTree<T>> I asImmutableTree(BiFunction<int[], Object[], I> allocator) {
        return allocator.apply(extractMemory(), extractElements());
    }

    @Override
    public ImmutableList<MutableTree<T>> subtrees(T subtreeRoot) {
        // TODO: implement
        return null;
    }

    @Override
    public ImmutableList<MutableTree<T>> subtrees() {
        // TODO: implement
        return null;
    }

    @Nonnull
    @Override
    public Object[] toArray() {
        int i = 0;
        Object[] elements = new Object[size()];
        TreeIterator<T> iterator = preOrderIterator();
        while (iterator.hasNext())
            elements[i++] = iterator.next();
        return elements;
    }

    @SuppressWarnings({"unchecked"})
    @Nonnull
    @Override
    public <S> S[] toArray(@Nonnull S[] array) {
        Objects.requireNonNull(array);
        Class<?> argType = array.getClass();
        if (!argType.isArray())
            throw new IllegalArgumentException("The argument is no array");
        Class<?> compType = argType.getComponentType();
        if (!root.element.getClass().isAssignableFrom(compType))
            throw new ArrayStoreException("Cannot store elements of type " + root.element.getClass().getName() + " in array holding elements of type " + compType.getName());
        int i = 0;
        S[] elements = array.length >= size() ? array : (S[]) new Object[size()];
        TreeIterator<T> iterator = preOrderIterator();
        while (iterator.hasNext())
            elements[i++] = (S) iterator.next();
        if (array.length > size())
            array[size()] = null;
        return elements;
    }

    @Override
    public Stream<T> stream() {
        Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(preOrderIterator(), 0);
        return StreamSupport.stream(spliterator, false);
    }

    @Override
    public MutableTree<T> empty() {
        return new NAryTree<>();
    }

    @Override
    public MutableTree<T> copy() {
        NAryTree<T> newTree = new NAryTree<>();
        newTree.root = root.copy();
        return newTree;
    }

    @Override
    public TreeIterator<T> preOrderIterator() {
        return new PreOrderIterator<T>(this);
    }

    @Override
    public TreeIterator<T> inOrderIterator() {
        return new InOrderIterator<T>(this);
    }

    @Override
    public TreeIterator<T> postOrderIterator() {
        return new PostOrderIterator<T>(this);
    }

    @Override
    public TreeIterator<T> levelOrderIterator() {
        return new LevelOrderIterator<T>(this);
    }

    @Override
    public TreeIterator<T> depthFirstSearchIterator() {
        return new DFSIterator<T>(this);
    }

    @Nonnull
    @Override
    public Iterator<T> iterator() {
        return preOrderIterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        traversePreOrder(action::accept);
    }

    private int[] extractMemory() {
        return new int[0];
    }

    private Object[] extractElements() {
        return new Object[0];
    }
}
