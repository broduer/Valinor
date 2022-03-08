package com.valinor.game.content.items;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 08, 2022
 */
public class DarkShard extends Interaction {

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == DARK_SHARD || usedWith.getId() == DARK_SHARD) && (use.getId() == ARMADYL_HELMET || usedWith.getId() == ARMADYL_HELMET)) {
            player.optionsTitled("Are you sure you wish to combine the shard?", "Yes.", "No,", () -> {
                player.inventory().remove(new Item(DARK_SHARD), true);
                player.inventory().remove(new Item(ARMADYL_HELMET), true);
                player.inventory().add(new Item(DARK_ARMADYL_HELMET), true);
            });
            return true;
        }
        if ((use.getId() == DARK_SHARD || usedWith.getId() == DARK_SHARD) && (use.getId() == ARMADYL_CHESTPLATE || usedWith.getId() == ARMADYL_CHESTPLATE)) {
            player.optionsTitled("Are you sure you wish to combine the shard?", "Yes.", "No,", () -> {
                player.inventory().remove(new Item(DARK_SHARD), true);
                player.inventory().remove(new Item(ARMADYL_CHESTPLATE), true);
                player.inventory().add(new Item(DARK_ARMADYL_CHESTPLATE), true);
            });
            return true;
        }
        if ((use.getId() == DARK_SHARD || usedWith.getId() == DARK_SHARD) && (use.getId() == ARMADYL_CHAINSKIRT || usedWith.getId() == ARMADYL_CHAINSKIRT)) {
            player.optionsTitled("Are you sure you wish to combine the shard?", "Yes.", "No,", () -> {
                player.inventory().remove(new Item(DARK_SHARD), true);
                player.inventory().remove(new Item(ARMADYL_CHAINSKIRT), true);
                player.inventory().add(new Item(DARK_ARMADYL_CHAINSKIRT), true);
            });
            return true;
        }
        if ((use.getId() == DARK_SHARD || usedWith.getId() == DARK_SHARD) && (use.getId() == BANDOS_CHESTPLATE || usedWith.getId() == BANDOS_CHESTPLATE)) {
            player.optionsTitled("Are you sure you wish to combine the shard?", "Yes.", "No,", () -> {
                player.inventory().remove(new Item(DARK_SHARD), true);
                player.inventory().remove(new Item(BANDOS_CHESTPLATE), true);
                player.inventory().add(new Item(DARK_BANDOS_CHESTPLATE), true);
            });
            return true;
        }
        if ((use.getId() == DARK_SHARD || usedWith.getId() == DARK_SHARD) && (use.getId() == BANDOS_TASSETS || usedWith.getId() == BANDOS_TASSETS)) {
            player.optionsTitled("Are you sure you wish to combine the shard?", "Yes.", "No,", () -> {
                player.inventory().remove(new Item(DARK_SHARD), true);
                player.inventory().remove(new Item(BANDOS_TASSETS), true);
                player.inventory().add(new Item(DARK_BANDOS_TASSETS), true);
            });
            return true;
        }
        return false;
    }
}
