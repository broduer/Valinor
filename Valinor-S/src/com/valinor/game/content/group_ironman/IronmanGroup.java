package com.valinor.game.content.group_ironman;

import com.valinor.game.world.entity.mob.player.IronMode;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.Utils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Represents an ironman group
 *
 * @author optimum on 14/05/2020
 */
public class IronmanGroup {

    private String groupName;
    public String leaderName;
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
            .setGroupName(player.getUsername())
            .setMembers(members);
    }


    public void acceptInvitation(Player player) {
        invitation = Optional.empty();
        if(!members.contains(new IronmanGroupMember(player))) {
            members.add(new IronmanGroupMember(player));
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
     * @param player - the player too update
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
     * A method to try and invite a player to a clan
     * @param inviter - the person inviting
     * @param target - the target to invite
     * @return - true if the target can be invited
     */
    public boolean canInvite(Player inviter, Player target) {
        if(target.skills().totalLevel() > 500) {
            inviter.message("<col=FF0000>You can't invite this player, they need to be below 500 total level.");
            return false;
        }

        if(target.ironMode() == IronMode.NONE) {
            inviter.message("<col=FF0000>This player isn't a ironman.");
            return false;
        }

        if(IronmanGroupHandler.hasInvitation(target)) {
            inviter.message("<col=FF0000>This player already has a pending request.");
            return false;
        }

        if(isGroupMember(target)) {
            inviter.message("<col=FF0000>This player is already on your group.");
            return false;
        }

        if(!leaderName.equalsIgnoreCase(inviter.getUsername())) {
            inviter.message("<col=FF0000>You have to be group leader to invite others.");
            return false;
        }

        if(inviter.ironMode() != target.ironMode()) {
            inviter.message("<col=FF0000>"+target.getUsername()+" cannot join your group as they are not a "+inviter.ironMode().name);
            return false;
        }

        if(isGroupFull()) {
            inviter.message("<col=FF0000>Your group is already full.");
            return false;
        }

        if(IronmanGroupHandler.getPlayersGroup(target).isPresent()) {
            inviter.message("<col=FF0000>This player already has a group.");
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
