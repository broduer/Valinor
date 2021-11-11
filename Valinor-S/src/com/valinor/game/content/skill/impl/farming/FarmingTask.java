package com.valinor.game.content.skill.impl.farming;

import com.valinor.game.task.Task;
import com.valinor.game.world.entity.mob.player.Player;

/**
 * The task who manages the player's farming timer when online.
 *
 * @author Gabriel || Wolfsdarker
 */
public class FarmingTask extends Task {

    private final Player player;

    public FarmingTask(Player player) {
        super("FarmingTask",501, player, false);
        this.player = player;
    }

    @Override
    protected void execute() {
        if (player == null) {
            stop();
            return;
        }

        player.getFarming().updateCropStages(player);
        player.getFarming().updatePatches(player);
    }
}
