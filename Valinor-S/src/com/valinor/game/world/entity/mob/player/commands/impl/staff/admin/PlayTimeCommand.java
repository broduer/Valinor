package com.valinor.game.world.entity.mob.player.commands.impl.staff.admin;

import com.valinor.game.content.areas.lumbridge.dialogue.Hans;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

/**
 * @author PVE
 * @Since september 13, 2020
 */
public class PlayTimeCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        player.message(Hans.getTimeDHS(player));
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isDeveloperOrGreater(player));
    }
}
