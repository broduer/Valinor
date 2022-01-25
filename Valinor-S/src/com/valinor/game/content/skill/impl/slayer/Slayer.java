package com.valinor.game.content.skill.impl.slayer;

import com.valinor.game.content.daily_tasks.DailyTaskManager;
import com.valinor.game.content.daily_tasks.DailyTasks;
import com.valinor.game.content.skill.impl.slayer.master.SlayerMaster;
import com.valinor.game.content.skill.impl.slayer.slayer_task.SlayerCreature;
import com.valinor.game.content.skill.impl.slayer.superior_slayer.SuperiorSlayer;
import com.valinor.game.content.tasks.BottleTasks;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.*;

/**
 * @author PVE
 * @Since juli 20, 2020
 */
public class Slayer {

    private static final Logger logger = LogManager.getLogger(Slayer.class);

    private static List<SlayerMaster> masters = new ArrayList<>();

    public static int TURAEL_ID = 1;
    public static int MAZCHNA_ID = 2;
    public static int VANNAKA_ID = 3;
    public static int CHAELDAR_ID = 4;
    public static int NIEVE_ID = 5;
    public static int DURADEL_ID = 6;
    public static int KRYSTILIA_ID = 7;
    public static int KONAR_QUO_MATEN_ID = 8;

    public static int findIdByMaster(int npc) {
        for (SlayerMaster master : masters) {
            if (master.npcId == npc) {
                return master.id;
            }
        }
        return 0;
    }

    public static SlayerMaster lookup(int id) {
        for (SlayerMaster master : masters) {
            if (master.id == id) {
                return master;
            }
        }
        return null;
    }

    public void loadMasters() {
        long start = System.currentTimeMillis();
        // Load all masters from the json file
        masters = Utils.jsonArrayToList(Paths.get("data", "def", "slayermasters.json"), SlayerMaster[].class);

        if (masters == null) return;
        // Verify integrity, make sure matches are made.
        masters.forEach(master -> master.defs.forEach(taskdef -> {
            if (taskdef != null) {
                if (SlayerCreature.lookup(taskdef.getCreatureUid()) == null) {
                    throw new RuntimeException("could not load slayer task def " + taskdef.getCreatureUid() + " could not resolve uid; " + master.npcId);
                }
            }
        }));
        long elapsed = System.currentTimeMillis() - start;
        logger.info("Loaded slayer masters for {}. It took {}ms.", "./data/def/slayermasters.json", elapsed);
    }

    public static boolean creatureMatches(Player player, int id) {
        SlayerCreature task = SlayerCreature.lookup(player.getAttribOr(AttributeKey.SLAYER_TASK_ID, 0));
        return task != null && task.matches(id);
    }

    public static String taskName(int id) {
        return SlayerCreature.lookup(id) != null ? Utils.formatEnum(SlayerCreature.lookup(id).name()) : "None";
    }

    public static void displayCurrentAssignment(Player player) {
        String name = taskName(player.getAttribOr(AttributeKey.SLAYER_TASK_ID, 0));
        int num = player.getAttribOr(AttributeKey.SLAYER_TASK_AMT, 0);

        if (num == 0) {
            player.getPacketSender().sendString(63208, "None");
        } else {
            player.getPacketSender().sendString(63208, "" + num + " x " + name);
        }
    }

    public static void cancelTask(Player player, boolean adminCancel) {
        if (adminCancel) {
            player.putAttrib(AttributeKey.SLAYER_TASK_ID, 0);
            player.putAttrib(AttributeKey.SLAYER_TASK_AMT, 0);
            player.getPacketSender().sendString(SLAYER_TASK.childId, QuestTab.InfoTab.INFO_TAB.get(SLAYER_TASK.childId).fetchLineData(player));
            return;
        }

        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.OPTION, "Would you like to reset your task?", "Yes.", "No.");
                setPhase(0);
            }

            @Override
            protected void select(int option) {
                if (isPhase(0)) {
                    if (option == 1) {
                        int pts = player.getAttribOr(AttributeKey.SLAYER_REWARD_POINTS, 0);
                        int required = 10;

                        if (10 > pts) {
                            player.message("You need " + required + " points to cancel your task.");
                        } else {
                            player.putAttrib(AttributeKey.SLAYER_TASK_ID, 0);
                            player.putAttrib(AttributeKey.SLAYER_TASK_AMT, 0);
                            player.putAttrib(AttributeKey.SLAYER_TASK_SPREE, 0);
                            player.getPacketSender().sendString(TASK_STREAK.childId, QuestTab.InfoTab.INFO_TAB.get(TASK_STREAK.childId).fetchLineData(player));
                            player.getPacketSender().sendString(SLAYER_TASK.childId, QuestTab.InfoTab.INFO_TAB.get(SLAYER_TASK.childId).fetchLineData(player));
                            player.putAttrib(AttributeKey.SLAYER_REWARD_POINTS, pts - required);
                            player.getPacketSender().sendString(SLAYER_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(SLAYER_POINTS.childId).fetchLineData(player));
                            Slayer.displayCurrentAssignment(player);
                            player.message("You have successfully cancelled your task.");
                        }
                    }
                    stop();
                }
            }
        });
    }

    public static void reward(Player killer, Npc npc) {
        if (killer.slayerTaskAmount() > 0) {

            // Check our task. Decrease. Reward. leggo
            var task = killer.<Integer>getAttribOr(AttributeKey.SLAYER_TASK_ID, 0);
            var amt = killer.<Integer>getAttribOr(AttributeKey.SLAYER_TASK_AMT, 0);
            var inWilderness = WildernessArea.inWilderness(npc.tile());
            var bonusPoints = killer.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.BONUS_SLAYER_POINTS);
            var master = Math.max(1, killer.<Integer>getAttribOr(AttributeKey.SLAYER_MASTER, 0));

            if (task > 0) {
                // Resolve taskdef
                SlayerCreature taskdef = SlayerCreature.lookup(task);
                if (taskdef != null && taskdef.matches(npc.id())) {

                    if (master == Slayer.KRYSTILIA_ID && !inWilderness) {
                        killer.message("<col=FF0000>You must kill your slayer assignment within the wilderness to receive experience!");
                        return;
                    }

                    //Making sure that we have a fallback exp drop
                    killer.skills().addXp(Skills.SLAYER, npc.combatInfo().slayerxp != 0 ? npc.combatInfo().slayerxp : npc.maxHp());
                    killer.putAttrib(AttributeKey.SLAYER_TASK_AMT, amt - 1);
                    amt -= 1;

                    //Update quest tab
                    killer.getPacketSender().sendString(SLAYER_TASK.childId, QuestTab.InfoTab.INFO_TAB.get(SLAYER_TASK.childId).fetchLineData(killer));

                    // Finished?
                    if (amt <= 0) {
                        // Give points
                        var spree = killer.<Integer>getAttribOr(AttributeKey.SLAYER_TASK_SPREE, 0) + 1;
                        if (master == Slayer.TURAEL_ID) {
                            killer.message("<col=7F00FF>You have completed your task; return to a Slayer master.");
                        } else {
                            double ptsget;
                            if (spree % 50 == 0) {
                                if (master == Slayer.MAZCHNA_ID) {
                                    ptsget = 15;
                                } else if (master == Slayer.VANNAKA_ID) {
                                    ptsget = 60;
                                } else if (master == Slayer.CHAELDAR_ID) {
                                    ptsget = 150;
                                } else if (master == Slayer.NIEVE_ID) {
                                    ptsget = 180;
                                } else if (master == Slayer.DURADEL_ID) {
                                    ptsget = 225;
                                } else if (master == Slayer.KONAR_QUO_MATEN_ID) {
                                    ptsget = 270;
                                } else if (master == Slayer.KRYSTILIA_ID) {
                                    ptsget = 375;
                                } else {
                                    ptsget = 0;
                                }
                            } else if (spree % 10 == 0) {
                                if (master == Slayer.MAZCHNA_ID) {
                                    ptsget = 5;
                                } else if (master == Slayer.VANNAKA_ID) {
                                    ptsget = 20;
                                } else if (master == Slayer.CHAELDAR_ID) {
                                    ptsget = 50;
                                } else if (master == Slayer.NIEVE_ID) {
                                    ptsget = 60;
                                } else if (master == Slayer.DURADEL_ID) {
                                    ptsget = 75;
                                } else if (master == Slayer.KONAR_QUO_MATEN_ID) {
                                    ptsget = 90;
                                } else if (master == Slayer.KRYSTILIA_ID) {
                                    ptsget = 125;
                                } else {
                                    ptsget = 0;
                                }
                            } else {
                                if (master == Slayer.MAZCHNA_ID) {
                                    ptsget = 10;
                                } else if (master == Slayer.VANNAKA_ID) {
                                    ptsget = 15;
                                } else if (master == Slayer.CHAELDAR_ID) {
                                    ptsget = 20;
                                } else if (master == Slayer.NIEVE_ID) {
                                    ptsget = 30;
                                } else if (master == Slayer.DURADEL_ID) {
                                    ptsget = 35;
                                } else if (master == Slayer.KONAR_QUO_MATEN_ID) {
                                    ptsget = 35;
                                } else if (master == Slayer.KRYSTILIA_ID) {
                                    ptsget = 45;
                                } else {
                                    ptsget = 0;
                                }
                            }

                            if (bonusPoints) {
                                ptsget += 15;
                            }

                            if (ptsget > 0) {
                                killer.message("<col=7F00FF>You've completed " + spree + " tasks in a row and received " + (int) ptsget + " points; return to a Slayer Master.");
                                killer.putAttrib(AttributeKey.SLAYER_REWARD_POINTS, Math.min(65535, (int) killer.getAttribOr(AttributeKey.SLAYER_REWARD_POINTS, 0) + (int) ptsget));
                                killer.getPacketSender().sendString(SLAYER_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(SLAYER_POINTS.childId).fetchLineData(killer));
                            }

                            //TODO achievements here
                            killer.getTaskBottleManager().increase(BottleTasks.COMPLETE_SLAYER_TASKS);
                            DailyTaskManager.increase(DailyTasks.SLAYER, killer);
                            killer.putAttrib(AttributeKey.SLAYER_MASTER, 0);
                        }

                        killer.putAttrib(AttributeKey.SLAYER_TASK_SPREE, spree);
                        killer.putAttrib(AttributeKey.COMPLETED_SLAYER_TASKS, (int) killer.getAttribOr(AttributeKey.COMPLETED_SLAYER_TASKS, 0) + 1);
                        killer.getPacketSender().sendString(SLAYER_TASKS_COMPLETED.childId, QuestTab.InfoTab.INFO_TAB.get(SLAYER_TASKS_COMPLETED.childId).fetchLineData(killer));
                    } else {
                        // Chance to spawn a superior slayer one if unlocked.
                        SuperiorSlayer.trySpawn(killer, taskdef, npc);
                    }
                }
            }
        }
    }

    public static String tipFor(SlayerCreature task) {
        switch (task.uid) {
            case 2:
                return "Goblins can be found at the Goblin Village, Stronghold of Security, and Lumbridge.";
            case 3:
                return "Rats can be found inside the Taverley and Edgeville dungeon, and around Lumbridge.";
            case 4:
                return "Spiders can found inside the Taverley and Edgeville dungeon, and are commonly found " +
                    "around Lumbridge, Varrock, and Falador. Antipoison is recommend (only if fighting the" +
                    "poisonous variants)";
            case 5:
                return "Birds are commonly found at the Lumbridge or Falador chicken coops.";
            case 6:
                return "Cows can be slaughtered on the Lumbridge or Falador grazing land.";
            case 7:
                return "Scorpions can be slayed inside the Dwarven mines or around the Karamja Volcano.";
            case 8:
                return "Bats can be killed inside the Taverley dungeon or outside of the Mage Arena. Be" +
                    " aware of PKers outside the Mage Arena though, as that is a very popular PVP area.";
            case 9:
                return "Wolves can be killed on the White Wolf Mountain or at the Wilderness Agility" +
                    "course. Be aware of PVPers as this is a popular PVP area.";
            case 10:
                return "Zombies can be slayed inside the Edgeville Dungeon. ";
            case 11:
                return "Skeletons can be found inside the Edgeville, Taverley, or Waterfall dungeon.";
            case 12:
                return "Ghosts can be killed inside the Taverley dungeon.";
            case 13:
                return "Bears can be found around the Ardougne mines or low-to-mid level Wilderness." +
                    " (Careful for PK'ers!)";
            case 14:
                return "Hill Giants can be found inside the Edgeville and Taverley dungeon, or northeast" +
                    "of the Chaos Temple inside the Wilderness.";
            case 15:
                return "Ice Giants can be found inside the Asgarnian Ice Caves or by the level 44 Obelisk" +
                    "inside the wilderness. Beware of PKers though, as this is a very popular PVP area.";
            case 16:
                return "Fire Giants can be found inside the Stronghold Slayer, Brimhaven, and Waterfall dungeon.";
            case 19:
                return "Ice Warriors can be found inside the Asgarnian Ice Caves or by the level 44 Obelisk" +
                    "inside the wilderness. Beware of PKers though, as this is a very popular PVP area.";
            case 21:
                return "Hobgoblins can be found inside the Asgarnian Ice and Godwars dungeon.";
            case 24:
                return "Green Dragons can be found North of Goblin Village in 13 Wilderness, between" +
                    "the Ruins and Graveyard of Shadows in level 24 Wilderness, west of the Bone Yard," +
                    "between the Lava Maze and Hobgoblin mine, and north east of the Chaos Temple. Beware" +
                    "of the PKers here though, as they're extremely popular PVP areas. A Anti-dragon shield," +
                    " Dragonfire shield or Antifire potion and a good Stab (Abyssal whip or Dragon scimitar " +
                    "works too!) or Ranged weapon is recommended.";
            case 25:
                return "Blue Dragons can be found inside the Taverley Dungeon. A Anti-dragon shield," +
                    " Dragonfire shield or Antifire potion and a good Stab (Abyssal whip or Dragon scimitar " +
                    "works too!) or Ranged weapon is recommended.";
            case 27:
                return "Black Dragons can be found inside the Taverley, or Brimhaven dungeon. A Anti-dragon shield," +
                    " Dragonfire shield or Antifire potion and a good Stab (Abyssal whip or Dragon scimitar " +
                    "works too!) or Ranged weapon is recommended.";
            case 28:
                return "Lesser demons can be found scattered throughout the wild or near a dungeon close to mage bank.";
            case 29:
                return "Greater Demons can be found inside the Strongold Slayer Cave, at the Brimhaven Dungeon," +
                    " or inside the wilderness at the Demonic Ruins. Be aware of PVPers at the Demonic Ruins, though, " +
                    "as it is a very popular PVP area.";
            case 30:
                return "Black Demons can be found at the Taverley, Edgeville, or Brimhaven Dungeon. Good gear and/or" +
                    " protection from melee, with several prayer potions is highly recommended.";
            case 31:
                return "Hell Hounds can be killed inside the Stronghold Slayer cave, or at the Taverley Dungeon.";
            case 35:
                return "Dagannoths can be killed at the Waterbirth Island.";
            case 36:
                return "Turoths can be killed at the Fremennik Slayer Dungeon. Broad arrows/bolts with a strong " +
                    "range weapon is recommended.";
            case 37:
                return "Cave Crawlers can be found at the Fremennik Slayer Dungeon. Antipoision and a weapon using" +
                    " slash attacks are extremely recommended.";
            case 39:
                return "Crawling Hands are located at the Slayer Tower.";
            case 41:
                return "Aberrant spectres are found at the Stronghold Slayer Cave, or at the Slayer Tower. A Nose peg" +
                    "or Slayer helmet is extremely recommended.";
            case 42:
                return "Abyssal Demons can be found at the Stronghold Slayer cave, or at the Slayer Tower. Good gear " +
                    "and weapon is highly recommended when fighting these monsters.";
            case 44:
                return "Cockatrices can be found at the Fremennik Slayer Dungeon. A mirror shield is highly recommended.";
            case 45:
                return "Kurasks can be found at the Fremennik Slayer Dungeon. A leaf-bladed weapon or Broad arrows/bolts are" +
                    "highly recommend when fighting these monsters.";
            case 46:
                return "Gargoyles can be found at the Stronghold Slayer Cave or the Slayer Tower. A Rock Hammer is required" +
                    "to kill these monsters.";
            case 47:
                return "Pyrefiends can be found at the Fremennik Slayer, or Smoke Dungeon. Magic-resistant armour is recommended.";
            case 48:
                return "Bloodvelds can be killed at the Stronghold Slayer Cave or Slayer Tower. Dragonhide armour and a decent" +
                    "weapon is recommend when fighting these monsters.";
            case 49:
                return "Dust Devils can be killed at the Smoke Dungeon.";
            case 50:
                return "Jellies can be found at the Fremennik Slayer Dungeon. Magic-resistant armour is recommended.";
            case 51:
                return "Rockslugs can be found at the Fremennik Slayer Dungeon. A bag of salt is required" +
                    "to finish these monsters.";
            case 52:
                return "Nechryaels can be found at the Stronghold Slayer Cave or Slayer Tower.";
            case 58:
                return "Bronze Dragons can be found at the Stronghold Slayer Cave or Brimhaven Dungeon. A Anti-dragon shield," +
                    " Dragonfire shield or Antifire potion and a good Stab (Abyssal whip or Dragon scimitar " +
                    "works too!) or Fire Bold (or better) is recommended.";
            case 59:
                return "Iron Dragons can be found at the Stronghold Slayer Cave or Brimhaven Dungeon. A Anti-dragon shield," +
                    " Dragonfire shield or Antifire potion and a good Stab (Abyssal whip or Dragon scimitar " +
                    "works too!) or Fire Bold (or better) is recommended.";
            case 60:
                return "Steel Dragons can be found at the Stronghold Slayer Cave or Brimhaven Dungeon. A Anti-dragon shield," +
                    " Dragonfire shield or Antifire potion and a good Stab (Abyssal whip or Dragon scimitar " +
                    "works too!) or Fire Bold (or better) is recommended.";
            case 66:
                return "Dark Beasts can be found inside the Mourner Tunnels. Good armour/weapon is recommended when fighting" +
                    "these monsters.";
            case 72:
                return "Skeletal Wyverns can be found in the Asgarnian Ice Dungeon. Protect from Range, Mirror Shields, and good" +
                    "armour/weapon is suggested when fighting these monsters.";
            case 75:
                return "Icefiends can be found inside the Godwars Dungeon.";
            case 76:
                return "Minotaurs can be found inside the Godwars Dungeon or on the 1st level of the Stronghold of Security.";
            case 77:
                return "Flesh Crawlers can be found on the 2nd level of the Stronghold of Security.";
            case 79:
                return "Ankous can be found in the Stronghold Slayer Cave, on the 4th level of the Stronghold of Security, and at" +
                    " the Forgotten Cemetery. Beware of PKers at the Forgotten Cemetary, though. As it's inside the wilderness.";
            case 80:
                return "Cave Horrors can be found at Mos Le'Harmless Caves.";
            case 91:
                return "Magic axes can be found in a hut east of mage bank. A lockpick is required to get in.";
            case 92:
                return "Cave Krakens can be awaken at the Stronghold Slayer Cave. A Magic weapon is preferred, as Ranged his are heavily" +
                    "reduced. You're unable to use Melee on these monsters.";
            case 95:
                return "Smoke Devils can be found at the Stronghold Slayer Cave. A Slayer helm or facemask is suggested when" +
                    "fighting these monsters.";
            case 96:
                return "Tzhaar monsters can be found inside the Tzhaar Cave.";
            case 98:
                return "A boss task in W2 counts any major wilderness boss you may encounter. Vet'ion, Chaos Fanatic, Callisto and so on are all valid.";
            case 104:
                return "Lava dragons are a strong breed located north-east of black chinchompas. A form of anti-dragon shield is strongly recommended.";
            case 106:
                return "Fossil Island Wyverns aren't located on Fossil Island in this world. They're near an altar close to the KBD entrance.";
            default:
                return "You don't appear to have a task!";
        }
    }

}
