package com.valinor.game.world.items.container.shop.currency.impl;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.items.container.shop.currency.Currency;

import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.TOURNAMENT_POINTS;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 11, 2022
 */
public class TournamentPointsCurrency implements Currency {

    @Override
    public boolean tangible() {
        return false;
    }

    @Override
    public boolean takeCurrency(Player player, int amount) {
        int tournamentPoints = player.getAttribOr(AttributeKey.TOURNAMENT_POINTS, 0);
        if (tournamentPoints >= amount) {
            player.putAttrib(AttributeKey.TOURNAMENT_POINTS, tournamentPoints - amount);
            player.getPacketSender().sendString(TOURNAMENT_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(TOURNAMENT_POINTS.childId).fetchLineData(player));
            return true;
        } else {
            player.message("You do not have enough tournament points.");
            return false;
        }
    }

    @Override
    public void recieveCurrency(Player player, int amount) {
        //Empty can't receive currency from shops
    }

    @Override
    public int currencyAmount(Player player, int cost) {
        return player.getAttribOr(AttributeKey.TOURNAMENT_POINTS, 0);
    }

    @Override
    public boolean canRecieveCurrency(Player player) {
        return false;
    }
}
