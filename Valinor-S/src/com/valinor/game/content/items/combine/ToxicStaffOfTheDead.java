package com.valinor.game.content.items.combine;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 17, 2022
 */
public class ToxicStaffOfTheDead extends Interaction {

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == MAGIC_FANG && usedWith.getId() == STAFF_OF_THE_DEAD) || (use.getId() == STAFF_OF_THE_DEAD && usedWith.getId() == MAGIC_FANG)) {
            if (!player.inventory().containsAll(MAGIC_FANG, STAFF_OF_THE_DEAD)) {
                return true;
            }

            player.inventory().remove(MAGIC_FANG);
            player.inventory().remove(STAFF_OF_THE_DEAD);
            player.inventory().add(new Item(TOXIC_STAFF_OF_THE_DEAD));
            player.animate(3015);
            return true;
        }
        return false;
    }
}
