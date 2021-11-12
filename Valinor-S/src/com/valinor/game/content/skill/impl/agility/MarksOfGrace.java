package com.valinor.game.content.skill.impl.agility;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;

import java.util.List;

/**
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * mei 07, 2020
 */
public class MarksOfGrace {

    private static final int MARK_LIFETIME = 10 * 60 * 1000; // 10 minutes

    public static void trySpawn(Player player, List<Tile> tiles, int rarity, int threshold) {
        int odds = 1;

        // Donator perks grant extra odds
        switch (player.getMemberRights()) {
            case ONYX_MEMBER -> odds += 15;
            case DRAGONSTONE_MEMBER -> odds += 10;
            case DIAMOND_MEMBER -> odds += 8;
            case EMERALD_MEMBER -> odds += 4;
            case SAPPHIRE_MEMBER -> odds += 2;
        }

        if (player.skills().level(Skills.AGILITY) > threshold + 20) {
            odds = (int) Math.max(1, (odds * 0.7)); // You don't want to end up in this :)
        }

        // Check for the odds. :)
        if (Utils.rollDie(rarity, odds)) {

            int MARK_OF_GRACE = 11849;
            GroundItem item = new GroundItem(new Item(MARK_OF_GRACE), Utils.randomElement(tiles), player);
            item.setTimer(MARK_LIFETIME);

            GroundItemHandler.createGroundItem(item);
        }
    }
}
