package com.valinor.game.world.entity.mob.player.commands.impl.youtubers;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

/**
 * @author Patrick van Elderen | June, 21, 2021, 14:33
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class VekeRSCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        player.getPacketSender().sendURL("https://www.youtube.com/user/mursu41");
        player.message("Opening VekeRS's channel in your web browser...");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }

}
