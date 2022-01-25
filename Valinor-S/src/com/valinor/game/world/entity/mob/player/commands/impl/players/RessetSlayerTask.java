package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 25, 2022
 */
public class RessetSlayerTask implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        Slayer.cancelTask(player, true);
        player.message("Your task has been canceled.");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}
