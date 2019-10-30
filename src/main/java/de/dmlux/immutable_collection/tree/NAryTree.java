package de.dmlux.immutable_collection.tree;

import com.google.common.collect.ImmutableList;
import de.dmlux.immutable_collection.tree.iterators.*;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SuppressWarnings({"unused"})
public class NAryTree<T> extends AbstractMutableTree<T> {

    public NAryTree() {
        super();
    }

    private NAryTree(Node<T> root) {
        super(root);
    }

    @Override
    public ImmutableTree<T> asImmutableTree() {
        return extract(ImmutableNAryTree::new);
    }

    @Override
    public <I extends ImmutableTree<T>> I asImmutableTree(BiFunction<int[], Object[], I> allocator) {
        return extract(allocator);
    }

    @Override
    public Node<T> rootNode() {
        return root;
    }

    @Override
    public ImmutableList<MutableTree<T>> subtrees(T subtreeRoot) {
        ImmutableList.Builder<MutableTree<T>> subtrees = ImmutableList.builder();
        ObjectIdentity<T> OID = new ObjectIdentity<>(subtreeRoot);
        List<Node<T>> roots = elementInformation.get(OID);
        if (roots.isEmpty())
            return ImmutableList.of();
        for (Node<T> root : roots) {
            NAryTree<T> subtree = new NAryTree<>(root.copy());
            retrieveNodes(subtree.elementInformation, subtree.root);
            subtrees.add(subtree);
        }
        return subtrees.build();
    }

    @Override
    public ImmutableList<MutableTree<T>> subtrees() {
        ImmutableList.Builder<MutableTree<T>> subtrees = ImmutableList.builder();
        for (Node<T> subtreeRoot : root.children) {
            NAryTree<T> subtree = new NAryTree<>(subtreeRoot.copy());
            retrieveNodes(subtree.elementInformation, subtree.root);
            subtrees.add(subtree);
        }
        return subtrees.build();
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

    private <I extends ImmutableTree<T>> I extract(BiFunction<int[], Object[], I> allocator) {
        int[] memory = new int[size() * 2];
        List<Object> elements = elementInformation.keySet().stream().map(OID -> OID.object).collect(Collectors.toList());
        int i = 0;
        TreeIterator<T> iterator = preOrderIterator();
        while (iterator.hasNext()) {
            IteratorNode<T> node = iterator.nextNode();
            memory[i++] = node.getChildren().size();
            memory[i++] = elements.indexOf(node.getElement());
        }
        return allocator.apply(memory, elements.toArray());
    }
}
