package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

import static com.valinor.game.GameConstants.STORE_URL;

public class StoreCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        player.getPacketSender().sendURL(STORE_URL);
        player.message("Opening the web shop in your web browser...");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }

}
