package com.valinor.game.content.tasks;

import com.valinor.game.content.daily_tasks.TaskCategory;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.CustomItemIdentifiers.PVP_SCROLL;
import static com.valinor.util.CustomItemIdentifiers.TASK_BOTTLE_PVP;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 04, 2022
 */
public class PvpTaskBottle extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == TASK_BOTTLE_PVP) {
                if (player.getTaskBottleManager().hasTask()) {
                    player.message("You already have an task and cannot get another.");
                    return true;
                }

                player.optionsTitled("Are you sure you wish to open the task bottle?", "Yes.", "No.", () -> {
                    if(!player.inventory().contains(TASK_BOTTLE_PVP))
                        return;

                    player.inventory().remove(TASK_BOTTLE_PVP);
                    player.inventory().add(new Item(PVP_SCROLL));
                    player.getTaskBottleManager().giveTask(TaskCategory.PVP_TASK);
                });
                return true;
            }

            if(item.getId() == PVP_SCROLL) {
                player.getTaskBottleManager().open();
                return true;
            }
        }
        return false;
    }
}
