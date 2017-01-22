package net.evlikat.epiccollections;

/**
 * PartialComparator.
 */
public interface PartialComparator<T> {

    /**
     * Compares t1 with t2.
     * @param t1
     * @param t2
     * @return 1 if t1 > t2, -1 if t1 < t2, 0 if t1 == t2, and null if values can not be compared
     */
    Integer compare(T t1, T t2);
}
