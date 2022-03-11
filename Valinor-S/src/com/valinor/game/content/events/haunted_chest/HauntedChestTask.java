package com.valinor.game.content.events.haunted_chest;

import com.valinor.game.task.Task;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 10, 2022
 */
public class HauntedChestTask extends Task {

    public HauntedChestTask() {
        super("HauntedChestTask", 100);
    }

    @Override
    public void execute() {
        HauntedChest hauntedChest = HauntedChest.getInstance();
        hauntedChest.startEvent();
    }
}
