package net.evlikat.epiccollections;

import net.evlikat.epiccollections.util.Pair;
import net.evlikat.epiccollections.util.Range;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LinearMapTest_ResidueClassRing {

    private LinearMap<Integer, Integer> residueClassRing;

    @Before
    public void setUp() throws Exception {
        residueClassRing = new LinearMap<>(new ResidueClassRing());
    }

    @Test
    public void putFourElements() throws Exception {
        residueClassRing.put(1, 15);
        residueClassRing.put(3, 35);
        residueClassRing.put(5, 55);
        residueClassRing.put(6, 65);
        assertEquals(4, residueClassRing.size());
        assertEquals(15, residueClassRing.get(1).intValue());
        assertEquals(35, residueClassRing.get(3).intValue());
        assertEquals(55, residueClassRing.get(5).intValue());
        assertEquals(65, residueClassRing.get(6).intValue());
    }

    @Test
    public void putFourElementsIntersectingKeys() throws Exception {
        residueClassRing.put(1, 15);
        residueClassRing.put(3, 35);
        residueClassRing.put(8, 55);
        assertEquals(2, residueClassRing.size());
        assertEquals(55, residueClassRing.get(1).intValue());
        assertEquals(35, residueClassRing.get(3).intValue());
    }

    @Test
    public void isEmpty() throws Exception {
        assertTrue(residueClassRing.isEmpty());
        residueClassRing.put(1, 2);
        assertFalse(residueClassRing.isEmpty());
    }

    @Test
    public void containsKey() throws Exception {
        residueClassRing.put(1, 2);
        assertTrue(residueClassRing.containsKey(1));
        assertTrue(residueClassRing.containsKey(8));
        assertFalse(residueClassRing.containsKey(5));
    }

    @Test
    public void containsValue() throws Exception {
        residueClassRing.put(1, 2);
        assertTrue(residueClassRing.containsValue(2));
        assertFalse(residueClassRing.containsValue(5));
    }

    @Test
    public void remove() throws Exception {
        residueClassRing.put(1, 15);
        residueClassRing.put(3, 35);
        residueClassRing.put(5, 55);
        assertEquals(3, residueClassRing.size());
        residueClassRing.remove(1);
        assertEquals(2, residueClassRing.size());
        residueClassRing.remove(3);
        assertEquals(1, residueClassRing.size());
        residueClassRing.remove(12);
        assertEquals(0, residueClassRing.size());
        assertTrue(residueClassRing.isEmpty());
    }

    @Test
    public void putAll() throws Exception {
        HashMap<Integer, Integer> source = new HashMap<>();
        source.put(1, 15);
        source.put(3, 35);
        source.put(5, 55);
        residueClassRing.putAll(source);
        assertEquals(3, residueClassRing.size());
        assertFalse(residueClassRing.isEmpty());
        assertEquals(15, residueClassRing.get(1).intValue());
        assertEquals(35, residueClassRing.get(3).intValue());
        assertEquals(55, residueClassRing.get(5).intValue());
    }

    @Test
    public void clear() throws Exception {
        residueClassRing.put(1, 15);
        residueClassRing.put(3, 35);
        residueClassRing.put(5, 55);
        residueClassRing.clear();
        assertTrue(residueClassRing.isEmpty());
        assertEquals(0, residueClassRing.size());
    }

    @Test
    public void keySet() throws Exception {
        residueClassRing.put(1, 15);
        residueClassRing.put(3, 35);
        residueClassRing.put(5, 55);
        HashSet<Integer> expected = new HashSet<>(3);
        expected.add(1);
        expected.add(3);
        expected.add(5);
        assertEquals(expected, residueClassRing.keySet());
    }

    @Test
    public void values() throws Exception {
        residueClassRing.put(1, 15);
        residueClassRing.put(3, 35);
        residueClassRing.put(5, 55);
        ArrayList<Integer> expected = new ArrayList<>(3);
        expected.add(15);
        expected.add(35);
        expected.add(55);
        assertEquals(expected, residueClassRing.values());
    }

    @Test
    public void entrySet() throws Exception {
        residueClassRing.put(1, 15);
        residueClassRing.put(3, 35);
        residueClassRing.put(5, 55);
        HashSet<Pair<Integer, Integer>> expected = new HashSet<>(3);
        expected.add(Pair.of(1, 15));
        expected.add(Pair.of(3, 35));
        expected.add(Pair.of(5, 55));
        assertEquals(expected, residueClassRing.entrySet());
    }

    private final class ResidueClassRing implements LinearMapCharacteristic<Integer> {

        @Override
        public int width() {
            return 7;
        }

        @Override
        public Integer keyByIndex(int index) {
            return index;
        }

        @Override
        public int indexByKey(Integer key) {
            return key % 7;
        }
    }
}