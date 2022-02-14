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
import static com.valinor.util.CustomItemIdentifiers.PLATINUM_MYSTERY_BOX;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 10, 2022
 */
public class PlatinumMysteryBox extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == PLATINUM_MYSTERY_BOX) {
                if (!player.inventory().contains(PLATINUM_MYSTERY_BOX))
                    return true;
                player.inventory().remove(PLATINUM_MYSTERY_BOX);

                Item reward;
                boolean rare = false;
                if(World.getWorld().rollDie(10,1)) {
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
                    World.getWorld().sendWorldMessage("<img=452><shad=0><col=0052cc>" + player.getUsername() + " just received " + Utils.getVowelFormat(reward.unnote().name()) + " from a gold mystery box!");
                }
                Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a gold mystery box and received x"+amt+" "+reward.unnote().name()+".", "boxes_opened");
                return true;
            }
        }
        return false;
    }

    private static final List<Item> OTHER_REWARDS = Arrays.asList(
        new Item(ANGLERFISH+1, World.getWorld().random(250,2500)),
        new Item(SUPER_COMBAT_POTION4+1, World.getWorld().random(250,2500)),
        new Item(MORRIGANS_JAVELIN, World.getWorld().random(10,75)),
        new Item(MORRIGANS_THROWING_AXE, World.getWorld().random(10,75)),
        new Item(ANTIQUE_EMBLEM_TIER_8, 1),
        new Item(ANTIQUE_EMBLEM_TIER_9, 1),
        new Item(ANTIQUE_EMBLEM_TIER_10, 1),
        new Item(CRYSTAL_KEY, World.getWorld().random(5,10)),
        new Item(BANDOS_GODSWORD, 1),
        new Item(SARADOMIN_GODSWORD, 1),
        new Item(ZAMORAK_GODSWORD, 1),
        new Item(BERSERKER_RING_I, 1),
        new Item(SEERS_RING_I, 1),
        new Item(WARRIOR_RING_I, 1),
        new Item(ARCHERS_RING_I, 1),
        new Item(DRAGON_JAVELIN, World.getWorld().random(100,500)),
        new Item(DIVINE_SUPER_COMBAT_POTION4+1, World.getWorld().random(50,100)),
        new Item(BRIMSTONE_RING, 1),
        new Item(KRAKEN_TENTACLE, 1),
        new Item(AMULET_OF_FURY, 1),
        new Item(MAGES_BOOK, 1),
        new Item(TOME_OF_FIRE, 1),
        new Item(VERACS_ARMOUR_SET, 1),
        new Item(TORAGS_ARMOUR_SET, 1),
        new Item(AHRIMS_ARMOUR_SET, 1),
        new Item(KARILS_ARMOUR_SET, 1),
        new Item(GUTHANS_ARMOUR_SET, 1),
        new Item(DHAROKS_ARMOUR_SET, 1)
    );

    private static final List<Item> RARE_REWARDS = Arrays.asList(
        new Item(ARMADYL_GODSWORD, 1),
        new Item(DRAGON_CLAWS, 1),
        new Item(SPECTRAL_SPIRIT_SHIELD, 1),
        new Item(DRAGON_CLAWS, 1),
        new Item(VESTAS_LONGSWORD, 1),
        new Item(VESTAS_SPEAR, 1),
        new Item(STATIUSS_WARHAMMER, 1),
        new Item(ZURIELS_STAFF, 1),
        new Item(VESTAS_CHAINBODY, 1),
        new Item(VESTAS_PLATESKIRT, 1),
        new Item(STATIUSS_FULL_HELM, 1),
        new Item(STATIUSS_PLATEBODY, 1),
        new Item(STATIUSS_PLATELEGS, 1),
        new Item(MORRIGANS_COIF, 1),
        new Item(MORRIGANS_LEATHER_BODY, 1),
        new Item(MORRIGANS_LEATHER_CHAPS, 1),
        new Item(ZURIELS_HOOD, 1),
        new Item(ZURIELS_ROBE_TOP, 1),
        new Item(ZURIELS_ROBE_BOTTOM, 1),
        new Item(DRAGONFIRE_SHIELD, 1),
        new Item(ANCIENT_WYVERN_SHIELD, 1)
    );

    public static void rollForPlatinumMysteryBox(Player killer, Player target) {
        var roll = switch (killer.getMemberRights()) {
            case NONE -> 250;
            case SAPPHIRE_MEMBER -> 245;
            case EMERALD_MEMBER -> 240;
            case RUBY_MEMBER -> 235;
            case DIAMOND_MEMBER -> 230;
            case DRAGONSTONE_MEMBER -> 225;
            case ONYX_MEMBER -> 215;
            case ZENYTE_MEMBER -> 200;
        };

        if(World.getWorld().rollDie(roll,1)) {
            killer.getInventory().addOrDrop(new Item(PLATINUM_MYSTERY_BOX));
            killer.message(Color.PURPLE.wrap("You've found a platinum mystery box searching the corpse of "+target.getUsername()+"."));
        }
    }

}
