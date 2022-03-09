package me.ikevoodoo.omnilib.serialization;

import me.ikevoodoo.omnilib.serialization.providers.SerializerProvider;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class StackSerializer implements Serializer<ItemStack> {

    static {
        if(!SerializerProvider.has(ItemStack.class)) {
            SerializerProvider.register(ItemStack.class, new StackSerializer());
        }

        // Ensure that at least one ItemMeta serializer is present
        if(!SerializerProvider.has(ItemMeta.class)) {
            SerializerProvider.register(ItemMeta.class, new ItemMetaSerializer());
        }
    }

    @Override
    public byte[] serialize(ItemStack object) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream os = new DataOutputStream(b);
        try {
            os.writeUTF(object.getType().toString());
            os.writeInt(object.getAmount());
            ItemMeta meta = object.getItemMeta();
            ItemMetaSerializer serializer = (ItemMetaSerializer) SerializerProvider.get(ItemMeta.class);
            os.write(serializer.serialize(meta));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b.toByteArray();
    }

    @Override
    public ItemStack deserialize(byte[] bytes) {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        DataInputStream is = new DataInputStream(b);
        try {
            ItemStack stack = new ItemStack(Material.valueOf(is.readUTF()), is.readInt());
            if(is.readBoolean()) {
                ItemMeta meta = stack.getItemMeta();
                ItemMetaSerializer serializer = (ItemMetaSerializer) SerializerProvider.get(ItemMeta.class);
                stack.setItemMeta(serializer.deserialize(meta, is.readAllBytes()));
            }
            return stack;
        } catch (Exception e) {
            return null;
        }
    }
}
