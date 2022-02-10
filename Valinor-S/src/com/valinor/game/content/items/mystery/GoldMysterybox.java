package com.valinor.game.content.items.mystery;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.DIVINE_SUPER_COMBAT_POTION4;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 10, 2022
 */
public class GoldMysterybox extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == GOLD_MYSTERY_BOX) {
                if (!player.inventory().contains(GOLD_MYSTERY_BOX))
                    return true;
                player.inventory().remove(GOLD_MYSTERY_BOX);

                Item reward;
                boolean rare = false;
                if(World.getWorld().rollDie(25,1)) {
                    reward = Utils.randomElement(RARE_REWARDS);
                    rare = true;
                } else {
                    reward = Utils.randomElement(OTHER_REWARDS);
                }
                player.inventory().addOrBank(reward);

                var amt = reward.getAmount();
                player.message("You open the gold mystery box and found...");
                player.message("x"+amt+" "+reward.unnote().name()+".");

                if(rare) {
                    World.getWorld().sendWorldMessage("<img=1081><col=0052cc>" + player.getUsername() + " just received " + Utils.getVowelFormat(reward.unnote().name()) + " from a gold mystery box!");
                }
                Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a gold mystery box and received x"+amt+" "+reward.unnote().name()+".", "boxes_opened");
                return true;
            }
        }
        return false;
    }

    private static final List<Item> OTHER_REWARDS = Arrays.asList(
        new Item(ANGLERFISH+1, World.getWorld().random(25,500)),
        new Item(SUPER_COMBAT_POTION4+1, World.getWorld().random(25,500)),
        new Item(MORRIGANS_JAVELIN, World.getWorld().random(1,50)),
        new Item(MORRIGANS_THROWING_AXE, World.getWorld().random(1,50)),
        new Item(ANTIQUE_EMBLEM_TIER_1, 1),
        new Item(ANTIQUE_EMBLEM_TIER_2, 1),
        new Item(ANTIQUE_EMBLEM_TIER_3, 1),
        new Item(CRYSTAL_KEY, 1),
        new Item(DRAGON_BOOTS, 1),
        new Item(RANGER_BOOTS, 1),
        new Item(INFINITY_BOOTS, 1),
        new Item(BERSERKER_RING_I, 1),
        new Item(SEERS_RING_I, 1),
        new Item(WARRIOR_RING_I, 1),
        new Item(ARCHERS_RING_I, 1),
        new Item(DRAGON_JAVELIN, World.getWorld().random(25,100)),
        new Item(DIVINE_SUPER_COMBAT_POTION4+1, World.getWorld().random(10,50))
    );

    private static final List<Item> RARE_REWARDS = Arrays.asList(
        new Item(BRIMSTONE_RING, 1),
        new Item(TOME_OF_FIRE, 1),
        new Item(DARK_BOW, 1),
        new Item(ABYSSAL_WHIP, 1),
        new Item(SMOKE_BATTLESTAFF, 1),
        new Item(MASTER_WAND, 1),
        new Item(RUNE_POUCH_I, 1),
        new Item(DRAGON_BOOTS_ORNAMENT_KIT, 1),
        new Item(DRAGON_DEFENDER_ORNAMENT_KIT, 1)
    );

    public static void rollForGoldMysteryBox(Player killer, Player target) {
        var roll = switch (killer.getMemberRights()) {
            case NONE -> 120;
            case SAPPHIRE_MEMBER -> 115;
            case EMERALD_MEMBER -> 110;
            case RUBY_MEMBER -> 105;
            case DIAMOND_MEMBER -> 100;
            case DRAGONSTONE_MEMBER -> 95;
            case ONYX_MEMBER -> 85;
            case ZENYTE_MEMBER -> 75;
        };

        if(World.getWorld().rollDie(roll,1)) {
            killer.getInventory().addOrDrop(new Item(GOLD_MYSTERY_BOX));
            killer.message(Color.PURPLE.wrap("You've found a gold mystery box searching the corpse of "+target.getUsername()+"."));
        }
    }

}
