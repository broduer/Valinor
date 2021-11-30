package com.valinor.game.content.skill.impl.slayer.content;

import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.content.skill.impl.slayer.SlayerConstants;
import com.valinor.game.content.skill.impl.slayer.slayer_task.SlayerCreature;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.dialogue.Expression;

import static com.valinor.game.world.entity.AttributeKey.SLAYER_TASK_ID;
import static com.valinor.util.NpcIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 30, 2021
 */
public class EnchantedGemD extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        send(DialogueType.PLAYER_STATEMENT, Expression.DEFAULT, "How do I turn this stupid thing o-");
        boolean hasTask = player.slayerTaskAmount() > 0;
        if (hasTask) {
            setPhase(0);
        } else {
            send(DialogueType.STATEMENT, "You need something new to hunt.");
            setPhase(20);
        }
    }

    @Override
    protected void next() {
        String name = Slayer.taskName(player.getAttribOr(SLAYER_TASK_ID, 0));
        var master = Math.max(1, player.<Integer>getAttribOr(AttributeKey.SLAYER_MASTER, 0));
        if (getPhase() == 0) {
            if (master == Slayer.TURAEL_ID || master == Slayer.KRYSTILIA_ID) {
                send(DialogueType.NPC_STATEMENT, TURAEL, Expression.DEFAULT, "'Ello, and what are you after, then?");
                setPhase(1);
            } else if (master == Slayer.MAZCHNA_ID) {
                send(DialogueType.NPC_STATEMENT, MAZCHNA, Expression.DEFAULT, "Hello brave warrior. What would you like?");
                setPhase(5);
            } else if (master == Slayer.VANNAKA_ID) {
                send(DialogueType.NPC_STATEMENT, VANNAKA, Expression.DEFAULT, "Hmm... What do you want?");
                setPhase(8);
            } else if (master == Slayer.CHAELDAR_ID) {
                send(DialogueType.NPC_STATEMENT, CHAELDAR, Expression.DEFAULT, "Hello human.", "What brings you around these parts?");
                setPhase(11);
            } else if (master == Slayer.NIEVE_ID) {
                send(DialogueType.NPC_STATEMENT, NIEVE, Expression.DEFAULT, "Hello there Adventurer.", "I am Nieve one of the more elite Slayer Masters.", "What do you need?");
                setPhase(14);
            } else if (master == Slayer.DURADEL_ID || master == Slayer.KONAR_QUO_MATEN_ID) {
                send(DialogueType.NPC_STATEMENT, DURADEL, Expression.DEFAULT, "What do you want?");
                setPhase(17);
            } else if (master == 0) {
                send(DialogueType.STATEMENT, "You do currently not have a slayer task.");
                setPhase(20);
            }
        } else {
            if (getPhase() == 1) {
                send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "I have forgotten my assignment.", "Teleport to task.", "Nevermind.");
                setPhase(2);
            } else {
                if (getPhase() == 3) {
                    send(DialogueType.NPC_STATEMENT, TURAEL, Expression.DEFAULT, "Your task was to kill " + player.slayerTaskAmount() +  " <col=0000ff>" + name + "s</col>.");
                    setPhase(4);
                } else {
                    if (getPhase() == 4) {
                        stop();
                    } else {
                        if (getPhase() == 5) {
                            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "I have forgotten my assignment.", "Teleport to task.", "Nevermind.");
                            setPhase(6);
                        } else {
                            if (getPhase() == 7) {
                                send(DialogueType.NPC_STATEMENT, MAZCHNA, Expression.DEFAULT, "Your task is to kill " + player.slayerTaskAmount() + " <col=0000ff>" + name + "s</col>.");
                                setPhase(4);
                            } else {
                                if (getPhase() == 8) {
                                    send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "I have forgotten my assignment.", "Teleport to task.", "Nevermind.");
                                    setPhase(9);
                                } else {
                                    if (getPhase() == 10) {
                                        send(DialogueType.NPC_STATEMENT, VANNAKA, Expression.DEFAULT, "Your task is to kill " + player.slayerTaskAmount() + " <col=0000ff>" + name + "s</col>.");
                                        setPhase(4);
                                    } else {
                                        if (getPhase() == 11) {
                                            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "I have forgotten my assignment.", "Teleport to task.", "Nevermind.");
                                            setPhase(12);
                                        } else {
                                            if (getPhase() == 13) {
                                                send(DialogueType.NPC_STATEMENT, CHAELDAR, Expression.DEFAULT, "Your task is to kill " + player.slayerTaskAmount() + " <col=0000ff>" + name + "s</col>.");
                                                setPhase(4);
                                            } else {
                                                if (getPhase() == 14) {
                                                    send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "I have forgotten my assignment.", "Teleport to task.", "Nevermind.");
                                                    setPhase(15);
                                                } else {
                                                    if (getPhase() == 16) {
                                                        send(DialogueType.NPC_STATEMENT, NIEVE, Expression.DEFAULT, "Your task is to kill " + player.slayerTaskAmount() + " <col=0000ff>" + name + "s</col>.");
                                                        setPhase(4);
                                                    } else {
                                                        if (getPhase() == 17) {
                                                            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "I have forgotten my assignment.", "Teleport to task.", "Nevermind.");
                                                            setPhase(18);
                                                        } else {
                                                            if (getPhase() == 19) {
                                                                send(DialogueType.NPC_STATEMENT, DURADEL, Expression.DEFAULT, "Your task is to kill " + player.slayerTaskAmount() + " <col=0000ff>" + name + "s</col>.");
                                                                setPhase(4);
                                                            } else {
                                                                if (getPhase() == 20) {
                                                                    stop();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void select(int index) {
        if (getPhase() == 2) {
            if (index == 1) {
                send(DialogueType.PLAYER_STATEMENT, Expression.DEFAULT, "I have forgotten my assignment.");
                setPhase(3);
            } else if (index == 2) {
                if (!player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.TELEPORT_TO_TASK)) {
                    player.message("You have not unlocked this feature yet.");
                    stop();
                    return;
                }
                SlayerCreature.teleport(player);
                stop();
            } else if (index == 3) {
                send(DialogueType.PLAYER_STATEMENT, Expression.DEFAULT, "Nevermind.");
                setPhase(4);
            }
        } else {
            if (getPhase() == 6) {
                if (index == 1) {
                    send(DialogueType.PLAYER_STATEMENT, Expression.DEFAULT, "I have forgotten my assignment.");
                    setPhase(7);
                } else if (index == 2) {
                    if (!player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.TELEPORT_TO_TASK)) {
                        player.message("You have not unlocked this feature yet.");
                        stop();
                        return;
                    }
                    SlayerCreature.teleport(player);
                    stop();
                } else if (index == 3) {
                    send(DialogueType.PLAYER_STATEMENT, Expression.DEFAULT, "Nevermind.");
                    setPhase(4);
                }
            } else {
                if (getPhase() == 9) {
                    if (index == 1) {
                        send(DialogueType.PLAYER_STATEMENT, Expression.DEFAULT, "I have forgotten my assignment.");
                        setPhase(10);
                    } else if (index == 2) {
                        if (!player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.TELEPORT_TO_TASK)) {
                            player.message("You have not unlocked this feature yet.");
                            stop();
                            return;
                        }
                        SlayerCreature.teleport(player);
                        stop();
                    } else if (index == 3) {
                        send(DialogueType.PLAYER_STATEMENT, Expression.DEFAULT, "Nevermind.");
                        setPhase(4);
                    }
                } else {
                    if (getPhase() == 12) {
                        if (index == 1) {
                            send(DialogueType.PLAYER_STATEMENT, Expression.DEFAULT, "I have forgotten my assignment.");
                            setPhase(13);
                        } else if (index == 2) {
                            if (!player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.TELEPORT_TO_TASK)) {
                                player.message("You have not unlocked this feature yet.");
                                stop();
                                return;
                            }
                            SlayerCreature.teleport(player);
                            stop();
                        } else if (index == 3) {
                            send(DialogueType.PLAYER_STATEMENT, Expression.DEFAULT, "Nevermind.");
                            setPhase(4);
                        }
                    } else {
                        if (getPhase() == 15) {
                            if (index == 1) {
                                send(DialogueType.PLAYER_STATEMENT, Expression.DEFAULT, "I have forgotten my assignment.");
                                setPhase(16);
                            } else if (index == 2) {
                                if (!player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.TELEPORT_TO_TASK)) {
                                    player.message("You have not unlocked this feature yet.");
                                    stop();
                                    return;
                                }
                                SlayerCreature.teleport(player);
                                stop();
                            } else if (index == 3) {
                                send(DialogueType.PLAYER_STATEMENT, Expression.DEFAULT, "Nevermind.");
                                setPhase(4);
                            }
                        } else {
                            if (getPhase() == 18) {
                                if (index == 1) {
                                    send(DialogueType.PLAYER_STATEMENT, Expression.DEFAULT, "I have forgotten my assignment.");
                                    setPhase(19);
                                } else if (index == 2) {
                                    if (!player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.TELEPORT_TO_TASK)) {
                                        player.message("You have not unlocked this feature yet.");
                                        stop();
                                        return;
                                    }
                                    SlayerCreature.teleport(player);
                                    stop();
                                } else if (index == 3) {
                                    send(DialogueType.PLAYER_STATEMENT, Expression.DEFAULT, "Nevermind.");
                                    setPhase(4);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
