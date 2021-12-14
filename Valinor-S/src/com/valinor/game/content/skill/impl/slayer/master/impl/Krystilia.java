package com.valinor.game.content.skill.impl.slayer.master.impl;

import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.content.skill.impl.slayer.master.SlayerMaster;
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
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.SLAYER_TASK;
import static com.valinor.util.NpcIdentifiers.KRYSTILIA;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 18, 2021
 */
public class Krystilia extends Interaction {

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        if(option == 1) {
            if (npc.id() == KRYSTILIA) {
                displayOptions(player);
                return true;
            }
        }
        if(option == 2) {
            if (npc.id() == KRYSTILIA) {
                warn(player);
                return true;
            }
        }
        if(option == 3) {
            if (npc.id() == KRYSTILIA) {
                World.getWorld().shop(14).open(player);
                return true;
            }
        }
        if(option == 4) {
            if (npc.id() == KRYSTILIA) {
                player.getSlayerRewards().open();
                return true;
            }
        }
        return false;
    }

    private void assignTask(Player player) {
        int numleft = player.slayerTaskAmount();

        if (numleft > 0) {
            player.getDialogueManager().start(new Dialogue() {
                @Override
                protected void start(Object... parameters) {
                    send(DialogueType.NPC_STATEMENT, KRYSTILIA, Expression.HAPPY, "You're still hunting " + Slayer.taskName(player.slayerTaskId()) + "; you have " + numleft + " to go. Come", "back when you've finished your task.");
                    setPhase(0);
                }

                @Override
                protected void next() {
                    if (isPhase(0)) {
                        send(DialogueType.NPC_STATEMENT, KRYSTILIA, Expression.HAPPY, "Come back to me when you've completed your task", "for a new one.");
                        setPhase(1);
                    } else if (isPhase(1)) {
                        displayTips(player);
                    }
                }
            });
            return;
        }

        SlayerMaster master = Slayer.lookup(Slayer.KRYSTILIA_ID);
        if(master == null) {
            System.out.println("no such slayer master available.");
            return;
        }

        SlayerTaskDef def = master.randomTask(player);
        if(def == null) {
            System.out.println("no task available.");
            return;
        }

        player.putAttrib(AttributeKey.WILDERNESS_SLAYER_TASK_ACTIVE, true);
        player.putAttrib(AttributeKey.SLAYER_MASTER, Slayer.KRYSTILIA_ID);
        player.putAttrib(AttributeKey.SLAYER_TASK_ID, def.getCreatureUid());
        int task_amt = player.getSlayerRewards().slayerTaskAmount(player, def);
        player.putAttrib(AttributeKey.SLAYER_TASK_AMT, task_amt);

        player.getPacketSender().sendString(SLAYER_TASK.childId, QuestTab.InfoTab.INFO_TAB.get(SLAYER_TASK.childId).fetchLineData(player));

        SlayerCreature task = SlayerCreature.lookup(player.slayerTaskId());
        int num = player.slayerTaskAmount();
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.NPC_STATEMENT, KRYSTILIA, Expression.HAPPY, "Ok, great. Your new task is to kill " + num + " " + Slayer.taskName(task.uid) + ".");
                setPhase(0);
            }

            @Override
            protected void next() {
                if (isPhase(0)) {
                    stop();
                    displayTips(player);
                }
            }
        });
    }

    private static void displayTips(Player player) {
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Got any tips for me?", "Ok, thanks!");
                setPhase(0);
            }

            @Override
            protected void next() {
                if (isPhase(1)) {
                    stop();
                }
            }

            @Override
            protected void select(int option) {
                if (isPhase(0)) {
                    if (option == 1) {
                        String tip = Slayer.tipFor(SlayerCreature.lookup(player.slayerTaskId()));
                        send(DialogueType.NPC_STATEMENT, KRYSTILIA, Expression.HAPPY, tip);
                        setPhase(1);
                    } else if (option == 2) {
                        send(DialogueType.PLAYER_STATEMENT, Expression.HAPPY, "Ok, thanks!");
                        setPhase(1);
                    }
                }
            }
        });
    }

    private void displayOptions(Player player) {
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "What is 'Wilderness Slayer'?", "I'd like a task, please.", "Nothing");
                setPhase(0);
            }

            @Override
            protected void select(int option) {
                if (isPhase(0)) {
                    if (option == 1) {
                        stop();
                        describeWildernessSlayer(player);
                    } else if (option == 2) {
                        stop();
                        warn(player);
                    } else if (option == 3) {
                        stop();
                    }
                }
            }
        });
    }

    private void describeWildernessSlayer(Player player) {
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.NPC_STATEMENT, KRYSTILIA, Expression.HAPPY, "Wilderness Slayer is an activity that can be carried out", "by slaying selective monsters in the wilderness. Every task", "you complete will be rewarded with 25 slayer points and");
                setPhase(0);
            }

            @Override
            protected void next() {
                if (isPhase(0)) {
                    send(DialogueType.NPC_STATEMENT, KRYSTILIA, Expression.HAPPY, "these points can be exchanged within a shop of mine.", "If you are unable to complete your slayer task or simply", "wish to change it you can do so by speaking to me.", "However, there is a fee to this service.");
                    setPhase(1);
                } else if (isPhase(1)) {
                    send(DialogueType.NPC_STATEMENT, KRYSTILIA, Expression.HAPPY, "The point you earn from slayer tasks can be used", "for various goods in my shop. Lastly, remember, ALL of these", "tasks are in the wilderness so beware!");
                    setPhase(2);
                } else if (isPhase(2)) {
                    stop();
                }
            }
        });
    }

    private void warn(Player player) {
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.NPC_STATEMENT, KRYSTILIA, Expression.HAPPY, "Before I assign you anything, I want to make something", "clear. My tasks have to be done in the wilderness.", "Only kills inside the wilderness will count.");
                setPhase(0);
            }

            @Override
            protected void next() {
                if (isPhase(0)) {
                    send(DialogueType.NPC_STATEMENT, KRYSTILIA, Expression.HAPPY, "If you don't like my tasks, you can come back", "to me and cancel your task for a fee.");
                    setPhase(1);
                } else if (isPhase(1)) {
                    send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Yes, I understand I must kill it in the Wilderness.", "No thanks, I don't want tasks from you.");
                    setPhase(2);
                } else if (isPhase(3)) {
                    stop();
                    assignTask(player);
                }
            }

            @Override
            protected void select(int option) {
                if (isPhase(2)) {
                    if (option == 1) {
                        send(DialogueType.PLAYER_STATEMENT, Expression.HAPPY, "Yes, I understand I must kill it in the Wilderness.");
                        setPhase(3);
                    } else if (option == 2) {
                        stop();
                    }
                }
            }
        });
    }
}
