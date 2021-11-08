package com.valinor.game.world.entity.mob.player.commands.impl.staff.admin;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

public class DismissBroadcastCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        World.getWorld().sendWorldMessage("dismissbroadcast##");
    }

    @Override
    public boolean canUse(Player player) {
        return player.getPlayerRights().isDeveloperOrGreater(player);
    }
}
