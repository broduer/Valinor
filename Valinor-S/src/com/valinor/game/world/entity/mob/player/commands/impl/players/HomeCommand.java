package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.GameServer;
import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.position.Tile;

public class HomeCommand implements Command {

    public void execute(Player player, String command, String[] parts) {
        Tile tile = GameServer.properties().defaultTile;

        if (!Teleports.canTeleport(player,true, TeleportType.GENERIC)) {
            return;
        }

        Teleports.basicTeleport(player, tile);
        player.message("You have been teleported to home.");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }

}
