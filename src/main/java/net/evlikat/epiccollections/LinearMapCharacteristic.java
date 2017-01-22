package net.evlikat.epiccollections;

import net.evlikat.epiccollections.util.Range;

/**
 * LinearMapCharacteristic.
 */
public interface LinearMapCharacteristic<K> {

    int width();

    K keyByIndex(int index);

    int indexByKey(K key);
}
