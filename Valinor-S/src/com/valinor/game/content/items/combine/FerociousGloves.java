package com.valinor.game.content.items.combine;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.ItemIdentifiers.FEROCIOUS_GLOVES;
import static com.valinor.util.ItemIdentifiers.HYDRA_LEATHER;

/**
 *
 * @author Patrick van Elderen | Zerikoth | PVE
 * @date maart 23, 2021 13:35
 */
public class FerociousGloves extends Interaction {

    @Override
    public boolean handleItemOnObject(Player player, Item item, GameObject object) {
        if(item.getId() == HYDRA_LEATHER && object.getId() == 34628) {
            if(!player.inventory().contains(HYDRA_LEATHER))
                return true;
            player.inventory().remove(HYDRA_LEATHER);
            player.inventory().add(new Item(FEROCIOUS_GLOVES));
            player.message("By feeding the tough to work leather through the machine, you manage to form a pair");
            player.message("of gloves.");
            return true;
        }
        return false;
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == HYDRA_LEATHER) {
                player.message("This leather looks pretty tough to work with... Maybe the dragonkin had a way.");
                return true;
            }
        }
        return false;
    }
}
