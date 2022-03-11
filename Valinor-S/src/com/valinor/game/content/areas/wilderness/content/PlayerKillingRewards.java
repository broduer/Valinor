package com.valinor.game.content.areas.wilderness.content;

import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.areas.wilderness.content.bounty_hunter.hotspot.Hotspot;
import com.valinor.game.content.areas.wilderness.content.hitman_services.Hitman;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.WildernessActivityManager;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.impl.EdgevilleActivity;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.impl.PureActivity;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.impl.WildernessHotspot;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.impl.ZerkerActivity;
import com.valinor.game.content.daily_tasks.DailyTaskManager;
import com.valinor.game.content.daily_tasks.DailyTasks;
import com.valinor.game.content.items.mystery.BronzeMysteryBox;
import com.valinor.game.content.items.mystery.GoldMysterybox;
import com.valinor.game.content.items.mystery.PlatinumMysteryBox;
import com.valinor.game.content.items.mystery.SilverMysteryBox;
import com.valinor.game.content.tasks.BottleTasks;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.CombatConstants;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.Color;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.*;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

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

    private static void increaseWhipKills(Player player, Player target) {
        if (player.getEquipment().containsAny(ABYSSAL_WHIP, FROZEN_ABYSSAL_WHIP, VOLCANIC_ABYSSAL_WHIP, ABYSSAL_TENTACLE, ABYSSAL_WHIP_TIER_1, ABYSSAL_WHIP_TIER_2, ABYSSAL_WHIP_TIER_3, ABYSSAL_WHIP_TIER_4, ABYSSAL_WHIP_TIER_5_1, ABYSSAL_WHIP_TIER_5_2, ABYSSAL_WHIP_TIER_5_3, ABYSSAL_WHIP_TIER_5_4, ABYSSAL_WHIP_TIER_5_5)) {
            int whipKills = (Integer) player.getAttribOr(AttributeKey.WHIP_KILLS, 0) + 1;
            player.putAttrib(AttributeKey.WHIP_KILLS, whipKills);
            player.message(Color.BLUE.tag() + "You've received earn " + Color.VIOLET.tag() + "(+1) whip kill" + Color.BLUE.tag() + " after defeating " + Color.VIOLET.tag() + "" + target.getUsername() + "" + Color.BLUE.tag() + "! You now have " + Color.VIOLET.tag() + " " + whipKills + " whip kills" + Color.BLUE.tag() + "!");
        }
    }

    private static void increaseDDSKills(Player player, Player target) {
        if (player.getEquipment().containsAny(DRAGON_DAGGER, DRAGON_DAGGERP, DRAGON_DAGGERP_5680, DRAGON_DAGGERP_5698, DRAGON_DAGGER_TIER_1, DRAGON_DAGGER_TIER_2, DRAGON_DAGGER_TIER_3, DRAGON_DAGGER_TIER_4, DRAGON_DAGGER_TIER_5_1, DRAGON_DAGGER_TIER_5_2, DRAGON_DAGGER_TIER_5_3, DRAGON_DAGGER_TIER_5_4, DRAGON_DAGGER_TIER_5_5)) {
            int ddsKills = (Integer) player.getAttribOr(AttributeKey.DDS_KILLS, 0) + 1;
            player.putAttrib(AttributeKey.DDS_KILLS, ddsKills);
            player.message(Color.BLUE.tag() + "You've received earn " + Color.VIOLET.tag() + "(+1) dds kill" + Color.BLUE.tag() + " after defeating " + Color.VIOLET.tag() + "" + target.getUsername() + "" + Color.BLUE.tag() + "! You now have " + Color.VIOLET.tag() + " " + ddsKills + " dds kills" + Color.BLUE.tag() + "!");
        }
    }

    private static void increaseGmaulKills(Player player, Player target) {
        if (player.getEquipment().containsAny(GRANITE_MAUL, GRANITE_MAUL_12848, GRANITE_MAUL_20557, GRANITE_MAUL_24225, GRANITE_MAUL_24227, GRANITE_MAUL_TIER_1, GRANITE_MAUL_TIER_2, GRANITE_MAUL_TIER_3, GRANITE_MAUL_TIER_4, GRANITE_MAUL_TIER_5_1, GRANITE_MAUL_TIER_5_2, GRANITE_MAUL_TIER_5_3, GRANITE_MAUL_TIER_5_4, GRANITE_MAUL_TIER_5_5)) {
            int ddsKills = (Integer) player.getAttribOr(AttributeKey.GMAUL_KILLS, 0) + 1;
            player.putAttrib(AttributeKey.GMAUL_KILLS, ddsKills);
            player.message(Color.BLUE.tag() + "You've received earn " + Color.VIOLET.tag() + "(+1) gmaul kill" + Color.BLUE.tag() + " after defeating " + Color.VIOLET.tag() + "" + target.getUsername() + "" + Color.BLUE.tag() + "! You now have " + Color.VIOLET.tag() + " " + ddsKills + " gmaul kills" + Color.BLUE.tag() + "!");
        }
    }

    private static void increasedDScimKills(Player player, Player target) {
        if (player.getEquipment().containsAny(DRAGON_SCIMITAR, DRAGON_SCIMITAR_OR, DRAGON_SCIMITAR_TIER_1, DRAGON_SCIMITAR_TIER_2, DRAGON_SCIMITAR_TIER_3, DRAGON_SCIMITAR_TIER_4, DRAGON_SCIMITAR_TIER_5_1, DRAGON_SCIMITAR_TIER_5_2, DRAGON_SCIMITAR_TIER_5_3, DRAGON_SCIMITAR_TIER_5_4, DRAGON_SCIMITAR_TIER_5_5)) {
            int dscimKills = (Integer) player.getAttribOr(AttributeKey.D_SCIM_KILLS, 0) + 1;
            player.putAttrib(AttributeKey.D_SCIM_KILLS, dscimKills);
            player.message(Color.BLUE.tag() + "You've received earn " + Color.VIOLET.tag() + "(+1) dscim kill" + Color.BLUE.tag() + " after defeating " + Color.VIOLET.tag() + "" + target.getUsername() + "" + Color.BLUE.tag() + "! You now have " + Color.VIOLET.tag() + " " + dscimKills + " dscim kills" + Color.BLUE.tag() + "!");
        }
    }

    private static void increaseRuneCbowKills(Player player, Player target) {
        if (player.getEquipment().containsAny(RUNE_CROSSBOW, RUNE_CROSSBOW_TIER_1, RUNE_CROSSBOW_TIER_2, RUNE_CROSSBOW_TIER_3, RUNE_CROSSBOW_TIER_4, RUNE_CROSSBOW_TIER_5_1, RUNE_CROSSBOW_TIER_5_2, RUNE_CROSSBOW_TIER_5_3, RUNE_CROSSBOW_TIER_5_4, RUNE_CROSSBOW_TIER_5_5)) {
            int rcbowKills = (Integer) player.getAttribOr(AttributeKey.RUNE_C_BOW_KILLS, 0) + 1;
            player.putAttrib(AttributeKey.RUNE_C_BOW_KILLS, rcbowKills);
            player.message(Color.BLUE.tag() + "You've received earn " + Color.VIOLET.tag() + "(+1) rune c'bow kill" + Color.BLUE.tag() + " after defeating " + Color.VIOLET.tag() + "" + target.getUsername() + "" + Color.BLUE.tag() + "! You now have " + Color.VIOLET.tag() + " " + rcbowKills + " rune c'bow kills" + Color.BLUE.tag() + "!");
        }
    }

    private static void increaseDMaceKills(Player player, Player target) {
        if (player.getEquipment().containsAny(DRAGON_MACE, DRAGON_MACE_TIER_1, DRAGON_MACE_TIER_2, DRAGON_MACE_TIER_3, DRAGON_MACE_TIER_4, DRAGON_MACE_TIER_5_1, DRAGON_MACE_TIER_5_2, DRAGON_MACE_TIER_5_3, DRAGON_MACE_TIER_5_4, DRAGON_MACE_TIER_5_5)) {
            int dmaceKills = (Integer) player.getAttribOr(AttributeKey.D_MACE_KILLS, 0) + 1;
            player.putAttrib(AttributeKey.D_MACE_KILLS, dmaceKills);
            player.message(Color.BLUE.tag() + "You've received earn " + Color.VIOLET.tag() + "(+1) dmace kill" + Color.BLUE.tag() + " after defeating " + Color.VIOLET.tag() + "" + target.getUsername() + "" + Color.BLUE.tag() + "! You now have " + Color.VIOLET.tag() + " " + dmaceKills + " dmace kills" + Color.BLUE.tag() + "!");
        }
    }

    private static void increaseDLongKills(Player player, Player target) {
        if (player.getEquipment().containsAny(DRAGON_LONGSWORD, DRAGON_LONGSWORD_TIER_1, DRAGON_LONGSWORD_TIER_2, DRAGON_LONGSWORD_TIER_3, DRAGON_LONGSWORD_TIER_4, DRAGON_LONGSWORD_TIER_5_1, DRAGON_LONGSWORD_TIER_5_2, DRAGON_LONGSWORD_TIER_5_3, DRAGON_LONGSWORD_TIER_5_4, DRAGON_LONGSWORD_TIER_5_5)) {
            int dlongKills = (Integer) player.getAttribOr(AttributeKey.D_LONG_KILLS, 0) + 1;
            player.putAttrib(AttributeKey.D_LONG_KILLS, dlongKills);
            player.message(Color.BLUE.tag() + "You've received earn " + Color.VIOLET.tag() + "(+1) dlong kill" + Color.BLUE.tag() + " after defeating " + Color.VIOLET.tag() + "" + target.getUsername() + "" + Color.BLUE.tag() + "! You now have " + Color.VIOLET.tag() + " " + dlongKills + " dlong kills" + Color.BLUE.tag() + "!");
        }
    }

    private static void increaseMSBKills(Player player, Player target) {
        if (player.getEquipment().containsAny(MAGIC_SHORTBOW, MAGIC_SHORTBOW_I, MAGIC_SHORTBOW_TIER_1, MAGIC_SHORTBOW_TIER_2, MAGIC_SHORTBOW_TIER_3, MAGIC_SHORTBOW_TIER_4, MAGIC_SHORTBOW_TIER_5_1, MAGIC_SHORTBOW_TIER_5_2, MAGIC_SHORTBOW_TIER_5_3, MAGIC_SHORTBOW_TIER_5_4, MAGIC_SHORTBOW_TIER_5_5)) {
            int msbKills = (Integer) player.getAttribOr(AttributeKey.MSB_KILLS, 0) + 1;
            player.putAttrib(AttributeKey.MSB_KILLS, msbKills);
            player.message(Color.BLUE.tag() + "You've received earn " + Color.VIOLET.tag() + "(+1) msb kill" + Color.BLUE.tag() + " after defeating " + Color.VIOLET.tag() + "" + target.getUsername() + "" + Color.BLUE.tag() + "! You now have " + Color.VIOLET.tag() + " " + msbKills + " msb kills" + Color.BLUE.tag() + "!");
        }
    }

    private static void increaseDbowKills(Player player, Player target) {
        if (player.getEquipment().containsAny(DARK_BOW, DARK_BOW_TIER_1, DARK_BOW_TIER_2, DARK_BOW_TIER_3, DARK_BOW_TIER_4, DARK_BOW_TIER_5_1, DARK_BOW_TIER_5_2, DARK_BOW_TIER_5_3, DARK_BOW_TIER_5_4, DARK_BOW_TIER_5_5)) {
            int dbowKills = (Integer) player.getAttribOr(AttributeKey.DBOW_KILLS, 0) + 1;
            player.putAttrib(AttributeKey.DBOW_KILLS, dbowKills);
            player.message(Color.BLUE.tag() + "You've received earn " + Color.VIOLET.tag() + "(+1) dbow kill" + Color.BLUE.tag() + " after defeating " + Color.VIOLET.tag() + "" + target.getUsername() + "" + Color.BLUE.tag() + "! You now have " + Color.VIOLET.tag() + " " + dbowKills + " dbow kills" + Color.BLUE.tag() + "!");
        }
    }

    private static void increaseSOLKills(Player player, Player target) {
        if (player.getEquipment().containsAny(STAFF_OF_LIGHT, STAFF_OF_LIGHT_TIER_1, STAFF_OF_LIGHT_TIER_2, STAFF_OF_LIGHT_TIER_3, STAFF_OF_LIGHT_TIER_4, STAFF_OF_LIGHT_TIER_5_1, STAFF_OF_LIGHT_TIER_5_2, STAFF_OF_LIGHT_TIER_5_3, STAFF_OF_LIGHT_TIER_5_4, STAFF_OF_LIGHT_TIER_5_5)) {
            int dbowKills = (Integer) player.getAttribOr(AttributeKey.DBOW_KILLS, 0) + 1;
            player.putAttrib(AttributeKey.STAFF_OF_LIGHT_KILLS, dbowKills);
            player.message(Color.BLUE.tag() + "You've received earn " + Color.VIOLET.tag() + "(+1) sol kill" + Color.BLUE.tag() + " after defeating " + Color.VIOLET.tag() + "" + target.getUsername() + "" + Color.BLUE.tag() + "! You now have " + Color.VIOLET.tag() + " " + dbowKills + " sol kills" + Color.BLUE.tag() + "!");
        }
    }

    /**
     * Increases the kill count for that specific weapon.
     */
    public static void increaseMeleeWeaponKills(Player player, Player target) {
        increaseDDSKills(player, target);
        increaseGmaulKills(player, target);
        increaseWhipKills(player, target);
        increasedDScimKills(player, target);
        increaseDMaceKills(player, target);
        increaseDLongKills(player, target);
        increaseSOLKills(player, target);
    }

    public static void increaseRangedWeaponKills(Player player, Player target) {
        increaseRuneCbowKills(player, target);
        increaseMSBKills(player, target);
        increaseDbowKills(player, target);
    }

    public static void increaseMagicWeaponKills(Player player, Player target) {
        increaseSOLKills(player, target);
    }

    private static void checkForTask(Player player) {
        if (CombatConstants.wearingTorags(player)) {
            player.getTaskBottleManager().increase(BottleTasks.WEAR_TORAGS_TASK);
        }

        if (CombatConstants.wearingFullDharoks(player)) {
            player.getTaskBottleManager().increase(BottleTasks.WEAR_FULL_DH_TASK);
        }

        var risk = player.<Long>getAttribOr(AttributeKey.RISKED_WEALTH, 0L);
        if (risk > 30_000_000L && !Prayers.usingPrayer(player, Prayers.PROTECT_ITEM)) {
            player.getTaskBottleManager().increase(BottleTasks.KILL_WITH_30M_RISK);
        }

        if (player.getEquipment().hasAt(EquipSlot.WEAPON, ItemIdentifiers.DRAGON_SCIMITAR_OR)) {
            player.getTaskBottleManager().increase(BottleTasks.KILL_WITH_DRAGON_SCIMITAR_OR);
        }

        if (player.getEquipment().hasAt(EquipSlot.WEAPON, ItemIdentifiers.INQUISITORS_MACE)) {
            player.getTaskBottleManager().increase(BottleTasks.KILL_WITH_INQUISITORS_MACE);
        }

        if (!player.getEquipment().hasHead() || !player.getEquipment().hasChest() || !player.getEquipment().hasLegs()) {
            player.getTaskBottleManager().increase(BottleTasks.KILL_WITHOUT_HEAD_BODY_AND_LEGS);
        }

        if (!player.getEquipment().hasRing() || !player.getEquipment().hasAmulet() || !player.getEquipment().hasHands()) {
            player.getTaskBottleManager().increase(BottleTasks.KILL_WITHOUT_RING_AMULET_AND_GLOVES);
        }

        if (player.getEquipment().hasAt(EquipSlot.HEAD, OBSIDIAN_HELMET) || player.getEquipment().hasAt(EquipSlot.BODY, OBSIDIAN_PLATEBODY) || player.getEquipment().hasAt(EquipSlot.LEGS, OBSIDIAN_PLATELEGS)) {
            player.getTaskBottleManager().increase(BottleTasks.KILL_WEARING_FULL_OBSIDIAN);
        }

        if (!player.skills().combatStatsBoosted()) {
            player.getTaskBottleManager().increase(BottleTasks.KILL_WITHOUT_BOOSTED_STATS);
        }
    }
   
    private static int shutdownValueOf(int streak) {
        int bonus = 100 * streak;
        if(bonus > 2_500)
            bonus = 2_500;
        return bonus;
    }

    private static int killstreakValueOf(int streak) {
        int bonus = 25 * streak;
        if(bonus > 375)
            bonus = 375;
        return bonus;
    }

    private static int firstKillOfTheDay(Player killer) {
        if (System.currentTimeMillis() >= (long) killer.getAttribOr(AttributeKey.FIRST_KILL_OF_THE_DAY, 0L)) {
            killer.message("You've earned 1,500 additional pkp for your first kill of the day!");
            killer.putAttrib(AttributeKey.FIRST_KILL_OF_THE_DAY, System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24));
            return 1500;
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
                //check for active bountys
                Hitman.checkOnKill(killer, target);

                //check for tasks
                checkForTask(killer);

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

                boolean edgeActivity = WildernessActivityManager.getSingleton().isActivityCurrent(EdgevilleActivity.class) && WildernessActivityManager.getSingleton().getActivity(EdgevilleActivity.class).canReward(killer);
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

                var pkpReward = 300;//Base value

                // Apply donation boost, if any
                pkpReward = (int) ((double )pkpReward * killer.getMemberRights().pkpMultiplier());

                // Apply target's killstreak on our reward. Oh, and our streak.
                pkpReward += shutdownValueOf(target_killstreak); //Add the shutdown value bonus to the pkp reward
                pkpReward += killstreakValueOf(killstreak); //Add the killstreak value bonus to the pkp reward
                pkpReward += WildernessArea.wildernessLevel(killer.tile()) * 2; //Add the wilderness level bonus to the reward

                // Double pkp, if enabled. Can be toggled with ::pkpmultiplier <int>. Default 1.
                pkpReward *= World.getWorld().pkpMultiplier;

                pkpReward += firstKillOfTheDay(killer); //2000pkp for first kill of the day

                if(hotspotActivity || killer.tile().inArea(Hotspot.ACTIVE.area)) {
                    pkpReward *= 2.0;
                    killer.message("<col=6a1a18><img=453> You get double pkp for killing a player in a hotspot!");
                    var hotspotPoints = killer.<Integer>getAttribOr(AttributeKey.HOTSPOT_POINTS, 0) + 1;
                    killer.putAttrib(AttributeKey.HOTSPOT_POINTS, hotspotPoints);
                    killer.message("<col=6a1a18><img=453> You received one hotspot point, you now have "+ Utils.formatNumber(hotspotPoints)+" hotspot points.");
                }

                var blood_reaper = killer.hasPetOut("Blood Reaper pet");
                if(blood_reaper) {
                    int extraPkp = (int) (pkpReward * 0.10);
                    pkpReward += extraPkp;
                }

                killer.message(Color.RED.tag() + "<shad=0>[PK Points]</col></shad> " + Color.BLUE.tag() + "You earn " + Color.VIOLET.tag() + "(+" + pkpReward + ") pkp " + Color.BLUE.tag() + " after killing " + Color.VIOLET.tag() + "" + target.getUsername() + "" + Color.BLUE.tag() + "!");

                int updatePkp = (Integer) killer.getAttribOr(AttributeKey.PK_POINTS, 0) + pkpReward;
                killer.putAttrib(AttributeKey.PK_POINTS, updatePkp);
                killer.getPacketSender().sendString(QuestTab.InfoTab.PK_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.PK_POINTS.childId).fetchLineData(killer));

                //Drop cash for every kill you make
                killer.inventory().addOrDrop(new Item(COINS_995, World.getWorld().random(2_500_000, 5_000_000)));

                var risk = killer.<Long>getAttribOr(AttributeKey.RISKED_WEALTH, 0L);

                //If a player is risking over 50M coins roll for an extra reward
                if (World.getWorld().rollDie(35, 1) && risk > 50_000_000) {
                    killer.getRisk().reward();
                }

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

        boolean edgeville = killer.tile().region() == 12343 || killer.tile().region() == 12087;
        boolean mageBank = killer.tile().region() == 12605 || killer.tile().region() == 12349 || killer.tile().region() == 12093;
        boolean revCave = killer.tile().region() == 12701 || killer.tile().region() == 12702 || killer.tile().region() == 12703 || killer.tile().region() == 12957 || killer.tile().region() == 12958 || killer.tile().region() == 12959;
        boolean memberCave = killer.tile().memberCave();

        if(edgeville) {
            DailyTaskManager.increase(DailyTasks.EDGEVILLE_KILLS, killer);
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
