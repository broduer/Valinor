package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.content.areas.wilderness.content.hitman_services.Hitman;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 09, 2022
 */
public class BountyCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        Hitman.requestBounty(player);
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}
