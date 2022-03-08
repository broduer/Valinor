package com.valinor.game.content.items.mystery;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Utils;

import java.util.List;

import static com.valinor.util.CustomItemIdentifiers.VOTE_BOX;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 08, 2022
 */
public class VoteBox extends Interaction {

    private static final int EXTREME_ROLL = 150;
    private static final int RARE_ROLL = 30;
    private static final int UNCOMMON_ROLL = 10;

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == VOTE_BOX) {
                reward(player, item.getId());
                return true;
            }
        }
        return false;
    }

    private boolean rare = false;

    public Item rollReward() {
        List<Item> items = null;
        if(World.getWorld().rollDie(EXTREME_ROLL, 1)) {
            rare = true;
            items = List.of(EXTREME_RARE_REWARDS);
        } else if (World.getWorld().rollDie(RARE_ROLL, 1)) {
            rare = true;
            items = List.of(RARE_REWARDS);
        } else if (World.getWorld().rollDie(UNCOMMON_ROLL, 1)) {
            items = List.of(UNCOMMON_REWARDS);
        } else {
            items = List.of(COMMON_REWARDS);
        }
        return Utils.randomElement(items);
    }

    private void reward(Player player, int id) {
        if (player.inventory().contains(id)) {
            player.inventory().remove(id);
            Item reward = rollReward();
            if (rare) {
                World.getWorld().sendWorldMessage("<img=452><shad=0><col=0052cc>" + player.getUsername() + " just received " + Utils.getVowelFormat(reward.unnote().name()) + " from a armour vote box!");
            }
            player.inventory().addOrBank(reward);
            rare = false;

            var amt = reward.getAmount();
            player.message("You open the vote mystery box and found...");
            player.message("x" + amt + " " + reward.unnote().name() + ".");
            Utils.sendDiscordInfoLog(player.getUsername() + " with IP " + player.getHostAddress() + " just opened a vote mystery box and received x" + amt + " " + reward.unnote().name() + ".", "boxes_opened");
        }
    }

    private static final Item[] EXTREME_RARE_REWARDS = new Item[]{
        new Item(AVERNIC_DEFENDER, 1),
        new Item(INFERNAL_CAPE, 1)
    };

    private static final Item[] RARE_REWARDS = new Item[]{
        new Item(ARMADYL_GODSWORD, 1),
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
        new Item(ANCIENT_WYVERN_SHIELD, 1),
        new Item(ANTIQUE_EMBLEM_TIER_8, 1),
        new Item(ANTIQUE_EMBLEM_TIER_9, 1),
        new Item(ANTIQUE_EMBLEM_TIER_10, 1),
        new Item(BANDOS_GODSWORD, 1),
        new Item(SARADOMIN_GODSWORD, 1),
        new Item(ZAMORAK_GODSWORD, 1),
        new Item(BRIMSTONE_RING, 1)
    };

    private static final Item[] UNCOMMON_REWARDS = new Item[]{
        new Item(ABYSSAL_TENTACLE, 1),
        new Item(AMULET_OF_FURY, 1),
        new Item(RANGER_BOOTS, 1),
        new Item(INFINITY_BOOTS, 1),
        new Item(BERSERKER_RING_I, 1),
        new Item(SEERS_RING_I, 1),
        new Item(WARRIOR_RING_I, 1),
        new Item(ARCHERS_RING_I, 1),
        new Item(MAGES_BOOK, 1),
        new Item(TOME_OF_FIRE, 1),
    };

    private static final Item[] COMMON_REWARDS = new Item[]{
        new Item(DHAROKS_ARMOUR_SET),
        new Item(KARILS_ARMOUR_SET),
        new Item(AHRIMS_ARMOUR_SET),
        new Item(VERACS_ARMOUR_SET),
        new Item(TORAGS_ARMOUR_SET),
        new Item(GUTHANS_ARMOUR_SET),
        new Item(MORRIGANS_JAVELIN, World.getWorld().random(1,10)),
        new Item(MORRIGANS_THROWING_AXE, World.getWorld().random(1,10)),
        new Item(ANTIQUE_EMBLEM_TIER_1, 1),
        new Item(BERSERKER_RING, 1),
        new Item(SEERS_RING, 1),
        new Item(WARRIOR_RING, 1),
        new Item(ARCHERS_RING, 1),
    };
}
