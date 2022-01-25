package com.valinor.game.world.entity.mob.player.commands.impl.member;

import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.position.areas.impl.WildernessArea;

public class DCaveCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        boolean member = player.getMemberRights().isSapphireMemberOrGreater(player);
        if(!member && !player.getPlayerRights().isDeveloperOrGreater(player)) {
            player.message("You need to be at least a sapphire member to use this command.");
            return;
        }

        if (WildernessArea.inWilderness(player.tile()) && !player.getPlayerRights().isDeveloperOrGreater(player)) {
            player.message("You can't use this command in the wilderness.");
            return;
        }

        if(player.isInTournamentLobby() || player.inActiveTournament()) {
            player.message("You can't use this command in a tournament");
            return;
        }

        Tile tile = new Tile(2335, 9795);

        if (!Teleports.canTeleport(player,true, TeleportType.GENERIC) || !Teleports.pkTeleportOk(player, tile)) {
            return;
        }

        Teleports.basicTeleport(player, tile);
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}
