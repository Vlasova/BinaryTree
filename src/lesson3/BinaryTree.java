package lesson3;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    @Override
    public boolean remove(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> node = findNode(t);
        if (node == null) return false;
        Node<T> parent = findParent(node);
        if (parent == null) {
            root = delete(node);
            size--;
            return true;
        }
        int comparison = node.value.compareTo(parent.value);
        assert comparison != 0;
        if (comparison < 0) {
            parent.left = delete(node);
        }
        else {
            parent.right = delete(node);
        }
        size--;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    private Node<T> findParent(Node<T> child) {
        if (root == null || child == null) return null;
        return findParent(root, child.value);
    }

    private Node<T> findParent(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return null;
        }
        else if (comparison < 0) {
            if (start.left.value == value) return start;
            return findParent(start.left, value);
        }
        else {
            if(start.right.value == value) return start;
            return findParent(start.right, value);
        }
    }

    private Node<T> delete(Node<T> node) {
        if (node.right == null) {
            if (node.left == null) {
                return null;
            }
            else {
                return node.left;
            }
        }
        else {
            if (node.left == null) {
                return node.right;
            }
            Node<T> minimumLeft = node.right;
            Node<T> minimumLeftParent = node;
            while (minimumLeft.left != null) {
                minimumLeftParent = minimumLeft;
                minimumLeft = minimumLeft.left;
            }
            minimumLeft.left = node.left;
            if (minimumLeftParent != node) {
                minimumLeftParent.left = minimumLeft.right;
                minimumLeft.right = node.right;
            }
            return minimumLeft;
        }
    }

    private Node<T> findNode(T value) {
        if (root == null) return null;
        return findNode(root, value);
    }

    private Node<T> findNode(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return null;
            return findNode(start.left, value);
        }
        else {
            if(start.right == null) return null;
            return findNode(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;

        private BinaryTreeIterator() {}

        private Node<T> findNext() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }
}
