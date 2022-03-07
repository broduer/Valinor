package com.valinor.game.world.entity.mob.player.commands.impl.staff.admin;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.entity.mob.player.commands.impl.players.PromoCodeCommand;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 07, 2022
 */
public class DisablePromoCodeCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        PromoCodeCommand.PROMO_CODE_COMMAND_ENABLED =! PromoCodeCommand.PROMO_CODE_COMMAND_ENABLED;
        String msg = PromoCodeCommand.PROMO_CODE_COMMAND_ENABLED ? "Disabled" : "Enabled";
        player.message("The promo code command is now "+msg+".");
    }

    @Override
    public boolean canUse(Player player) {
        return player.getPlayerRights().isAdminOrGreater(player);
    }
}
