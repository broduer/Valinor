package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.content.syntax.impl.ChangePasswordSyntax;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

public class ChangePasswordCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {

        // Known exploit
        if (command.contains("\r") || command.contains("\n")) {
            return;
        }

        player.setEnterSyntax(new ChangePasswordSyntax());
        player.getPacketSender().sendEnterInputPrompt("Enter your new password:");


    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}
