package com.valinor.game.world.entity.mob.player.commands.impl.staff.admin;

import com.valinor.game.content.items.StarterBox;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 07, 2022
 */
public class DisableStarterBox implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        StarterBox.STARTER_BOX_ENABLED =! StarterBox.STARTER_BOX_ENABLED;
        String msg = StarterBox.STARTER_BOX_ENABLED ? "Enabled" : "Disabled";
        player.message("The starter box is now "+msg+".");
    }

    @Override
    public boolean canUse(Player player) {
        return player.getPlayerRights().isAdminOrGreater(player);
    }
}
