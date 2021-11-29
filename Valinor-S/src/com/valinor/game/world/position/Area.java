package com.valinor.game.world.position;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;

/**
 * Created by Bart on 8/22/2015.
 */
public class Area {

    public final int swX;
    public final int neX;
    public final int swY;
    public final int neY;
    public int level;
    private boolean largeViewPort;

    public Area(int swX, int swY, int neX, int neY) {
        this.swX = swX;
        this.neX = neX;
        this.swY = swY;
        this.neY = neY;
    }

    public Area(int swX, int swY, int neX, int neY, int level) {
        this.swX = swX;
        this.neX = neX;
        this.swY = swY;
        this.neY = neY;
        this.level = level;
    }

    public Area(int rid) {
        swX = (rid >> 8) * 64;
        neX = swX + 63;
        swY = (rid & 0xFF) * 64;
        neY = swX + 63;
        level = 0;
    }

    public Area(Tile spawnTile, int radius) {
        this(spawnTile.x - radius, spawnTile.y - radius, spawnTile.x + radius, spawnTile.y + radius, spawnTile.level);
    }

    public int randomX() {
        return World.getWorld().get(swX, neX);
    }

    public int randomY() {
        return World.getWorld().get(swY, neY);
    }

    public Area(Tile botleft, Tile topright) {
        this(botleft.x, botleft.y, topright.x, topright.y);
    }

    public Area(Tile botleft, Tile topright, int level) {
        this(botleft.x, botleft.y, topright.x, topright.y, level);
    }

    public Area(Area area) {
        this(area.swX, area.swY, area.neX, area.neY, area.level);
    }

    public Area(Area area, int level) {
        this(area.swX, area.swY, area.neX, area.neY, level);
    }

    public boolean contains(Tile t) {
        return contains(t, false);
    }

    public boolean contains(Tile t, boolean checkZ) {
        if (t.x >= swX && t.x <= neX && t.y >= swY && t.y <= neY) {
            if (checkZ) {
                return t.level == level;
            } else {
                if (level < 3 && t.level < 3) {
                    return true;
                } else {
                    // deal with instances! allow 0-3 on your own instance gap
                    // see InstanceAreaZLimit class for proof
                    int lowerBound = (level / 4) * 4; // 16 /4 = 4th instance, *4 gets lowerBOund 16. if it was 17 it'd still be lowerbound 16.
                    //int zInInstance = level % 4;
                    //System.out.printf("test %s vs %s is lvl %s in %s-%s%n", z1, z2, zInInstance, lowerBound, (lowerBound + 3));
                    //System.out.println(t.level >= lowerBound && t.level <= (lowerBound + 3) ? t.level +" matched "+level : "reject "+t.level+" to "+level);
                    return t.level >= lowerBound && t.level <= (lowerBound + 3);
                }
            }
        }
        return false;
    }

    public boolean containsClosed(Tile t) {
        return containsClosed(t, false);
    }

    public boolean containsClosed(Tile t, boolean checkHeight) {
        return t.x >= swX && t.x < neX && t.y >= swY && t.y < neY && (!checkHeight || t.level == level);
    }

    public boolean contains(Mob e) {
        return contains(e.tile());
    }

    public boolean contains(Mob e, boolean checkHeight) {
        return contains(e.tile(), checkHeight);
    }

    public int x1() {
        return swX;
    }

    public int x2() {
        return neX;
    }

    public int z1() {
        return swY;
    }

    public int z2() {
        return neY;
    }

    public int level() {
        return level;
    }

    public int width() {
        return neX - swX;
    }

    public int length() {
        return neY - swY;
    }

    public Tile center() {
        return new Tile(swX + (neX - swX) / 2, swY + (neY - swY) / 2, level);
    }

    public Tile bottomLeft() {
        return new Tile(swX, swY, level);
    }

    public Tile bottomRight() {
        return new Tile(neX, swY, level);
    }

    public Tile topRight() {
        return new Tile(neX, neY, level);
    }

    public Tile topLeft() {
        return new Tile(swX, neY, level);
    }

    public Area enlarge(int tiles) {
        return new Area(swX - tiles, swY - tiles, neX + tiles, neY + tiles, level);
    }

    public Tile randomTile() {
        double wx = neX - swX;
        double wz = neY - swY;
        return new Tile(swX + (int) Math.round(wx * Math.random()), swY + (int) Math.round(wz * Math.random()), level);
    }

    public boolean within(Tile other, int size, int distance) {
        if (other == null)
            return false;
        final Tile otherEnd = new Tile(other.getX() + size - 1, other.getY() + size - 1);
        return !(swX - otherEnd.getX() - distance > 0) && !(neX - other.getX() + distance < 0) && !(neY - other.getY() + distance < 0) && !(swY - otherEnd.getY() - distance > 0);
    }

    public boolean inBounds(Tile p) {
        return p.getX() >= swX && p.getX() <= neX && p.getY() >= swY && p.getY() <= neY;
    }

    public Tile relative(Tile tile) {
        return bottomLeft().plus(tile);
    }

    public Area relative(Area area) {
        return new Area(swX + area.swX, swY + area.swY, swX + area.neX, swY + area.neY, level + area.level);
    }

    public Area toArea() {
        return new Area(swX, swY, neX, neY, level);
    }

    public Area transform(int minX, int minY, int maxX, int maxY, int level) {
        return new Area(this.swX + minX, this.swY + minY, this.neX + maxX, this.neY + maxY, this.level + level);
    }

    public boolean overlaps(Area other) {
        return Tile.overlaps(swX, swY, neX - swX + 1, neY - swY + 1, other.swX, other.swY, other.neX - other.swX + 1, other.neY - other.swY + 1);
    }

    public Area setLargeViewPort(boolean largeViewPort) {
        this.largeViewPort = largeViewPort;
        return this;
    }

    public boolean largeViewPort() {
        return largeViewPort;
    }

    @Override
    public String toString() {
        return "Area[" + swX + ".." + swY + ", " + neX + ".." + neY + "]";
    }
}
