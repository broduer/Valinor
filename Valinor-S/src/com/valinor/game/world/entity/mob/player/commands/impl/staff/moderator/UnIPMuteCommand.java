package com.valinor.game.world.entity.mob.player.commands.impl.staff.moderator;

import com.valinor.game.GameEngine;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.util.Utils;

/**
 * @author Patrick van Elderen | February, 04, 2021, 14:07
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class UnIPMuteCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (command.length() <= 8)
            return;
        String IPToRemove = Utils.formatText(command.substring(9)); // after "unipmute "

        if (IPToRemove.isEmpty()) {
            player.message("You must enter a valid IP.");
            return;
        }
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isModeratorOrGreater(player));
    }

}
