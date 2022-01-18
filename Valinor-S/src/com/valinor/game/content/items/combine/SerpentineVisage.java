package com.valinor.game.content.items.combine;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 18, 2022
 */
public class SerpentineVisage extends Interaction {

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == CHISEL && usedWith.getId() == SERPENTINE_VISAGE) || (use.getId() == SERPENTINE_VISAGE && usedWith.getId() == CHISEL)) {
            if (!player.inventory().contains(SERPENTINE_VISAGE)) {
                return true;
            }

            if (!player.inventory().contains(CHISEL)) {
                player.message("You need a chisel to do that.");
                return true;
            }

            player.inventory().remove(SERPENTINE_VISAGE);
            player.inventory().add(new Item(SERPENTINE_HELM));
            player.animate(3015);
            return true;
        }
        return false;
    }
}
