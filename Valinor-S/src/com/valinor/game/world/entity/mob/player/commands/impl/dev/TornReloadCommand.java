package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.game.content.tournaments.TournamentManager;
import com.valinor.game.world.entity.mob.player.Player;

public class TornReloadCommand implements com.valinor.game.world.entity.mob.player.commands.Command {
    @Override
    public void execute(Player player, String command, String[] parts) {
        TournamentManager.initalizeTournaments();
        player.message("Tornament system has been reloaded.");
    }

    @Override
    public boolean canUse(Player player) {
        return player.getPlayerRights().isDeveloperOrGreater(player);
    }
}
