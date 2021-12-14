package com.valinor.game.content.items.mystery;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.game.world.entity.AttributeKey.*;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.MARK_OF_GRACE;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 23, 2021
 */
public class PointMysteryBox extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == POINTS_MYSTERY_BOX) {
                if (!player.inventory().contains(POINTS_MYSTERY_BOX))
                    return true;
                player.inventory().remove(POINTS_MYSTERY_BOX);
                var amount = World.getWorld().random(3,5);
                var votePoints = World.getWorld().random(1,2);
                String points = "";

                if (World.getWorld().rollDie(50, 1)) {
                    amount += 5;
                    votePoints += 1;
                } else if (World.getWorld().rollDie(100, 1)) {
                    amount += 10;
                    votePoints += 2;
                } else if (World.getWorld().rollDie(150, 1)) {
                    amount += 15;
                    votePoints += 3;
                }

                if (World.getWorld().rollDie(50, 1)) {
                    var coins = World.getWorld().random(250, 1000);
                    player.inventory().addOrBank(new Item(VALINOR_COINS, coins));
                    World.getWorld().sendWorldMessage("<img=1081><col=0052cc>" + player.getUsername() + " just received x" + coins + " Valinor coins from a points mystery box!");
                } else if (World.getWorld().rollDie(35, 1)) {
                    points = "vote points";
                    var votePts = player.<Integer>getAttribOr(VOTE_POINTS, 0) + votePoints;
                    player.putAttrib(VOTE_POINTS, votePts);
                    player.getPacketSender().sendString(QuestTab.InfoTab.VOTE_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.VOTE_POINTS.childId).fetchLineData(player));
                } else if (World.getWorld().rollDie(25, 1)) {
                    points = "marks of grace";
                    player.inventory().addOrBank(new Item(MARK_OF_GRACE, amount));
                } else if (World.getWorld().rollDie(6, 1)) {
                    points = "slayer points";
                    var slayerRewardPoints = player.<Integer>getAttribOr(SLAYER_REWARD_POINTS, 0) + amount;
                    player.putAttrib(SLAYER_REWARD_POINTS, slayerRewardPoints);
                    player.getPacketSender().sendString(QuestTab.InfoTab.SLAYER_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.SLAYER_POINTS.childId).fetchLineData(player));
                } else if (World.getWorld().rollDie(5, 1)) {
                    points = "boss points";
                    var bossPoints = player.<Integer>getAttribOr(BOSS_POINTS, 0) + amount;
                    player.putAttrib(BOSS_POINTS, bossPoints);
                    player.getPacketSender().sendString(QuestTab.InfoTab.BOSS_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.BOSS_POINTS.childId).fetchLineData(player));
                    player.getPacketSender().sendString(QuestTab.InfoTab.BOSS_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.BOSS_POINTS.childId).fetchLineData(player));
                } else if (World.getWorld().rollDie(3, 1)) {
                    points = "double drops scroll";
                    player.inventory().addOrBank(new Item(DOUBLE_DROPS_SCROLL, amount));
                } else {
                    points = "pk points";
                    var pkPoints = player.<Integer>getAttribOr(PK_POINTS, 0) + amount;
                    player.putAttrib(PK_POINTS, pkPoints);
                    player.getPacketSender().sendString(QuestTab.InfoTab.PK_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.PK_POINTS.childId).fetchLineData(player));
                }

                if (amount > 5) {
                    World.getWorld().sendWorldMessage("<img=1081><col=0052cc>" + player.getUsername() + " just received x" + amount + " " + points + " from a points mystery box!");
                }

                var voteP = points.contains("vote");
                var amt = voteP ? votePoints : amount;
                player.message("You open the points mystery box and found...");
                player.message("x"+amt+" "+points+".");
                return true;
            }
        }
        return false;
    }
}
