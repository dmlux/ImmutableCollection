package de.dmlux.immutable_collection.tree;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@DisplayName("Test Suite: AbstractMutableTree<T>")
@TestInstance(PER_CLASS)
class AbstractMutableTreeTests {

    @Test
    void testSetRoot() {
        NAryTree<Integer> tree = new NAryTree<>();
        tree.setRoot(10);
        assertEquals(1, tree.size());
        assertNotNull(tree.root);
        assertEquals((Integer) 10, tree.root.element);
        assertEquals(0, tree.root.children.size());
        assertEquals(1, tree.elementInformation.size());
        assertNotNull(tree.elementInformation.get(new ObjectIdentity<>(10)));
        assertEquals(1, tree.elementInformation.get(new ObjectIdentity<>(10)).size());
        assertEquals(tree.root, tree.elementInformation.get(new ObjectIdentity<>(10)).get(0));
    }

    @Test
    void testSetChild() {
        NAryTree<Integer> tree = new NAryTree<>();
        tree.setRoot(10);
        tree.addChild(10, 5);
        tree.addChild(10, 20);
        tree.addChild(5, 10);
        tree.addChild(5, 2);
        assertEquals(5, tree.size());
        assertEquals(2, tree.root.children.size());
        assertEquals(5, (int) tree.root.children.get(0).element);
        assertEquals(20, (int) tree.root.children.get(1).element);
        assertEquals(10, (int) tree.root.children.get(0).children.get(0).element);
        assertEquals(2, (int) tree.root.children.get(0).children.get(1).element);
        assertEquals(0, tree.root.children.get(0).children.get(0).children.size());
        assertEquals(0, tree.root.children.get(0).children.get(1).children.size());
        assertEquals(0, tree.root.children.get(1).children.size());
        assertEquals(4, tree.elementInformation.size());
        // TODO: write more tests
    }
}
