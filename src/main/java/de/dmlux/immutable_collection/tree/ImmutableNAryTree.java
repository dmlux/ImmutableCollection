package de.dmlux.immutable_collection.tree;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SuppressWarnings({"WeakerAccess"})
public class ImmutableNAryTree<T> extends AbstractImmutableTree<T> {

    public ImmutableNAryTree() {
        super();
    }

    public ImmutableNAryTree(int[] memory, Object[] elements) {
        super(memory, elements);
    }

    @Override
    public ImmutableList<ImmutableTree<T>> subtrees(T subtreeRoot) {
        // TODO: implement
        return null;
    }

    @Override
    public ImmutableList<ImmutableTree<T>> subtrees() {
        // TODO: implement
        return null;
    }

    @Nonnull
    @Override
    public Object[] toArray() {
        // TODO: implement
        return new Object[0];
    }

    @Nonnull
    @Override
    public <S> S[] toArray(@Nonnull S[] array) {
        // TODO: implement
        return null;
    }

    @Override
    public Stream<T> stream() {
        Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(preOrderIterator(), 0);
        return StreamSupport.stream(spliterator, false);
    }

    @Override
    public ImmutableTree<T> empty() {
        return new ImmutableNAryTree<>();
    }

    @Override
    public ImmutableTree<T> copy() {
        return new ImmutableNAryTree<>(memory, elements);
    }

    @Override
    public TreeIterator<T> preOrderIterator() {
        return new PreOrderIterator<>(this);
    }

    @Override
    public TreeIterator<T> inOrderIterator() {
        return new InOrderIterator<>(this);
    }

    @Override
    public TreeIterator<T> postOrderIterator() {
        return new PostOrderIterator<>(this);
    }

    @Override
    public TreeIterator<T> levelOrderIterator() {
        return new LevelOrderIterator<>(this);
    }

    @Override
    public TreeIterator<T> depthFirstSearchIterator() {
        return new DFSIterator<>(this);
    }

    @Override
    public MutableTree<T> asMutableTree() {
        // TODO: implement
        return null;
    }

    @Override
    public <M extends MutableTree<T>> M asMutableTree(Function<ImmutableTree<T>, M> allocator) {
        return allocator.apply(this);
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
}
