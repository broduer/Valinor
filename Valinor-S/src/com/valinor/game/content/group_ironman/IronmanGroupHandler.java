package com.valinor.game.content.group_ironman;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.valinor.game.GameEngine;
import com.valinor.game.content.group_ironman.sorts.IronmanGroupAverageSort;
import com.valinor.game.content.group_ironman.sorts.IronmanGroupDateSort;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.GameMode;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.rights.PlayerRights;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import static com.valinor.game.world.entity.AttributeKey.HARDCORE_GROUP_FALLEN;
import static com.valinor.game.world.entity.mob.player.rights.PlayerRights.GROUP_HARDCORE_IRONMAN;
import static com.valinor.game.world.entity.mob.player.rights.PlayerRights.GROUP_IRON_MAN;

/**
 * @author optimum on 14/05/2020
 */
public class IronmanGroupHandler extends Interaction {

    private static final Logger log = LoggerFactory.getLogger(IronmanGroupHandler.class);

    /**
     * The full list of ironmen
     */
    private static List<IronmanGroup> ironManGroups = new ArrayList<>();
    private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().disableHtmlEscaping().create();
    private static final String SAVE_FILE = "./data/saves/groups/all_groups.json";

    /**
     * Gets a players group
     *
     * @param player - the player we are getting group for
     */
    public static Optional<IronmanGroup> getPlayersGroup(Player player) {
        return ironManGroups.stream().filter(e -> e.playerExists(player)).findFirst();
    }

    public static boolean isGroupLeader(Player player) {
        return ironManGroups.stream().anyMatch(e -> e.getLeaderName().equalsIgnoreCase(player.getUsername()));
    }

    public static boolean isTradingPermitted(Player player, Player other) {
        if (player == null || other == null) {
            return false;
        }

        // and again, we have to get it's optional

        Optional<IronmanGroup> group = player.getIronmanGroup();

        //So now, because this is optional, it could be null

        // If it has a value
        return group.isPresent() && group.get().isGroupMember(other);
    }

    /**
     * Handles the logout of a player
     */
    @Override
    public boolean onLogout(Player player) {
        System.out.println("huh trigger");
        //But this one doesnt
        Optional<IronmanGroup> group = getPlayersGroup(player);
        if(group.isPresent()) {
            group.get().updatePlayer(player);
            saveIronmanGroups();
            clearInvitation(player);
            System.out.println("trigger");
            return true;
        }
        return false;
    }

    /**
     * Creates an ironman group
     *
     * @param player - the player creating the group
     * @return - true if created
     */
    public static Optional<IronmanGroup> createIronmanGroup(Player player) {
        IronmanGroup newTeam = IronmanGroup.createGroup(player);

        if (getGroupByName(player.getUsername()).isPresent()) {
            player.message("You already have an active ironman group.");
            return Optional.empty();
        }

        if (getPlayersGroup(player).isPresent()) {
            player.message("You already have an active ironman group.");
            return Optional.empty();
        }

        ironManGroups.add(newTeam);
        if (!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
            player.setPlayerRights(player.ironMode() == GameMode.HARDCORE ? GROUP_HARDCORE_IRONMAN : GROUP_IRON_MAN);
            player.getPacketSender().sendRights();
        }
        player.message("Your own group has been successfully created.");
        player.message("Your group will become visible once you start inviting members.");
        GroupIronmanInterface.open(player);
        return Optional.of(newTeam);
    }

    /**
     * Gets a group by it's name
     *
     * @param name - the group name
     */
    public static Optional<IronmanGroup> getGroupByName(String name) {
        return ironManGroups.stream().filter(e -> e.getGroupName().equalsIgnoreCase(name)).findFirst();
    }

    /**
     * Checks if a person already has invitation
     *
     * @param player - the player to check for invitation
     * @return - true if the person has an invitation
     */
    public static boolean hasInvitation(Player player) {
        return getInvitation(player).isPresent();
    }

    /**
     * Accepts an ironman's invitation
     *
     * @param player - the player accepted
     */
    public static void acceptInvitation(Player leader, Player player) {
        Optional<IronmanGroup> invitation = getInvitation(player);
        if (invitation.isPresent()) {
            invitation.get().acceptInvitation(leader, player);
            player.message(Color.PURPLE.wrap("You've accepted the invitation and joined the group!"));
        }
    }

    public static Optional<IronmanGroup> getInvitation(Player player) {
        for (IronmanGroup group : ironManGroups) {
            Optional<String> invitation = group.getInvitation();
            if (invitation.isPresent()) {
                if (player.getUsername().equalsIgnoreCase(group.getInvitation().get())) {
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
                if (player.getUsername().equalsIgnoreCase(group.getInvitation().get())) {
                    group.setInvitation(Optional.empty());
                }
            }
        }
    }

    /**
     * Returns a list of the latest ironman groups created
     */
    public static List<IronmanGroup> getLatestIronmanGroups(int amount) {
        ironManGroups.sort(IronmanGroupDateSort.DATE_SORT.reversed());
        return ironManGroups.stream().filter(e -> e.getMembers().size() > 1)
            .limit(amount).collect(Collectors.toList());
    }

    public static List<IronmanGroup> getBestIronmenGroups(int count) {
        ironManGroups.sort(IronmanGroupAverageSort.AVERAGE_SORT);
        return ironManGroups.stream().filter(e -> e.getMembers().size() > 1)
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
        getGroup.ifPresent(ironmanGroup -> {
            demoteAll(player);
            deleteGroup(ironmanGroup, player);
        });
    }

    public static void demoteAll(Player player) {
        Optional<IronmanGroup> group = getPlayersGroup(player);
        if (group.isPresent()) {
            for (Player member : group.get().getOnlineMembers()) {
                if(!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
                    member.setPlayerRights(group.get().isHardcoreGroup() ? PlayerRights.HARDCORE_IRON_MAN : PlayerRights.IRON_MAN);
                    member.getPacketSender().sendRights();
                }
            }
        }
    }

    @Override
    public void onLogin(Player player) {
        Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(player);
        if(group.isPresent() && group.get().isHardcoreGroup() && player.ironMode() == GameMode.HARDCORE && !player.<Boolean>getAttribOr(HARDCORE_GROUP_FALLEN,false)) {
            var lives = group.get().getHardcoreLives();
            if(lives == 0) {
                player.ironMode(GameMode.REGULAR);
                if(!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
                    player.setPlayerRights(PlayerRights.IRON_MAN);
                    player.getPacketSender().sendRights();
                }
                player.message(Color.PURPLE.wrap("Your group has lost their last life, you have been demoted to ironman."));
                player.putAttrib(HARDCORE_GROUP_FALLEN,true);
            }
        }

        if(group.isEmpty() && (player.getPlayerRights() == GROUP_IRON_MAN || player.getPlayerRights() == GROUP_HARDCORE_IRONMAN)) {
            if(!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
                player.setPlayerRights(player.getPlayerRights() == GROUP_IRON_MAN ? PlayerRights.IRON_MAN : PlayerRights.HARDCORE_IRON_MAN);
                player.getPacketSender().sendRights();
            }
            String plural = player.getPlayerRights() == PlayerRights.IRON_MAN ? "regular" : "hardcore";
            player.message(Color.BLUE.wrap("The ironman group has been lifted, you have been demoted to a "+plural+" ironman."));
        }
    }

    public static void leaveGroup(Player player) {
        Optional<IronmanGroup> group = getPlayersGroup(player);
        Optional<Player> leader = Optional.empty();
        if (group.isPresent()) {
            leader = World.getWorld().getPlayerByName(group.get().getLeaderName());
        }

        Optional<Player> finalLeader = leader;
        if (finalLeader.isPresent()) {
            group.ifPresent(ironmanGroup -> ironmanGroup.leaveGroup(finalLeader.get(), player));
            if(!player.getPlayerRights().isStaffMemberOrYoutuber(player)) {
                player.setPlayerRights(group.get().isHardcoreGroup() ? PlayerRights.HARDCORE_IRON_MAN : PlayerRights.IRON_MAN);
                player.getPacketSender().sendRights();
            }
            player.message(Color.PURPLE.wrap("You have left the group."));
        }
    }

    public static void kick(Player leader, Player memberToKick) {
        Optional<IronmanGroup> group = getPlayersGroup(memberToKick);
        if (group.isPresent()) {
            group.ifPresent(ironmanGroup -> ironmanGroup.kickFromGroup(leader, memberToKick));
            memberToKick.message(Color.PURPLE.wrap("You have been kicked from the group."));
            if(!memberToKick.getPlayerRights().isStaffMemberOrYoutuber(memberToKick)) {
                memberToKick.setPlayerRights(group.get().isHardcoreGroup() ? PlayerRights.HARDCORE_IRON_MAN : PlayerRights.IRON_MAN);
                memberToKick.getPacketSender().sendRights();
            }
        } else {
            leader.message(Color.RED.wrap("Unable to find player in group."));
        }
    }

    public static void changeGroupLeader(Player leader, Player newLeader) {
        Optional<IronmanGroup> getGroup = getPlayersGroup(newLeader);
        if (getGroup.isPresent()) {
            getGroup.get().setLeaderName(newLeader.getUsername());
            newLeader.message(Color.PURPLE.wrap("You're the new leader of the group."));
        } else {
            leader.message(Color.RED.wrap("Unable to find player in group."));
        }
    }

    /**
     * Loads all the groups
     */
    public static void loadIronmanGroups() {
        GameEngine.getInstance().submitLowPriority(() -> {
            try {
                File loadDirectory = new File(SAVE_FILE);
                if (!loadDirectory.exists())
                    return;
                ironManGroups = GSON.fromJson(new FileReader(loadDirectory), new TypeToken<List<IronmanGroup>>() {
                }.getType());
                ironManGroups.forEach(g -> {
                    g.getGroupStorage().set(new Item[80]);
                    //final Item[] items = Arrays.copyOf(g.loadSaveTemp, g.loadSaveTemp.length);
                    for (int i = 0; i < Math.min(g.loadSaveTemp.length, g.getGroupStorage().getItems().length); i++) {
                        g.getGroupStorage().getItems()[i] = g.loadSaveTemp[i];
                    }
                });
                log.info("Loaded " + ironManGroups.size() + "ironmen groups");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Saves all groups to the designated file
     */
    public static void saveIronmanGroups() {
        ironManGroups.forEach(g -> {
            g.loadSaveTemp = g.getGroupStorage().toNonNullArray();
        });
        GameEngine.getInstance().submitLowPriority(() -> {
            try {
                File loadDirectory = new File(SAVE_FILE);
                String contents = GSON.toJson(ironManGroups);
                Files.write(loadDirectory.toPath(), contents.getBytes());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private IronmanGroupHandler() throws IllegalAccessException {
        throw new IllegalAccessException("cannot init this class");
    }
}
