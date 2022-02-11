package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 11, 2022
 */
public class ToggleDidYouKnowCommand implements Command {

    public void execute(Player player, String command, String[] parts) {
        boolean didYouKnow = player.getAttribOr(AttributeKey.DID_YOU_KNOW, true);
        didYouKnow = !didYouKnow;
        player.putAttrib(AttributeKey.DID_YOU_KNOW, didYouKnow);
        player.message("Your did you know messages are now " + (didYouKnow ? "enabled." : "disabled."));
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }

}
