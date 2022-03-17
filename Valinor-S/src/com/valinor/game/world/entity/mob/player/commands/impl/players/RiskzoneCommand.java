package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.position.Tile;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 17, 2022
 */
public class RiskzoneCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        Tile tile = new Tile(3107, 3509);

        if (!Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
            return;
        }

        Teleports.basicTeleport(player, tile);
        player.message("You have been teleported to the Riskzone.");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}
