package me.ikevoodoo.omnilib.geometry.regions;

import me.ikevoodoo.omnilib.geometry.Region;
import me.ikevoodoo.omnilib.geometry.RegionPopulator;
import me.ikevoodoo.omnilib.utils.MathUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class RectangularRegion implements Region {

    private Location start, end;

    public RectangularRegion(Location start, Location end) {
        this.start = start;
        this.end = end;

        if (start.getWorld() != end.getWorld()) {
            throw new IllegalArgumentException("Start and end must be in the same world");
        }

        if(start.getWorld() == null || end.getWorld() == null) {
            throw new IllegalArgumentException("Start and end must have a world");
        }
    }

    @Override
    public void populate(RegionPopulator populator) {
        World world = start.getWorld();
        double x1 = Math.min(start.getX(), end.getX());
        double x2 = Math.max(start.getX(), end.getX());
        double y1 = Math.min(start.getY(), end.getY());
        double y2 = Math.max(start.getY(), end.getY());
        double z1 = Math.min(start.getZ(), end.getZ());
        double z2 = Math.max(start.getZ(), end.getZ());
        for (double x = x1; x <= x2; x++) {
            for (double y = y1; y <= y2; y++) {
                for (double z = z1; z <= z2; z++) {
                    Block block = world.getBlockAt((int)x, (int)y, (int)z);
                    world.setType((int)x, (int)y, (int)z, populator.visit(block, this));
                }
            }
        }
    }

    @Override
    public boolean contains(World world, double x, double y, double z) {
        return MathUtils.isBetween(x, start.getX(), end.getX())
                && MathUtils.isBetween(y, start.getY(), end.getY())
                && MathUtils.isBetween(z, start.getZ(), end.getZ());
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public void setEnd(Location end) {
        this.end = end;
    }
}
