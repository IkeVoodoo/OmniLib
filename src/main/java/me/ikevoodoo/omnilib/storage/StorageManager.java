package me.ikevoodoo.omnilib.storage;

public interface StorageManager<K, V> {

    V    get(K key);
    V    add(K key);
    void rem(K key);

    void save();
    void load();

}
