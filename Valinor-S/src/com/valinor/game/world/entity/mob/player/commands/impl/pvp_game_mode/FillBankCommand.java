package com.valinor.game.world.entity.mob.player.commands.impl.pvp_game_mode;

import com.valinor.game.world.entity.mob.player.GameMode;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.items.Item;

import static com.valinor.game.GameConstants.BANK_ITEMS;
import static com.valinor.game.GameConstants.TAB_AMOUNT;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 04, 2022
 */
public class FillBankCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if(player.gameMode() != GameMode.INSTANT_PKER && !player.getPlayerRights().isDeveloperOrGreater(player)) {
            player.message("Only Instant Pkers can use this command.");
            return;
        }
        for(Item item : BANK_ITEMS) {
            player.getBank().depositFromNothing(item);
        }
        player.message("We have filled up your bank with starter items.");
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}
