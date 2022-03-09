package me.ikevoodoo.omnilib.storage.blocks;

import me.ikevoodoo.omnilib.storage.Storage;

import java.io.File;
import java.util.HashMap;

public class BlockStorage implements Storage {

    private final BlockStorageManager manager;
    private final HashMap<String, Object> values = new HashMap<>();
    private final File file;

    protected BlockStorage(BlockStorageManager manager, File file) {
        this.manager = manager;
        this.file = file;
    }

    @Override
    public int getInt(String key) {
        return 0;
    }

    @Override
    public long getLong(String key) {
        return 0;
    }

    @Override
    public double getDouble(String key) {
        return 0;
    }

    @Override
    public boolean getBoolean(String key) {
        return false;
    }

    @Override
    public String getString(String key) {
        return null;
    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public void set(String key, Object value) {

    }

    @Override
    public void remove(String key) {

    }
}
