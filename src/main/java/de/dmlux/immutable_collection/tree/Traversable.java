package de.dmlux.immutable_collection.tree;

import de.dmlux.immutable_collection.tree.iterators.IteratorNode;
import de.dmlux.immutable_collection.tree.iterators.TreeIterator;

import java.util.Objects;
import java.util.function.Consumer;

public interface Traversable<T> extends Iterable<T> {

    TreeIterator<T> preOrderIterator();

    default void traversePreOrder(Consumer<T> action) {
        Objects.requireNonNull(action);
        TreeIterator<T> iterator = preOrderIterator();
        while (iterator.hasNext()) {
            action.accept(iterator.next());
        }
    }

    default void traverseTreeNodesPreOrder(Consumer<IteratorNode<T>> action) {
        Objects.requireNonNull(action);
        TreeIterator<T> iterator = preOrderIterator();
        while (iterator.hasNext()) {
            action.accept(iterator.nextNode());
        }
    }

    TreeIterator<T> inOrderIterator();

    default void traverseInOrder(Consumer<T> action) {
        Objects.requireNonNull(action);
        TreeIterator<T> iterator = preOrderIterator();
        while (iterator.hasNext()) {
            action.accept(iterator.next());
        }
    }

    default void traverseTreeNodesInOrder(Consumer<IteratorNode<T>> action) {
        Objects.requireNonNull(action);
        TreeIterator<T> iterator = preOrderIterator();
        while (iterator.hasNext()) {
            action.accept(iterator.nextNode());
        }
    }

    TreeIterator<T> postOrderIterator();

    default void traversePostOrder(Consumer<T> action) {
        Objects.requireNonNull(action);
        TreeIterator<T> iterator = preOrderIterator();
        while (iterator.hasNext()) {
            action.accept(iterator.next());
        }
    }

    default void traverseTreeNodesPostOrder(Consumer<IteratorNode<T>> action) {
        Objects.requireNonNull(action);
        TreeIterator<T> iterator = preOrderIterator();
        while (iterator.hasNext()) {
            action.accept(iterator.nextNode());
        }
    }

    TreeIterator<T> levelOrderIterator();

    default void traverseLevelOrder(Consumer<T> action) {
        Objects.requireNonNull(action);
        TreeIterator<T> iterator = preOrderIterator();
        while (iterator.hasNext()) {
            action.accept(iterator.next());
        }
    }

    default void traverseTreeNodesLevelOrder(Consumer<IteratorNode<T>> action) {
        Objects.requireNonNull(action);
        TreeIterator<T> iterator = preOrderIterator();
        while (iterator.hasNext()) {
            action.accept(iterator.nextNode());
        }
    }

    TreeIterator<T> depthFirstSearchIterator();

    default void traverseDepthFirstSearch(Consumer<T> action) {
        Objects.requireNonNull(action);
        TreeIterator<T> iterator = preOrderIterator();
        while (iterator.hasNext()) {
            action.accept(iterator.next());
        }
    }

    default void traverseTreeNodesDepthFirstSearch(Consumer<IteratorNode<T>> action) {
        Objects.requireNonNull(action);
        TreeIterator<T> iterator = preOrderIterator();
        while (iterator.hasNext()) {
            action.accept(iterator.nextNode());
        }
    }
}
