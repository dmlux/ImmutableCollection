package de.dmlux.immutable_collection.tree;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    T element;
    int level;
    List<Node<T>> children = new ArrayList<>();

    Node(T element) {
        this.element = element;
        level = -1;
    }

    Node(T element, int level) {
        this.element = element;
        this.level = level;
    }

    public Node<T> copy() {
        Node<T> newNode = new Node<>(element);
        newNode.level = level;
        List<Node<T>> newChildren = new ArrayList<>();
        children.forEach(node -> newChildren.add(node.copy()));
        newNode.children = newChildren;
        return newNode;
    }

    public int countNodes() {
        return 1 + children.stream().mapToInt(Node::countNodes).sum();
    }

    public T getElement() {
        return element;
    }

    public int getLevel() {
        return level;
    }

    public List<Node<T>> getChildren() {
        return children;
    }
}
