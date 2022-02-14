package com.valinor.game.content.items.mystery;

import com.valinor.game.GameConstants;
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
                if (World.getWorld().rollDie(50, 1)) {
                    var min = Points.VALINOR_COINS.mysteryChestMinAmount;
                    var max = Points.VALINOR_COINS.mysteryChestMaxAmount;
                    var amt = World.getWorld().random(min, max);
                    World.getWorld().sendWorldMessage("<img=452><shad=0><col=0052cc>" + player.getUsername() + " just received x" + amt + " "+GameConstants.SERVER_NAME+" coins from a points mystery chest!");
                    player.message("You open the points mystery chest and found...");
                    player.message("x"+amt+" "+GameConstants.SERVER_NAME+" coins.");
                    player.inventory().addOrBank(new Item(VALINOR_COINS, amt));
                    World.getWorld().sendWorldMessage("<img=452><shad=0><col=0052cc>" + player.getUsername() + " just received x" + amt + " "+GameConstants.SERVER_NAME+" coins from a points mystery chest!");
                    Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a points mystery chest and received x"+amt+" "+GameConstants.SERVER_NAME+" coins.", "boxes_opened");
                } else if (World.getWorld().rollDie(35, 1)) {
                    var min = Points.VOTE_POINTS.mysteryChestMinAmount;
                    var max = Points.VOTE_POINTS.mysteryChestMaxAmount;
                    var amt = World.getWorld().random(min, max);
                    World.getWorld().sendWorldMessage("<img=452><shad=0><col=0052cc>" + player.getUsername() + " just received x" + amt + " vote points from a points mystery chest!");
                    player.message("You open the points mystery chest and found...");
                    player.message("x"+amt+" vote points.");
                    var votePts = player.<Integer>getAttribOr(VOTE_POINTS, 0) + amt;
                    player.putAttrib(VOTE_POINTS, votePts);
                    player.getPacketSender().sendString(QuestTab.InfoTab.VOTE_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.VOTE_POINTS.childId).fetchLineData(player));
                    Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a points mystery chest and received x"+votePts+" vote points.", "boxes_opened");
                } else if (World.getWorld().rollDie(25, 1)) {
                    var min = Points.MARKS_OF_GRACE.mysteryChestMinAmount;
                    var max = Points.MARKS_OF_GRACE.mysteryChestMaxAmount;
                    var amt = World.getWorld().random(min, max);
                    World.getWorld().sendWorldMessage("<img=452><shad=0><col=0052cc>" + player.getUsername() + " just received x" + amt + " marks of grace from a points mystery chest!");
                    player.message("You open the points mystery chest and found...");
                    player.message("x"+amt+" marks of grace.");
                    player.inventory().addOrBank(new Item(MARK_OF_GRACE, amt));
                    Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a points mystery chest and received x"+amt+" marks of grace.", "boxes_opened");
                } else if (World.getWorld().rollDie(6, 1)) {
                    var min = Points.SLAYER_REWARD_POINTS.mysteryChestMinAmount;
                    var max = Points.SLAYER_REWARD_POINTS.mysteryChestMaxAmount;
                    var amt = World.getWorld().random(min, max);
                    if(amt >= 10) {
                        World.getWorld().sendWorldMessage("<img=452><shad=0><col=0052cc>" + player.getUsername() + " just received x" + amt + " slayer points from a points mystery chest!");
                    }
                    player.message("You open the points mystery chest and found...");
                    player.message("x"+amt+" slayer points.");
                    var slayerRewardPoints = player.<Integer>getAttribOr(SLAYER_REWARD_POINTS, 0) + amt;
                    player.putAttrib(SLAYER_REWARD_POINTS, slayerRewardPoints);
                    player.getPacketSender().sendString(QuestTab.InfoTab.SLAYER_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.SLAYER_POINTS.childId).fetchLineData(player));
                    Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a points mystery chest and received x"+slayerRewardPoints+" slayer points.", "boxes_opened");
                } else if (World.getWorld().rollDie(5, 1)) {
                    var min = Points.BOSS_POINTS.mysteryChestMinAmount;
                    var max = Points.BOSS_POINTS.mysteryChestMaxAmount;
                    var amt = World.getWorld().random(min, max);
                    if(amt >= 25) {
                        World.getWorld().sendWorldMessage("<img=452><shad=0><col=0052cc>" + player.getUsername() + " just received x" + amt + " boss points from a points mystery chest!");
                    }
                    player.message("You open the points mystery chest and found...");
                    player.message("x"+amt+" boss points.");
                    var bossPoints = player.<Integer>getAttribOr(BOSS_POINTS, 0) + amt;
                    player.putAttrib(BOSS_POINTS, bossPoints);
                    player.getPacketSender().sendString(QuestTab.InfoTab.BOSS_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.BOSS_POINTS.childId).fetchLineData(player));
                    Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a points mystery chest and received x"+bossPoints+" boss points.", "boxes_opened");
                } else if (World.getWorld().rollDie(3, 1)) {
                    var min = Points.DOUBLE_DROP_SCROLLS.mysteryChestMinAmount;
                    var max = Points.DOUBLE_DROP_SCROLLS.mysteryChestMaxAmount;
                    var amt = World.getWorld().random(min, max);
                    player.inventory().addOrBank(new Item(DOUBLE_DROPS_SCROLL, amt));
                    Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a points mystery chest and received x"+amt+" double drop scrolls.", "boxes_opened");
                } else {
                    var min = Points.PK_POINTS.mysteryChestMinAmount;
                    var max = Points.PK_POINTS.mysteryChestMaxAmount;
                    var amt = World.getWorld().random(min, max);
                    if(amt >= 450) {
                        World.getWorld().sendWorldMessage("<img=452><shad=0><col=0052cc>" + player.getUsername() + " just received x" + amt + " pk points from a points mystery chest!");
                    }
                    player.message("You open the points mystery chest and found...");
                    player.message("x"+amt+" pk points.");
                    var pkp = player.<Integer>getAttribOr(PK_POINTS, 0) + amt;
                    player.putAttrib(PK_POINTS, pkp);
                    player.getPacketSender().sendString(QuestTab.InfoTab.PK_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.PK_POINTS.childId).fetchLineData(player));
                    Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a points mystery chest and received x"+amt+" pk points.", "boxes_opened");
                }
                return true;
            }
        }
        return false;
    }
}
