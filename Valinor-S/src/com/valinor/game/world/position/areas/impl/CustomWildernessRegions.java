package com.valinor.game.world.position.areas.impl;

/**
 * This class utilizes all custom wilderness regions.
 *
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * December 7, 2021
 */
public enum CustomWildernessRegions {

    MEMBER_CAVE_1(9369, 20),
    MEMBER_CAVE_2(9370, 20);

    public final int region;
    public final int level;

    CustomWildernessRegions(final int region, final int level){
        this.region = region;
        this.level = level;
    }

    public static CustomWildernessRegions byRegion(int id) {
        for (CustomWildernessRegions customWildernessRegions : CustomWildernessRegions.values()) {
            if(customWildernessRegions.region == id) {
                return customWildernessRegions;//Found region
            }
        }
        //Nothing was found
        return null;
    }
}
