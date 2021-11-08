package com.valinor.game.world.entity.mob.player.commands.impl.staff.moderator;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

public class UnVanishCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        player.looks().hide(false);
        player.message("You are now exposed again.");
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isModeratorOrGreater(player));
    }
}
