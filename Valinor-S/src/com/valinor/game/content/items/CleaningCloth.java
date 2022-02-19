package com.valinor.game.content.items;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.CustomItemIdentifiers.FROST_WHIP;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 19, 2022
 */
public class CleaningCloth extends Interaction {

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == CLEANING_CLOTH || usedWith.getId() == CLEANING_CLOTH) && (use.getId() == TWISTED_BOW_ORANGE || usedWith.getId() == TWISTED_BOW_ORANGE)) {
            player.inventory().remove(new Item(TWISTED_BOW_ORANGE),true);
            player.inventory().add(new Item(TWISTED_BOW),true);
            return true;
        }
        if ((use.getId() == CLEANING_CLOTH || usedWith.getId() == CLEANING_CLOTH) && (use.getId() == TWISTED_BOW_PURPLE || usedWith.getId() == TWISTED_BOW_PURPLE)) {
            player.inventory().remove(new Item(TWISTED_BOW_PURPLE),true);
            player.inventory().add(new Item(TWISTED_BOW),true);
            return true;
        }
        if ((use.getId() == CLEANING_CLOTH || usedWith.getId() == CLEANING_CLOTH) && (use.getId() == LAVA_WHIP || usedWith.getId() == LAVA_WHIP)) {
            player.inventory().remove(new Item(LAVA_WHIP),true);
            player.inventory().add(new Item(ABYSSAL_WHIP),true);
            return true;
        }
        if ((use.getId() == CLEANING_CLOTH || usedWith.getId() == CLEANING_CLOTH) && (use.getId() == FROST_WHIP || usedWith.getId() == FROST_WHIP)) {
            player.inventory().remove(new Item(FROST_WHIP),true);
            player.inventory().add(new Item(ABYSSAL_WHIP),true);
            return true;
        }
        return false;
    }
}
