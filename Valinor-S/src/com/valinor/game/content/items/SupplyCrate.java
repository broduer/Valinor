package com.valinor.game.content.items;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 30, 2021
 */
public class SupplyCrate extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == SUPPLY_CRATE) {
                open(player, item.getId(), 1);
                return true;
            }
            if (item.getId() == EXTRA_SUPPLY_CRATE) {
                open(player, item.getId(), 3);
                return true;
            }
        }
        return false;
    }

    private void open(Player player, int id, int rolls) {
        if (player.inventory().contains(id)) {
            player.inventory().remove(id);
            for (int i = 0; i < rolls; i++) {
                Item reward = reward().copy();
                if (rare) {
                    World.getWorld().sendWorldMessage("<img=1081><col=0052cc>" + player.getUsername() + " just received " + Utils.getVowelFormat(reward.unnote().name()) + " from a supply crate!");
                }
                player.inventory().addOrDrop(reward);
                rare = false;
            }
        }
    }

    private final List<Item> RARE_REWARDS = Arrays.asList(new Item(TOME_OF_FIRE), new Item(DRAGON_AXE));

    private final List<Item> OTHER_REWARDS = Arrays.asList(
        new Item(OAK_LOGS + 1, World.getWorld().random(20,40)),
        new Item(WILLOW_LOGS + 1, World.getWorld().random(20,40)),
        new Item(TEAK_LOGS + 1, World.getWorld().random(20,40)),
        new Item(MAPLE_LOGS + 1, World.getWorld().random(20,40)),
        new Item(MAHOGANY_LOGS + 1, World.getWorld().random(20,40)),
        new Item(YEW_LOGS + 1, World.getWorld().random(20,40)),
        new Item(MAGIC_LOGS + 1, World.getWorld().random(20,40)),
        new Item(UNCUT_SAPPHIRE + 1, World.getWorld().random(4, 12)),
        new Item(UNCUT_EMERALD + 1, World.getWorld().random(4, 12)),
        new Item(UNCUT_RUBY + 1, World.getWorld().random(2, 4)),
        new Item(UNCUT_DIAMOND + 1, World.getWorld().random(4, 12)),
        new Item(LIMESTONE + 1, World.getWorld().random(6, 14)),
        new Item(SILVER_ORE + 1, World.getWorld().random(20, 24)),
        new Item(IRON_ORE + 1, World.getWorld().random(10, 30)),
        new Item(COAL + 1, World.getWorld().random(20, 28)),
        new Item(GOLD_ORE + 1, World.getWorld().random(16, 22)),
        new Item(MITHRIL_ORE + 1, World.getWorld().random(6, 10)),
        new Item(ADAMANTITE_ORE + 1, World.getWorld().random(4, 6)),
        new Item(RUNITE_ORE + 1, World.getWorld().random(2, 4)),
        new Item(GRIMY_GUAM_LEAF + 1, World.getWorld().random(6, 12)),
        new Item(GRIMY_MARRENTILL + 1, World.getWorld().random(6, 12)),
        new Item(GRIMY_TARROMIN + 1, World.getWorld().random(6, 12)),
        new Item(GRIMY_HARRALANDER + 1, World.getWorld().random(6, 12)),
        new Item(GRIMY_RANARR_WEED + 1, World.getWorld().random(6, 12)),
        new Item(GRIMY_IRIT_LEAF + 1, World.getWorld().random(6, 10)),
        new Item(GRIMY_AVANTOE + 1, World.getWorld().random(6, 10)),
        new Item(GRIMY_KWUARM + 1, World.getWorld().random(2, 4)),
        new Item(GRIMY_CADANTINE + 1, World.getWorld().random(2, 4)),
        new Item(GRIMY_LANTADYME + 1, World.getWorld().random(2, 4)),
        new Item(GRIMY_DWARF_WEED + 1, World.getWorld().random(2, 4)),
        new Item(GRIMY_TORSTOL + 1, World.getWorld().random(4, 12)),
        new Item(RANARR_SEED, World.getWorld().random(4, 12)),
        new Item(TARROMIN_SEED, World.getWorld().random(4, 12)),
        new Item(HARRALANDER_SEED, World.getWorld().random(4, 12)),
        new Item(TOADFLAX_SEED, World.getWorld().random(4, 12)),
        new Item(IRIT_SEED, World.getWorld().random(4, 12)),
        new Item(AVANTOE_SEED, World.getWorld().random(4, 12)),
        new Item(SNAPDRAGON_SEED, World.getWorld().random(4, 12)),
        new Item(CADANTINE_SEED, World.getWorld().random(4, 12)),
        new Item(LANTADYME_SEED, World.getWorld().random(4, 12)),
        new Item(DWARF_WEED_SEED, World.getWorld().random(4, 12)),
        new Item(TORSTOL_SEED, World.getWorld().random(4, 12)),
        new Item(RAW_ANCHOVIES + 1, World.getWorld().random(4, 12)),
        new Item(RAW_TROUT + 1, World.getWorld().random(12, 22)),
        new Item(RAW_SALMON + 1, World.getWorld().random(12, 22)),
        new Item(RAW_TUNA + 1, World.getWorld().random(12, 22)),
        new Item(RAW_LOBSTER + 1, World.getWorld().random(12, 22)),
        new Item(RAW_SWORDFISH + 1, World.getWorld().random(12, 22)),
        new Item(RAW_SHARK + 1, World.getWorld().random(12, 22)),
        new Item(RAW_ANGLERFISH + 1, World.getWorld().random(12, 22)),
        new Item(COINS_995, World.getWorld().random(20_000, 50_000)),
        new Item(PURE_ESSENCE + 1, World.getWorld().random(40, 140))
    );

    private boolean rare = false;

    private Item reward() {
        if (World.getWorld().rollDie(100, 1)) {
            rare = true;
            return Utils.randomElement(RARE_REWARDS);
        } else {
            return Utils.randomElement(OTHER_REWARDS);
        }
    }
}
