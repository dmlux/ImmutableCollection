package de.dmlux.immutable_collection.tree;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@DisplayName("Test Suite: AbstractMutableTree<T>")
@TestInstance(PER_CLASS)
class AbstractMutableTreeTests {

    @Test
    void testSetRoot() {
        MutableTree<Integer> tree = new NAryTree<>();
        ImmutableTree<Integer> immutableTree = tree.asImmutableTree();
        immutableTree.add(5);
    }
}
