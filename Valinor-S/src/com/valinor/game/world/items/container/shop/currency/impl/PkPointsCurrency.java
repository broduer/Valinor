package com.valinor.game.world.items.container.shop.currency.impl;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.container.shop.currency.Currency;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 24, 2021
 */
public class PkPointsCurrency implements Currency {

    @Override
    public boolean tangible() {
        return false;
    }

    @Override
    public boolean takeCurrency(Player player, int amount) {
        int pkPoints = player.getAttribOr(AttributeKey.PK_POINTS, 0);
        if (pkPoints >= amount) {
            player.putAttrib(AttributeKey.PK_POINTS, pkPoints - amount);
            return true;
        } else {
            player.message("You do not have enough pk points.");
            return false;
        }
    }

    @Override
    public void recieveCurrency(Player player, int amount) {
        //Empty can't receive currency from shops
    }

    @Override
    public int currencyAmount(Player player, int cost) {
        return player.getAttribOr(AttributeKey.PK_POINTS, 0);
    }

    @Override
    public boolean canRecieveCurrency(Player player) {
        return false;
    }

    @Override
    public String toString() {
        return "pk points";
    }
}
