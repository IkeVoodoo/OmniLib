package me.ikevoodoo.omnilib.geometry;

import org.bukkit.Location;
import org.bukkit.World;


public interface Region {

    void populate(RegionPopulator populator);

    default boolean contains(Location loc) {
        return contains(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
    }

    boolean contains(World world, double x, double y, double z);

}
