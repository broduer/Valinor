package com.valinor.game.content.items.combine;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 23, 2022
 */
public class Arclight extends Interaction {

    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == DARKLIGHT || usedWith.getId() == DARKLIGHT) && (use.getId() == ANCIENT_SHARD || usedWith.getId() == ANCIENT_SHARD)) {
            player.inventory().remove(DARKLIGHT);
            player.inventory().remove(ANCIENT_SHARD);
            player.inventory().add(new Item(ARCLIGHT));
            player.message("You combine the darklight with the ancient shard to make a arclight");
            return true;
        }
        return false;
    }
}
