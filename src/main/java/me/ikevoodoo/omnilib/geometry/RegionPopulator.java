package me.ikevoodoo.omnilib.geometry;

import org.bukkit.Material;
import org.bukkit.block.Block;

public interface RegionPopulator {

    Material visit(Block input, Region region);

}
