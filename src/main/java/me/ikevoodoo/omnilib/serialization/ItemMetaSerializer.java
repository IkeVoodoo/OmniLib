package me.ikevoodoo.omnilib.serialization;

import me.ikevoodoo.omnilib.serialization.providers.SerializerProvider;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemMetaSerializer implements Serializer<ItemMeta> {

    static {
        if(!SerializerProvider.has(ItemMeta.class)) {
            SerializerProvider.register(ItemMeta.class, new ItemMetaSerializer());
        }
    }

    @Override
    public byte[] serialize(ItemMeta meta) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream os = new DataOutputStream(stream);
        try {
            os.writeBoolean(meta != null);
            if (meta != null) {
                os.writeBoolean(meta.hasDisplayName());
                if (meta.hasDisplayName()) {
                    os.writeUTF(meta.getDisplayName());
                }
                os.writeBoolean(meta.hasLore());
                if (meta.hasLore()) {
                    os.writeInt(meta.getLore().size());
                    for (String s : meta.getLore()) {
                        os.writeUTF(s);
                    }
                }

                os.writeBoolean(meta.hasLocalizedName());
                if (meta.hasLocalizedName()) {
                    os.writeUTF(meta.getLocalizedName());
                }

                os.writeBoolean(meta.hasCustomModelData());
                if (meta.hasCustomModelData()) {
                    os.writeInt(meta.getCustomModelData());
                }

                os.writeBoolean(meta.hasEnchants());
                if (meta.hasEnchants()) {
                    os.writeInt(meta.getEnchants().size());
                    for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
                        os.writeUTF(entry.getKey().getKey().toString());
                        os.writeInt(entry.getValue());
                    }
                }

                os.writeInt(meta.getItemFlags().size());
                for (ItemFlag flag : meta.getItemFlags()) {
                    os.writeUTF(flag.name());
                }

                os.writeBoolean(meta.isUnbreakable());

                os.writeBoolean(meta.hasAttributeModifiers());
                if (meta.hasAttributeModifiers()) {
                    os.writeInt(meta.getAttributeModifiers().size());
                    meta.getAttributeModifiers().forEach((s, attributeModifier) -> {
                        try {
                            os.writeUTF(s.name());
                            os.writeUTF(attributeModifier.getUniqueId().toString());
                            os.writeUTF(attributeModifier.getName());
                            os.writeDouble(attributeModifier.getAmount());
                            os.writeUTF(attributeModifier.getOperation().name());
                            os.writeBoolean(attributeModifier.getSlot() != null);
                            if (attributeModifier.getSlot() != null) {
                                os.writeUTF(attributeModifier.getSlot().name());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stream.toByteArray();
    }

    public ItemMeta deserialize(ItemMeta meta, byte[] data) {
        ByteArrayInputStream b = new ByteArrayInputStream(data);
        DataInputStream is = new DataInputStream(b);
        try {
            if (is.readBoolean()) {
                meta.setDisplayName(is.readUTF());
            }
            if (is.readBoolean()) {
                List<String> lore = new ArrayList<>();
                int size = is.readInt();
                for (int i = 0; i < size; i++) {
                    lore.add(is.readUTF());
                }
            }
            if (is.readBoolean()) {
                meta.setLocalizedName(is.readUTF());
            }
            if (is.readBoolean()) {
                meta.setCustomModelData(is.readInt());
            }
            if (is.readBoolean()) {
                int size = is.readInt();
                for (int i = 0; i < size; i++) {
                    Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(is.readUTF()));
                    if (enchantment != null) {
                        meta.addEnchant(enchantment, is.readInt(), true);
                    } else {
                        is.readInt();
                    }
                }
            }
            if (is.readBoolean()) {
                int size = is.readInt();
                for (int i = 0; i < size; i++) {
                    meta.addItemFlags(ItemFlag.valueOf(is.readUTF()));
                }
            }
            meta.setUnbreakable(is.readBoolean());
            if (is.readBoolean()) {
                int size = is.readInt();
                for (int i = 0; i < size; i++) {
                    meta.addAttributeModifier(Attribute.valueOf(is.readUTF()), new AttributeModifier(UUID.fromString(is.readUTF()), is.readUTF(), is.readDouble(), AttributeModifier.Operation.valueOf(is.readUTF())));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return meta;
    }

    /**
     * Use {@link #deserialize(ItemMeta, byte[])} instead
     *
     * @return null
     * */
    @Override
    public ItemMeta deserialize(byte[] bytes) {
        return null;
    }
}
