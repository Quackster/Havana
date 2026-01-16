package org.alexdev.havana.util;

import java.util.Objects;

/**
 * A simple immutable pair (tuple) class that replaces Apache Commons Lang3 Pair.
 *
 * @param <L> the left element type
 * @param <R> the right element type
 */
public class Pair<L, R> {
    private final L left;
    private final R right;

    private Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Creates a new pair from the given elements.
     *
     * @param left  the left element
     * @param right the right element
     * @param <L>   the left element type
     * @param <R>   the right element type
     * @return a new Pair instance
     */
    public static <L, R> Pair<L, R> of(L left, R right) {
        return new Pair<>(left, right);
    }

    /**
     * Gets the left element.
     *
     * @return the left element
     */
    public L getLeft() {
        return left;
    }

    /**
     * Gets the right element.
     *
     * @return the right element
     */
    public R getRight() {
        return right;
    }

    /**
     * Gets the key (alias for getLeft).
     * Provided for compatibility with Apache Commons Pair.
     *
     * @return the left element
     */
    public L getKey() {
        return left;
    }

    /**
     * Gets the value (alias for getRight).
     * Provided for compatibility with Apache Commons Pair.
     *
     * @return the right element
     */
    public R getValue() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(left, pair.left) && Objects.equals(right, pair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return "(" + left + ", " + right + ")";
    }
}
