package com.valinor.game.content.areas.wilderness.content.bounty_hunter;

import com.valinor.GameServer;
import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.areas.wilderness.content.PlayerKillingRewards;
import com.valinor.game.content.areas.wilderness.content.TopPkers;
import com.valinor.game.content.areas.wilderness.content.bounty_hunter.bounty_tasks.BountyHunterTask;
import com.valinor.game.content.areas.wilderness.content.bounty_hunter.bounty_tasks.BountyTaskEvent;
import com.valinor.game.content.mechanics.Death;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.content.areas.wilderness.content.bounty_hunter.emblem.BountyHunterEmblem;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.valinor.game.content.areas.wilderness.content.bounty_hunter.BountyHunterConstants.LEVEL_INDICATOR_ID;
import static com.valinor.game.content.areas.wilderness.content.bounty_hunter.BountyHunterConstants.TARGET_NAME_ID;
import static com.valinor.game.world.entity.AttributeKey.EMBLEM_WEALTH;
import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.PK_POINTS;
import static com.valinor.util.ItemIdentifiers.ANTIQUE_EMBLEM_TIER_1;
import static com.valinor.util.Utils.formatNumber;

/**
 * The bounty hunter minigame as of 4 November 2019.
 *
 * @author Professor Oak
 * @author Zerikoth
 */
public class BountyHunter {

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
                    for (final Player potential : WildernessArea.players) {
                        //Check if player2 is a valid target..
                        if (validTargetContester(potential)) {
                            //Check other stuff...

                            if (potential.getHostAddress().equalsIgnoreCase(player.getHostAddress()) && !player.getPlayerRights().isDeveloperOrGreater(player)) {
                                continue;
                            }

                            if (potential.looks().hidden()) {
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
                            if (player.getClanChat() != null && player.getClanChat().equalsIgnoreCase(potential.getClanChat())) {
                                continue;
                            }

                            if (Math.abs(player.skills().combatLevel() - potential.skills().combatLevel()) > 5) {
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
            player.getPacketSender().sendMessage("You've been assigned " + target.getUsername() + " as your target!");
            player.getPacketSender().sendString(TARGET_NAME_ID, target.getUsername());
            target.getPacketSender().sendMessage("You've been assigned " + player.getUsername() + " as your target!");
            target.getPacketSender().sendString(TARGET_NAME_ID, player.getUsername());
            BountyHunterWidget.sendBountyWidget(player);
            BountyHunterWidget.sendBountyWidget(target);

            //Send hints..
            player.getPacketSender().sendEntityHint(target);
            target.getPacketSender().sendEntityHint(player);

            if (!player.hasBountyTask()) {// Don't overwrite existing tasks
                player.setBountyHunterTask(new BountyTaskEvent(player, target));
            }

            if (!target.hasBountyTask()) {
                target.setBountyHunterTask(new BountyTaskEvent(target, player));
            }
        }
    }

    /**
     * Un assigns an existing {@link TargetPair}.
     */
    public static void unassign(Player player) {
        final Optional<TargetPair> pair = getPairFor(player);
        if (pair.isPresent()) {

            TARGET_PAIRS.remove(pair.get());

            final Player target = pair.get().getPlayer1();
            final Player p = pair.get().getPlayer2();

            //Reset hints..
            target.getPacketSender().sendEntityHintRemoval(true);
            p.getPacketSender().sendEntityHintRemoval(true);

            //Reset strings
            target.getPacketSender().sendString(TARGET_NAME_ID, "").sendString(LEVEL_INDICATOR_ID, "");
            p.getPacketSender().sendString(TARGET_NAME_ID, "").sendString(LEVEL_INDICATOR_ID, "");

            //Set timers
            p.getTargetSearchTimer().start(TARGET_SEARCH_DELAY_SECONDS);
            target.getTargetSearchTimer().start(TARGET_SEARCH_DELAY_SECONDS);

            //Reset tasks
            BountyHunterTask.resetBountyTask(target, false);
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

        if (killer.getRecentKills().contains(killed.getHostAddress())) {
            rewardPlayer = false;
            //System.out.println("Let's not reward the player, already killed before.");
        } else if (killer.<String>getAttribOr(AttributeKey.MAC_ADDRESS, "invalid").equals(killed.<String>getAttribOr(AttributeKey.MAC_ADDRESS, "invalid"))) {
            rewardPlayer = false;
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

        if (killer.getPlayerRights().isDeveloperOrGreater(killer)) {
            rewardPlayer = true;
        }

        if (rewardPlayer) {
            TopPkers.SINGLETON.increase(killer.getUsername());

            //Other rewards
            if (WildernessArea.inWilderness(killer.tile())) { // Only reward if in wild
                PlayerKillingRewards.reward(killer, killed, true);
            }
        } else {
            killer.message("You don't get any rewards for that kill.");
            //Player is probably farming kills.
        }

        Optional<Player> target = getTargetfor(killer);

        //Check if the player killed was our target..
        if (target.isPresent() && target.get().equals(killed)) {

            //Reset targets
            unassign(killer);

            //If player isn't farming kills..
            if (rewardPlayer) {
                //Send messages
                killed.message("You were defeated by your target!");

                //Set before the kill message because otherwise the bounty task points aren't calculated in total points
                BountyHunterTask.BountyTasks.checkOnKill(killer);

                var pkp = killer.<Integer>getAttribOr(AttributeKey.PK_POINTS, 0) + 100;
                killer.putAttrib(AttributeKey.PK_POINTS, pkp);
                killer.getPacketSender().sendString(QuestTab.InfoTab.PK_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.PK_POINTS.childId).fetchLineData(killer));
                killer.message("You were awarded with 100 extra PKP for killing your target!");

                AchievementsManager.activate(killer, Achievements.BOUNTY_HUNTER_I, 1);
                AchievementsManager.activate(killer, Achievements.BOUNTY_HUNTER_II, 1);
                AchievementsManager.activate(killer, Achievements.BOUNTY_HUNTER_III, 1);

                Optional<BountyHunterEmblem> emblem = BountyHunterEmblem.getBest(killer, true);
                if (emblem.isPresent()) {
                    killer.inventory().remove(new Item(emblem.get().getItemId()));
                    killer.inventory().add(new Item(emblem.get().getNextOrLast().getItemId()));
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
        for (BountyHunterEmblem emblem : BountyHunterEmblem.values()) {
            if (player.inventory().contains(emblem.getItemId())) {
                list.add(emblem);
            }
        }

        if (list.isEmpty()) {
            return 0;
        }

        int pkPoints = 0;

        for (BountyHunterEmblem emblem : list) {
            int amount = player.inventory().count(emblem.getItemId());
            if (amount > 0) {
                pkPoints += (emblem.getPkPoints() * amount);
                player.putAttrib(EMBLEM_WEALTH, formatNumber(pkPoints) + " pk points");

                if (performSale) {
                    if (!player.inventory().contains(emblem.getItemId())) {
                        return 0;
                    }
                    player.inventory().remove(emblem.getItemId(), amount);
                    var pkp = player.<Integer>getAttribOr(AttributeKey.PK_POINTS, 0) + pkPoints;
                    player.putAttrib(AttributeKey.PK_POINTS, pkp);
                    player.getPacketSender().sendString(PK_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(PK_POINTS.childId).fetchLineData(player));
                    player.clearAttrib(EMBLEM_WEALTH);
                }
            }
        }
        return pkPoints;
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
