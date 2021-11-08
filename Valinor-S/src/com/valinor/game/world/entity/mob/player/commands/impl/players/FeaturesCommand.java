package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

import static com.valinor.game.GameConstants.DONATOR_FEATURES_URL;

public class FeaturesCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        player.getPacketSender().sendURL(DONATOR_FEATURES_URL);
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }

}
