package de.dmlux.immutable_collection.tree.iterators;

import com.google.common.collect.ImmutableList;
import de.dmlux.immutable_collection.tree.AbstractMutableTree;
import de.dmlux.immutable_collection.tree.Node;

import java.util.EmptyStackException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.stream.Collectors;

public class PreOrderIterator<T> implements TreeIterator<T> {
    private Stack<Context<T>> nodes = new Stack<>();

    public PreOrderIterator(AbstractMutableTree<T> tree) {
        if (tree.rootNode() != null)
            retrieveNodes(tree.rootNode(), new Stack<>());
    }

    @Override
    public boolean hasNext() {
        return !nodes.empty();
    }

    @Override
    public T next() {
        try {
            return nodes.pop().node.getElement();
        } catch (EmptyStackException e) {
            throw new NoSuchElementException("Iterator cannot supply further elements");
        }
    }

    @Override
    public IteratorNode<T> nextNode() {
        try {
            Context<T> context = nodes.pop();
            return new IteratorNode<>(
                    context.node.getElement(),
                    context.node.getLevel(),
                    ImmutableList.copyOf(context.node.getChildren().stream().map(Node::getElement).collect(Collectors.toList())),
                    context.parents
            );
        } catch (EmptyStackException e) {
            throw new NoSuchElementException("Iterator cannot supply further elements");
        }
    }

    @SuppressWarnings({"unchecked"})
    private void retrieveNodes(Node<T> subtreeRoot, Stack<T> parents) {
        assert subtreeRoot != null;
        List<Node<T>> children = subtreeRoot.getChildren();
        if (children.isEmpty()) {
            nodes.push(new Context<>(subtreeRoot, (Stack<T>)parents.clone()));
        } else {
            Stack<T> newParents = (Stack<T>) parents.clone();
            newParents.push(subtreeRoot.getElement());
            for (int i = children.size() - 1; i >= children.size() / 2; i--)
                retrieveNodes(children.get(i), newParents);
            for (int i = children.size() / 2 - 1; i >= 0; i--)
                retrieveNodes(children.get(i), newParents);
            nodes.push(new Context<>(subtreeRoot, parents));
        }
    }

    static class Context<T> {
        Node<T> node;
        Stack<T> parents;
        Context(Node<T> node, Stack<T> parents) {
            this.node = node;
            this.parents = parents;
        }
    }
}
