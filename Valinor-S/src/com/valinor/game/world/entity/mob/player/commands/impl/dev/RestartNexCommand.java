package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex.ZarosGodwars;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 17, 2022
 */
public class RestartNexCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        ZarosGodwars.clear();
        ZarosGodwars.startEvent();
    }

    @Override
    public boolean canUse(Player player) {
        return player.getPlayerRights().isDeveloperOrGreater(player);
    }
}
