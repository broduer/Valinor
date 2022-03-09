package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.util.Color;
import com.valinor.util.timers.TimerKey;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 07, 2022
 */
public class HelpCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if(player.getTimers().has(TimerKey.HELP_COMMAND_USED)) {
            player.message(Color.RED.wrap("You can use this command again in: "+player.getTimers().asMinutesAndSecondsLeft(TimerKey.HELP_COMMAND_USED))+".");
            return;
        }
        player.message("We have requested help.");
        player.getTimers().addOrSet(TimerKey.HELP_COMMAND_USED,500);
        World.getWorld().sendStaffMessage("Player: "+Color.BLUE.wrap(player.getUsername())+" has requested ::help.");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}
