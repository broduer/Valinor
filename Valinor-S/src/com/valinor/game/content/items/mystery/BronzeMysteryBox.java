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
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 10, 2022
 */
public class BronzeMysteryBox extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == BRONZE_MYSTERY_BOX) {
                if (!player.inventory().contains(BRONZE_MYSTERY_BOX))
                    return true;
                player.inventory().remove(BRONZE_MYSTERY_BOX);

                Item reward = Utils.randomElement(REWARDS);
                player.inventory().addOrBank(reward);

                var amt = reward.getAmount();
                player.message("You open the bronze mystery box and found...");
                player.message("x"+amt+" "+reward.unnote().name()+".");

                Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a bronze mystery box and received x"+amt+" "+reward.unnote().name()+".", "boxes_opened");
                return true;
            }
        }
        return false;
    }

    private static final List<Item> REWARDS = Arrays.asList(
        new Item(ANGLERFISH+1, World.getWorld().random(25,100)),
        new Item(SUPER_COMBAT_POTION4+1, World.getWorld().random(25,100)),
        new Item(MORRIGANS_JAVELIN, World.getWorld().random(1,10)),
        new Item(MORRIGANS_THROWING_AXE, World.getWorld().random(1,10)),
        new Item(ANTIQUE_EMBLEM_TIER_1, 1),
        new Item(CRYSTAL_KEY, 1),
        new Item(DRAGON_CHAINBODY_3140, 1),
        new Item(DRAGON_PLATELEGS, 1),
        new Item(DRAGON_PLATESKIRT, 1),
        new Item(COINS_995, 10_000_000)
    );

    public static void rollForBronzeMysteryBox(Player killer, Player target) {
        var roll = switch (killer.getMemberRights()) {
            case NONE -> 20;
            case SAPPHIRE_MEMBER -> 19;
            case EMERALD_MEMBER -> 18;
            case RUBY_MEMBER -> 17;
            case DIAMOND_MEMBER -> 16;
            case DRAGONSTONE_MEMBER -> 15;
            case ONYX_MEMBER -> 13;
            case ZENYTE_MEMBER -> 10;
        };

        if(World.getWorld().rollDie(roll,1)) {
            killer.getInventory().addOrDrop(new Item(BRONZE_MYSTERY_BOX));
            killer.message(Color.PURPLE.wrap("You've found a bronze mystery box searching the corpse of "+target.getUsername()+"."));
        }
    }

}
