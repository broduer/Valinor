package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.content.tournaments.TournamentManager;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

public class ExitCommand implements Command {

    public void execute(Player player, String command, String[] parts) {
        //remove from tourny
        TournamentManager.leaveTourny(player, false,true);
        player.message("You have left the tournament.");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }

}
