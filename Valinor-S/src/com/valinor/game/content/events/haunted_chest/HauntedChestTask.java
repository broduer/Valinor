package com.valinor.game.content.events.haunted_chest;

import com.valinor.game.task.Task;

import static com.valinor.game.content.events.haunted_chest.HauntedChest.EVENT_INTERVAL;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 10, 2022
 */
public class HauntedChestTask extends Task {

    public HauntedChestTask() {
        super("HauntedChestTask", EVENT_INTERVAL,true);
    }

    @Override
    public void execute() {
        HauntedChest hauntedChest = HauntedChest.getInstance();
        hauntedChest.startEvent();
    }
}
