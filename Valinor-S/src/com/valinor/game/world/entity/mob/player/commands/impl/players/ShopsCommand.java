package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.position.Tile;

/**
 * @author Malefique
 * @Since december 10, 2020
 */
public class ShopsCommand implements Command {

    public void execute(Player player, String command, String[] parts) {
        Tile tile = new Tile(3080, 3510, 0);

        if (Teleports.canTeleport(player,true, TeleportType.GENERIC)) {
            Teleports.basicTeleport(player, tile);
        }

        player.message("You have been teleported to the shops area.");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}
