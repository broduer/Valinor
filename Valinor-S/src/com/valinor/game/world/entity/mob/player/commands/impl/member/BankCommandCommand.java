package com.valinor.game.world.entity.mob.player.commands.impl.member;

import com.valinor.GameServer;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.Color;

public class BankCommandCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if(!player.getMemberRights().isDragonstoneMemberOrGreater(player) && (!player.getPlayerRights().isDeveloperOrGreater(player) && !GameServer.properties().test)) {
            player.message("You need to be at least a Legendary member to use this command.");
            return;
        }

        if(!player.getPlayerRights().isDeveloperOrGreater(player) && WildernessArea.inWilderness(player.tile())) {
            player.message("<col="+ Color.RED.getColorValue()+">You can't use this command here.");
            return;
        }

        if(player.getRaids() != null && player.getRaids().raiding(player)) {
            player.message("You cannot use this command whilst raiding.");
            return;
        }

        if((player.inActiveTournament() || player.isInTournamentLobby()) && (!player.getPlayerRights().isDeveloperOrGreater(player))) {
            player.message("You cannot use this command during an active tournament.");
            return;
        }

        if (!player.getPlayerRights().isDeveloperOrGreater(player) && CombatFactory.inCombat(player)) {
            player.message("You cannot use this command during combat.");
            return;
        }

        if(player.busy()) {
            player.message("You're to busy to use the ::bank command right now.");
            return;
        }

        player.getBank().open();
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }

}
