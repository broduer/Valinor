package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare;

import com.valinor.game.world.position.Tile;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 27, 2021
 */
public enum FlowerRotary {

    ALPHA(new int[]{1, 1}, new int[]{-1, -1}),
    BETA(new int[]{1, -1}, new int[]{-1, 1}),
    GAMMA(new int[]{-1, 1}, new int[]{1, -1}),
    DELTA(new int[]{-1, 1}, new int[]{1, -1});

    private final int[] light, dark;

    FlowerRotary(int[] light, int[] dark) {
        this.light = light;
        this.dark = dark;
    }

    public int[] getLight() {
        return light;
    }

    public int[] getDark() {
        return dark;
    }

    public boolean safe(Tile center, Tile location) {
        Tile n = center.transform(getLight()[0] * 10, getLight()[1] * 10, 0);
        if (n.getX() > center.getX()) {
            if (location.getX() < center.getX()) {
                return false;
            }
        } else {
            if (location.getX() > center.getX()) {
                return false;
            }
        }
        if (n.getY() > center.getY()) {
            return location.getY() >= center.getY();
        } else {
            return location.getY() <= center.getY();
        }
    }
}
