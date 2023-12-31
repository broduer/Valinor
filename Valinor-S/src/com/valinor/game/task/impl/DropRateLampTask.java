package com.valinor.game.task.impl;

import com.valinor.game.task.Task;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.Color;

import static com.valinor.game.world.entity.AttributeKey.DOUBLE_DROP_LAMP_TICKS;

/**
 * @author Patrick van Elderen | December, 07, 2020, 09:40
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class DropRateLampTask extends Task {

    private final Player player;

    public DropRateLampTask(Player player) {
        super("DropRateLampTask", 1, player, false);
        this.player = player;
    }

    @Override
    public void execute() {
        int ticksLeft = player.getAttribOr(DOUBLE_DROP_LAMP_TICKS, 0);

        if(ticksLeft > 0) {
            ticksLeft--;

            player.putAttrib(DOUBLE_DROP_LAMP_TICKS, ticksLeft);

            if(ticksLeft == 0) {
                player.message(Color.RED.tag()+"Your double drops bonus has ended.");
                stop();
            }
        }
    }
}
