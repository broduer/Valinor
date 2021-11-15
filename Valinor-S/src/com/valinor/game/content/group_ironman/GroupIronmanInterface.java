package com.valinor.game.content.group_ironman;

import com.valinor.game.content.syntax.impl.InviteToGroup;
import com.valinor.game.content.syntax.impl.SetGroupName;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.IronMode;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.interaction.PacketInteraction;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.util.List;
import java.util.Optional;

/**
 * @author optimum on 14/05/2020
 */
public class GroupIronmanInterface extends PacketInteraction {

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
        System.out.println("huh");
        clear(player);
        List<IronmanGroup> best = IronmanGroupHandler.getBestIronmenGroups(10);
        for (int i = 0; i < best.size(); i++) {
            player.getPacketSender().sendString(i + LEADERBOARD_GROUP_NAME, Utils.optimizeText(best.get(i).getGroupName()));
            player.getPacketSender().sendString(i + LEADERBOARD_AVERAGE_COMBAT, "Level: " + best.get(i).getAverageCombatLevel());
            player.getPacketSender().sendString(i + LEADERBOARD_ONLINE_STATUS, best.get(i).getOnlineStatusText());
            player.getPacketSender().sendString(i + LEADERBOARD_AVERAGE_TOTAL, "Total Level: " + best.get(i).getAverageTotalLevel());
            player.getPacketSender().sendString(i + LEADERBOARD_AVERAGE_XP, "Total Exp:" + Utils.getValueWithoutRepresentationK(best.get(i).getAverageTotalXp()));
        }

        List<IronmanGroup> recent = IronmanGroupHandler.getLatestIronmanGroups(6);
        for (int i = 0; i < recent.size(); i++) {
            player.getPacketSender().sendString(i + RECENT_GROUP_NAME, Utils.optimizeText(recent.get(i).getGroupName()));
            player.getPacketSender().sendString(i + RECENT_GROUP_MEMBERS, recent.get(i).getMembersAsString());
        }
        player.getInterfaceManager().open(INTERFACE_ID);
    }

    @Override
    public boolean handleButtonInteraction(Player player, int button) {
        if (button == 67012) {
            if(player.ironMode() == IronMode.NONE) {
                player.message(Color.RED.wrap("Only ironman can use the invite functionality."));
                return true;
            }
            Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(player);
            if(group.isEmpty()) {
                player.message(Color.RED.wrap("You do not have a ironman group yet."));
                return true;
            }

            player.setEnterSyntax(new InviteToGroup());
            player.getPacketSender().sendEnterInputPrompt("Who would you like to invite to your group?");
            return true;
        }
        if (button == 67013) {
            if(player.ironMode() == IronMode.NONE) {
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
            if(player.ironMode() == IronMode.NONE) {
                player.message(Color.RED.wrap("You're not a ironman."));
                return true;
            }

            if(!IronmanGroupHandler.isGroupLeader(player)) {
                player.message(Color.RED.wrap("You are not the leader of this group."));
                return true;
            }

            if (player.skills().totalLevel() > 500) {
                player.messageBox("You may not delete your group and form a new one, your total level is over 500!");
                return true;
            }
            IronmanGroupHandler.deleteGroup(player);
            player.messageBox("You have successfully deleted your group. <br>Form a new one by accepting or sending an invite.");
            return true;
        }
        if (button == 67018) {
            if(player.ironMode() == IronMode.NONE) {
                player.message(Color.RED.wrap("Only ironman can make a group."));
                return true;
            }
            IronmanGroupHandler.createIronmanGroup(player);
            return true;
        }
        return false;
    }
}
