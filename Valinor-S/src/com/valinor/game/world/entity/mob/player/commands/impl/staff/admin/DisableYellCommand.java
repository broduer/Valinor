package com.valinor.game.world.entity.mob.player.commands.impl.staff.admin;

import com.valinor.GameServer;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 12, 2022
 */
public class DisableYellCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        GameServer.yellEnabled =! GameServer.yellEnabled;
        String msg = GameServer.yellEnabled ? "Enabled" : "Disabled";
        player.message("Yelling is now "+msg+".");
    }

    @Override
    public boolean canUse(Player player) {
        return player.getPlayerRights().isAdminOrGreater(player);
    }
}
