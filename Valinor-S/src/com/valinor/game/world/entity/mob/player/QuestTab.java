package com.valinor.game.world.entity.mob.player;

import com.valinor.GameServer;
import com.valinor.game.content.DropsDisplay;
import com.valinor.game.content.achievements.AchievementWidget;
import com.valinor.game.content.areas.wilderness.content.boss_event.WildernessBossEvent;
import com.valinor.game.content.collection_logs.LogType;
import com.valinor.game.content.daily_tasks.DailyTaskManager;
import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static final Logger logger = LogManager.getLogger(QuestTab.class);

    private QuestTab() {
    }

    public enum InfoTab {

        TIME(72998) {
            @Override
            public String fetchLineData(Player player) {
                return "Time: <col=ffffff> " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM hh:mm a"));
            }
        },

        UPTIME(72999) {
            @Override
            public String fetchLineData(Player player) {
                return "Uptime: <col=ffffff>" + QuestTab.fetchUpTime();
            }
        },

        WORLD_BOSS_SPAWN(73000) {
            @Override
            public String fetchLineData(Player player) {
                return "Next World Boss: <col=ffffff>" + WildernessBossEvent.getINSTANCE().timeTill(false);
            }
        },

        GAME_MODE(73001) {
            @Override
            public String fetchLineData(Player player) {
                return "Game Mode: <col=ffffff>" + Utils.gameModeToString(player);
            }
        },

        EXP_MODE(73002) {
            @Override
            public String fetchLineData(Player player) {
                return "Exp Mode: <col=ffffff>" + player.expmode().toName();
            }
        },

        PLAY_TIME(73003) {
            @Override
            public String fetchLineData(Player player) {
                return "Play Time: <col=ffffff>" + Utils.convertSecondsToShortDuration((long) player.<Integer>getAttribOr(AttributeKey.GAME_TIME, 0), false);
            }
        },

        REGISTERED_ON(73004) {
            @Override
            public String fetchLineData(Player player) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
                return "Registered On: <col=ffffff>" + simpleDateFormat.format(player.getCreationDate());
            }
        },

        MEMBER_RANK(73005) {
            @Override
            public String fetchLineData(Player player) {
                return "Member Rank: <col=ffffff>" + player.getMemberRights().getName();
            }
        },

        TOTAL_DONATED(73006) {
            @Override
            public String fetchLineData(Player player) {
                double totalAmountPaid = player.getAttribOr(AttributeKey.TOTAL_PAYMENT_AMOUNT, 0D);
                return "Total Donated: <col=ffffff>" + totalAmountPaid + "0$";
            }
        },

        VOTE_POINTS(73007) {
            @Override
            public String fetchLineData(Player player) {
                int votePoints = player.getAttribOr(AttributeKey.VOTE_POINTS, 0);
                return "Vote Points: <col=ffffff>" + votePoints;
            }
        },

        BOSS_POINTS(73008) {
            @Override
            public String fetchLineData(Player player) {
                int bossPoints = player.getAttribOr(AttributeKey.BOSS_POINTS, 0);
                return "Boss Points: <col=ffffff>" + Utils.formatNumber(bossPoints);
            }
        },

        ACHIEVEMENT_POINTS(73009) {
            @Override
            public String fetchLineData(Player player) {
                int achievementPoints = player.getAttribOr(AttributeKey.ACHIEVEMENT_POINTS, 0);
                return "Achievement Points: <col=ffffff>" + Utils.formatNumber(achievementPoints);
            }
        },

        PK_POINTS(73010) {
            @Override
            public String fetchLineData(Player player) {
                int pkPoints = player.getAttribOr(AttributeKey.PK_POINTS, 0);
                return "Pk Points: <col=ffffff>" + Utils.formatNumber(pkPoints);
            }
        },

        BOUNTY_HUNTER_POINTS(73011) {
            @Override
            public String fetchLineData(Player player) {
                int bountyHunterPoints = player.getAttribOr(AttributeKey.BOUNTY_HUNTER_POINTS, 0);
                return "Bounty Hunter Points: <col=ffffff>" + Utils.formatNumber(bountyHunterPoints);
            }
        },

        REFERRALS(73012) {
            @Override
            public String fetchLineData(Player player) {
                int referralCount = player.getAttribOr(AttributeKey.REFERRALS_COUNT, 0);
                return "Referrals: <col=ffffff>" + Utils.formatNumber(referralCount);
            }
        },

        SLAYER_TASK(73013) {
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

        TASK_STREAK(73014) {
            @Override
            public String fetchLineData(Player player) {
                int num = player.getAttribOr(AttributeKey.SLAYER_TASK_SPREE, 0);
                return "Task Streak: <col=ffffff> " + Utils.formatNumber(num);
            }
        },

        TASKS_COMPLETED(73015) {
            @Override
            public String fetchLineData(Player player) {
                int num = player.getAttribOr(AttributeKey.COMPLETED_SLAYER_TASKS, 0);
                return "Tasks Completed: <col=ffffff> " + Utils.formatNumber(num);
            }
        },

        SLAYER_KEYS_RECEIVED(73016) {
            @Override
            public String fetchLineData(Player player) {
                var keys = player.<Integer>getAttribOr(AttributeKey.SLAYER_KEYS_RECEIVED, 0);
                return "Slayer Keys Received: <col=ffffff>" + Utils.formatNumber(keys);
            }
        },

        SLAYER_POINTS(73017) {
            @Override
            public String fetchLineData(Player player) {
                int rewardPoints = player.getAttribOr(AttributeKey.SLAYER_REWARD_POINTS, 0);
                return "Slayer Points: <col=ffffff>" + Utils.formatNumber(rewardPoints);
            }
        },

        SLAYER_TASKS_COMPLETED(73018) {
            @Override
            public String fetchLineData(Player player) {
                int slayerTasksCompleted = player.getAttribOr(AttributeKey.COMPLETED_SLAYER_TASKS, 0);
                return "Slayer Tasks Completed: <col=ffffff>" + Utils.formatNumber(slayerTasksCompleted);
            }
        };

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
            case 72985 -> {
                QuestTab.refreshInfoTab(player);
                return true;
            }

            case 73108 -> {
                AchievementWidget.openEasyJournal(player);
                return true;
            }

            case 73118 -> {
                player.getCollectionLog().open(LogType.BOSSES);
                return true;
            }

            case 73123 -> {
                DropsDisplay.start(player);
                return true;
            }

            case 73128 -> {
                player.getPresetManager().open();
                return true;
            }

            case 73133 -> {
                DailyTaskManager.openEasyList(player);
                return true;
            }

            case 73138 -> {
                //TODO
                return true;
            }
        }
        return false;
    }

    public static void refreshInfoTab(final Player player) {
        InfoTab.INFO_TAB.forEach((childId, lineInfo) -> {
            player.getPacketSender().sendString(childId, lineInfo.fetchLineData(player));
        });
    }

    public static String fetchUpTime() {
        final long upTime = System.currentTimeMillis() - GameServer.startTime;
        return String.format("%d hrs, %d mins",
            TimeUnit.MILLISECONDS.toHours(upTime),
            TimeUnit.MILLISECONDS.toMinutes(upTime) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(upTime)));
    }

}
