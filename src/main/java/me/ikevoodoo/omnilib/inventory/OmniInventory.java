package me.ikevoodoo.omnilib.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OmniInventory {

    private Inventory inventory;
    private String title;

    private final List<OmniInventory> mirrors;

    protected OmniInventory() {
        inventory = Bukkit.createInventory(null, 9);
        title = inventory.getType().getDefaultTitle();
        mirrors = new CopyOnWriteArrayList<>();
    }

    public boolean isEmpty() {
        for (int i = 0; i < inventory.getSize(); i++) {
            if(!isAir(i)) return false;
        }
        return true;
    }

    public boolean isAir(int slot) {
        ItemStack item = inventory.getItem(slot);
        return item == null || item.getType() == Material.AIR;
    }

    public void fill(ItemStack item) {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, item);
        }
        contentsUpdated();
    }

    public void clear() {
        inventory.clear();
        contentsUpdated();
    }

    public void sort() {
        sort(InventoryConstants.emptyWeights);
    }

    public void sort(HashMap<Material, Integer> weights) {
        ItemStack[] items = inventory.getStorageContents();
        Arrays.sort(items, (stack1, stack2) -> {
            if (stack1 == null || stack1.getType() == Material.AIR) return 1;
            if (stack2 == null || stack2.getType() == Material.AIR) return -1;
            int w1 = weights.getOrDefault(stack1.getType(), 0);
            int w2 = weights.getOrDefault(stack2.getType(), 0);
            if (w1 == w2) {
                int amt1 = stack1.getAmount();
                int amt2 = stack2.getAmount();
                if (amt1 == amt2) {
                    return stack1.getType().name().compareTo(stack2.getType().name());
                }
                return amt1 > amt2 ? 1 : -1;
            }
            return w1 > w2 ? 1 : -1;

        });
        inventory.setStorageContents(items);
        contentsUpdated();
    }

    public String getTitle() {
        return title;
    }

    public void setSize(int size) {
        recreate(size, getTitle());
    }

    public int getSize() {
        return inventory.getSize();
    }

    public void setTitle(String title) {
        this.title = title;
        recreate(getSize(), title);
    }

    public void addMirror(OmniInventory mirror) {
        if(mirror == this || mirror == null || mirror.mirrors.contains(this)) return;
        mirrors.add(mirror);
    }

    public void removeMirror(OmniInventory mirror) {
        mirrors.remove(mirror);
    }

    public void setItem(int slot, ItemStack item) {
        inventory.setItem(slot, item);
        contentsUpdated();
    }

    public void setItem(int slot, Material material) {
        if(material == Material.AIR) setItem(slot, InventoryConstants.AIR);
        setItem(slot, new ItemStack(material));
    }

    public void removeItem(int slot) {
        setItem(slot, Material.AIR);
    }

    public void setContents(ItemStack[] stacks) {
        inventory.setContents(stacks);
        contentsUpdated();
    }

    public ItemStack[] getContents() {
        return inventory.getContents();
    }

    private void contentsUpdated() {
        for (OmniInventory mirror : mirrors) {
            mirror.setContents(getContents());
        }
    }

    private void recreate(int size, String title) {
        ItemStack[] items = inventory.getStorageContents();
        boolean wasEmpty = isEmpty();
        List<HumanEntity> viewers = inventory.getViewers();
        inventory = Bukkit.createInventory(null, size, title);
        if(!wasEmpty) {
            if (items.length > size)
                items = Arrays.copyOf(items, size - 1);
            inventory.setStorageContents(items);
        }
        viewers.forEach(viewer -> {
            viewer.closeInventory();
            viewer.openInventory(inventory);
        });
    }

}
