package com.valinor.game.world.items.container.shop.currency.impl;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.items.container.shop.currency.Currency;

/**
 * @author Patrick van Elderen | June, 10, 2021, 19:10
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class BossPointsCurrency implements Currency {

    @Override
    public boolean tangible() {
        return false;
    }

    @Override
    public boolean takeCurrency(Player player, int amount) {
        int bossPoints = player.getAttribOr(AttributeKey.BOSS_POINTS, 0);
        if (bossPoints >= amount) {
            player.putAttrib(AttributeKey.BOSS_POINTS, bossPoints - amount);
            player.getPacketSender().sendString(QuestTab.InfoTab.BOSS_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.BOSS_POINTS.childId).fetchLineData(player));
            return true;
        } else {
            player.message("You do not have enough boss points.");
            return false;
        }
    }

    @Override
    public void recieveCurrency(Player player, int amount) {
        //Empty can't receive currency from shops
    }

    @Override
    public int currencyAmount(Player player, int cost) {
        return player.getAttribOr(AttributeKey.BOSS_POINTS, 0);
    }

    @Override
    public boolean canRecieveCurrency(Player player) {
        return false;
    }

    @Override
    public String toString() {
        return "Boss points";
    }
}
