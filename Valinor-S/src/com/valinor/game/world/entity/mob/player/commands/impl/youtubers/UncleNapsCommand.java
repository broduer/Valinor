package com.valinor.game.world.entity.mob.player.commands.impl.youtubers;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 13, 2022
 */
public class UncleNapsCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        player.getPacketSender().sendURL("https://www.youtube.com/channel/UCb8Q08QpUGFJz7zslWax9QQ");
        player.message("Opening UncleNaps's channel in your web browser...");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }

}
