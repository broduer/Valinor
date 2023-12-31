package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

public class SaveAllCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        //Proper way of saving, use the logout service.
        World.getWorld().ls.saveAllAsync();
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isDeveloperOrGreater(player));
    }
}
