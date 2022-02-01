package com.valinor.game.content.items.mystery;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Utils;

import static com.valinor.game.world.entity.AttributeKey.*;
import static com.valinor.game.world.entity.AttributeKey.PK_POINTS;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.CustomItemIdentifiers.DOUBLE_DROPS_SCROLL;
import static com.valinor.util.ItemIdentifiers.MARK_OF_GRACE;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 23, 2021
 */
public class PointsMysteryChest extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == POINTS_MYSTERY_CHEST) {
                if (!player.inventory().contains(POINTS_MYSTERY_CHEST))
                    return true;
                player.inventory().remove(POINTS_MYSTERY_CHEST);
                var amount = World.getWorld().random(10, 15);
                var votePoints = World.getWorld().random(3,7);
                var pkPoints = World.getWorld().random(300,700);
                String points = "";

                if (World.getWorld().rollDie(50, 1)) {
                    amount += 8;
                    votePoints += 2;
                    pkPoints += 100;
                } else if (World.getWorld().rollDie(100, 1)) {
                    amount += 13;
                    votePoints += 3;
                    pkPoints += 200;
                } else if (World.getWorld().rollDie(150, 1)) {
                    amount += 20;
                    votePoints += 4;
                    pkPoints += 300;
                }

                if (World.getWorld().rollDie(50, 1)) {
                    var coins = World.getWorld().random(500, 1500);
                    player.inventory().addOrBank(new Item(VALINOR_COINS, coins));
                    World.getWorld().sendWorldMessage("<img=1081><col=0052cc>" + player.getUsername() + " just received x" + coins + " Valinor coins from a points mystery box!");
                    Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a points mystery box and received x"+coins+" Valinor coins.", "boxes_opened");
                } else if (World.getWorld().rollDie(35, 1)) {
                    points = "vote points";
                    var votePts = player.<Integer>getAttribOr(VOTE_POINTS, 0) + votePoints;
                    player.putAttrib(VOTE_POINTS, votePts);
                    player.getPacketSender().sendString(QuestTab.InfoTab.VOTE_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.VOTE_POINTS.childId).fetchLineData(player));
                    Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a points mystery box and received x"+votePts+" vote points.", "boxes_opened");
                } else if (World.getWorld().rollDie(25, 1)) {
                    points = "marks of grace";
                    player.inventory().addOrBank(new Item(MARK_OF_GRACE, amount));
                    Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a points mystery box and received x"+points+" marks of grace.", "boxes_opened");
                } else if (World.getWorld().rollDie(6, 1)) {
                    points = "slayer points";
                    var slayerRewardPoints = player.<Integer>getAttribOr(SLAYER_REWARD_POINTS, 0) + amount;
                    player.putAttrib(SLAYER_REWARD_POINTS, slayerRewardPoints);
                    player.getPacketSender().sendString(QuestTab.InfoTab.SLAYER_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.SLAYER_POINTS.childId).fetchLineData(player));
                    Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a points mystery box and received x"+slayerRewardPoints+" slayer points.", "boxes_opened");
                } else if (World.getWorld().rollDie(5, 1)) {
                    points = "boss points";
                    var bossPoints = player.<Integer>getAttribOr(BOSS_POINTS, 0) + amount;
                    player.putAttrib(BOSS_POINTS, bossPoints);
                    player.getPacketSender().sendString(QuestTab.InfoTab.BOSS_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.BOSS_POINTS.childId).fetchLineData(player));
                    player.getPacketSender().sendString(QuestTab.InfoTab.BOSS_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.BOSS_POINTS.childId).fetchLineData(player));
                    Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a points mystery box and received x"+bossPoints+" boss points.", "boxes_opened");
                } else if (World.getWorld().rollDie(3, 1)) {
                    points = "double drops scroll";
                    player.inventory().addOrBank(new Item(DOUBLE_DROPS_SCROLL, amount));
                    Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a points mystery box and received x"+points+" double drop scrolls.", "boxes_opened");
                } else {
                    points = "pk points";
                    var pkp = player.<Integer>getAttribOr(PK_POINTS, 0) + pkPoints;
                    player.putAttrib(PK_POINTS, pkp);
                    player.getPacketSender().sendString(QuestTab.InfoTab.PK_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.PK_POINTS.childId).fetchLineData(player));
                    Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a points mystery box and received x"+pkPoints+" pk points.", "boxes_opened");
                }

                if (amount > 15) {
                    World.getWorld().sendWorldMessage("<img=1081><col=0052cc>" + player.getUsername() + " just received x" + amount + " " + points + " from a points mystery chest!");
                }

                var voteP = points.contains("vote");
                var amt = voteP ? votePoints : amount;
                player.message("You open the points mystery chest and found...");
                player.message("X "+amt+" "+points+".");
                return true;
            }
        }
        return false;
    }
}
