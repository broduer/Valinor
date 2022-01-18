package com.valinor.game.content.items.combine;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 18, 2022
 */
public class TridentOfTheSwamp extends Interaction {

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == MAGIC_FANG && usedWith.getId() == TRIDENT_OF_THE_SEAS) || (use.getId() == TRIDENT_OF_THE_SEAS && usedWith.getId() == MAGIC_FANG)) {
            if (!player.inventory().containsAll(MAGIC_FANG, TRIDENT_OF_THE_SEAS)) {
                return true;
            }

            if (!player.inventory().contains(CHISEL)) {
                player.message("You need a chisel to do that.");
                return true;
            }

            player.inventory().remove(MAGIC_FANG);
            player.inventory().remove(TRIDENT_OF_THE_SEAS);
            player.inventory().add(new Item(TRIDENT_OF_THE_SWAMP));
            player.animate(3015);
            return true;
        }
        return false;
    }
}
