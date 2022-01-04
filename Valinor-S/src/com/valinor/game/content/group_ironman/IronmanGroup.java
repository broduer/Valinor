package com.valinor.game.content.group_ironman;

import com.google.gson.annotations.Expose;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.GameMode;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.container.bank.GroupStorage;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.time.Instant;
import java.util.*;

import static com.valinor.game.world.entity.mob.player.rights.PlayerRights.GROUP_HARDCORE_IRONMAN;
import static com.valinor.game.world.entity.mob.player.rights.PlayerRights.GROUP_IRON_MAN;

/**
 * Represents an ironman group
 *
 * @author optimum on 14/05/2020
 */
public class IronmanGroup {

    @Expose
    private String groupName;
    @Expose
    private String leaderName;
    @Expose
    private boolean hardcoreGroup;
    @Expose
    private int hardcoreLives;
    @Expose
    private List<IronmanGroupMember> members = List.of();
    @Expose
    private Date dateStated;
    private Optional<String> invitation = Optional.empty();

    private final GroupStorage groupStorage = new GroupStorage(null);

    @Expose
    public Item[] loadSaveTemp;

    private boolean storageInUse;

    /**
     * Creates a new group from a player
     * @param player - the player creating the group
     */
    public static IronmanGroup createGroup(Player player) {
        List<IronmanGroupMember> members = new ArrayList<>();
        members.add(new IronmanGroupMember(player));
        return new IronmanGroup().setDateStated(Date.from(Instant.now()))
            .setLeaderName(player.getUsername())
            .setHardcoreGroup(player.ironMode() == GameMode.HARDCORE)
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
            player.setPlayerRights(player.ironMode() == GameMode.HARDCORE ? GROUP_HARDCORE_IRONMAN : GROUP_IRON_MAN);
            player.getPacketSender().sendRights();
        }

        Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(leader);
        if(group.isPresent() && player.ironMode() == GameMode.HARDCORE) {
            group.get().setHardcoreLives(group.get().getHardcoreLives() + 1);
        }
    }

    public void leaveGroup(Player leader, Player player) {
        final IronmanGroupMember member = members.stream().filter(e -> e.getUsername().equalsIgnoreCase(player.getUsername())).findFirst().get();
        members.remove(member);

        Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(leader);
        if(group.isPresent() && player.ironMode() == GameMode.HARDCORE) {
            group.get().setHardcoreLives(group.get().getHardcoreLives() - 1);
        }
    }

    public void kickFromGroup(Player leader, Player player) {
        final IronmanGroupMember member = members.stream().filter(e -> e.getUsername().equalsIgnoreCase(player.getUsername())).findFirst().get();
        members.remove(member);

        Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(leader);
        if(group.isPresent() && player.ironMode() == GameMode.HARDCORE) {
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
        return members.stream().filter(Objects::nonNull).anyMatch(
            e -> e.getUsername().equalsIgnoreCase(player.getUsername()));
    }

    public boolean isGroupMember(String name) {
        return members.stream().filter(Objects::nonNull).anyMatch(e -> e.getUsername().equalsIgnoreCase(name));
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
        if(playerToInv.ironMode() == GameMode.NONE) {
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
        return this;
    }

    public int getHardcoreLives() {
        return hardcoreLives;
    }

    public IronmanGroup storageInUse(boolean storageInUse) {
        this.storageInUse = storageInUse;
        return this;
    }

    public boolean storageInUse() {
        return storageInUse;
    }

    public GroupStorage getGroupStorage() {
        return groupStorage;
    }

    public GroupStorage getGroupStorage(Player player) {
        groupStorage.setPlayer(player); // that'll fix contextual calls
        return groupStorage;
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
