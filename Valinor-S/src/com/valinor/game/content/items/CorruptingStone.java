package com.valinor.game.content.items;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.CustomItemIdentifiers.CORRUPTING_STONE;
import static com.valinor.util.ItemIdentifiers.*;

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
        if ((use.getId() == CORRUPTING_STONE || usedWith.getId() == CORRUPTING_STONE) && (use.getId() == BOW_OF_FAERDHINEN || usedWith.getId() == BOW_OF_FAERDHINEN)) {
            player.optionsTitled("Would you like to combine the stone with your bow?", "Yes", "No", () -> {
                if (!player.inventory().containsAll(CORRUPTING_STONE, BOW_OF_FAERDHINEN)) {
                    return;
                }
                player.inventory().remove(new Item(CORRUPTING_STONE), true);
                player.inventory().remove(new Item(BOW_OF_FAERDHINEN), true);
                player.inventory().add(new Item(BOW_OF_FAERDHINEN_C), true);
            });
            return true;
        }
        if ((use.getId() == CORRUPTING_STONE || usedWith.getId() == CORRUPTING_STONE) && (use.getId() == BLADE_OF_SAELDOR || usedWith.getId() == BLADE_OF_SAELDOR)) {
            player.optionsTitled("Would you like to combine the stone with your blade?", "Yes", "No", () -> {
                if (!player.inventory().containsAll(CORRUPTING_STONE, BLADE_OF_SAELDOR)) {
                    return;
                }
                player.inventory().remove(new Item(CORRUPTING_STONE), true);
                player.inventory().remove(new Item(BLADE_OF_SAELDOR), true);
                player.inventory().add(new Item(BLADE_OF_SAELDOR_C), true);
            });
            return true;
        }
        return false;
    }
}
