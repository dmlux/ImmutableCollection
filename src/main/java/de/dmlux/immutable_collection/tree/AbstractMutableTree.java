package de.dmlux.immutable_collection.tree;

import java.util.*;
import java.util.function.Consumer;

@SuppressWarnings({"WeakerAccess"})
public abstract class AbstractMutableTree<T> implements MutableTree<T> {
    protected Node<T> root;
    protected Map<ObjectIdentity<T>, List<Node<T>>> elementInformation;

    protected AbstractMutableTree() {
        root = null;
        elementInformation = new HashMap<>();
    }

    protected AbstractMutableTree(Node<T> root) {
        this.root = root;
        elementInformation = new HashMap<>();
        retrieveNodes(elementInformation, root);
    }

    protected void retrieveNodes(Map<ObjectIdentity<T>, List<Node<T>>> elementInformation, Node<T> subtreeRoot) {
        ObjectIdentity<T> OID = new ObjectIdentity<>(subtreeRoot.element);
        elementInformation.computeIfAbsent(OID, k -> new ArrayList<>()).add(subtreeRoot);
        subtreeRoot.children.forEach(child -> retrieveNodes(elementInformation, subtreeRoot));
    }

    public abstract Node<T> rootNode();

    @Override
    public void setRoot(T element) {
        elementInformation.clear();
        root = generateNode(element, 0);
    }

    private Node<T> generateNode(T element, int level) {
        Node<T> node = new Node<>(element, level);
        ObjectIdentity<T> OID = new ObjectIdentity<>(element);
        elementInformation.computeIfAbsent(OID, k -> new ArrayList<>()).add(node);
        return node;
    }

    @Override
    public void addChild(T parent, T child) {
        ObjectIdentity<T> parentOID = new ObjectIdentity<>(parent);
        if (root == null || !elementInformation.containsKey(parentOID))
            throw new NoSuchElementException("Parent element " + parent + " is not available in tree");
        addChildToParent(parentOID, new ObjectIdentity<>(child));
    }

    private void addChildToParent(ObjectIdentity<T> parentOID, ObjectIdentity<T> childOID) {
        List<Node<T>> candidates = elementInformation.get(parentOID);
        Node<T> parentNode = candidates.get(candidates.size() - 1);
        Node<T> childNode = generateNode(childOID.object, parentNode.level + 1);
        parentNode.children.add(childNode);
    }

    @Override
    public boolean removeAll(T element) {
        if (root == null) return false;
        return removeAll(root, element);
    }

    private boolean removeAll(Node<T> subTreeRoot, T element) {
        assert subTreeRoot != null;
        boolean result = false;
        if (!subTreeRoot.children.isEmpty()) {
            List<Node<T>> newChildren = new ArrayList<>();
            for (Node<T> child : subTreeRoot.children)
                if (child.element != element)
                    newChildren.add(child);
                else
                    removeSubtree(child);
            result = newChildren.size() < subTreeRoot.children.size();
            subTreeRoot.children = newChildren;
            for (Node<T> child : subTreeRoot.children)
                result |= removeAll(child, element);
        }
        return result;
    }

    private void removeSubtree(Node<T> subTreeRoot) {
        assert subTreeRoot != null;
        subTreeRoot.children.forEach(this::removeSubtree);
        removeInstanceFromTree(subTreeRoot);
    }

    private void removeInstanceFromTree(Node<T> node) {
        assert node != null;
        ObjectIdentity<T> OID = new ObjectIdentity<>(node.element);
        List<Node<T>> candidates = elementInformation.get(OID);
        candidates.remove(node);
        if (candidates.isEmpty())
            elementInformation.remove(OID);
    }

    @Override
    public boolean replaceAll(T element, T newElement) {
        if (root == null || element == newElement) return false;
        ObjectIdentity<T> elementOID = new ObjectIdentity<>(element);
        if (!elementInformation.containsKey(elementOID))
            throw new NoSuchElementException("Element is not available in tree");
        ObjectIdentity<T> newElementOID = new ObjectIdentity<>(newElement);
        if (!elementInformation.containsKey(newElementOID)) {
            List<Node<T>> newCandidates = elementInformation.get(elementOID);
            newCandidates.forEach(node -> node.element = newElement);
            elementInformation.put(newElementOID, newCandidates);
            elementInformation.remove(elementOID);
        } else {
            List<Node<T>> newCandidates = elementInformation.get(newElementOID);
            List<Node<T>> oldCandidates = elementInformation.get(elementOID);
            oldCandidates.forEach(node -> node.element = newElement);
            newCandidates.addAll(oldCandidates);
            elementInformation.remove(elementOID);
        }
        return true;
    }

    @Override
    public T rootElement() {
        if (root != null)
            return root.element;
        throw new NoSuchElementException("No root element to return");
    }

    @Override
    public int size() {
        return root.countNodes();
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean contains(Object element) {
        if (root == null) return false;
        Class<T> clazz = (Class<T>) root.element.getClass();
        if (clazz.isAssignableFrom(element.getClass()))
            return elementInformation.containsKey(new ObjectIdentity<>(clazz.cast(element)));
        throw new IllegalArgumentException("Array of elements of type " + clazz.getName() + " cannot hold elements of type " + element.getClass().getName());
    }

    @Override
    public void clear() {
        root = null;
        elementInformation.clear();
    }

    protected Map<ObjectIdentity<T>, List<Node<T>>> compressElementInformation() {
        Map<ObjectIdentity<T>, List<Node<T>>> newElementInformation = new HashMap<>();
        traverseNodes(node -> {
            ObjectIdentity<T> OID = new ObjectIdentity<>(node.element);
            newElementInformation.computeIfAbsent(OID, k -> new ArrayList<>()).add(node);
        });
        return newElementInformation;
    }

    protected void traverseNodes(Consumer<Node<T>> action) {
        Objects.requireNonNull(action);
        InternalIterator<T> iterator = new InternalIterator<>(this);
        while(iterator.hasNext()) {
            action.accept(iterator.next());
        }
    }

    protected static class InternalIterator<T> implements Iterator<Node<T>> {
        Queue<Node<T>> nodes = new ArrayDeque<>();
        Queue<Node<T>> visited = new ArrayDeque<>();

        public InternalIterator(AbstractMutableTree<T> tree) {
            Objects.requireNonNull(tree);
            retrieveNodes(tree.root);
        }

        @Override
        public boolean hasNext() {
            return nodes.peek() != null;
        }

        @Override
        public Node<T> next() {
            try {
                return nodes.remove();
            } catch(NoSuchElementException e) {
                throw new NoSuchElementException("Iterator can not supply further elements");
            }
        }

        public void retrieveNodes(Node<T> subtreeRoot) {
            assert subtreeRoot != null;
            visited.add(subtreeRoot);
            while (!visited.isEmpty()) {
                Node<T> current = visited.remove();
                nodes.add(current);
                visited.addAll(current.children);
            }
        }
    }
}
