package com.valinor.scene;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 14, 2022
 */
public class WorldPoint {

    private final int x;
    private final int y;
    private final int z;

    public WorldPoint(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public WorldPoint(int x, int y) {
        this(x, y, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof WorldPoint)) {
            return false;
        }
        WorldPoint worldPoint = (WorldPoint) other;
        return worldPoint.x == x && worldPoint.y == y && worldPoint.z == z;
    }

    public int getLocalX() {
        return x - 8 * getRegionX();
    }

    public int getLocalY() {
        return y - 8 * getRegionY();
    }

    public int getRegionX() {
        return (x >> 3) - 6;
    }

    public int getRegionY() {
        return (y >> 3) - 6;
    }

    public int getRegionID() {
        int regionX = getX() >> 3;
        int regionY = getY() >> 3;
        return (regionX / 8 << 8) + regionY / 8;
    }

    public boolean isWithinDistance(WorldPoint other) {
        if (z != other.z) {
            return false;
        }
        int deltaX = other.x - x;
        int deltaY = other.y - y;
        return deltaX <= 14 && deltaX >= -15 && deltaY <= 14 && deltaY >= -15;
    }
}
