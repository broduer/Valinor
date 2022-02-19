package com.valinor.game.content.items;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 02, 2021
 */
public class Dye extends Interaction {

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == ORANGE_DYE || usedWith.getId() == ORANGE_DYE) && (use.getId() == TWISTED_BOW || usedWith.getId() == TWISTED_BOW)) {
            player.inventory().remove(new Item(ORANGE_DYE),true);
            player.inventory().remove(new Item(TWISTED_BOW),true);
            player.inventory().add(new Item(TWISTED_BOW_ORANGE),true);
            return true;
        }
        if ((use.getId() == PURPLE_DYE || usedWith.getId() == PURPLE_DYE) && (use.getId() == TWISTED_BOW || usedWith.getId() == TWISTED_BOW)) {
            player.inventory().remove(new Item(PURPLE_DYE),true);
            player.inventory().remove(new Item(TWISTED_BOW),true);
            player.inventory().add(new Item(TWISTED_BOW_PURPLE),true);
            return true;
        }
        if ((use.getId() == RED_DYE || usedWith.getId() == RED_DYE) && (use.getId() == TWISTED_BOW || usedWith.getId() == TWISTED_BOW)) {
            player.inventory().remove(new Item(RED_DYE),true);
            player.inventory().remove(new Item(TWISTED_BOW),true);
            player.inventory().add(new Item(TWISTED_BOW_RED),true);
            return true;
        }
        return false;
    }
}
