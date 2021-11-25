package com.valinor.game.content.skill.impl.slayer.master.impl;

import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.content.skill.impl.slayer.master.SlayerMaster;
import com.valinor.game.content.skill.impl.slayer.master.dialogues.TuraelD;
import com.valinor.game.content.skill.impl.slayer.slayer_task.SlayerCreature;
import com.valinor.game.content.skill.impl.slayer.slayer_task.SlayerTaskDef;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.dialogue.Expression;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.net.packet.interaction.PacketInteraction;

import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.SLAYER_TASK;
import static com.valinor.util.NpcIdentifiers.TURAEL;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 18, 2021
 */
public class Turael extends PacketInteraction {

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        if(option == 1) {
            if (npc.id() == TURAEL) {
                player.getDialogueManager().start(new TuraelD());
                return true;
            }
        }
        if(option == 2) {
            if (npc.id() == TURAEL) {
                assignTask(player);
                return true;
            }
        }
        if(option == 3) {
            if (npc.id() == TURAEL) {
                World.getWorld().shop(14).open(player);
                return true;
            }
        }
        if(option == 4) {
            if (npc.id() == TURAEL) {
                player.getSlayerRewards().open();
                return true;
            }
        }
        return false;
    }

    public static void assignTask(Player player) {
        SlayerMaster master = Slayer.master(Slayer.TURAEL_ID);
        if(master == null) {
            System.out.println("no such slayer master available.");
            return;
        }

        SlayerTaskDef def = master.randomTask(player);
        if(def == null) {
            System.out.println("no task available.");
            return;
        }

        player.putAttrib(AttributeKey.SLAYER_MASTER, Slayer.TURAEL_ID);
        player.putAttrib(AttributeKey.SLAYER_TASK_ID, def.getCreatureUid());
        int task_amt = player.getSlayerRewards().slayerTaskAmount(player, def);
        player.putAttrib(AttributeKey.SLAYER_TASK_AMT, task_amt);

        player.getPacketSender().sendString(SLAYER_TASK.childId, QuestTab.InfoTab.INFO_TAB.get(SLAYER_TASK.childId).fetchLineData(player));
    }

    public static void giveTask(Player player) {
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.PLAYER_STATEMENT, Expression.HAPPY, "I need another assignment.");
                setPhase(22);
            }

            @Override
            protected void next() {
                if(isPhase(15)) {
                    stop();
                } else if (isPhase(22)) {
                    // Tell the player if we can go Mazchna-mode
                    if (player.skills().combatLevel() >= 20) {
                        send(DialogueType.NPC_STATEMENT, TURAEL, Expression.ANXIOUS, "You're actually very strong, are you sure you don't", "want Mazchna in Morytania to assign you a task?");
                        setPhase(23);
                    } else {
                        setPhase(24);
                    }
                } else if (isPhase(23)) {
                    send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "No that's okay, I'll take a task from you.", "Oh okay then, I'll go talk to Mazchna.");
                    setPhase(25);
                } else if (isPhase(24)) {
                    // Time to check our task state. Can we hand out?
                    int numleft = player.slayerTaskAmount();
                    if (numleft > 0) {
                        send(DialogueType.NPC_STATEMENT, TURAEL, Expression.H, "You're still hunting " + Slayer.taskName(player.slayerTaskId()) + "; you have " + numleft + " to go. Come", "back when you've finished your task.");
                        setPhase(15);
                        return;
                    }

                    // Give them a task.
                    assignTask(player);

                    SlayerCreature task = SlayerCreature.lookup(player.slayerTaskId());
                    int num = player.slayerTaskAmount();
                    send(DialogueType.NPC_STATEMENT, TURAEL, Expression.ANXIOUS, "Excellent, you're doing great. Your new task is to kill " + num + " " + Slayer.taskName(task.uid) + ".");
                    setPhase(26);
                } else if (isPhase(26)) {
                    send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Got any tips for me?", "Okay, great!");
                    setPhase(27);
                } else if (isPhase(28)) {
                    SlayerCreature task = SlayerCreature.lookup(player.slayerTaskId());
                    send(DialogueType.NPC_STATEMENT, TURAEL, Expression.HAPPY, Slayer.tipFor(task));
                    setPhase(29);
                } else if (isPhase(29)) {
                    send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_ONE, "Great, thanks!");
                    setPhase(15);
                }
            }

            @Override
            protected void select(int option) {
                if (isPhase(25)) {
                    if(option == 1) {
                        send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_THREE, "No that's okay, I'll take a task from you.");
                        setPhase(24);
                    } else if (option == 2) {
                        send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_THREE, "Oh okay then, I'll go talk to Mazchna.");
                        setPhase(15);
                    }
                }
            }
        });
    }
}
