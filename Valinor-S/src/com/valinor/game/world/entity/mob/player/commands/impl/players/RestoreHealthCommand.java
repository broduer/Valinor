package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.Color;
import com.valinor.util.SecondsTimer;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 17, 2022
 */
public class RestoreHealthCommand implements Command {

    private final SecondsTimer commandDelay = new SecondsTimer();

    @Override
    public void execute(Player player, String command, String[] parts) {
        if(WildernessArea.inWilderness(player.tile())) {
            player.message(Color.RED.wrap("What the hell are you thinking? That doesn't work in dangerous areas!"));
            return;
        }

        if (commandDelay.active()) {
            player.message(Color.RED.wrap("You can only use this command every 5 seconds."));
            return;
        }

        if(!player.tile().homeRegion() && !player.tile().memberZone()) {
            player.message(Color.RED.wrap("You can't heal yourself here!"));
            return;
        }
        player.heal();
        commandDelay.start(5);
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}
