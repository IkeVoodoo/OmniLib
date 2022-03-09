package me.ikevoodoo.omnilib.geometry.regions;

import me.ikevoodoo.omnilib.geometry.Region;
import me.ikevoodoo.omnilib.geometry.RegionPopulator;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PolygonRegion implements Region {

    private final List<Location> points;

    private World world;
    private double minX, maxX;
    private double minY, maxY;
    private double minZ, maxZ;

    public PolygonRegion(Location... points) {
        this.points = new ArrayList<>();
        Collections.addAll(this.points, points);
        recalculate();
    }

    @Override
    public void populate(RegionPopulator populator) {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                for (int z = 0; z < getLength(); z++) {
                    if (contains(world, x, y, z)) {
                        populator.visit(world.getBlockAt(x, y, z), this);
                    }
                }
            }
        }
    }

    @Override
    public boolean contains(World world, double x, double y, double z) {
        boolean contains = false;
        for (int i = 0, j = points.size() - 1; i < points.size(); j = i++) {
            boolean zCondition = points.get(i).getZ() > z != points.get(j).getZ() > z;
            boolean yCondition = points.get(i).getY() > y != points.get(j).getY() > y;
            boolean xCondition = points.get(i).getX() > x != points.get(j).getX() > x;
            if (zCondition && yCondition && xCondition) {
                contains = !contains;
            }
        }
        return contains;
    }

    public double getWidth() {
        return getMaxX() - getMinX();
    }

    public double getHeight() {
        return getMaxY() - getMinY();
    }

    public double getLength() {
        return getMaxZ() - getMinZ();
    }

    public double getMinX() {
        return minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getMinZ() {
        return minZ;
    }

    public double getMaxZ() {
        return maxZ;
    }

    private void recalculate() {
        if(points.size() > 0) {
            recalculateBounds();
            world = points.get(0).getWorld();
        }
    }

    private void recalculateBounds() {
        minX = Collections.min(points, Comparator.comparingDouble(Location::getX)).getX();
        maxX = Collections.max(points, Comparator.comparingDouble(Location::getX)).getX();

        minY = Collections.min(points, Comparator.comparingDouble(Location::getY)).getY();
        maxY = Collections.max(points, Comparator.comparingDouble(Location::getY)).getY();

        minZ = Collections.min(points, Comparator.comparingDouble(Location::getZ)).getZ();
        maxZ = Collections.max(points, Comparator.comparingDouble(Location::getZ)).getZ();
    }
}
