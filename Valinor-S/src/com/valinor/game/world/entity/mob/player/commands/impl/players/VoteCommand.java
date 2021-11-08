package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

public class VoteCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        player.getPacketSender().sendURL("https://ferox-os.com/vote/");
        player.message("Opening https://ferox-os.com/vote/ in your web browser...");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }

}
