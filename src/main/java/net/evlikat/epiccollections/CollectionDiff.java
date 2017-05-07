package net.evlikat.epiccollections;

import java.util.Collection;
import java.util.stream.Collectors;

public class CollectionDiff<T> {

    private final Collection<T> newItems;
    private final Collection<T> removedItems;

    private CollectionDiff(Collection<T> newItems, Collection<T> removedItems) {
        this.newItems = newItems;
        this.removedItems = removedItems;
    }

    public static <T> CollectionDiff<T> compare(Collection<T> first, Collection<T> second) {
        return new CollectionDiff<>(
                second.stream().filter(b -> !first.contains(b)).collect(Collectors.toList()),
                first.stream().filter(a -> !second.contains(a)).collect(Collectors.toList()));
    }

    public Collection<T> newItems() {
        return newItems;
    }

    public Collection<T> removedItems() {
        return removedItems;
    }
}
