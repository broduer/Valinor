package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.game.content.tradingpost.TradingPost;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

/**
 * @author Patrick van Elderen | May, 28, 2021, 15:57
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class DisableTpItemListingCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        TradingPost.TRADING_POST_LISTING_ENABLED =! TradingPost.TRADING_POST_LISTING_ENABLED;
        String msg = TradingPost.TRADING_POST_LISTING_ENABLED ? "Enabled" : "Disabled";
        player.message("Trading post listing is now "+msg+".");
    }

    @Override
    public boolean canUse(Player player) {
        return player.getPlayerRights().isDeveloperOrGreater(player);
    }
}
