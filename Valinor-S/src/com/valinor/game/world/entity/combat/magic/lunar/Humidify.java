package com.valinor.game.world.entity.combat.magic.lunar;

import com.valinor.game.content.items.WaterContainers;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;

import static com.valinor.util.ItemIdentifiers.CLAY;
import static com.valinor.util.ItemIdentifiers.SOFT_CLAY;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 17, 2021
 */
public class Humidify {

    public static void castHumidify(Player player) {
        int count = 0;
        for (Item item : player.getInventory().getItems()) {
            if (item != null) {
                /* Fill up our water containers */
                for (WaterContainers containers : WaterContainers.values()) {
                    if (item.getId() == containers.empty) {
                        player.inventory().remove(item);
                        player.inventory().add(new Item(containers.full));
                        count++;
                    }
                }

                /* Turn any clay into soft clay */
                if (item.getId() == CLAY) {
                    player.inventory().remove(CLAY);
                    player.inventory().add(new Item(SOFT_CLAY));
                    count++;
                }
            }
        }

        if (count > 0) {
            player.runFn(1, () -> {
                player.lock();
                player.animate(6294);
                player.graphic(1061, 72, 0);
            }).then(4, player::unlock);
        }

        if(count == 0) {
            player.message("You have nothing in your inventory that this spell can humidify.");
        }
    }
}
