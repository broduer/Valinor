package com.valinor.game.world.entity.mob.player;

import com.valinor.GameServer;
import com.valinor.game.content.DropsDisplay;
import com.valinor.game.content.achievements.AchievementWidget;
import com.valinor.game.content.areas.wilderness.content.boss_event.WildernessBossEvent;
import com.valinor.game.content.collection_logs.LogType;
import com.valinor.game.content.items_kept_on_death.ItemsKeptOnDeath;
import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
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

        TIME(12658) {
            @Override
            public String fetchLineData(Player player) {
                return "Time: <col=ffffff> " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM hh:mm a"));
            }
        },

        UPTIME(12659) {
            @Override
            public String fetchLineData(Player player) {
                return "Uptime: <col=ffffff>" + QuestTab.fetchUpTime();
            }
        },

        WORLD_BOSS_SPAWN(12660) {
            @Override
            public String fetchLineData(Player player) {
                return "Next World Boss: <col=ffffff>" + WildernessBossEvent.getINSTANCE().timeTill(false);
            }
        },

        GAME_MODE(12666) {
            @Override
            public String fetchLineData(Player player) {
                return "Game Mode: <col=ffffff>" + Utils.gameModeToString(player);
            }
        },

        PLAY_TIME(12667) {
            @Override
            public String fetchLineData(Player player) {
                return "Play Time: <col=ffffff>" + Utils.convertSecondsToShortDuration((long) player.<Integer>getAttribOr(AttributeKey.GAME_TIME, 0), false);
            }
        },

        REGISTERED_ON(12668) {
            @Override
            public String fetchLineData(Player player) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
                return "Registered On: <col=ffffff>" + simpleDateFormat.format(player.getCreationDate());
            }
        },

        MEMBER_RANK(12669) {
            @Override
            public String fetchLineData(Player player) {
                return "Member Rank: <col=ffffff>" + player.getMemberRights().getName();
            }
        },

        TOTAL_DONATED(12670) {
            @Override
            public String fetchLineData(Player player) {
                double totalAmountPaid = player.getAttribOr(AttributeKey.TOTAL_PAYMENT_AMOUNT, 0D);
                return "Total Donated: <col=ffffff>" + totalAmountPaid + "0$";
            }
        },

        VOTE_POINTS(12671) {
            @Override
            public String fetchLineData(Player player) {
                int votePoints = player.getAttribOr(AttributeKey.VOTE_POINS, 0);
                return "Vote Points: <col=ffffff>" + votePoints;
            }
        },

        BOSS_POINTS(12672) {
            @Override
            public String fetchLineData(Player player) {
                int bossPoints = player.getAttribOr(AttributeKey.BOSS_POINTS, 0);
                return "Boss Points: <col=ffffff>" + Utils.formatNumber(bossPoints);
            }
        },

        REFERRALS(12673) {
            @Override
            public String fetchLineData(Player player) {
                int referralCount = player.getAttribOr(AttributeKey.REFERRALS_COUNT, 0);
                return "Referrals: <col=ffffff>" + Utils.formatNumber(referralCount);
            }
        },

        PLAYERS_PKING(12676) {
            @Override
            public String fetchLineData(Player player) {
                return "Players in wild: <col=ffffff>" + World.getWorld().getPlayersInWild();
            }
        },

        ELO_RATING(12677) {
            @Override
            public String fetchLineData(Player player) {
                return "Elo Rating: <col=ffffff> 0";
            }
        },

        KILLS(12678) {
            @Override
            public String fetchLineData(Player player) {
                int kills = player.getAttribOr(AttributeKey.PLAYER_KILLS, 0);
                return "Player kills: <col=ffffff>" + Utils.formatNumber(kills);
            }
        },

        DEATHS(12679) {
            @Override
            public String fetchLineData(Player player) {
                int deaths = player.getAttribOr(AttributeKey.PLAYER_DEATHS, 0);
                return "Player deaths: <col=ffffff>" + Utils.formatNumber(deaths);
            }
        },

        KD_RATIO(12680) {
            @Override
            public String fetchLineData(Player player) {
                return "K/D Ratio: <col=ffffff>" + player.getKillDeathRatio();
            }
        },

        TARGETS_KILLED(12681) {
            @Override
            public String fetchLineData(Player player) {
                int kills = player.getAttribOr(AttributeKey.TARGET_KILLS, 0);
                return "Targets Killed: <col=ffffff>" + Utils.formatNumber(kills);
            }
        },

        CURRENT_KILLSTREAK(12682) {
            @Override
            public String fetchLineData(Player player) {
                return "Killstreak: <col=ffffff> 0";
            }
        },

        KILLSTREAK_RECORD(12684) {
            @Override
            public String fetchLineData(Player player) {
                return "Highest killstreak: <col=ffffff> 0";
            }
        },

        TARGET_KILLS(12683) {
            @Override
            public String fetchLineData(Player player) {
                return "Wilderness streak: <col=ffffff> 0";
            }
        },

        TARGET_POINTS(12685) {
            @Override
            public String fetchLineData(Player player) {
                return "Target points: <col=ffffff> 0";
            }
        },

        RISKED_WEALTH(12686) {
            @Override
            public String fetchLineData(Player player) {
                long risked = ItemsKeptOnDeath.getLostItemsValue();
                return "Risked wealth: <col=ffffff>" + Utils.formatNumber(risked) + " BM";
            }
        },

        WILDERNESS_KEY(12687) {
            @Override
            public String fetchLineData(Player player) {
                return "Next Wildy Key: <col=ffffff> 0";
            }
        },

        SLAYER_TASK(12690) {
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

        TASK_STREAK(12691) {
            @Override
            public String fetchLineData(Player player) {
                int num = player.getAttribOr(AttributeKey.SLAYER_TASK_SPREE, 0);
                return "Task Streak: <col=ffffff> " + Utils.formatNumber(num);
            }
        },

        TASKS_COMPLETED(12692) {
            @Override
            public String fetchLineData(Player player) {
                int num = player.getAttribOr(AttributeKey.COMPLETED_SLAYER_TASKS, 0);
                return "Tasks Completed: <col=ffffff> " + Utils.formatNumber(num);
            }
        },

        SLAYER_KEYS_RECEIVED(12693) {
            @Override
            public String fetchLineData(Player player) {
                var keys = player.<Integer>getAttribOr(AttributeKey.SLAYER_KEYS_RECEIVED, 0);
                return "Slayer Keys Received: <col=ffffff>" + Utils.formatNumber(keys);
            }
        },


        SLAYER_POINTS(12694) {
            @Override
            public String fetchLineData(Player player) {
                int rewardPoints = player.getAttribOr(AttributeKey.SLAYER_REWARD_POINTS, 0);
                return "Slayer Points: <col=ffffff>" + Utils.formatNumber(rewardPoints);
            }
        },

        PLAYERS_ONLINE(12700) {
            @Override
            public String fetchLineData(Player player) {
                int count = World.getWorld().getPlayers().size();
                return "Players Online: <col=ffffff>" + count;
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
            case 12652 -> {
                AchievementWidget.openEasyJournal(player);
                return true;
            }

            case 12698 -> {
                boolean locked = player.getAttribOr(AttributeKey.XP_LOCKED, false);
                player.putAttrib(AttributeKey.XP_LOCKED, !locked);

                if (locked) {
                    player.message("Your experience is now <col=65280>unlocked.");
                    player.getPacketSender().sendString(12698, "Exp: (<col=65280>unlocked</col>)");
                } else {
                    player.message("Your experience is now <col=ca0d0d>locked.");
                    player.getPacketSender().sendString(12698, "Exp: (<col=ca0d0d>locked</col>)");
                }
                return true;
            }

            case 12752 -> player.getDialogueManager().start(new Dialogue() {
                @Override
                protected void start(Object... parameters) {
                    send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "View Monster Drops.", "Open Collection Log.", "Open Boss Kill Log.", "Nevermind.");
                    setPhase(0);
                }

                @Override
                protected void select(int option) {
                    if (isPhase(0)) {
                        if (option == 1) {
                            DropsDisplay.start(player);
                            stop();
                        } else if (option == 2) {
                            player.getCollectionLog().open(LogType.BOSSES);
                            stop();
                        } else if (option == 3) {
                            player.getBossKillLog().openLog();
                            stop();
                        } else if (option == 4) {
                            stop();
                        }
                    }
                }
            });

            case 12754 -> {
                QuestTab.refreshInfoTab(player);
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
