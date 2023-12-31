package com.valinor.game.content.areas.wilderness.content.wilderness_activity;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.areas.impl.WildernessArea;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 30, 2022
 */
public enum WildernessLocations {

    EDGEVILLE {
        @Override
        public String location() {
            return "Edgeville";
        }

        @Override
        public int[] regionIds() {
            return new int[] { 12343, 12087 };
        }

    },

    DEMONIC_RUINS {
        @Override
        public String location() {
            return "Demonic Ruins";
        }

        @Override
        public int[] regionIds() {
            return new int[] { 13372, 12860, 13116 };
        }

    },

    REVENANT_CAVES {
        @Override
        public String location() {
            return "Revenants cave";
        }

        @Override
        public int[] regionIds() {
            return new int[] { 12701, 12702, 12703, 12957, 12958, 12959 };
        }

    },

    MAGE_BANK {

        @Override
        public String location() {
            return "Mage bank";
        }

        @Override
        public int[] regionIds() {
            return new int[] { 12605, 12349, 12093 };
        }
    };

    /**
     * If the player is in the region
     *
     * @param player
     *            The player
     */
    public boolean isInArea(Player player) {
        for (int region : regionIds()) {
            if (player.tile().region() == region && WildernessArea.inWilderness(player.tile())) {
                return true;
            }
        }
        return false;
    }

    /**
     * The location
     */
    public abstract String location();

    /**
     * The region ids relevant
     */
    public abstract int[] regionIds();

}
