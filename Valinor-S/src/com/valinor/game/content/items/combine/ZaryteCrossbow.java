package com.valinor.game.content.items.combine;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 20, 2022
 */
public class ZaryteCrossbow extends Interaction {

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == NIHIL_HORN && usedWith.getId() == ARMADYL_CROSSBOW) || (use.getId() == ARMADYL_CROSSBOW && usedWith.getId() == NIHIL_HORN)) {
            if (!player.inventory().containsAll(NIHIL_HORN, ARMADYL_CROSSBOW)) {
                return true;
            }

            if (!player.inventory().contains(NIHIL_SHARD, 250)) {
                player.message("You need 250 nihil shards.");
                return true;
            }

            player.inventory().remove(NIHIL_HORN);
            player.inventory().remove(ARMADYL_CROSSBOW);
            player.inventory().remove(NIHIL_SHARD,250);
            player.inventory().add(new Item(ZARYTE_CROSSBOW));
            return true;
        }
        return false;
    }
}
