package me.ikevoodoo.omnilib.storage.blocks;

import me.ikevoodoo.omnilib.storage.StorageManager;
import me.ikevoodoo.omnilib.utils.StringUtils;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;

public class BlockStorageManager implements StorageManager<Block, BlockStorage> {

    private final HashMap<Block, BlockStorage> storages = new HashMap<>();
    private final File file;
    private final Plugin plugin;

    private BlockStorageManager(Plugin plugin, String name) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), name);
        this.file.mkdirs();
    }

    public static BlockStorageManager from(Plugin plugin, String name) {
        return new BlockStorageManager(plugin, name);
    }

    @Override
    public BlockStorage get(Block key) {
        return storages.get(key);
    }

    @Override
    public BlockStorage add(Block key) {
        return storages.put(key, new BlockStorage(this, new File(file, StringUtils.random(16))));
    }

    @Override
    public void rem(Block key) {
        storages.remove(key);
    }

    @Override
    public void save() {

    }

    @Override
    public void load() {

    }
}
