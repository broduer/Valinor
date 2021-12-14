package com.valinor.game.content.items;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.CustomItemIdentifiers.CORRUPTING_STONE;
import static com.valinor.util.ItemIdentifiers.CORRUPTED_YOUNGLLEF;
import static com.valinor.util.ItemIdentifiers.YOUNGLLEF;

public class CorruptingStone extends Interaction {

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == CORRUPTING_STONE || usedWith.getId() == CORRUPTING_STONE) && (use.getId() == YOUNGLLEF || usedWith.getId() == YOUNGLLEF)) {
            player.optionsTitled("Would you like to combine the stone with your pet?", "Yes", "No", () -> {
                if (!player.inventory().containsAll(CORRUPTING_STONE, YOUNGLLEF)) {
                    return;
                }
                player.inventory().remove(new Item(CORRUPTING_STONE), true);
                player.inventory().remove(new Item(YOUNGLLEF), true);
                player.inventory().add(new Item(CORRUPTED_YOUNGLLEF), true);
            });
            return true;
        }
        return false;
    }
}
