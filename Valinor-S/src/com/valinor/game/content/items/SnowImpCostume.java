package com.valinor.game.content.items;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.CustomItemIdentifiers.SNOW_IMP_COSTUME;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 09, 2022
 */
public class SnowImpCostume extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == SNOW_IMP_COSTUME) {
                player.inventory().remove(SNOW_IMP_COSTUME);
                player.inventory().addOrDrop(new Item(SNOW_IMP_COSTUME_HEAD_21847,1));
                player.inventory().addOrDrop(new Item(SNOW_IMP_COSTUME_BODY_21849,1));
                player.inventory().addOrDrop(new Item(SNOW_IMP_COSTUME_LEGS_21851,1));
                player.inventory().addOrDrop(new Item(SNOW_IMP_COSTUME_TAIL_21853,1));
                player.inventory().addOrDrop(new Item(SNOW_IMP_COSTUME_GLOVES_21855,1));
                player.inventory().addOrDrop(new Item(SNOW_IMP_COSTUME_FEET_21857,1));
                return true;
            }
        }
        return false;
    }
}
