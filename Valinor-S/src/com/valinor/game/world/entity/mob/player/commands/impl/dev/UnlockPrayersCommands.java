package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

public class UnlockPrayersCommands implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        int type = Integer.parseInt(parts[1]);
        if (type == 0) {
            player.putAttrib(AttributeKey.PRESERVE, true);
        } else if (type == 1) {
            player.putAttrib(AttributeKey.RIGOUR, true);
        } else if (type == 2) {
            player.putAttrib(AttributeKey.AUGURY, true);
        }
        boolean preserve_unlocked = player.getAttribOr(AttributeKey.PRESERVE, false);
        boolean rigour_unlocked = player.getAttribOr(AttributeKey.RIGOUR, false);
        boolean augury_unlocked = player.getAttribOr(AttributeKey.AUGURY, false);
        player.getPacketSender().sendConfig(709, preserve_unlocked ? 1 : 0);
        player.getPacketSender().sendConfig(711, rigour_unlocked ? 1 : 0);
        player.getPacketSender().sendConfig(713, augury_unlocked ? 1 : 0);
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isDeveloperOrGreater(player));
    }

}
