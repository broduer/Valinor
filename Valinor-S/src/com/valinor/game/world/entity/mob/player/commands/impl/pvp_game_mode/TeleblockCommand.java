package com.valinor.game.world.entity.mob.player.commands.impl.pvp_game_mode;

import com.valinor.game.world.entity.mob.player.GameMode;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.items.Item;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 04, 2022
 */
public class TeleblockCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if(player.gameMode() != GameMode.INSTANT_PKER) {
            player.message("Only Instant Pkers can use this command.");
            return;
        }

        player.inventory().addAll(new Item(LAW_RUNE, 100), new Item(CHAOS_RUNE, 100), new Item(DEATH_RUNE, 100));
        player.message("You have spawned some teleblock runes.");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}
