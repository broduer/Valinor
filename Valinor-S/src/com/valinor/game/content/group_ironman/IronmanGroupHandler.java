package com.valinor.game.content.group_ironman;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valinor.game.content.group_ironman.sorts.IronmanGroupAverageSort;
import com.valinor.game.content.group_ironman.sorts.IronmanGroupDateSort;
import com.valinor.game.world.entity.mob.player.IronMode;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author optimum on 14/05/2020
 */
public final class IronmanGroupHandler {

    private static final Logger log = LoggerFactory.getLogger( IronmanGroupHandler.class );

    /**
     * The full list of ironmen
     */
    private static List<IronmanGroup> ironManGroups = new ArrayList<>();
    private static final Gson GSON = new Gson();
    private static final String SAVE_FILE = "./data/saves/groups/all_groups.json";

    /**
     * Gets a players group
     * @param player - the player we are getting group for
     */
    public static Optional<IronmanGroup> getPlayersGroup(Player player) {
        return ironManGroups.stream().filter(e -> e.playerExists(player)).findFirst();
    }

    public static boolean isGroupLeader(Player player) {
        for (IronmanGroup group : ironManGroups) {
            return player.getUsername().equalsIgnoreCase(group.getLeaderName());
        }
        return false;
    }

    public static boolean isGroupIronman(Player player) {
        return getGroupByName(player.getUsername()).isPresent() || getPlayersGroup(player).isPresent();
    }

    /**
     * Handles the logout of a player
     */
    public static void handleLogout(Player player) {
        Optional<IronmanGroup> getGroup = getPlayersGroup(player);
        getGroup.ifPresent(ironmanGroup -> ironmanGroup.updatePlayer(player));
        saveIronmanGroups();
        clearInvitation(player);
    }

    /**
     * Creates an ironman group
     *
     * @param player - the player creating the group
     * @return - true if created
     */
    public static Optional<IronmanGroup> createIronmanGroup(Player player) {
        //Ultimate ironmans cannot create a group
        if(player.ironMode() == IronMode.ULTIMATE) {
            player.message("You cannot create a group as an ultimate ironman.");
            return Optional.empty();
        }

        IronmanGroup newGroup = IronmanGroup.createGroup(player);
        boolean alreadyExists = getGroupByName(player.getUsername()).isPresent() || getPlayersGroup(player).isPresent();
        if(alreadyExists) {
            player.message("You already have an active ironman group.");
            return Optional.empty();
        }

        ironManGroups.add(newGroup);
        player.message("Your own group has been successfully made.");
        return Optional.of(newGroup);
    }

    /**
     * Gets a group by it's name
     * @param name - the group name
     */
    public static Optional<IronmanGroup> getGroupByName(String name) {
        return ironManGroups.stream().filter(e -> e.getGroupName().equalsIgnoreCase(name)).findFirst();
    }

    /**
     * Checks if a person already has invitation
     * @param player - the player to check for invitation
     * @return - true if the person has an invitation
     */
    public static boolean hasInvitation(Player player) {
        return getInvitation(player).isPresent();
    }

    /**
     * Accepts a ironman's invitation
     * @param player - the player accepted
     */
    public static void acceptInvitation(Player player) {
        Optional<IronmanGroup> invitation = getInvitation(player);
        if(invitation.isPresent()) {
            invitation.get().acceptInvitation(player);
            player.message(Color.PURPLE.wrap("You've accepted the invitation and joined the group!"));
        }
    }

    public static Optional<IronmanGroup> getInvitation(Player player) {
        for (IronmanGroup group : ironManGroups) {
            Optional<String> invitation = group.getInvitation();
            if (invitation.isPresent()) {
                if(player.getUsername().equalsIgnoreCase(group.getInvitation().get())) {
                    return Optional.of(group);
                }
            }
        }
        return Optional.empty();
    }

    public static void clearInvitation(Player player) {
        for (IronmanGroup group : ironManGroups) {
            Optional<String> invitation = group.getInvitation();
            if (invitation.isPresent()) {
                if(player.getUsername().equalsIgnoreCase(group.getInvitation().get())) {
                    group.setInvitation(Optional.empty());
                }
            }
        }
    }

    /**
     * Returns a list of the latest ironman groups created
     */
    public static List<IronmanGroup> getLatestIronmanGroups(int amount) {
        List<IronmanGroup> groups = new ArrayList<>(ironManGroups);
        groups.sort(IronmanGroupDateSort.DATE_SORT.reversed());
        return groups.stream().filter(e -> e.getMembers().size() > 1)
            .limit(amount).collect(Collectors.toList());
    }

    public static List<IronmanGroup> getBestIronmenGroups(int count) {
        List<IronmanGroup> groups = new ArrayList<>(ironManGroups);
        groups.sort(IronmanGroupAverageSort.AVERAGE_SORT);
        return groups.stream().filter(e -> e.getMembers().size() > 1)
            .limit(count).collect(Collectors.toList());
    }

    public static void deleteGroup(IronmanGroup group, Player player) {
        ironManGroups = ironManGroups.parallelStream()
            .filter(e -> !e.equals(group))
            .collect(Collectors.toList());

        player.message("You have deleted your group.");
    }

    public static void deleteGroup(Player player) {
        Optional<IronmanGroup> getGroup = getPlayersGroup(player);
        getGroup.ifPresent(ironmanGroup -> deleteGroup(ironmanGroup, player));
    }

    public static void leaveGroup(Player player) {
        Optional<IronmanGroup> getGroup = getPlayersGroup(player);
        getGroup.ifPresent(ironmanGroup -> ironmanGroup.leaveGroup(player));
        player.message(Color.PURPLE.wrap("You have left the group."));
        saveIronmanGroups();
    }

    public static void kick(Player leader, Player memberToKick) {
        Optional<IronmanGroup> getGroup = getPlayersGroup(memberToKick);
        if(getGroup.isPresent()) {
            getGroup.ifPresent(ironmanGroup -> ironmanGroup.kickFromGroup(memberToKick));
            memberToKick.message(Color.PURPLE.wrap("You have been kicked from the group."));
            saveIronmanGroups();
        } else {
            leader.message(Color.RED.wrap("Unable to find player in group."));
        }
    }

    public static void changeGroupLeader(Player leader, Player newLeader) {
        Optional<IronmanGroup> getGroup = getPlayersGroup(newLeader);
        if(getGroup.isPresent()) {
            getGroup.get().setLeaderName(newLeader.getUsername());
            newLeader.message(Color.PURPLE.wrap("You're the new leader of the group."));
            saveIronmanGroups();
        } else {
            leader.message(Color.RED.wrap("Unable to find player in group."));
        }
    }

    /**
     * Loads all the groups
     */
    public static void loadIronmanGroups() {
        try {
            File loadDirectory = new File(SAVE_FILE);
            List<String> contents = Files.readAllLines(loadDirectory.toPath());
            ironManGroups = GSON.fromJson(contents.get(0), new TypeToken<List<IronmanGroup>>(){}.getType());
            log.info("Loaded " + ironManGroups.size() + "ironmen groups");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Saves all groups to the designated file
     */
    public static void saveIronmanGroups() {
        try {
            File loadDirectory = new File(SAVE_FILE);
            String contents = GSON.toJson(ironManGroups);
            Files.write(loadDirectory.toPath(), contents.getBytes());
            ironManGroups = GSON.fromJson(contents, new TypeToken<List<IronmanGroup>>(){}.getType());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private IronmanGroupHandler() throws IllegalAccessException {
        throw new IllegalAccessException("cannot init this class");
    }
}
