package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.db.transactions.CollectTopVoterRewards;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 04, 2022
 */
public class ClaimTopVoter implements Command {

    private long lastCommandUsed;

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (System.currentTimeMillis() - lastCommandUsed >= 10000) {
            lastCommandUsed = System.currentTimeMillis();
            CollectTopVoterRewards.INSTANCE.collectTopVoterReward(player);
        }
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}
