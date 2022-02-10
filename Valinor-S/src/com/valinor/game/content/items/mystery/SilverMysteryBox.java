package com.valinor.game.content.items.mystery;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;

import static com.valinor.util.CustomItemIdentifiers.BRONZE_MYSTERY_BOX;
import static com.valinor.util.CustomItemIdentifiers.SILVER_MYSTERY_BOX;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 10, 2022
 */
public class SilverMysteryBox extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == SILVER_MYSTERY_BOX) {
                if (!player.inventory().contains(SILVER_MYSTERY_BOX))
                    return true;
                player.inventory().remove(SILVER_MYSTERY_BOX);

                Item reward = Utils.randomElement(REWARDS);
                player.inventory().addOrBank(reward);

                var amt = reward.getAmount();
                player.message("You open the silver mystery box and found...");
                player.message("x"+amt+" "+reward.unnote().name()+".");

                Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a silver mystery box and received x"+amt+" "+reward.unnote().name()+".", "boxes_opened");
                return true;
            }
        }
        return false;
    }

    private static final List<Item> REWARDS = Arrays.asList(
        new Item(ANGLERFISH+1, World.getWorld().random(25,250)),
        new Item(SUPER_COMBAT_POTION4+1, World.getWorld().random(25,250)),
        new Item(MORRIGANS_JAVELIN, World.getWorld().random(1,25)),
        new Item(MORRIGANS_THROWING_AXE, World.getWorld().random(1,25)),
        new Item(ANTIQUE_EMBLEM_TIER_1, 1),
        new Item(ANTIQUE_EMBLEM_TIER_2, 1),
        new Item(CRYSTAL_KEY, 1),
        new Item(DRAGON_CHAINBODY_3140, 1),
        new Item(DRAGON_PLATELEGS, 1),
        new Item(DRAGON_PLATESKIRT, 1),
        new Item(BERSERKER_RING, 1),
        new Item(SEERS_RING, 1),
        new Item(WARRIOR_RING, 1),
        new Item(ARCHERS_RING, 1),
        new Item(DRAGON_JAVELIN, World.getWorld().random(10,50)),
        new Item(DIVINE_SUPER_COMBAT_POTION4+1, World.getWorld().random(10,50))
    );

    public static void rollForSilverMysteryBox(Player killer, Player target) {
        var roll = switch (killer.getMemberRights()) {
            case NONE -> 40;
            case SAPPHIRE_MEMBER -> 38;
            case EMERALD_MEMBER -> 36;
            case RUBY_MEMBER -> 34;
            case DIAMOND_MEMBER -> 32;
            case DRAGONSTONE_MEMBER -> 30;
            case ONYX_MEMBER -> 27;
            case ZENYTE_MEMBER -> 24;
        };

        if(World.getWorld().rollDie(roll,1)) {
            killer.getInventory().addOrDrop(new Item(SILVER_MYSTERY_BOX));
            killer.message(Color.PURPLE.wrap("You've found a silver mystery box searching the corpse of "+target.getUsername()+"."));
        }
    }

}
