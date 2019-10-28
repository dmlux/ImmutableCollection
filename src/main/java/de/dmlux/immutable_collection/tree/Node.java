package de.dmlux.immutable_collection.tree;

import java.util.ArrayList;
import java.util.List;

class Node<T> {
    T element;
    int level;
    List<Node<T>> children = new ArrayList<>();

    Node(T element) {
        this.element = element;
        level = -1;
    }

    Node<T> copy() {
        Node<T> newNode = new Node<>(element);
        newNode.level = level;
        List<Node<T>> newChildren = new ArrayList<>();
        children.forEach(node -> newChildren.add(node.copy()));
        newNode.children = newChildren;
        return newNode;
    }

    int countNodes() {
        return 1 + children.stream().mapToInt(Node::countNodes).sum();
    }
}
