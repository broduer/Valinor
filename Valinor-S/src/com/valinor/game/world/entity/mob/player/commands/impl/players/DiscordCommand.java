package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

import static com.valinor.game.GameConstants.DISCORD_URL;

public class DiscordCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        player.getPacketSender().sendURL(DISCORD_URL);
        player.message("Opening discord in your web browser...");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }

}
