package com.valinor.game.world.items.container.shop.currency.impl;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.container.shop.currency.Currency;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 27, 2021
 */
public final class BountyHunterPointsCurrency implements Currency {

    @Override
    public boolean tangible() {
        return false;
    }

    @Override
    public boolean takeCurrency(Player player, int amount) {
        int bountyHunterPoints = player.getAttribOr(AttributeKey.BOUNTY_HUNTER_POINTS, 0);
        if (bountyHunterPoints >= amount) {
            player.putAttrib(AttributeKey.BOUNTY_HUNTER_POINTS, bountyHunterPoints - amount);
            return true;
        } else {
            player.message("You do not have enough Bounty hunter points.");
            return false;
        }
    }

    @Override
    public void recieveCurrency(Player player, int amount) {
        //Empty can't receive currency from shops
    }

    @Override
    public int currencyAmount(Player player, int cost) {
        return player.getAttribOr(AttributeKey.BOUNTY_HUNTER_POINTS, 0);
    }

    @Override
    public boolean canRecieveCurrency(Player player) {
        return false;
    }

    @Override
    public String toString() {
        return "Bounty hunter points";
    }
}
