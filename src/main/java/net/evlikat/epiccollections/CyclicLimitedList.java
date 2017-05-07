package net.evlikat.epiccollections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Cyclic Limited List.
 * Represents buffer containing N last added values.
 */
public class CyclicLimitedList<T> implements List<T> {

    private final int limit;
    private final ArrayList<T> inner;

    private int last = 0;

    public CyclicLimitedList(int limit) {
        this.inner = new ArrayList<>(limit);
        this.limit = limit;
    }

    @Override
    public int size() {
        return inner.size();
    }

    @Override
    public boolean isEmpty() {
        return inner.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return inner.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        if (cycled()) {
            return inner.iterator();
        }
        return new CyclicItr();
    }

    @Override
    public Object[] toArray() {
        Object[] raw = inner.toArray();
        if (!cycled()) {
            return raw;
        }
        return shifted(raw);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        T1[] raw = inner.toArray(a);
        if (!cycled()) {
            return raw;
        }
        return shifted(inner.toArray(a));
    }

    @Override
    public boolean add(T t) {
        if (!cycled()) {
            return inner.add(t);
        }
        inner.set(last, t);
        last = (last + 1) % limit;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        inner.clear();
        last = 0;
    }

    @Override
    public T get(int index) {
        if (!cycled()) {
            return inner.get(index);
        }
        return inner.get((last + index) % limit);
    }

    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= limit) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return inner.set((last + index) % limit, element);
    }

    @Override
    public void add(int index, T element) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return inner.indexOf(o) + last;
    }

    @Override
    public int lastIndexOf(Object o) {
        return inner.lastIndexOf(o) + last;
    }

    @Override
    public ListIterator<T> listIterator() {
        if (!cycled()) {
            return inner.listIterator();
        }
        return new CyclicListItr(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if (!cycled()) {
            return inner.listIterator(index);
        }
        return new CyclicListItr(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (!cycled()) {
            return inner.subList(fromIndex, toIndex);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <P> P[] shifted(P[] source) {
        P[] result = (P[]) new Object[source.length];
        return shifted(source, result);
    }

    @SuppressWarnings("unchecked")
    private <P> P[] shifted(P[] source, P[] dest) {
        System.arraycopy(source, last, dest, 0, limit - last);
        System.arraycopy(source, 0, dest, limit - last, last);
        return dest;
    }


    private boolean cycled() {
        return inner.size() == limit;
    }

    ArrayList<T> rebasedInner() {
        ArrayList<T> result = new ArrayList<>(inner.subList(last, Math.max(limit, inner.size())));
        result.addAll(inner.subList(0, last - 1));
        return result;
    }

    private final class CyclicItr implements Iterator<T> {

        private int elementsTraversed;

        @Override
        public boolean hasNext() {
            return elementsTraversed < limit;
        }

        @Override
        public T next() {
            if (elementsTraversed >= limit)
                throw new NoSuchElementException();
            return CyclicLimitedList.this.get(elementsTraversed++);
        }
    }

    private final class CyclicListItr implements ListIterator<T> {

        private int elementsTraversed;

        public CyclicListItr(int fromIndex) {
            this.elementsTraversed = fromIndex;
        }

        @Override
        public boolean hasNext() {
            return elementsTraversed < limit;
        }

        @Override
        public T next() {
            if (elementsTraversed >= limit)
                throw new NoSuchElementException();
            return CyclicLimitedList.this.get(elementsTraversed++);
        }

        @Override
        public boolean hasPrevious() {
            return elementsTraversed != 0;
        }

        @Override
        public T previous() {
            return CyclicLimitedList.this.get(elementsTraversed - 1);
        }

        @Override
        public int nextIndex() {
            return elementsTraversed + 1;
        }

        @Override
        public int previousIndex() {
            return elementsTraversed - 1;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(T t) {

        }

        @Override
        public void add(T t) {

        }
    }
}
