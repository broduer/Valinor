package com.valinor.game.world.entity.combat.bountyhunter;

import com.valinor.GameServer;
import com.valinor.game.content.daily_tasks.DailyTaskManager;
import com.valinor.game.content.daily_tasks.DailyTasks;
import com.valinor.game.content.mechanics.Death;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.bountyhunter.emblem.BountyHunterEmblem;
import com.valinor.game.world.entity.mob.player.ExpMode;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.Color;
import com.valinor.util.ItemIdentifiers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.valinor.game.world.entity.AttributeKey.EMBLEM_WEALTH;
import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.DEATHS;
import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.KILLS;
import static com.valinor.util.ItemIdentifiers.ANTIQUE_EMBLEM_TIER_1;
import static com.valinor.util.ItemIdentifiers.BLOOD_MONEY;
import static com.valinor.util.Utils.formatNumber;

/**
 * The bounty hunter minigame as of 4 November 2019.
 * 
 * @author Professor Oak
 * @author Zerikoth
 */
public class BountyHunter {

    /**
     * All players currently in the wilderness.
     */
    public static final List<Player> PLAYERS_IN_WILD = new ArrayList<>();

    /**
     * Target pairs.
     */
    private static final List<TargetPair> TARGET_PAIRS = new ArrayList<>();

    /**
     * Processes the bounty hunter system for the specified
     * player.
     */
    public static void sequence(Player player) {

        //Get our target..
        Optional<Player> target = getTargetfor(player);

        //Is player in the wilderness?
        if (WildernessArea.inWilderness(player.tile())) {
            //Check if the player has a target.
            //If not, search for a new one.
            if (target.isEmpty()) {
                //Only search for a target every {@code TARGET_DELAY_SECONDS}.
                if (!player.getTargetSearchTimer().active()) {
                    //Make sure we're a valid target..
                    if (!validTargetContester(player)) {
                        return;
                    }

                    //Search for a new target for the player..
                    for (final Player potential : PLAYERS_IN_WILD) {
                        //Check if player2 is a valid target..
                        if (validTargetContester(potential)) {
                            //Check other stuff...

                           if(potential.getHostAddress().equalsIgnoreCase(player.getHostAddress())) {
                                continue;
                            }

                            if(potential.looks().hidden()) {
                                continue;
                            }

                            //Can't get this user as target
                            if(potential.getUsername().equalsIgnoreCase("TRY CATCH ME")) {
                                continue;
                            }

                            //Check if we aren't looping ourselves..
                            if (player.equals(potential)) {
                                continue;
                            }

                            //Check that we haven't killed this player before..
                            if (player.getRecentKills().contains(potential.getHostAddress()) && GameServer.properties().production) {
                                continue;
                            }

                            //Skip clan mates
                            if(player.getClanChat().equalsIgnoreCase(potential.getClanChat())) {
                                continue;
                            }

                            if(Math.abs(player.skills().combatLevel() - potential.skills().combatLevel()) > 5) {
                                continue;
                            }

                            assign(player, potential);
                            break;
                        }
                    }
                    player.getTargetSearchTimer().start(TARGET_SEARCH_DELAY_SECONDS);
                }
            }
        } else {
            //Player leaves wilderness remove target, otherwise it takes to long to assign new target.
            if (target.isPresent()) {
                unassign(player);
                target.get().message("You have lost your target and will be given a new one shortly.");
            }
        }
    }

    /**
     * Assign a new {@link TargetPair} of the two specified players.
     */
    public static void assign(Player player, Player target) {
        if (getPairFor(player).isEmpty() && getPairFor(target).isEmpty()) {

            //Create a new pair..
            final TargetPair pair = new TargetPair(player, target);

            //Add the pair to our list..
            TARGET_PAIRS.add(pair);

            //Send messages..
            player.getPacketSender().sendMessage("You've been assigned "+target.getUsername()+" as your target!");
            target.getPacketSender().sendMessage("You've been assigned "+player.getUsername()+" as your target!");

            //Send hints..
            player.getPacketSender().sendEntityHint(target);
            target.getPacketSender().sendEntityHint(player);
        }
    }

    /**
     * Unassign an existing {@link TargetPair}.
     */
    public static void unassign(Player player) {
        final Optional<TargetPair> pair = getPairFor(player);
        if (pair.isPresent()) {

            TARGET_PAIRS.remove(pair.get());

            final Player p1 = pair.get().getPlayer1();
            final Player p2 = pair.get().getPlayer2();

            //Reset hints..
            p1.getPacketSender().sendEntityHintRemoval(true);
            p2.getPacketSender().sendEntityHintRemoval(true);

            //Set timers
            p2.getTargetSearchTimer().start(TARGET_SEARCH_DELAY_SECONDS);
            p1.getTargetSearchTimer().start(TARGET_SEARCH_DELAY_SECONDS);
        }
    }

    /**
     * Gets the {@link Player} target for the specified player.
     */
    public static Optional<Player> getTargetfor(Player player) {
        Optional<TargetPair> pair = getPairFor(player);
        if (pair.isPresent()) {

            //Check if player 1 in the pair is us.
            //If so, return the other player.
            if (pair.get().getPlayer1().equals(player)) {
                return Optional.of(pair.get().getPlayer2());
            }

            //Check if player 2 in the pair is us.
            //If so, return the other player.
            if (pair.get().getPlayer2().equals(player)) {
                return Optional.of(pair.get().getPlayer1());
            }
        }
        return Optional.empty();
    }

    /**
     * Gets the {@link TargetPair} for the specfied player.
     */
    public static Optional<TargetPair> getPairFor(final Player p) {
        for (TargetPair pair : TARGET_PAIRS) {
            if (p.equals(pair.getPlayer1()) ||
                    p.equals(pair.getPlayer2())) {
                return Optional.of(pair);
            }
        }
        return Optional.empty();
    }

    /**
     * Handles death for a player.
     * Rewards the killer.
     */
    public static void onDeath(Player killer, Player killed) {
        //Remove first index if we've killed 1
        if (killer.getRecentKills().size() >= 2) {
            killer.getRecentKills().remove(0);
        }

        //Should the player be rewarded for this kill?
        boolean rewardPlayer = true;

        if(killer.getRecentKills().contains(killed.getHostAddress())) {
            rewardPlayer = false;
            //System.out.println("Let's not reward the player, already killed before.");
        } else if (killer.getHostAddress().equals(killed.getHostAddress())) {
            rewardPlayer = false;
            //System.out.println("Let's not reward the player, same IP.");
        } else if (!WildernessArea.inWilderness(killer.tile())) {
            rewardPlayer = false;
            //System.out.println("Let's not reward the player, not in wild.");
        } else {
            //System.out.println("Let's reward the player");
            //Clear out the old kills so the player can kill other players, since it is recent kills, not lifetime kills.
            killer.getRecentKills().clear();
            killer.getRecentKills().add(killed.getHostAddress());
        }

        Optional<Player> target = getTargetfor(killer);

        if(killer.getPlayerRights().isDeveloperOrGreater(killer)) {
            rewardPlayer = true;
        }

        if (rewardPlayer) {

            //Other rewards
            if(WildernessArea.inWilderness(killer.tile()) && target.isPresent()) { // Only reward if in wild
                // Add a death. Only when dying to a player.
                int dc = (Integer) target.get().getAttribOr(AttributeKey.PLAYER_DEATHS, 0) + 1;
                target.get().putAttrib(AttributeKey.PLAYER_DEATHS, dc);

                //Increase the player killcount
                int killcount = (Integer) killer.getAttribOr(AttributeKey.PLAYER_KILLS, 0) + 1;
                killer.putAttrib(AttributeKey.PLAYER_KILLS, killcount);

                //Update the kills and deaths
                killer.getPacketSender().sendString(KILLS.childId, QuestTab.InfoTab.INFO_TAB.get(KILLS.childId).fetchLineData(killer));
                target.get().getPacketSender().sendString(DEATHS.childId, QuestTab.InfoTab.INFO_TAB.get(DEATHS.childId).fetchLineData(target.get()));
            }
        } else {
            killer.message("You don't get any rewards for that kill.");
            //Player is probably farming kills.
        }

        //Check if the player killed was our target..
        if (target.isPresent() && target.get().equals(killed)) {
            if (!rewardPlayer) {
                killed.message("Being killed by targets on the same address does not count.");
                killer.message("Killing targets on the same address does not count towards rewards.");
            }

            //Reset targets
            unassign(killer);

            //If player isn't farming kills..
            if (rewardPlayer) {
                //Send messages
                killed.getPacketSender().sendMessage("You were defeated by your target!");

                int targetKills = (Integer) killer.getAttribOr(AttributeKey.TARGET_KILLS, 0) + 1;
                killer.putAttrib(AttributeKey.TARGET_KILLS, targetKills);

                Optional<BountyHunterEmblem> emblem = BountyHunterEmblem.getBest(killer, true);

                if (emblem.isPresent()) {
                    killer.inventory().remove(new Item(emblem.get().getItemId()));
                    killer.inventory().add(new Item(emblem.get().getNextOrLast().getItemId()));

                    boolean tierTen = emblem.get().getNextOrLast().getItemId() == ItemIdentifiers.ANTIQUE_EMBLEM_TIER_10;
                    if(tierTen) {
                        DailyTaskManager.increase(DailyTasks.TIER_UPGRADE, killer);
                    }
                } else {
                    killer.inventory().addOrBank(new Item(ANTIQUE_EMBLEM_TIER_1));
                }
            }
        }

        killer.message(String.format(Death.randomKillMessage(), killed.getUsername()));
    }

    /**
     * Gets the amount of value for a player's emblems.
     */
    public static int exchange(Player player, boolean performSale) {
        ArrayList<BountyHunterEmblem> list = new ArrayList<>();
        for(BountyHunterEmblem emblem : BountyHunterEmblem.values()) {
            if(player.inventory().contains(emblem.getItemId())) {
                list.add(emblem);
            }
        }

        if(list.isEmpty()) {
            return 0;
        }

        int value = 0;
        int targetPoints = 0;

        for(BountyHunterEmblem emblem : list) {
            int amount = player.inventory().count(emblem.getItemId());
            if(amount > 0) {
                targetPoints += (emblem.getTargetPoints() * amount);
                value += (emblem.getBm() * amount);
                player.putAttrib(EMBLEM_WEALTH,formatNumber(value)+" BM and "+formatNumber(targetPoints)+" target points");

                if(performSale) {
                    if(!player.inventory().contains(emblem.getItemId())) {
                        return 0;
                    }
                    player.inventory().remove(emblem.getItemId(), amount);
                    player.inventory().add(new Item(BLOOD_MONEY, value));
                    player.clearAttrib(EMBLEM_WEALTH);
                }
            }
        }
        return value;
    }

    /***
     * Checks if the specified player is in a state of being able
     * to receive/be set a target.
     */
    private static boolean validTargetContester(Player p) {
        //A minimum of 30 combat is now required to receive targets in the minigame.
        if (p.skills().combatLevel() < 30) {
            return false;
        }

        return !(!p.isRegistered() || !(WildernessArea.inWilderness(p.tile())) || WildernessArea.wildernessLevel(p.tile()) <= 0 || p.isNullifyDamageLock() || p.dead() || p.isNeedsPlacement() || getPairFor(p).isPresent());
    }

    /**
     * The delay between each search for a new target.
     */
    private static final int TARGET_SEARCH_DELAY_SECONDS = GameServer.properties().production ? 30 : 5;

}
