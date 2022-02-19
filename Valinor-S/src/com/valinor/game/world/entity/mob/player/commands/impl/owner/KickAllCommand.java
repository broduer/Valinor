package com.valinor.game.world.entity.mob.player.commands.impl.owner;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

import static com.valinor.game.world.entity.AttributeKey.IS_BOT;

public class KickAllCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        // Known exploit
        if (command.contains("\r") || command.contains("\n")) {
            return;
        }
        int players = World.getWorld().getRegularPlayers();
        for (Player plr : World.getWorld().getPlayers()) {
            if (plr == null) continue;
            if (!plr.getPlayerRights().isStaffMember(plr) || plr.<Boolean>getAttribOr(IS_BOT, false)) {
                plr.requestLogout();
            }
        }
        player.message("You have kicked all " + players + " non-staff players.");
    }

    @Override
    public boolean canUse(Player player) {
        return player.getPlayerRights().isAdminOrGreater(player);
    }
}
