package com.valinor.game.world.entity.mob.player.commands.impl.member;

import com.valinor.game.content.syntax.impl.PickYellColour;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

public class YellColourCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if(!player.getMemberRights().isSapphireMemberOrGreater(player) && !player.getPlayerRights().isDeveloperOrGreater(player)) {
            player.message("<col=ca0d0d>Only Sapphire Members may use this feature.");
            return;
        }

        player.setEnterSyntax(new PickYellColour());
        player.getPacketSender().sendEnterInputPrompt("What colour would you like your tag to be? HTML Color codes: #336eff");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}
