package com.valinor.game.content.items.combine;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 23, 2022
 */
public class DarkTotem extends Interaction {

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == DARK_TOTEM_BASE || usedWith.getId() == DARK_TOTEM_BASE) && (use.getId() == DARK_TOTEM_MIDDLE || usedWith.getId() == DARK_TOTEM_MIDDLE)) {
            if(player.inventory().containsAll(DARK_TOTEM_BASE, DARK_TOTEM_MIDDLE, DARK_TOTEM_TOP)) {
                player.inventory().remove(DARK_TOTEM_BASE);
                player.inventory().remove(DARK_TOTEM_MIDDLE);
                player.inventory().remove(DARK_TOTEM_TOP);
                player.inventory().add(new Item(DARK_TOTEM));
                player.message(Color.PURPLE.wrap("You combined all the corrupt totem pieces to build a corrupt totem!"));
            }
            return true;
        }
        if ((use.getId() == DARK_TOTEM_MIDDLE || usedWith.getId() == DARK_TOTEM_MIDDLE) && (use.getId() == DARK_TOTEM_BASE || usedWith.getId() == DARK_TOTEM_BASE)) {
            if(player.inventory().containsAll(DARK_TOTEM_BASE, DARK_TOTEM_MIDDLE, DARK_TOTEM_TOP)) {
                player.inventory().remove(DARK_TOTEM_BASE);
                player.inventory().remove(DARK_TOTEM_MIDDLE);
                player.inventory().remove(DARK_TOTEM_TOP);
                player.inventory().add(new Item(DARK_TOTEM));
                player.message(Color.PURPLE.wrap("You combined all the corrupt totem pieces to build a corrupt totem!"));
            }
            return true;
        }
        if ((use.getId() == DARK_TOTEM_TOP || usedWith.getId() == DARK_TOTEM_TOP) && (use.getId() == DARK_TOTEM_BASE || usedWith.getId() == DARK_TOTEM_BASE)) {
            if(player.inventory().containsAll(DARK_TOTEM_BASE, DARK_TOTEM_MIDDLE, DARK_TOTEM_TOP)) {
                player.inventory().remove(DARK_TOTEM_BASE);
                player.inventory().remove(DARK_TOTEM_MIDDLE);
                player.inventory().remove(DARK_TOTEM_TOP);
                player.inventory().add(new Item(DARK_TOTEM));
                player.message(Color.PURPLE.wrap("You combined all the corrupt totem pieces to build a corrupt totem!"));
            }
            return true;
        }
        if ((use.getId() == DARK_TOTEM_TOP || usedWith.getId() == DARK_TOTEM_TOP) && (use.getId() == DARK_TOTEM_MIDDLE || usedWith.getId() == DARK_TOTEM_MIDDLE)) {
            if(player.inventory().containsAll(DARK_TOTEM_BASE, DARK_TOTEM_MIDDLE, DARK_TOTEM_TOP)) {
                player.inventory().remove(DARK_TOTEM_BASE);
                player.inventory().remove(DARK_TOTEM_MIDDLE);
                player.inventory().remove(DARK_TOTEM_TOP);
                player.inventory().add(new Item(DARK_TOTEM));
                player.message(Color.PURPLE.wrap("You combined all the corrupt totem pieces to build a corrupt totem!"));
            }
            return true;
        }
        return false;
    }
}
