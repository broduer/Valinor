package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.game.content.raids.chamber_of_xeric.ChamberOfXericReward;
import com.valinor.game.content.raids.theatre_of_blood.TheatreOfBlood;
import com.valinor.game.content.raids.theatre_of_blood.TheatreOfBloodRewards;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 15, 2022
 */
public class TestRaidsLootCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        player.putAttrib(AttributeKey.PERSONAL_POINTS, 900_000);
        /*for (int i = 0; i < 125; i++) {
            ChamberOfXericReward.giveRewards(player);
            ChamberOfXericReward.displayRewards(player);
            ChamberOfXericReward.withdrawReward(player);
        }*/
        TheatreOfBloodRewards.giveRewards(player);
        TheatreOfBlood.spawnLootChests(player);
        //TheatreOfBloodRewards.displayRewards(player);
        //TheatreOfBloodRewards.withdrawReward(player);
    }

    @Override
    public boolean canUse(Player player) {
        return player.getPlayerRights().isDeveloperOrGreater(player);
    }
}
