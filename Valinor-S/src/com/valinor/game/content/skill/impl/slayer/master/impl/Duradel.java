package com.valinor.game.content.skill.impl.slayer.master.impl;

import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.content.skill.impl.slayer.master.SlayerMaster;
import com.valinor.game.content.skill.impl.slayer.master.dialogues.DuradelD;
import com.valinor.game.content.skill.impl.slayer.slayer_task.SlayerCreature;
import com.valinor.game.content.skill.impl.slayer.slayer_task.SlayerTaskDef;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.dialogue.Expression;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.SLAYER_TASK;
import static com.valinor.util.NpcIdentifiers.DURADEL;
import static com.valinor.util.NpcIdentifiers.MAZCHNA;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 18, 2021
 */
public class Duradel extends Interaction {

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        if(option == 1) {
            if (npc.id() == DURADEL) {
                if (player.skills().combatLevel() < 100) {
                    player.message("You need a combat level of 100 to talk to Duradel.");
                    return true;
                }
                if (player.skills().level(Skills.SLAYER) < 50) {
                    player.message("You need a slayer level of 50 to talk to Duradel.");
                    return true;
                }
                player.getDialogueManager().start(new DuradelD());
                return true;
            }
        }
        if(option == 2) {
            if (npc.id() == DURADEL) {
                giveTask(player);
                return true;
            }
        }
        if(option == 3) {
            if (npc.id() == DURADEL) {
                World.getWorld().shop(14).open(player);
                return true;
            }
        }
        if(option == 4) {
            if (npc.id() == DURADEL) {
                player.getSlayerRewards().open();
                return true;
            }
        }
        return false;
    }

    private static void assignTask(Player player) {
        SlayerMaster master = Slayer.lookup(Slayer.DURADEL_ID);
        if(master == null) {
            System.out.println("no such slayer master available.");
            return;
        }

        SlayerTaskDef def = master.randomTask(player);
        if(def == null) {
            System.out.println("no task available.");
            return;
        }

        player.putAttrib(AttributeKey.SLAYER_MASTER, Slayer.DURADEL_ID);
        player.putAttrib(AttributeKey.SLAYER_TASK_ID, def.getCreatureUid());
        int task_amt = player.getSlayerRewards().slayerTaskAmount(player, def);
        player.putAttrib(AttributeKey.SLAYER_TASK_AMT, task_amt);

        player.getPacketSender().sendString(SLAYER_TASK.childId, QuestTab.InfoTab.INFO_TAB.get(SLAYER_TASK.childId).fetchLineData(player));
    }

    public static void giveTask(Player player) {
        // Time to check our task state. Can we hand out?
        int numleft = player.slayerTaskAmount();
        if (numleft > 0) {
            DialogueManager.npcChat(player, Expression.H, DURADEL, "You're still hunting " + Slayer.taskName(player.slayerTaskId()) + "; you have " + numleft + " to go. Come", "back when you've finished your task.");
            return;
        }

        // Give them a task.
        assignTask(player);

        SlayerCreature task = SlayerCreature.lookup(player.slayerTaskId());
        int num = player.slayerTaskAmount();
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                String n = task == null ? "NULL" : Slayer.taskName(task.uid);
                String taskName = n == null ? "NULL" : n;
                send(DialogueType.NPC_STATEMENT, DURADEL, Expression.ANXIOUS, "Excellent, you're doing great. Your new task is to kill", ""+num+" "+taskName+".");setPhase(0);
            }

            @Override
            protected void next() {
                if(isPhase(0)) {
                    send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Got any tips for me?", "Okay, great!");
                    setPhase(1);
                } else if(isPhase(2)) {
                    send(DialogueType.NPC_STATEMENT, DURADEL, Expression.ANXIOUS, Slayer.tipFor(task));
                    setPhase(3);
                } else if(isPhase(3)) {
                    send(DialogueType.PLAYER_STATEMENT, Expression.HAPPY, "Great, thanks!");
                    setPhase(4);
                } else if(isPhase(4)) {
                    stop();
                }
            }

            @Override
            protected void select(int option) {
                if(isPhase(1)) {
                    if(option == 1) {
                        send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_ONE, "Got any tips for me?");
                        setPhase(2);
                    } else if(option == 2) {
                        send(DialogueType.PLAYER_STATEMENT, Expression.HAPPY, "Okay, great!");
                        setPhase(4);
                    }
                }
            }
        });
    }
}
