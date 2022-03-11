package com.valinor.game.world.items.container.shop.currency.impl;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.items.container.shop.currency.Currency;

import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.HOTSPOT_POINTS;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 11, 2022
 */
public class HotspotPointsCurrency implements Currency {

    @Override
    public boolean tangible() {
        return false;
    }

    @Override
    public boolean takeCurrency(Player player, int amount) {
        int hotspotPoints = player.getAttribOr(AttributeKey.HOTSPOT_POINTS, 0);
        if (hotspotPoints >= amount) {
            player.putAttrib(AttributeKey.HOTSPOT_POINTS, hotspotPoints - amount);
            player.getPacketSender().sendString(HOTSPOT_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(HOTSPOT_POINTS.childId).fetchLineData(player));
            return true;
        } else {
            player.message("You do not have enough hotspot points.");
            return false;
        }
    }

    @Override
    public void recieveCurrency(Player player, int amount) {
        //Empty can't receive currency from shops
    }

    @Override
    public int currencyAmount(Player player, int cost) {
        return player.getAttribOr(AttributeKey.HOTSPOT_POINTS, 0);
    }

    @Override
    public boolean canRecieveCurrency(Player player) {
        return false;
    }
}
