package com.valinor.game.content.areas.wilderness.content;

import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.WildernessActivityManager;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.impl.EdgevileActivity;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.impl.PureActivity;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.impl.WildernessHotspot;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.impl.ZerkerActivity;
import com.valinor.game.content.daily_tasks.DailyTaskManager;
import com.valinor.game.content.daily_tasks.DailyTasks;
import com.valinor.game.content.items.mystery.BronzeMysteryBox;
import com.valinor.game.content.items.mystery.GoldMysterybox;
import com.valinor.game.content.items.mystery.PlatinumMysteryBox;
import com.valinor.game.content.items.mystery.SilverMysteryBox;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.CombatConstants;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.*;
import static com.valinor.util.CustomItemIdentifiers.*;

/**
 * The class which represents functionality for the PKP rewards.
 * Credits go to the developers from OSS.
 * <p>
 * Update: December, 12, 2020, 18:33
 * Added support for new features such as wilderness events.
 * Red skull, trained accounts and many more. Also optimized the class.
 *
 * @author <a href="http://www.rune-server.org/members/_Patrick_/">Patrick van
 * Elderen</a>
 */
public class PlayerKillingRewards {

    private static final Logger logger = LogManager.getLogger(PlayerKillingRewards.class);

    private static int shutdownValueOf(int streak) {
        return (10 * streak + 50 * (streak / 10));
    }

    private static int killstreakValueOf(int streak) {
        int points = 5 * streak;
        if(points > 100)
            points = 100;
        return points;
    }

    private static int firstKillOfTheDay(Player killer) {
        if (System.currentTimeMillis() >= (long) killer.getAttribOr(AttributeKey.FIRST_KILL_OF_THE_DAY, 0L)) {
            killer.message("You've earned 2,500 additional pkp for your first kill of the day!");
            killer.putAttrib(AttributeKey.FIRST_KILL_OF_THE_DAY, System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24));
            return 2500;
        }
        return 0;
    }

    public static void reward(Player killer, Player target, boolean valid) {
        // Add a death. Only when dying to a player.
        int dc = (Integer) target.getAttribOr(AttributeKey.PLAYER_DEATHS, 0) + 1;
        target.putAttrib(AttributeKey.PLAYER_DEATHS, dc);
        try {
            // Let's reward...

            // Add a kill when the kill is valid (not farming) and it's not in duel arena/FFA
            if (valid) {
                // Starter trade prevention
                if (killer.<Integer>getAttribOr(AttributeKey.GAME_TIME, 0) < 3000 && !killer.getPlayerRights().isDeveloperOrGreater(killer) && !target.getPlayerRights().isDeveloperOrGreater(target)) {
                    killer.message("You are restricted from receiving rewards from pking until 30 minutes of play time.");
                    killer.message("Only " + Math.ceil((int) (3000.0 - killer.<Integer>getAttribOr(AttributeKey.GAME_TIME, 0)) / 100.0) + "minutes left.");
                    return;
                }

                if (target.<Integer>getAttribOr(AttributeKey.GAME_TIME, 0) < 3000 && !target.getPlayerRights().isDeveloperOrGreater(target) && !killer.getPlayerRights().isDeveloperOrGreater(killer)) {
                    killer.message("Your partner is restricted from receiving rewards from pking until 30 minutes of play time.");
                    killer.message("Only " + Math.ceil((int) (3000.0 - killer.<Integer>getAttribOr(AttributeKey.GAME_TIME, 0)) / 100.0) + "minutes left.");
                    return;
                }

                //Update achievements
                updateAchievement(killer, target);

                //Refill the killers special attack on kills.
                killer.restoreSpecialAttack(100);

                // Ruin his kill streak. Only when dying to a player.
                int target_killstreak = target.getAttribOr(AttributeKey.KILLSTREAK, 0);
                target.clearAttrib(AttributeKey.KILLSTREAK);

                //Update target killstreak
                target.getPacketSender().sendString(CURRENT_KILLSTREAK.childId, QuestTab.InfoTab.INFO_TAB.get(CURRENT_KILLSTREAK.childId).fetchLineData(target));

                //Increase the player killcount
                int killcount = (Integer) killer.getAttribOr(AttributeKey.PLAYER_KILLS, 0) + 1;
                killer.putAttrib(AttributeKey.PLAYER_KILLS, killcount);

                //Update the kills and deaths
                killer.getPacketSender().sendString(KILLS.childId, QuestTab.InfoTab.INFO_TAB.get(KILLS.childId).fetchLineData(killer));
                target.getPacketSender().sendString(DEATHS.childId, QuestTab.InfoTab.INFO_TAB.get(DEATHS.childId).fetchLineData(target));

                //Update the kdr
                killer.getPacketSender().sendString(KD_RATIO.childId, QuestTab.InfoTab.INFO_TAB.get(KD_RATIO.childId).fetchLineData(killer));
                target.getPacketSender().sendString(KD_RATIO.childId, QuestTab.InfoTab.INFO_TAB.get(KD_RATIO.childId).fetchLineData(target));

                // Elo rating check.
                EloRating.modify(killer, target);

                int killstreak = (Integer) killer.getAttribOr(AttributeKey.KILLSTREAK, 0) + 1;
                killer.putAttrib(AttributeKey.KILLSTREAK, killstreak);

                //Update the killstreak
                killer.getPacketSender().sendString(CURRENT_KILLSTREAK.childId, QuestTab.InfoTab.INFO_TAB.get(CURRENT_KILLSTREAK.childId).fetchLineData(killer));

                // Did we reach a new high in terms of KS?
                int ksRecord = killer.getAttribOr(AttributeKey.KILLSTREAK_RECORD, 0);
                if (killstreak > ksRecord) {
                    killer.putAttrib(AttributeKey.KILLSTREAK_RECORD, killstreak);
                    killer.getPacketSender().sendString(QuestTab.InfoTab.KILLSTREAK_RECORD.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.KILLSTREAK_RECORD.childId).fetchLineData(killer));
                }

                // Killstreak going on?
                if (killstreak > 1) {
                    killer.message("You're currently on a killing spree of " + killstreak + "!");

                    if (killstreak % 5 == 0 || killstreak > 15) {
                        World.getWorld().getPlayers().forEach(player -> player.message("<col=ca0d0d><img=506> " + killer.getUsername() + " has a killing spree of " + killstreak + " and can be shut down for " + (100 + shutdownValueOf(killstreak)) + " PKP!"));
                    }
                }

                // Announce if you shut down a killstreak
                if (target_killstreak >= 5) {
                    World.getWorld().getPlayers().forEach(player -> player.message("<col=ca0d0d><img=506> " + killer.getUsername() + " has shut down " + target.getUsername() + " with a killing spree of " + target_killstreak + "."));
                }

                // If this passes our shutdown record, change it
                int record = killer.getAttribOr(AttributeKey.SHUTDOWN_RECORD, 0);
                if (target_killstreak > record) {
                    killer.putAttrib(AttributeKey.SHUTDOWN_RECORD, target_killstreak);
                }

                //Update the wilderness streak
                int wilderness_killstreak = (Integer) killer.getAttribOr(AttributeKey.WILDERNESS_KILLSTREAK, 0) + 1;
                killer.putAttrib(AttributeKey.WILDERNESS_KILLSTREAK, wilderness_killstreak);

                killer.getPacketSender().sendString(WILDERNESS_KILLSTREAK.childId, QuestTab.InfoTab.INFO_TAB.get(WILDERNESS_KILLSTREAK.childId).fetchLineData(killer));
                target.getPacketSender().sendString(WILDERNESS_KILLSTREAK.childId, QuestTab.InfoTab.INFO_TAB.get(WILDERNESS_KILLSTREAK.childId).fetchLineData(target));

                boolean edgeActivity = WildernessActivityManager.getSingleton().isActivityCurrent(EdgevileActivity.class) && WildernessActivityManager.getSingleton().getActivity(EdgevileActivity.class).canReward(killer);
                boolean pureActivity = WildernessActivityManager.getSingleton().isActivityCurrent(PureActivity.class) && WildernessActivityManager.getSingleton().getActivity(PureActivity.class).canReward(killer);
                boolean zerkerActivity = WildernessActivityManager.getSingleton().isActivityCurrent(ZerkerActivity.class) && WildernessActivityManager.getSingleton().getActivity(ZerkerActivity.class).canReward(killer);
                boolean hotspotActivity = WildernessActivityManager.getSingleton().isActivityCurrent(WildernessHotspot.class) && WildernessActivityManager.getSingleton().getActivity(WildernessHotspot.class).canReward(killer);

                //Check if any activities active, if so roll for a casket
                if (edgeActivity || pureActivity || zerkerActivity) {
                    if(World.getWorld().rollDie(45, 1)) {
                        killer.inventory().addOrDrop(new Item(WILDY_ACTIVITY_CASKET));
                        killer.message(Color.PURPLE.wrap("You've found a wildy activity casket searching the corpse of "+target.getUsername()+"."));
                    }
                }

                var pkpReward = 500;//Base value

                // Apply donation boost, if any
                pkpReward = (int) ((double )pkpReward * killer.getMemberRights().pkpMultiplier());

                // Apply target's killstreak on our reward. Oh, and our streak.
                pkpReward += shutdownValueOf(target_killstreak); //Add the shutdown value bonus to the pkp reward
                pkpReward += killstreakValueOf(killstreak); //Add the killstreak value bonus to the pkp reward
                pkpReward += WildernessArea.wildernessLevel(killer.tile()) * 2; //Add the wilderness level bonus to the reward

                // Double pkp, if enabled. Can be toggled with ::pkpmultiplier <int>. Default 1.
                pkpReward *= World.getWorld().pkpMultiplier;

                pkpReward += firstKillOfTheDay(killer); //2000pkp for first kill of the day

                if(hotspotActivity) {
                    pkpReward *= 2.0;
                    killer.message("<col=6a1a18><img=15> You get double pkp for killing a player in a hotspot!");
                }

                var blood_reaper = killer.hasPetOut("Blood Reaper pet");
                if(blood_reaper) {
                    int extraPkp = pkpReward * 10 / 100;
                    pkpReward += extraPkp;
                }

                killer.message(Color.RED.tag() + "<shad=0>[PK Points]</col></shad> " + Color.BLUE.tag() + "You earn " + Color.VIOLET.tag() + "(+" + pkpReward + ") pkp " + Color.BLUE.tag() + " after killing " + Color.VIOLET.tag() + "" + target.getUsername() + "" + Color.BLUE.tag() + "!");

                int updatePkp = (Integer) killer.getAttribOr(AttributeKey.PK_POINTS, 0) + pkpReward;
                killer.putAttrib(AttributeKey.PK_POINTS, updatePkp);
                killer.getPacketSender().sendString(QuestTab.InfoTab.PK_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.PK_POINTS.childId).fetchLineData(killer));

                //1 in 250 chance to receive a mystery box
                if(World.getWorld().rollDie(250,1)) {
                    killer.inventory().addOrBank(new Item(DONATOR_MYSTERY_BOX));
                    killer.message(Color.PURPLE.wrap("You've found a mystery box searching the corpse of "+target.getUsername()+"."));
                }

                //1 in 1000 chance to receive a pets' mystery box
                if(World.getWorld().rollDie(1000,1)) {
                    killer.inventory().addOrBank(new Item(PETS_MYSTERY_BOX));
                    killer.message(Color.PURPLE.wrap("You've found a pets mystery box searching the corpse of "+target.getUsername()+"."));
                    World.getWorld().sendWorldMessage("<img=452><shad=0>" + killer.getUsername() + " " + "found a pets mystery box searching the corpse of "+target.getUsername()+".");
                }

                BronzeMysteryBox.rollForBronzeMysteryBox(killer, target);
                SilverMysteryBox.rollForSilverMysteryBox(killer, target);
                GoldMysterybox.rollForGoldMysteryBox(killer, target);
                PlatinumMysteryBox.rollForPlatinumMysteryBox(killer, target);
            }
        } catch (Exception e) {
            logger.error("fk", e);
        }
    }

    private static void updateAchievement(Player killer, Player target) {
        AchievementsManager.activate(killer, Achievements.PVP_I, 1);
        AchievementsManager.activate(killer, Achievements.PVP_II, 1);
        AchievementsManager.activate(killer, Achievements.PVP_III, 1);

        //Killer needs killstreak of +25 to unlock
        int killstreak = killer.getAttribOr(AttributeKey.KILLSTREAK, 0);

        if(killstreak >= 10) {
            DailyTaskManager.increase(DailyTasks.KILLSTREAK, killer);
        }

        if (killstreak >= 25) {
            AchievementsManager.activate(killer, Achievements.BLOODTHIRSTY_I, 1);
        }

        //Killer needs killstreak of +50 to unlock
        if (killstreak >= 50) {
            AchievementsManager.activate(killer, Achievements.BLOODTHIRSTY_II, 1);
        }
        //Killer needs to end a killstreak of +50 to unlock
        int target_killstreak = target.getAttribOr(AttributeKey.KILLSTREAK, 0);
        if (target_killstreak >= 50) {
            AchievementsManager.activate(killer, Achievements.BLOODTHIRSTY_III, 1);
        }

        int wilderness_killstreak = (Integer) killer.getAttribOr(AttributeKey.WILDERNESS_KILLSTREAK, 0) + 1;
        if (wilderness_killstreak >= 5) {
            AchievementsManager.activate(killer, Achievements.SURVIVOR_I, 1);
        }

        if (wilderness_killstreak >= 10) {
            AchievementsManager.activate(killer, Achievements.SURVIVOR_II, 1);
        }

        if (WildernessArea.wildernessLevel(killer.tile()) >= 30) {
            AchievementsManager.activate(killer, Achievements.DEEP_WILD_I, 1);
            AchievementsManager.activate(killer, Achievements.DEEP_WILD_II, 1);
            AchievementsManager.activate(killer, Achievements.DEEP_WILD_III, 1);
            DailyTaskManager.increase(DailyTasks.DEEP_WILD, killer);
        }

        if (WildernessArea.wildernessLevel(killer.tile()) >= 50) {
            AchievementsManager.activate(killer, Achievements.EXTREME_DEEP_WILD_I, 1);
            AchievementsManager.activate(killer, Achievements.EXTREME_DEEP_WILD_II, 1);
            AchievementsManager.activate(killer, Achievements.EXTREME_DEEP_WILD_III, 1);
        }

        int combatLevel = killer.skills().combatLevel();
        int defenceLevel = killer.skills().level(Skills.DEFENCE);

        if (defenceLevel == 1 && combatLevel >= 80) {
            AchievementsManager.activate(killer, Achievements.PURE_I, 1);
            AchievementsManager.activate(killer, Achievements.PURE_II, 1);
            AchievementsManager.activate(killer, Achievements.PURE_III, 1);
            AchievementsManager.activate(killer, Achievements.PURE_IV, 1);
        }

        if (defenceLevel == 45 && combatLevel >= 95) {
            AchievementsManager.activate(killer, Achievements.ZERKER_I, 1);
            AchievementsManager.activate(killer, Achievements.ZERKER_II, 1);
            AchievementsManager.activate(killer, Achievements.ZERKER_III, 1);
            AchievementsManager.activate(killer, Achievements.ZERKER_IV, 1);
        }

        if (CombatConstants.wearingFullDharoks(killer)) {
            if (killer.hp() < 25) {
                AchievementsManager.activate(killer, Achievements.DHAROK_BOMBER_I, 1);
            }

            if (killer.hp() < 15) {
                AchievementsManager.activate(killer, Achievements.DHAROK_BOMBER_II, 1);
            }

            if (killer.hp() < 5) {
                AchievementsManager.activate(killer, Achievements.DHAROK_BOMBER_III, 1);
            }
        }

        boolean edgevile = killer.tile().region() == 12343 || killer.tile().region() == 12087;
        boolean mageBank = killer.tile().region() == 12605 || killer.tile().region() == 12349 || killer.tile().region() == 12093;
        boolean revCave = killer.tile().region() == 12701 || killer.tile().region() == 12702 || killer.tile().region() == 12703 || killer.tile().region() == 12957 || killer.tile().region() == 12958 || killer.tile().region() == 12959;
        boolean memberCave = killer.tile().memberCave();

        if(edgevile) {
            DailyTaskManager.increase(DailyTasks.EDGEVILE_KILLS, killer);
        }

        if(mageBank) {
            DailyTaskManager.increase(DailyTasks.MAGE_BANK_KILLS, killer);
        }

        if(revCave) {
            DailyTaskManager.increase(DailyTasks.KILL_PLAYERS_REV_CAVE, killer);
        }

        if(memberCave) {
            DailyTaskManager.increase(DailyTasks.MEMBER_CAVE_KILLS, killer);
        }
    }

}
