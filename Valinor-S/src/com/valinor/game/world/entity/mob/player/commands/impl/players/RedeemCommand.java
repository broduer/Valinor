package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.db.transactions.CollectPayments;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.util.Color;
import com.valinor.util.timers.TimerKey;

/**
 * @author Patrick van Elderen | May, 29, 2021, 19:47
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class RedeemCommand implements Command {

    public static boolean ENABLED = true;

    @Override
    public void execute(Player player, String command, String[] parts) {
        if(ENABLED) {
            if(player.getTimers().has(TimerKey.WAIT_FOR_DB)) {
                player.message("You must wait 10s before using this command again.");
                return;
            }
            player.getTimers().addOrSet(TimerKey.WAIT_FOR_DB, 10);
            CollectPayments.INSTANCE.collectPayments(player);
        } else {
            player.message(Color.RED.wrap("The ::redeem command has been temporarily disabled."));
        }
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}
