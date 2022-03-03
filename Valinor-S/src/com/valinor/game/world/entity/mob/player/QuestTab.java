package com.valinor.game.world.entity.mob.player;

import com.valinor.GameServer;
import com.valinor.game.GameConstants;
import com.valinor.game.content.DropsDisplay;
import com.valinor.game.content.achievements.AchievementWidget;
import com.valinor.game.content.areas.wilderness.content.EloRating;
import com.valinor.game.content.areas.wilderness.content.wilderness_key.WildernessKeyPlugin;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.WildernessActivityManager;
import com.valinor.game.content.boss_event.ChaoticNightmare;
import com.valinor.game.content.boss_event.WorldBossEvent;
import com.valinor.game.content.collection_logs.LogType;
import com.valinor.game.content.daily_tasks.DailyTaskManager;
import com.valinor.game.content.items_kept_on_death.ItemsKeptOnDeath;
import com.valinor.game.content.shootingStars.ShootingStars;
import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.Utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Ali 20.10.2017
 */
public final class QuestTab {

    private QuestTab() {
    }

    public enum InfoTab {

        TIME(53416) {
            @Override
            public String fetchLineData(Player player) {
                return "Time: <col=ffffff> " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM hh:mm a"));
            }
        },

        UPTIME(53417) {
            @Override
            public String fetchLineData(Player player) {
                return "Uptime: <col=ffffff>" + QuestTab.fetchUpTime();
            }
        },

        WORLD_BOSS_SPAWN(53418) {
            @Override
            public String fetchLineData(Player player) {
                return "Next World Boss: <col=ffffff>" + WorldBossEvent.getINSTANCE().timeTill(false);
            }
        },

        SHOOTING_STAR_SPAWN(53419) {
            @Override
            public String fetchLineData(Player player) {
                return "Next Shooting Star: <col=ffffff>" + ShootingStars.getINSTANCE().timeTill(false);
            }
        },

        WILDERNESS_KEY(53420) {
            @Override
            public String fetchLineData(Player player) {
                return "Next Wildy Key: <col=ffffff>" + WildernessKeyPlugin.timeTill(false);
            }
        },

        WILDERNESS_ACTIVITY(53421) {
            @Override
            public String fetchLineData(Player player) {
                return "Wildy activity: <col=ffffff>" + WildernessActivityManager.getSingleton().getActivityDescription();
            }
        },

        WILDERNESS_ACTIVITY_LOCATION(53422) {
            @Override
            public String fetchLineData(Player player) {
                return "- Location: <col=ffffff>" + WildernessActivityManager.getSingleton().getActivityLocation();
            }
        },

        CHAOTIC_NIGHTMARE(53423) {
            @Override
            public String fetchLineData(Player player) {
                return "Next chaotic nightmare: <col=ffffff>" + ChaoticNightmare.getInstance().timeTill(false);
            }
        },

        GAME_MODE(53424) {
            @Override
            public String fetchLineData(Player player) {
                return "Game Mode: <col=ffffff>" + Utils.gameModeToString(player);
            }
        },

        EXP_MODE(53425) {
            @Override
            public String fetchLineData(Player player) {
                return "Exp Mode: <col=ffffff>" + player.expmode().toName();
            }
        },

        PLAY_TIME(53426) {
            @Override
            public String fetchLineData(Player player) {
                return "Play Time: <col=ffffff>" + Utils.convertSecondsToShortDuration((long) player.<Integer>getAttribOr(AttributeKey.GAME_TIME, 0), false);
            }
        },

        REGISTERED_ON(53427) {
            @Override
            public String fetchLineData(Player player) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
                return "Registered On: <col=ffffff>" + simpleDateFormat.format(player.getCreationDate());
            }
        },

        MEMBER_RANK(53428) {
            @Override
            public String fetchLineData(Player player) {
                return "Member Rank: <col=ffffff>" + player.getMemberRights().getName();
            }
        },

        TOTAL_DONATED(53429) {
            @Override
            public String fetchLineData(Player player) {
                double totalAmountPaid = player.getAttribOr(AttributeKey.TOTAL_PAYMENT_AMOUNT, 0D);
                return "Total Donated: <col=ffffff>" + totalAmountPaid + "0$";
            }
        },

        VOTE_POINTS(53430) {
            @Override
            public String fetchLineData(Player player) {
                int votePoints = player.getAttribOr(AttributeKey.VOTE_POINTS, 0);
                return "Vote Points: <col=ffffff>" + votePoints;
            }
        },

        BOSS_POINTS(53431) {
            @Override
            public String fetchLineData(Player player) {
                int bossPoints = player.getAttribOr(AttributeKey.BOSS_POINTS, 0);
                return "Boss Points: <col=ffffff>" + Utils.formatNumber(bossPoints);
            }
        },

        ACHIEVEMENT_POINTS(53432) {
            @Override
            public String fetchLineData(Player player) {
                int achievementPoints = player.getAttribOr(AttributeKey.ACHIEVEMENT_POINTS, 0);
                return "Achievement Points: <col=ffffff>" + Utils.formatNumber(achievementPoints);
            }
        },

        PK_POINTS(53433) {
            @Override
            public String fetchLineData(Player player) {
                int pkPoints = player.getAttribOr(AttributeKey.PK_POINTS, 0);
                return "Pk Points: <col=ffffff>" + Utils.formatNumber(pkPoints);
            }
        },

        BOUNTY_HUNTER_POINTS(53434) {
            @Override
            public String fetchLineData(Player player) {
                int bountyHunterPoints = player.getAttribOr(AttributeKey.BOUNTY_HUNTER_POINTS, 0);
                return "Bounty Hunter Points: <col=ffffff>" + Utils.formatNumber(bountyHunterPoints);
            }
        },

        REFERRALS(53435) {
            @Override
            public String fetchLineData(Player player) {
                int referralCount = player.getAttribOr(AttributeKey.REFERRALS_COUNT, 0);
                return "Referrals: <col=ffffff>" + Utils.formatNumber(referralCount);
            }
        },

        SLAYER_TASK(53436) {
            @Override
            public String fetchLineData(Player player) {
                String name = Slayer.taskName(player.getAttribOr(AttributeKey.SLAYER_TASK_ID, 0));
                int num = player.getAttribOr(AttributeKey.SLAYER_TASK_AMT, 0);
                if (num == 0) {
                    return "Task: <col=ffffff> None";
                } else {
                    return "Task: <col=ffffff> " + num + " x " + name;
                }
            }
        },

        TASK_STREAK(53437) {
            @Override
            public String fetchLineData(Player player) {
                int num = player.getAttribOr(AttributeKey.SLAYER_TASK_SPREE, 0);
                return "Task Streak: <col=ffffff> " + Utils.formatNumber(num);
            }
        },

        TASKS_COMPLETED(53438) {
            @Override
            public String fetchLineData(Player player) {
                int num = player.getAttribOr(AttributeKey.COMPLETED_SLAYER_TASKS, 0);
                return "Tasks Completed: <col=ffffff> " + Utils.formatNumber(num);
            }
        },

        SLAYER_KEYS_RECEIVED(53439) {
            @Override
            public String fetchLineData(Player player) {
                var keys = player.<Integer>getAttribOr(AttributeKey.SLAYER_KEYS_RECEIVED, 0);
                return "Slayer Keys Received: <col=ffffff>" + Utils.formatNumber(keys);
            }
        },

        SLAYER_POINTS(53440) {
            @Override
            public String fetchLineData(Player player) {
                int rewardPoints = player.getAttribOr(AttributeKey.SLAYER_REWARD_POINTS, 0);
                return "Slayer Points: <col=ffffff>" + Utils.formatNumber(rewardPoints);
            }
        },

        SLAYER_TASKS_COMPLETED(53441) {
            @Override
            public String fetchLineData(Player player) {
                int slayerTasksCompleted = player.getAttribOr(AttributeKey.COMPLETED_SLAYER_TASKS, 0);
                return "Slayer Tasks Completed: <col=ffffff>" + Utils.formatNumber(slayerTasksCompleted);
            }
        },

        PLAYERS_IN_WILDERNESS(53442) {
            @Override
            public String fetchLineData(Player player) {
                return "Players in wild: <col=ffffff>" + WildernessArea.players.size();
            }
        },

        ELO_RATING(53443) {
            @Override
            public String fetchLineData(Player player) {
                int rating = player.getAttribOr(AttributeKey.ELO_RATING, EloRating.DEFAULT_ELO_RATING);
                return "Elo Rating: <col=ffffff>" + Utils.formatNumber(rating);
            }
        },

        KILLS(53444) {
            @Override
            public String fetchLineData(Player player) {
                int kills = player.getAttribOr(AttributeKey.PLAYER_KILLS, 0);
                return "Player kills: <col=ffffff>" + Utils.formatNumber(kills);
            }
        },

        DEATHS(53445) {
            @Override
            public String fetchLineData(Player player) {
                int deaths = player.getAttribOr(AttributeKey.PLAYER_DEATHS, 0);
                return "Player deaths: <col=ffffff>" + Utils.formatNumber(deaths);
            }
        },

        KD_RATIO(53446) {
            @Override
            public String fetchLineData(Player player) {
                return "K/D Ratio: <col=ffffff>" + player.getKillDeathRatio();
            }
        },

        CURRENT_KILLSTREAK(53447) {
            @Override
            public String fetchLineData(Player player) {
                int killstreak = player.getAttribOr(AttributeKey.KILLSTREAK, 0);
                return "Killstreak: <col=ffffff>" + Utils.formatNumber(killstreak);
            }
        },

        KILLSTREAK_RECORD(53448) {
            @Override
            public String fetchLineData(Player player) {
                int record = player.getAttribOr(AttributeKey.KILLSTREAK_RECORD, 0);
                return "Highest killstreak: <col=ffffff>" + Utils.formatNumber(record);
            }
        },

        WILDERNESS_KILLSTREAK(53449) {
            @Override
            public String fetchLineData(Player player) {
                int wildernessStreak = player.getAttribOr(AttributeKey.WILDERNESS_KILLSTREAK, 0);
                return "Wilderness streak: <col=ffffff>" + Utils.formatNumber(wildernessStreak);
            }
        },

        RISKED_WEALTH(53450) {
            @Override
            public String fetchLineData(Player player) {
                long risked = ItemsKeptOnDeath.getLostItemsValue();
                return "Risked wealth: <col=ffffff>" + Utils.formatRunescapeStyle(risked) + " Coins";
            }
        },
        ;

        public final int childId;

        InfoTab(int childId) {
            this.childId = childId;
        }

        public abstract String fetchLineData(final Player player);

        public static final Map<Integer, InfoTab> INFO_TAB;

        static {
            INFO_TAB = new HashMap<>();

            for (InfoTab info : InfoTab.values()) {
                INFO_TAB.put(info.childId, info);
            }
        }
    }

    public static boolean onButton(Player player, int button) {
        switch (button) {
            case 53405, 53505 -> {
                QuestTab.refreshInfoTab(player);
                player.getInterfaceManager().setSidebar(GameConstants.QUEST_TAB, 53400);
                return true;
            }

            case 53406 -> {
                player.getInterfaceManager().setSidebar(GameConstants.QUEST_TAB, 53500);
                return true;
            }

            case 53516 -> {
                AchievementWidget.openEasyJournal(player);
                return true;
            }

            case 53521 -> {
                player.getInterfaceManager().open(61380);
                return true;
            }

            case 53526 -> {
                player.getCollectionLog().open(LogType.BOSSES);
                return true;
            }

            case 53531 -> {
                DropsDisplay.start(player);
                return true;
            }

            case 53536 -> {
                player.getPresetManager().open();
                return true;
            }

            case 53541 -> {
                DailyTaskManager.openEasyList(player);
                return true;
            }

            case 53546 -> {
                player.message("We are a brand new server and do not have guides yet.");
                return true;
            }
        }
        return false;
    }

    public static void refreshInfoTab(final Player player) {
        InfoTab.INFO_TAB.forEach((childId, lineInfo) -> player.getPacketSender().sendString(childId, lineInfo.fetchLineData(player)));
    }

    public static String fetchUpTime() {
        final long upTime = System.currentTimeMillis() - GameServer.startTime;
        return String.format("%d hrs, %d mins",
            TimeUnit.MILLISECONDS.toHours(upTime),
            TimeUnit.MILLISECONDS.toMinutes(upTime) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(upTime)));
    }

}
