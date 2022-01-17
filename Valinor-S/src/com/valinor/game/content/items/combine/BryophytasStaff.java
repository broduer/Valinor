package com.valinor.game.content.items.combine;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 17, 2022
 */
public class BryophytasStaff extends Interaction {

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == BRYOPHYTAS_ESSENCE && usedWith.getId() == BATTLESTAFF) || (use.getId() == BATTLESTAFF && usedWith.getId() == BRYOPHYTAS_ESSENCE)) {
            if (!player.inventory().containsAll(BRYOPHYTAS_ESSENCE, BATTLESTAFF)) {
                return true;
            }

            if(player.skills().level(Skills.CRAFTING) < 62) {
                player.message("You do not have the required level to create this staff.");
                return true;
            }

            player.inventory().remove(BRYOPHYTAS_ESSENCE);
            player.inventory().remove(BATTLESTAFF);
            player.inventory().add(new Item(BRYOPHYTAS_STAFF));
            player.animate(3015);
            return true;
        }
        return false;
    }
}
