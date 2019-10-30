package de.dmlux.immutable_collection.tree;

import de.dmlux.immutable_collection.tree.iterators.PreOrderIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@DisplayName("Test Suite: NAryTree<T>")
@TestInstance(PER_CLASS)
class NAryTreeTests {
    private NAryTree<Integer> treeSmall;
    private NAryTree<Integer> treeMedium;

    @BeforeEach
    void setUp() {
        treeSmall = new NAryTree<>();
        treeSmall.setRoot(1);
        treeSmall.addChild(1, 2);
        treeSmall.addChild(2, 4);
        treeSmall.addChild(2, 5);
        treeSmall.addChild(1, 3);
        treeMedium = new NAryTree<>();
        treeMedium.setRoot(5);
        treeMedium.addChild(5, 1);
        treeMedium.addChild(5, 3);
        treeMedium.addChild(3, 2);
        treeMedium.addChild(2, 6);
        treeMedium.addChild(2, 7);
        treeMedium.addChild(3, 4);
        treeMedium.addChild(5, 2);
    }
}
