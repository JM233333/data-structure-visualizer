package jm233333.util;

/**
 * Class {@code Pair} implements a container for a pair of (different types of) members.
 *
 * @param <T1> type of the first member
 * @param <T2> type of the second member
 */
public class Pair<T1, T2> {

    public T1 first;
    public T2 second;

    /**
     * Creates a {@code Pair} and initializes it with default values (e.g. {@code null} for each member).
     */
    public Pair() {
        this(null, null);
    }

    /**
     * Creates a {@code Pair} and initializes it with specified values.
     *
     * @param first initial value of the first member
     * @param second initial value of the second member
     */
    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }
}
