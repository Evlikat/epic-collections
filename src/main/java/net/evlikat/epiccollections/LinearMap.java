package net.evlikat.epiccollections;

import net.evlikat.epiccollections.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Map for keys representing full order
 *
 * Can be used as Residue Class Ring, as map with Date interval key.
 * @author rprokhorov (evlikat)
 * @version 1.0
 */
public class LinearMap<K, V> implements Map<K, V> {

    private final LinearMapCharacteristic<K> characteristic;
    private final ArrayList<V> data;
    private int size;

    public LinearMap(LinearMapCharacteristic<K> characteristic) {
        this.characteristic = characteristic;
        int width = characteristic.width();
        this.data = new ArrayList<>(width);
        for (int i = 0; i < width; i++) {
            data.add(null);
        }
    }

    @SuppressWarnings("unchecked")
    private int valueIndex(Object key) {
        if (key == null)
            throw new NullPointerException("key can not be null");
        return characteristic.indexByKey((K) key);
    }

    /**
     * O(1)
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * O(1)
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * O(1)
     *
     * @return
     */
    public boolean containsKey(Object key) {
        return data.get(valueIndex(key)) != null;
    }

    /**
     * O(n)
     *
     * @return
     */
    public boolean containsValue(Object value) {
        if (isEmpty()) {
            return false;
        }
        for (V item : data) {
            if (value.equals(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * O(1)
     *
     * @param key
     * @return
     */
    public V get(Object key) {
        if (isEmpty()) {
            return null;
        }
        return data.get(valueIndex(key));
    }

    /**
     * O(1)
     *
     * @param key
     * @param newValue
     * @return
     */
    public V put(K key, V newValue) {
        int index = valueIndex(key);
        V oldValue = isEmpty() ? null : data.get(index);
        data.set(index, newValue);
        if (newValue != null && oldValue == null) {
            size++;
        }
        if (newValue == null && oldValue != null) {
            size--;
        }
        return oldValue;
    }

    /**
     * O(1)
     *
     * @param key
     * @return
     */
    public V remove(Object key) {
        if (isEmpty()) {
            return null;
        }
        int index = valueIndex(key);
        V oldValue = data.get(index);
        data.set(index, null);
        if (oldValue != null) {
            size--;
        }
        return oldValue;
    }

    /**
     * O(m)
     *
     * @param m
     */
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * O(n)
     */
    public void clear() {
        for (int i = 0; i < data.size(); i++) {
            data.set(i, null);
        }
        size = 0;
    }

    /**
     * O(n)
     *
     * @return
     */
    public Set<K> keySet() {
        LinkedHashSet<K> result = new LinkedHashSet<>();
        for (int i = 0; i < data.size(); i++) {
            K k;
            if (data.get(i) != null && (k = characteristic.keyByIndex(i)) != null) {
                result.add(k);
            }
        }
        return result;
    }

    /**
     * O(n)
     *
     * @return
     */
    public Collection<V> values() {
        ArrayList<V> result = new ArrayList<V>(size);
        for (V v : data) {
            if (v != null) {
                result.add(v);
            }
        }
        return result;
    }

    /**
     * O(n)
     *
     * @return
     */
    public Set<Entry<K, V>> entrySet() {
        LinkedHashSet<Entry<K, V>> result = new LinkedHashSet<>();
        for (int i = 0; i < data.size(); i++) {
            V value = data.get(i);
            K k;
            if (value != null && (k = characteristic.keyByIndex(i)) != null) {
                result.add(Pair.of(k, value));
            }
        }
        return result;
    }
}
