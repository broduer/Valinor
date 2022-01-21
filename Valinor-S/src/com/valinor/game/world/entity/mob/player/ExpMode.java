package com.valinor.game.world.entity.mob.player;

/**
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * juni 21, 2020
 */
public enum ExpMode {

    ROOKIE("Rookie",1, 50D,0D),
    CHALLENGER("Challenger",2, 30D,5D),
    GLADIATOR("Gladiator", 3,7.5D,10D);

    private final String name;
    private final int uid;
    private final double expMultiplier;
    private final double dropRateMultiplier;
    private static ExpMode[] cache;

    ExpMode(String name, int uid, double expMultiplier, double dropRateMultiplier) {
        this.name = name;
        this.uid = uid;
        this.expMultiplier = expMultiplier;
        this.dropRateMultiplier = dropRateMultiplier;
    }

    public int uid() {
        return uid;
    }

    public double getExpMultiplier() {
        return expMultiplier;
    }

    public double getDropRateMultiplier() {
        return dropRateMultiplier;
    }

    public static ExpMode forUid(int uid) {
        if (cache == null) {
            cache = values();
        }

        for (ExpMode m : cache) {
            if (m.uid == uid)
                return m;
        }

        return null;
    }

    public String toName() {
        return this.name;
    }
}
