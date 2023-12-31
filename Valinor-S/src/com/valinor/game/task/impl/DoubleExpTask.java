package com.valinor.game.task.impl;

import com.valinor.game.task.Task;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.Color;

import static com.valinor.game.world.entity.AttributeKey.DOUBLE_EXP_TICKS;

/**
 * @author Patrick van Elderen | April, 21, 2021, 15:10
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class DoubleExpTask extends Task {

    private final Player player;

    public DoubleExpTask(Player player) {
        super("DoubleExpTask", 1, player, false);
        this.player = player;
    }

    @Override
    public void execute() {
        int ticksLeft = player.getAttribOr(DOUBLE_EXP_TICKS, 0);

        if(ticksLeft > 0) {
            ticksLeft--;

            player.putAttrib(DOUBLE_EXP_TICKS, ticksLeft);

            if(ticksLeft == 0) {
                player.message(Color.RED.tag()+"Your double exp bonus has ended.");
                stop();
            }
        }
    }
}
