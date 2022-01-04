package com.valinor.game.content.group_ironman;

import com.valinor.game.content.syntax.impl.InviteToGroup;
import com.valinor.game.content.syntax.impl.SetGroupName;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.GameMode;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.util.List;
import java.util.Optional;

/**
 * @author optimum on 14/05/2020
 */
public class GroupIronmanInterface extends Interaction {

    public static final int INTERFACE_ID = 67_000;
    public static final int LEADERBOARD_GROUP_NAME = 67131;
    public static final int LEADERBOARD_AVERAGE_COMBAT = 67133;
    public static final int LEADERBOARD_ONLINE_STATUS = 67132;
    public static final int LEADERBOARD_AVERAGE_TOTAL = 67134;
    public static final int LEADERBOARD_AVERAGE_XP = 67135;
    public static final int RECENT_GROUP_NAME = 67231;
    public static final int RECENT_GROUP_MEMBERS = 67232;

    private static void clear(Player player) {
        //Top 10
        for (int i = 67130; i < 67190; i++) {
            player.getPacketSender().sendString(i, "");
        }

        //Recent
        for (int i = 67230; i < 67248; i++) {
            player.getPacketSender().sendString(i, "");
        }
    }

    /**
     * Opens the interface for a player
     */
    public static void open(Player player) {
        clear(player);
        List<IronmanGroup> best = IronmanGroupHandler.getBestIronmenGroups(10);
        //System.out.println("best size "+best.size());

        int bestCount = 0;
        int start = 67130, finish = (67130 + best.size() * 6);
        for (int i = start; i < finish; i += 6) {
            player.getPacketSender().sendString(i + 1, Utils.optimizeText(best.get(bestCount).getGroupName()));
            player.getPacketSender().sendString(i + 2, "Level: " + best.get(bestCount).getAverageCombatLevel());
            player.getPacketSender().sendString(i + 3, best.get(bestCount).getOnlineStatusText());
            player.getPacketSender().sendString(i + 4, "Total Level: " + best.get(bestCount).getAverageTotalLevel());
            player.getPacketSender().sendString(i + 5, "Total Exp:" + Utils.getValueWithoutRepresentationK(best.get(bestCount).getAverageTotalXp()));
            bestCount++;
        }

        List<IronmanGroup> recent = IronmanGroupHandler.getLatestIronmanGroups(6);
        //System.out.println("recent size "+recent.size());

        int recentCount = 0;
        int recentStart = 67230, recentFinish = (67230 + recent.size() * 3);
        for (int i = recentStart; i < recentFinish; i += 3) {
            player.getPacketSender().sendString(i + 1, Utils.optimizeText(recent.get(recentCount).getGroupName()));
            player.getPacketSender().sendString(i + 2, recent.get(recentCount).getMembersAsString());
            recentCount++;
        }
        player.getInterfaceManager().open(INTERFACE_ID);
    }

    @Override
    public boolean handleButtonInteraction(Player player, int button) {
        if (button == 67012) {
            if(player.gameMode() == GameMode.NONE) {
                player.message(Color.RED.wrap("Only ironman can use the invite functionality."));
                return true;
            }
            Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(player);
            if(group.isEmpty()) {
                player.message(Color.RED.wrap("You do not have a ironman group yet."));
                return true;
            }

            if(!IronmanGroupHandler.isGroupLeader(player)) {
                player.message(Color.RED.wrap("You are not the leader of this group."));
                return true;
            }

            player.setEnterSyntax(new InviteToGroup());
            player.getPacketSender().sendEnterInputPrompt("Who would you like to invite to your group?");
            return true;
        }
        if (button == 67013) {
            if(player.gameMode() == GameMode.NONE) {
                player.message(Color.RED.wrap("Only ironman can use set a group name."));
                return true;
            }

            if(!IronmanGroupHandler.isGroupLeader(player)) {
                player.message(Color.RED.wrap("You are not the leader of this group."));
                return true;
            }

            if (player.<Boolean>getAttribOr(AttributeKey.GROUP_NAME_SET,false)) {
                player.message("You have already set a name for your group.");
                return true;
            }
            player.setEnterSyntax(new SetGroupName());
            player.getPacketSender().sendEnterInputPrompt("Choose a name for your ironman group");
            return true;
        }
        if (button == 67014) {
            if(player.gameMode() == GameMode.NONE) {
                player.message(Color.RED.wrap("You're not a ironman."));
                return true;
            }
            player.getDialogueManager().start(new DisbandDialogue());
            return true;
        }
        if (button == 67018) {
            if(player.gameMode() == GameMode.NONE) {
                player.message(Color.RED.wrap("Only an ironman can make a group."));
                return true;
            }
            //Ultimate ironmans cannot create a group
            if(player.gameMode() == GameMode.ULTIMATE) {
                player.message("You cannot create a group as an ultimate ironman.");
                return true;
            }
            IronmanGroupHandler.createIronmanGroup(player);
            return true;
        }
        return false;
    }
}
