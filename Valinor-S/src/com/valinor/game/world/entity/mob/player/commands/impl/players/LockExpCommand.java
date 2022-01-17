package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.util.Color;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 17, 2022
 */
public class LockExpCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if(player.getAttribOr(AttributeKey.XP_LOCKED, false)) {
            player.message(Color.GREEN.wrap("Your exp is now unlocked."));
            player.putAttrib(AttributeKey.XP_LOCKED, false);
        } else {
            player.message(Color.RED.wrap("Your exp is now locked."));
            player.putAttrib(AttributeKey.XP_LOCKED, true);
        }
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }

}
