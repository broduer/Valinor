package com.valinor.game.content.group_ironman;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.IronMode;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.rights.PlayerRights;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.valinor.game.content.group_ironman.IronmanGroupHandler.saveIronmanGroups;
import static com.valinor.game.world.entity.AttributeKey.HARDCORE_GROUP_FALLEN;
import static com.valinor.game.world.entity.mob.player.rights.PlayerRights.GROUP_HARDCORE_IRONMAN;
import static com.valinor.game.world.entity.mob.player.rights.PlayerRights.GROUP_IRON_MAN;

/**
 * Represents an ironman group
 *
 * @author optimum on 14/05/2020
 */
public class IronmanGroup {

    private String groupName;
    private String leaderName;
    private boolean hardcoreGroup;
    private int hardcoreLives;
    private List<IronmanGroupMember> members;
    private Date dateStated;
    private Optional<String> invitation = Optional.empty();

    /**
     * Creates a new group from a player
     * @param player - the player creating the group
     */
    public static IronmanGroup createGroup(Player player) {
        List<IronmanGroupMember> members = new ArrayList<>();
        members.add(new IronmanGroupMember(player));
        return new IronmanGroup().setDateStated(Date.from(Instant.now()))
            .setLeaderName(player.getUsername())
            .setHardcoreGroup(player.ironMode() == IronMode.HARDCORE)
            .setHardcoreLives(1)
            .setGroupName(player.getUsername())
            .setMembers(members);
    }

    public void acceptInvitation(Player leader, Player player) {
        invitation = Optional.empty();
        IronmanGroupMember member = new IronmanGroupMember(player);
        if(!members.contains(member)) {
            members.add(member);
        }

        if(!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
            player.setPlayerRights(player.ironMode() == IronMode.HARDCORE ? GROUP_HARDCORE_IRONMAN : GROUP_IRON_MAN);
            player.getPacketSender().sendRights();
        }

        Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(leader);
        if(group.isPresent() && player.ironMode() == IronMode.HARDCORE) {
            group.get().setHardcoreLives(group.get().getHardcoreLives() + 1);
        }
    }

    public void checkForDemote(Player player) {
        Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(player);
        if(group.isPresent() && group.get().isHardcoreGroup() && player.ironMode() == IronMode.HARDCORE && !player.<Boolean>getAttribOr(HARDCORE_GROUP_FALLEN,false)) {
            var lives = group.get().getHardcoreLives();
            if(lives == 0) {
                player.ironMode(IronMode.REGULAR);
                player.setPlayerRights(PlayerRights.IRON_MAN);
                player.getPacketSender().sendRights();
                player.message(Color.PURPLE.wrap("Your group has lost their last life, you have been demoted to ironman."));
                player.putAttrib(HARDCORE_GROUP_FALLEN,true);
            }
        }
    }

    public void leaveGroup(Player leader, Player player) {
        final IronmanGroupMember member = members.stream().filter(e -> e.getUsername().equalsIgnoreCase(player.getUsername())).findFirst().get();
        members.remove(member);

        Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(leader);
        if(group.isPresent() && player.ironMode() == IronMode.HARDCORE) {
            group.get().setHardcoreLives(group.get().getHardcoreLives() - 1);
        }
    }

    public void kickFromGroup(Player leader, Player player) {
        final IronmanGroupMember member = members.stream().filter(e -> e.getUsername().equalsIgnoreCase(player.getUsername())).findFirst().get();
        members.remove(member);

        Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(leader);
        if(group.isPresent() && player.ironMode() == IronMode.HARDCORE) {
            group.get().setHardcoreLives(group.get().getHardcoreLives() - 1);
        }
    }

    public boolean isOnline() {
        return !getOnlineMembers().isEmpty();
    }

    public String getOnlineStatusText() {
        return isOnline() ? "<col=65280>Online" : "<col=FF0000>Offline";
    }

    public void setGroupName(Player player, String groupName) {
        if(player.getUsername().equalsIgnoreCase(leaderName)) {
            this.groupName = groupName;
        }
    }

    /**
     * Updates the player
     *
     * @param player - the player to update
     */
    public void updatePlayer(Player player) {
        Optional<IronmanGroupMember> member = getMember(player.getUsername());
        if (member.isEmpty()) {
            return;
        }
        member.get().update(player);
    }


    public Optional<IronmanGroupMember> getMember(String username) {
        return members.stream().filter(e -> e.getUsername().equalsIgnoreCase(username)).findFirst();
    }

    /**
     * Checks a player exists within the groups
     * @param player - the player we are checking
     * @return true if the player exists
     */
    public boolean playerExists(Player player) {
        return members.stream().anyMatch(
            e -> e.getUsername().equalsIgnoreCase(player.getUsername()));
    }

    public boolean isGroupMember(String name) {
        return members.stream().anyMatch(e -> e.getUsername().equalsIgnoreCase(name));
    }

    public boolean isGroupMember(Player player) {
        return isGroupMember(player.getUsername());
    }

    /**
     * Gets a list of online players
     *
     * @return - a list of online players
     */
    public List<Player> getOnlineMembers() {
        List<Player> players = new ArrayList<>();
        for (IronmanGroupMember member : members) {
            Optional<Player> online = member.getPlayer();
            online.ifPresent(players::add);
        }
        return players;
    }

    /**
     * Group leaders have the option to create a group, set a group name, and invite players. Up to 4 additional players can be invited in the group.
     * The group member that creates the group by default will be the group leader. Leaders can only invite fresh accounts to the group.
     * This is to prevent unfair advantages to groups by inviting experienced players to the group.
     * Groups will be automatically disbanded if there are no more members in the group and the leader leaves.
     *
     * @param leader - the group leader
     * @param playerToInv - the player to invite
     * @return - true if the target can be invited
     */
    public boolean canInvite(Player leader, Player playerToInv) {
        //Check if the player is the leader of the group
        if(!leaderName.equalsIgnoreCase(leader.getUsername())) {
            leader.message(Color.RED.wrap("You have to be group leader to invite others."));
            return false;
        }

        //Check if our group is already full
        if(isGroupFull()) {
            leader.message(Color.RED.wrap("Your group is already full."));
            return false;
        }

        //Check if the player isn't already part of another ironman group
        if(IronmanGroupHandler.getPlayersGroup(playerToInv).isPresent()) {
            leader.message(Color.RED.wrap("This player already has a group."));
            return false;
        }

        //Check if the player being invited is an ironman
        if(playerToInv.ironMode() == IronMode.NONE) {
            leader.message(Color.RED.wrap("This player isn't a ironman."));
            return false;
        }

        //Check if the player already has a pending invitation
        if(IronmanGroupHandler.hasInvitation(playerToInv)) {
            leader.message(Color.RED.wrap("This player already has a pending request."));
            return false;
        }

        //Check if the player is already part of your group
        if(isGroupMember(playerToInv)) {
            leader.message(Color.RED.wrap("This player is already in your group."));
            return false;
        }

        //You can only invite freshly made accounts, total level of 32 and max 30 minutes of game time.
        var isNewPlayer = playerToInv.skills().totalLevel() <= 32 && playerToInv.<Integer>getAttribOr(AttributeKey.GAME_TIME,0) < 3000;
        if(!isNewPlayer) {
            leader.message(Color.RED.wrap("You can't invite this player."));
            return false;
        }

        //You can only invite players with the same ironman mode
        if(leader.ironMode() != playerToInv.ironMode()) {
            leader.message(Color.RED.wrap(playerToInv.getUsername()+" cannot join your group as they are not a "+leader.ironMode().name));
            return false;
        }
        return true;
    }

    /**
     * Sends an invitation to the person
     *
     * @param target - the target player
     */
    public void sendInvitation(Player inviter, Player target) {
        invitation = Optional.of(target.getUsername());
        target.message(inviter.getUsername() + ":groupinvite:");
        inviter.message("Request sent to " + target.getUsername());
        inviter.getInterfaceManager().close();
    }

    public boolean isGroupFull() {
        return members.size() >= 4;
    }

    public int getAverageCombatLevel() {
        int count = members.size();
        int total = members.stream().mapToInt(IronmanGroupMember::getCombatLevel).sum();
        return (int)Math.abs((float) total / ((float) count));
    }

    public long getAverageTotalXp() {
        int count = members.size();
        long total = members.stream().mapToLong(IronmanGroupMember::getTotalXp).sum();
        return (long)Math.abs((float) total / ((float) count));
    }

    public int getAverageTotalLevel() {
        int count = members.size();
        int total = members.stream().mapToInt(IronmanGroupMember::getTotalLevel).sum();
        return (int)Math.abs((float) total / ((float) count));
    }

    public String getGroupName() {
        return groupName;
    }

    public IronmanGroup setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public IronmanGroup setLeaderName(String leaderName) {
        this.leaderName = leaderName;
        return this;
    }

    public IronmanGroup setHardcoreGroup(boolean hardcoreGroup) {
        this.hardcoreGroup = hardcoreGroup;
        return this;
    }

    public boolean isHardcoreGroup() {
        return hardcoreGroup;
    }

    public IronmanGroup setHardcoreLives(int hardcoreLives) {
        this.hardcoreLives = hardcoreLives;
        saveIronmanGroups();//When changing lives, update json.
        return this;
    }

    public int getHardcoreLives() {
        return hardcoreLives;
    }

    public List<IronmanGroupMember> getMembers() {
        return members;
    }

    public IronmanGroup setMembers(List<IronmanGroupMember> members) {
        this.members = members;
        return this;
    }

    public Date getDateStated() {
        return dateStated;
    }

    public IronmanGroup setDateStated(Date dateStated) {
        this.dateStated = dateStated;
        return this;
    }


    public Optional<String> getInvitation() {
        return invitation;
    }

    public IronmanGroup setInvitation(Optional<String> invitation) {
        this.invitation = invitation;
        return this;
    }

    public String getMembersAsString() {
        StringBuilder builder = new StringBuilder();
        for (IronmanGroupMember member : members) {
            builder.append(member.getUsername()).append(", ");
        }
        return Utils.optimizeText(builder.substring(0, builder.length() - 2));
    }

}
