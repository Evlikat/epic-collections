package net.evlikat.epiccollections;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * CyclicLimitedListTest.
 */
public class CyclicLimitedListTest {

    private CyclicLimitedList<Integer> list;

    @Before
    public void setUp() throws Exception {
        list = new CyclicLimitedList<>(3);
    }

    @Test
    public void testAddAndSize() throws Exception {
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(3, list.size());
        list.add(4);
        assertEquals(3, list.size());
        list.add(5);
        list.add(6);
        list.add(7);
        assertEquals(3, list.size());
    }

    @Test
    public void isEmpty() throws Exception {
        assertTrue(list.isEmpty());
        list.add(1);
        list.add(2);
        list.add(3);
        assertFalse(list.isEmpty());
        list.add(4);
        assertFalse(list.isEmpty());
    }

    @Test
    public void contains() throws Exception {
        assertFalse(list.contains(1));
        list.add(1);
        assertTrue(list.contains(1));
        list.add(2);
        assertTrue(list.contains(1));
        list.add(3);
        assertTrue(list.contains(1));
        list.add(4);
        assertFalse(list.contains(1));
    }

    @Test
    public void testGet() throws Exception {
        assertFalse(list.contains(1));
        list.add(1);
        assertEquals(1, list.size());
        assertEquals(1, (int) list.get(0));
        list.add(2);
        assertEquals(2, list.size());
        assertEquals(1, (int) list.get(0));
        assertEquals(2, (int) list.get(1));
        list.add(3);
        assertEquals(3, list.size());
        assertEquals(1, (int) list.get(0));
        assertEquals(2, (int) list.get(1));
        assertEquals(3, (int) list.get(2));
        list.add(4);
        assertEquals(3, list.size());
        assertEquals(2, (int) list.get(0));
        assertEquals(3, (int) list.get(1));
        assertEquals(4, (int) list.get(2));
        list.add(5);
        list.add(6);
        list.add(7);
        assertEquals(3, list.size());
        assertEquals(5, (int) list.get(0));
        assertEquals(6, (int) list.get(1));
        assertEquals(7, (int) list.get(2));
    }

    @Test
    public void testSet() throws Exception {

    }

    @Test
    public void testIterator() throws Exception {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Iterator<Integer> it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals(2, (int) it.next());
        assertTrue(it.hasNext());
        assertEquals(3, (int) it.next());
        assertTrue(it.hasNext());
        assertEquals(4, (int) it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void toArray() throws Exception {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Object[] result = list.toArray();
        assertArrayEquals(result, new Integer[]{2, 3, 4});
    }

    @Test
    public void toArrayTyped() throws Exception {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Integer[] out = new Integer[3];
        Integer[] result = list.toArray(out);
        assertArrayEquals(result, new Integer[]{2, 3, 4});
    }

    @Test
    public void add() throws Exception {

    }

}