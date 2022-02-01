package com.valinor.game.world.items.container.shop.currency.impl;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.items.container.shop.currency.Currency;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 24, 2021
 */
public class AchievementPointsCurrency implements Currency {

    @Override
    public boolean tangible() {
        return false;
    }

    @Override
    public boolean takeCurrency(Player player, int amount) {
        int achievementPoints = player.getAttribOr(AttributeKey.ACHIEVEMENT_POINTS, 0);
        if (achievementPoints >= amount) {
            player.putAttrib(AttributeKey.ACHIEVEMENT_POINTS, achievementPoints - amount);
            player.getPacketSender().sendString(QuestTab.InfoTab.ACHIEVEMENT_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.ACHIEVEMENT_POINTS.childId).fetchLineData(player));
            return true;
        } else {
            player.message("You do not have enough achievement points.");
            return false;
        }
    }

    @Override
    public void recieveCurrency(Player player, int amount) {
        var achievementPoints = player.<Integer>getAttribOr(AttributeKey.ACHIEVEMENT_POINTS, 0) + amount;
        player.putAttrib(AttributeKey.ACHIEVEMENT_POINTS, achievementPoints);
        player.getPacketSender().sendString(QuestTab.InfoTab.ACHIEVEMENT_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.ACHIEVEMENT_POINTS.childId).fetchLineData(player));
    }

    @Override
    public int currencyAmount(Player player, int cost) {
        return player.getAttribOr(AttributeKey.ACHIEVEMENT_POINTS, 0);
    }

    @Override
    public boolean canRecieveCurrency(Player player) {
        return true;
    }

    @Override
    public String toString() {
        return "Achievement points";
    }
}
