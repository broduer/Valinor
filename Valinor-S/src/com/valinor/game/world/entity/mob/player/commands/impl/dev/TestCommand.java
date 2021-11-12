
package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.game.content.account.AccountSelection;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.util.Varbit;

public class TestCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        AccountSelection.open(player);
        //System.out.println(player.pet().def().name);
        player.message("Test command has been activated.");
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isDeveloperOrGreater(player));
    }

}
