package com.valinor.game.content.tasks;

import com.valinor.game.content.daily_tasks.TaskCategory;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.CustomItemIdentifiers.SKILLING_SCROLL;
import static com.valinor.util.CustomItemIdentifiers.TASK_BOTTLE_SKILLING;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 01, 2021
 */
public class SkillingTaskBottle extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == TASK_BOTTLE_SKILLING) {
                if (player.getTaskBottleManager().hasTask()) {
                    player.message("You already have an task and cannot get another.");
                    return true;
                }

                player.optionsTitled("Are you sure you wish to open the task bottle?", "Yes.", "No.", () -> {
                    if(!player.inventory().contains(TASK_BOTTLE_SKILLING))
                        return;

                    player.inventory().remove(TASK_BOTTLE_SKILLING);
                    player.inventory().add(new Item(SKILLING_SCROLL));
                    player.getTaskBottleManager().giveTask(TaskCategory.SKILLING_TASK);
                });
                return true;
            }

            if(item.getId() == SKILLING_SCROLL) {
                player.getTaskBottleManager().open();
                return true;
            }
        }
        return false;
    }
}
