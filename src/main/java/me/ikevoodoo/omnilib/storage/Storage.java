package me.ikevoodoo.omnilib.storage;

public interface Storage {

    int       getInt(String key);
    long      getLong(String key);
    double    getDouble(String key);
    boolean   getBoolean(String key);
    String    getString(String key);
    Object    get(String key);
    void      set(String key, Object value);
    void      remove(String key);

}
