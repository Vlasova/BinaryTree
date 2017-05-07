package lesson3;

import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryTreeTest {
    @Test
    public void removeTest() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.add(10);
        tree.add(5);
        tree.add(3);
        tree.add(2);
        tree.add(4);
        assertEquals(5, tree.size());
        tree.remove(5);
        assertEquals(4, tree.size());
        assertFalse(tree.contains(5));
        assertTrue(tree.contains(2));
        tree.add(15);
        tree.add(13);
        tree.add(12);
        tree.add(20);
        tree.add(18);
        tree.add(16);
        tree.add(17);
        tree.add(24);
        tree.add(26);
        tree.remove(20);
        assertFalse(tree.contains(20));
        assertTrue(tree.contains(16));
        assertEquals(12, tree.size());
        tree.remove(15);
        assertFalse(tree.contains(15));
        assertTrue(tree.contains(24));
        assertEquals(11, tree.size());

        //тест на случай, когда удаляется корень
        tree.remove(10);
        assertFalse(tree.contains(10));
        assertTrue(tree.contains(16));
        assertEquals(10 ,tree.size());
        assertTrue(tree.checkInvariant());
    }
}

