package de.dmlux.immutable_collection.tree;

import javax.annotation.Nonnull;
import java.util.*;

@SuppressWarnings({"WeakerAccess"})
public abstract class AbstractMutableTree<T> implements MutableTree<T> {
    protected Node<T> root;
    private Map<ObjectIdentity<T>, List<Node<T>>> elementInformation;

    protected AbstractMutableTree() {
        root = null;
        elementInformation = new HashMap<>();
    }

    @Override
    public void setRoot(T element) {
        elementInformation.clear();
        root = generateNode(element, 0);
    }

    private Node<T> generateNode(T element, int level) {
        Node<T> node = new Node<>(element);
        node.level = level;
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
    public boolean add(T element) {
        if (root == null) root = generateNode(element, 0);
        else root.children.add(generateNode(element, 1));
        return true;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean remove(Object element) {
        if (root == null) return false;
        if (root.element == element) {
            root = null;
            elementInformation.clear();
            return true;
        }
        Class<T> clazz = (Class<T>) root.element.getClass();
        if (clazz.isAssignableFrom(element.getClass()))
            return remove(root, clazz.cast(element));
        throw new IllegalArgumentException("Cannot remove object of class " + element.getClass().getName() + ". Expected element of class " + clazz.getName());
    }

    private boolean remove(Node<T> subtreeRoot, T element) {
        assert subtreeRoot != null;
        boolean found = false;
        List<Node<T>> newChildren = new ArrayList<>();
        for (Node<T> child : subtreeRoot.children) {
            if (!found && child.element == element) {
                removeInstanceFromTree(child);
                found = true;
            } else {
                newChildren.add(child);
                if (!found)
                    found = remove(child, element);
            }
        }
        subtreeRoot.children = newChildren;
        return found;
    }

    @Override
    public boolean containsAll(@Nonnull Collection<?> collection) {
        Objects.requireNonNull(collection);
        if (root == null) return false;
        for (Object element : collection)
            if (!contains(element))
                return false;
        return true;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean addAll(@Nonnull Collection<? extends T> collection) {
        Objects.requireNonNull(collection);
        boolean result = false;
        for (Object object : collection)
            // TODO: type check element before casting it to T
            result |= add((T) object);
        return result;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean removeAll(@Nonnull Collection<?> collection) {
        Objects.requireNonNull(collection);
        if (root == null) return false;
        if (collection.isEmpty()) return false;
        Class<T> clazz = (Class<T>) root.element.getClass();
        Class<?> collectionType = collection.toArray()[0].getClass();
        if (!collection.isEmpty() && !clazz.isAssignableFrom(collectionType))
            throw new IllegalArgumentException("Cannot remove object of class " + collectionType.getName() + ". Expected element of class " + clazz.getName());
        boolean result = false;
        for (Object object : collection)
            result |= removeAll(clazz.cast(object));
        return result;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean retainAll(@Nonnull Collection<?> collection) {
        Objects.requireNonNull(collection);
        if (root == null) return false;
        if (collection.isEmpty() || !collection.contains(root.element)) {
            root = null;
            elementInformation.clear();
            return true;
        }
        Class<T> clazz = (Class<T>) root.element.getClass();
        Class<?> collectionType = collection.toArray()[0].getClass();
        if (!clazz.isAssignableFrom(collectionType))
            throw new IllegalArgumentException("Cannot remove object of class " + collectionType.getName() + ". Expected element of class " + clazz.getName());
        return retainAll(root, collection);
    }

    private boolean retainAll(Node<T> subtreeRoot, @Nonnull Collection<?> collection) {
        assert subtreeRoot != null;
        assert collection != null;
        List<Node<T>> newChildren = new ArrayList<>();
        for (Node<T> child : subtreeRoot.children)
            if (collection.contains(child.element))
                newChildren.add(child);
            else
                removeInstanceFromTree(child);
        boolean result = newChildren.size() < subtreeRoot.children.size();
        for (Node<T> child : newChildren)
            result |= retainAll(child, collection);
        subtreeRoot.children = newChildren;
        return result;
    }

    @Override
    public void clear() {
        root = null;
        elementInformation.clear();
    }
}
