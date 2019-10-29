package de.dmlux.immutable_collection.tree;

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
        treeMedium.add(2);
    }

    @Test
    void testSubtrees() {
        fail();
    }

    @Test
    void testSubtreesParam() {
        fail();
    }

    @Test
    void testCopy() {
        fail();
    }

    @Test
    void testPreOrderIterator() {
        fail();
    }

    @Test
    void testPostOrderIterator() {
        fail();
    }

    @Test
    void testInOrderIterator() {
        fail();
    }

    @Test
    void testLevelOrderIterator() {
        fail();
    }

    @Test
    void testDeptFirstSearchIterator() {
        fail();
    }

    @Test
    void testStream() {
        Stream<Integer> stream = treeSmall.stream();
        assertNotNull(stream);
        assertEquals(5, stream.count());
        List<Integer> list = new ArrayList<>();
        treeSmall.stream().forEach(number -> list.add(number));
        assertEquals(1, (int) list.get(0));
        assertEquals(2, (int) list.get(1));
        assertEquals(4, (int) list.get(2));
        assertEquals(5, (int) list.get(3));
        assertEquals(3, (int) list.get(4));
    }

    @Test
    void testAsImmutableTree() {
        ImmutableNAryTree<Integer> immutableTree = treeSmall.asImmutableTree(ImmutableNAryTree::new);
        assertNotNull(immutableTree);
        assertEquals(treeSmall.size(), immutableTree.size());
        Object[] mutableValues = treeSmall.toArray();
        Object[] immutableValues = immutableTree.toArray();
        assertEquals(mutableValues.length, immutableValues.length);
        for (int i = 0; i < mutableValues.length; i++)
            assertEquals(mutableValues[i], immutableValues[i]);
    }

    @Test
    void testToArrayObjectArray() {
        Object[] array = treeMedium.toArray();
        ArrayList<Integer> values = new ArrayList<>();
        treeMedium.traversePreOrder(values::add);
        assertNotNull(array);
        assertEquals(array.length, treeMedium.size());
        assertEquals(array[0], values.get(0));
        assertEquals(array[1], values.get(1));
        assertEquals(array[2], values.get(2));
        assertEquals(array[3], values.get(3));
        assertEquals(array[4], values.get(4));
        assertEquals(array[5], values.get(5));
        assertEquals(array[6], values.get(6));
        assertEquals(array[7], values.get(7));
    }

    @Test
    void testToArrayArrayParam() {
        ArrayList<Integer> values = new ArrayList<>();
        treeMedium.traversePreOrder(values::add);
        Integer[] array1 = new Integer[7];
        assertThrows(NullPointerException.class, () -> treeMedium.toArray((Integer[]) null));
        assertThrows(ArrayStoreException.class, () -> treeMedium.toArray(new String[0]));
        Object[] resultToArray1 = treeMedium.toArray(array1);
        assertNotSame(resultToArray1, array1);
        assertEquals(resultToArray1[0], values.get(0));
        assertEquals(resultToArray1[1], values.get(1));
        assertEquals(resultToArray1[2], values.get(2));
        assertEquals(resultToArray1[3], values.get(3));
        assertEquals(resultToArray1[4], values.get(4));
        assertEquals(resultToArray1[5], values.get(5));
        assertEquals(resultToArray1[6], values.get(6));
        assertEquals(resultToArray1[7], values.get(7));
        Integer[] array2 = new Integer[10];
        array2[8] = Integer.MIN_VALUE;
        array2[9] = Integer.MIN_VALUE;
        Object[] resultToArray2 = treeMedium.toArray(array2);
        assertSame(resultToArray2, array2);
        assertEquals(resultToArray2[0], values.get(0));
        assertEquals(resultToArray2[1], values.get(1));
        assertEquals(resultToArray2[2], values.get(2));
        assertEquals(resultToArray2[3], values.get(3));
        assertEquals(resultToArray2[4], values.get(4));
        assertEquals(resultToArray2[5], values.get(5));
        assertEquals(resultToArray2[6], values.get(6));
        assertEquals(resultToArray2[7], values.get(7));
        assertNull(resultToArray2[8]);
        assertEquals((Integer) Integer.MIN_VALUE, resultToArray2[9]);
        Integer[] array3 = new Integer[8];
        Object[] resultToArray3 = treeMedium.toArray(array3);
        assertSame(resultToArray3, array3);
        assertEquals(resultToArray3[0], values.get(0));
        assertEquals(resultToArray3[1], values.get(1));
        assertEquals(resultToArray3[2], values.get(2));
        assertEquals(resultToArray3[3], values.get(3));
        assertEquals(resultToArray3[4], values.get(4));
        assertEquals(resultToArray3[5], values.get(5));
        assertEquals(resultToArray3[6], values.get(6));
        assertEquals(resultToArray3[7], values.get(7));
    }

    @Test
    void testForEach() {
        ArrayList<Integer> v1 = new ArrayList<>();
        ArrayList<Integer> v2 = new ArrayList<>();
        treeSmall.traversePreOrder(v1::add);
        treeSmall.forEach(v2::add);
        assertEquals(v1.size(), v2.size());
        assertEquals(v1.get(0), v2.get(0));
        assertEquals(v1.get(1), v2.get(1));
        assertEquals(v1.get(2), v2.get(2));
        assertEquals(v1.get(3), v2.get(3));
        assertEquals(v1.get(4), v2.get(4));
    }

    @Test
    void testIterator() {
        Iterator<Integer> iterator = treeSmall.iterator();
        assertNotNull(iterator);
        assertEquals(PreOrderIterator.class, iterator.getClass());
        List<Integer> list = new ArrayList<>();
        while (iterator.hasNext())
            list.add(iterator.next());
        assertEquals(1, (int) list.get(0));
        assertEquals(2, (int) list.get(1));
        assertEquals(4, (int) list.get(2));
        assertEquals(5, (int) list.get(3));
        assertEquals(3, (int) list.get(4));
    }
}
