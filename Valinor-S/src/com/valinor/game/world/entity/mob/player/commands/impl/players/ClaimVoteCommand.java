package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.GameServer;
import com.valinor.db.transactions.CollectVotes;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.util.timers.TimerKey;

/**
 * @author Patrick van Elderen | May, 29, 2021, 11:13
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class ClaimVoteCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (GameServer.properties().enableSql) {
            if(player.getTimers().has(TimerKey.WAIT_FOR_DB)) {
                player.message("You must wait 10s before using this command again.");
                return;
            }
            player.getTimers().addOrSet(TimerKey.WAIT_FOR_DB, 10);
            CollectVotes.INSTANCE.collectVotes(player);
        } else {
            player.message("The database is offline at this moment.");
        }
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}
