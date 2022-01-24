package com.valinor.game.content.items;

import com.valinor.game.content.skill.impl.slayer.slayer_task.SlayerCreature;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.CustomItemIdentifiers.SLAYER_TELEPORT_SCROLL;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 24, 2022
 */
public class SlayerTeleportScroll extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == SLAYER_TELEPORT_SCROLL) {
                player.inventory().remove(SLAYER_TELEPORT_SCROLL, 1);
                SlayerCreature.teleport(player);
                return true;
            }
        }
        return false;
    }
}
