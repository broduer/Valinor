package com.valinor.game.content.items.combine;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.ItemIdentifiers.*;

public class DragonHunterLance extends Interaction {

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == HYDRAS_CLAW || usedWith.getId() == HYDRAS_CLAW) && (use.getId() == ZAMORAKIAN_HASTA || usedWith.getId() == ZAMORAKIAN_HASTA)) {
            if (player.inventory().containsAll(HYDRAS_CLAW, ZAMORAKIAN_HASTA)) {
                player.confirmDialogue(new String[]{"Are you sure you wish to combine the Hydra claw and the", "Zamorakian hasta to create the Dragon hunter lance", "This can not be reversed."}, "", "Proceed with the combination.", "Cancel.", () -> {
                    if(!player.inventory().containsAll(HYDRAS_CLAW, ZAMORAKIAN_HASTA)) {
                        return;
                    }
                    player.animate(4462);
                    player.graphic(759,15,0);
                    player.inventory().remove(HYDRAS_CLAW);
                    player.inventory().remove(ZAMORAKIAN_HASTA);
                    player.inventory().add(new Item(DRAGON_HUNTER_LANCE));
                    player.message("You successfully combine the Hydra claw and the Zamorakian hasta to create the");
                    player.message("Dragon hunter lance.");
                    player.itemDialogue("You successfully combine the Hydra claw and the<br>Zamorakian hasta to create the Dragon hunter lance.", DRAGON_HUNTER_LANCE);
                });
            }
            return true;
        }
        return false;
    }
}
