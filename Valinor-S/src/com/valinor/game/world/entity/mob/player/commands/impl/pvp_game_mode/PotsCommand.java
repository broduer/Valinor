package com.valinor.game.world.entity.mob.player.commands.impl.pvp_game_mode;

import com.valinor.game.world.entity.mob.player.GameMode;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.areas.impl.WildernessArea;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 04, 2022
 */
public class PotsCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if(player.gameMode() != GameMode.INSTANT_PKER && !player.getPlayerRights().isDeveloperOrGreater(player)) {
            player.message("Only Instant Pkers can use this command.");
            return;
        }

        if (!player.tile().inSafeZone() && !player.getPlayerRights().isDeveloperOrGreater(player)) {
            player.message("You can only use this command at safe zones.");
            return;
        }

        if(WildernessArea.inWilderness(player.tile())) {
            player.message("You can only use this command at safe zones.");
            return;
        }

        player.inventory().addAll(new Item(SUPER_ATTACK4), new Item(SUPER_STRENGTH4), new Item(SUPER_DEFENCE4));
        player.message("You have spawned a combat potions set.");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}
