package com.valinor.game.content.items;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;

import static com.valinor.util.CustomItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 22, 2022
 */
public class Scrolls extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if (item.getId() == SAPPHIRE_MEMBER_RANK) {
                player.optionsTitled("Are you sure you wish to read the scroll?", "Yes.", "No.", () -> {
                    //Safety for cheat clients
                    if(!player.inventory().contains(SAPPHIRE_MEMBER_RANK)) {
                        return;
                    }

                    var total = player.<Double>getAttribOr(AttributeKey.TOTAL_PAYMENT_AMOUNT, 0D);

                    if(total >= 10) {
                        player.message(Color.RED.wrap("You already have more then 9$ total donated, and cannot open this scroll."));
                        return;
                    }

                    player.inventory().remove(SAPPHIRE_MEMBER_RANK);
                    var increaseBy = 10;
                    total = player.<Double>getAttribOr(AttributeKey.TOTAL_PAYMENT_AMOUNT, 0D) + increaseBy;
                    player.putAttrib(AttributeKey.TOTAL_PAYMENT_AMOUNT, total);
                    player.getMemberRights().update(player,false);
                    player.getPacketSender().sendString(QuestTab.InfoTab.TOTAL_DONATED.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.TOTAL_DONATED.childId).fetchLineData(player));
                });

                return true;
            }
            if (item.getId() == FIFTY_TOTAL_DONATED_SCROLL) {
                player.optionsTitled("Are you sure you wish to read the scroll?", "Yes.", "No.", () -> {
                    //Safety for cheat clients
                    if(!player.inventory().contains(FIFTY_TOTAL_DONATED_SCROLL)) {
                        return;
                    }

                    player.inventory().remove(FIFTY_TOTAL_DONATED_SCROLL);
                    var increaseBy = 50;
                    var total = player.<Double>getAttribOr(AttributeKey.TOTAL_PAYMENT_AMOUNT, 0D) + increaseBy;
                    player.putAttrib(AttributeKey.TOTAL_PAYMENT_AMOUNT, total);
                    player.getMemberRights().update(player,false);
                    player.getPacketSender().sendString(QuestTab.InfoTab.TOTAL_DONATED.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.TOTAL_DONATED.childId).fetchLineData(player));
                });
                return true;
            }
            if (item.getId() == ONE_HUNDRED_TOTAL_DONATED_SCROLL) {
                player.optionsTitled("Are you sure you wish to read the scroll?", "Yes.", "No.", () -> {
                    //Safety for cheat clients
                    if(!player.inventory().contains(ONE_HUNDRED_TOTAL_DONATED_SCROLL)) {
                        return;
                    }

                    player.inventory().remove(ONE_HUNDRED_TOTAL_DONATED_SCROLL);
                    var increaseBy = 100;
                    var total = player.<Double>getAttribOr(AttributeKey.TOTAL_PAYMENT_AMOUNT, 0D) + increaseBy;
                    player.putAttrib(AttributeKey.TOTAL_PAYMENT_AMOUNT, total);
                    player.getMemberRights().update(player,false);
                    player.getPacketSender().sendString(QuestTab.InfoTab.TOTAL_DONATED.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.TOTAL_DONATED.childId).fetchLineData(player));
                });
                return true;
            }
            if (item.getId() == HUNDRED_FIFTY_TOTAL_DONATED_SCROLL) {
                player.optionsTitled("Are you sure you wish to read the scroll?", "Yes.", "No.", () -> {
                    //Safety for cheat clients
                    if(!player.inventory().contains(HUNDRED_FIFTY_TOTAL_DONATED_SCROLL)) {
                        return;
                    }

                    player.inventory().remove(HUNDRED_FIFTY_TOTAL_DONATED_SCROLL);
                    var increaseBy = 150;
                    var total = player.<Double>getAttribOr(AttributeKey.TOTAL_PAYMENT_AMOUNT, 0D) + increaseBy;
                    player.putAttrib(AttributeKey.TOTAL_PAYMENT_AMOUNT, total);
                    player.getMemberRights().update(player,false);
                    player.getPacketSender().sendString(QuestTab.InfoTab.TOTAL_DONATED.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.TOTAL_DONATED.childId).fetchLineData(player));
                });
                return true;
            }
        }
        return false;
    }
}
