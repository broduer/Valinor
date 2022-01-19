package com.valinor.game.content.items.combine;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 19, 2022
 */
public class BonecrusherNecklace extends Interaction {

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == HYDRA_TAIL || usedWith.getId() == HYDRA_TAIL) && (use.getId() == BONECRUSHER || usedWith.getId() == BONECRUSHER)) {
            if(!player.inventory().contains(DRAGONBONE_NECKLACE)) {
                player.message("You also need a Dragonbone necklace.");
                return true;
            }
            player.inventory().remove(DRAGONBONE_NECKLACE);
            player.inventory().remove(HYDRA_TAIL);
            player.inventory().remove(BONECRUSHER);
            player.inventory().add(new Item(BONECRUSHER_NECKLACE), true);
            return true;
        }
        if ((use.getId() == HYDRA_TAIL || usedWith.getId() == HYDRA_TAIL) && (use.getId() == DRAGONBONE_NECKLACE || usedWith.getId() == DRAGONBONE_NECKLACE)) {
            if(!player.inventory().contains(BONECRUSHER)) {
                player.message("You also need a Bonecrusher.");
                return true;
            }
            player.inventory().remove(DRAGONBONE_NECKLACE);
            player.inventory().remove(HYDRA_TAIL);
            player.inventory().remove(BONECRUSHER);
            player.inventory().add(new Item(BONECRUSHER_NECKLACE), true);
            return true;
        }
        return false;
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 4) {
            if(item.getId() == BONECRUSHER_NECKLACE) {
                player.inventory().remove(BONECRUSHER_NECKLACE);
                player.inventory().add(new Item(DRAGONBONE_NECKLACE));
                player.inventory().add(new Item(HYDRA_TAIL));
                player.inventory().add(new Item(BONECRUSHER));
                return true;
            }
        }
        return false;
    }
}
