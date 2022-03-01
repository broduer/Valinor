package com.valinor.game.world.items.container.shop.currency.impl;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.items.container.shop.ShopUtility;
import com.valinor.game.world.items.container.shop.currency.Currency;
import com.valinor.util.Color;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.Utils;

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
        //PKP as item goes over points
        int pkpInInventory = player.inventory().count(CustomItemIdentifiers.PKP_TICKET);
        if (pkpInInventory > 0) {
            if(pkpInInventory >= amount) {
                player.inventory().remove(CustomItemIdentifiers.PKP_TICKET, amount);
                return true;
            }
        }
        var pkPoints = player.<Integer>getAttribOr(AttributeKey.PK_POINTS, 0);
        if (pkPoints >= amount) {
            player.putAttrib(AttributeKey.PK_POINTS, pkPoints - amount);
            player.getPacketSender().sendString(QuestTab.InfoTab.PK_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.PK_POINTS.childId).fetchLineData(player));
            return true;
        }
        return false;
    }

    @Override
    public void recieveCurrency(Player player, int amount) {
        var pkPoints = player.<Integer>getAttribOr(AttributeKey.PK_POINTS, 0);
        player.putAttrib(AttributeKey.PK_POINTS, pkPoints + amount);
        player.getPacketSender().sendString(QuestTab.InfoTab.PK_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.PK_POINTS.childId).fetchLineData(player));
        String shopName = "Pk Point Shop - (PKP : "+ Color.RED.wrap(Utils.formatNumber(player.<Integer>getAttribOr(AttributeKey.PK_POINTS,0)))+")";
        player.getPacketSender().sendString(ShopUtility.NAME_INTERFACE_CHILD_ID, shopName);
    }

    @Override
    public int currencyAmount(Player player, int cost) {
        int amount;
        int pkpInInventory = player.inventory().count(CustomItemIdentifiers.PKP_TICKET);

        //If we have enough pkp in inventory otherwise fallback.
        if (pkpInInventory >= cost) {
            amount = pkpInInventory;
        } else {
            long current = player.<Integer>getAttribOr(AttributeKey.PK_POINTS, 0);
            if (current >= Integer.MAX_VALUE)
                return Integer.MAX_VALUE;
            amount = Math.toIntExact(current);
        }
        return amount;
    }

    @Override
    public boolean canRecieveCurrency(Player player) {
        return true;
    }

    @Override
    public String toString() {
        return "pk points";
    }
}
