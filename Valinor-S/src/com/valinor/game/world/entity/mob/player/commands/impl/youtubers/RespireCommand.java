package com.valinor.game.world.entity.mob.player.commands.impl.youtubers;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

public class RespireCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        player.getPacketSender().sendURL("https://youtube.com/c/InferiaDZN");
        player.message("Opening Respire's channel in your web browser...");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }

}
