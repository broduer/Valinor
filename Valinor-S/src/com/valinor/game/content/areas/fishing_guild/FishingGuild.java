package com.valinor.game.content.areas.fishing_guild;

import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.dialogue.Expression;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.ItemIdentifiers.MINNOW;
import static com.valinor.util.ItemIdentifiers.RAW_SHARK;
import static com.valinor.util.NpcIdentifiers.KYLIE_MINNOW;
import static com.valinor.util.NpcIdentifiers.KYLIE_MINNOW_7728;
import static com.valinor.util.ObjectIdentifiers.*;

/**
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * april 21, 2020
 */
public class FishingGuild extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if (option == 1) {
            //System.out.println("fishing object " + obj.toString());
            if (obj.getId() == DOOR_20925) {
                int change = player.tile().y >= 3394 ? -1 : 1;
                if (change == 1 && player.skills().level(Skills.FISHING) < 68) {
                    DialogueManager.sendStatement(player, "You do not meet the level 68 Fishing requirement to enter the Guild.");
                    return false;
                }

                GameObject old = new GameObject(obj.getId(), obj.tile(), obj.getType(), 3);
                GameObject spawned = new GameObject(obj.getId(), obj.tile(), obj.getType(), 4);
                ObjectManager.replace(old, spawned, 1);

                player.getMovementQueue().walkTo(new Tile(player.tile().x, player.tile().y + change));
                player.lockNoDamage();
                Chain.bound(null).runFn(1, () -> {
                    player.unlock();
                    String plural = change == -1 ? "leave" : "enter";
                    player.message("You " + plural + " the Fishing Guild.");
                });
                return true;
            }

            if (obj.getId() == ROW_BOAT_30376) {
                int fishingLevel = player.skills().level(Skills.FISHING);
                if (fishingLevel < 82) {
                    DialogueManager.npcChat(player, Expression.NODDING_ONE, KYLIE_MINNOW, "G'day, only the best fishers are allowed onto the fishing platform.", "You need a fishing level of 82.");
                    return true;
                }

                player.runFn(1, () -> {
                    player.lock();
                    player.getPacketSender().sendScreenFade("", 1, 3);
                }).then(3, () -> DialogueManager.sendStatement(player, "You board the boat and row to the fishing platform.")).then(2, () -> {
                    player.teleport(2614, 3440);
                    player.unlock();
                });
                return true;
            }

            if (obj.getId() == ROW_BOAT_30377) {
                player.runFn(1, () -> {
                    player.lock();
                    player.getPacketSender().sendScreenFade("", 1, 3);
                }).then(3, () -> DialogueManager.sendStatement(player, "You board the boat and row to the " + ("dock") + ".")).then(2, () -> {
                    player.teleport(2600, 3425);
                    player.unlock();
                });
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        if (option == 1) {
            if (npc.id() == KYLIE_MINNOW) {
                if (player.<Boolean>getAttribOr(AttributeKey.KYLIE_MINNOW_DIALOGUE_STARTED, false)) {
                    int fishingLevel = player.skills().level(Skills.FISHING);
                    if (fishingLevel < 82)
                        notExperiencedDialogue(player);
                    else
                        experiencedDialogue(player);
                } else {
                    introductionDialogue(player);
                }
                return true;
            }
            if (npc.id() == KYLIE_MINNOW_7728) {
                DialogueManager.npcChat(player, Expression.NODDING_ONE, KYLIE_MINNOW_7728,"Catch some minnows!");
                return true;
            }
        }

        if (option == 2) {
            if (npc.id() == KYLIE_MINNOW_7728) {
                String fishName = "shark";
                if (!player.inventory().contains(MINNOW)) {
                    DialogueManager.npcChat(player, Expression.NODDING_ONE, KYLIE_MINNOW, "You'll be needing at least 40 minnows to trade for a " + fishName + "!", "Come back and see me when you have some more!");
                    return true;
                }
                int amount = player.inventory().count(MINNOW);
                if (amount < 40) {
                    DialogueManager.npcChat(player, Expression.NODDING_ONE, KYLIE_MINNOW, "You'll be needing at least 40 minnows to trade for a " + fishName + "!", "Come back and see me when you have some more!");
                    return true;
                }
                int max = amount / 40;
                player.getDialogueManager().start(new Dialogue() {
                    @Override
                    protected void start(Object... parameters) {
                        send(DialogueType.NPC_STATEMENT, KYLIE_MINNOW, Expression.NODDING_ONE, "I can give you " + ("a shark") + " for every 40 minnows that you", "give me. How many " + fishName + " would you like?");
                        setPhase(0);
                    }

                    @Override
                    protected void next() {
                        if (isPhase(0)) {
                            stop();
                            player.setEnterSyntax(new EnterSyntax() {
                                @Override
                                public void handleSyntax(Player player, long amt) {
                                    int exchange = (int) Math.min(amt, max);
                                    player.inventory().remove(MINNOW, exchange * 40);
                                    player.getInventory().add(RAW_SHARK + 1, exchange);
                                    DialogueManager.sendStatement(player, "You trade in " + exchange * 40 + " minnows for " + exchange + " sharks.");
                                }
                            });
                            player.getPacketSender().sendEnterAmountPrompt("How many " + fishName + " would you like? (0 - " + max + ")");
                        }
                    }
                });
                return true;
            }
        }
        return false;
    }

    private void introductionDialogue(Player player) {
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.NPC_STATEMENT, KYLIE_MINNOW, Expression.NODDING_ONE, "Strewth! Nippy little blighters!");
                setPhase(0);
            }

            @Override
            protected void next() {
                if (isPhase(0)) {
                    send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_ONE, "Excuse me?");
                    setPhase(1);
                } else if (isPhase(1)) {
                    send(DialogueType.NPC_STATEMENT, KYLIE_MINNOW, Expression.NODDING_ONE, "Oh G'day! Sorry, didn't see you there!");
                    setPhase(2);
                } else if (isPhase(2)) {
                    send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_ONE, "That's okay. What seems to be the problem?");
                    setPhase(3);
                } else if (isPhase(3)) {
                    send(DialogueType.NPC_STATEMENT, KYLIE_MINNOW, Expression.NODDING_ONE, "Oh, well I wanted to catch some minnows so I could use", "em' as bait, but the little blighters are just too quick!", "I managed to get 'em all rounded up over on my", "fishing platform, but I still can't catch 'em!");
                    setPhase(4);
                } else if (isPhase(4)) {
                    send(DialogueType.NPC_STATEMENT, KYLIE_MINNOW, Expression.VERY_SAD, "Then, to make things worse, any time I do finally", "catch some, those bloody flying fish jump out at me", "and gobble up me minnows!");
                    setPhase(5);
                } else if (isPhase(5)) {
                    send(DialogueType.NPC_STATEMENT, KYLIE_MINNOW, Expression.NODDING_ONE, "What I need is someone who can catch 'em for me!");
                    setPhase(6);
                } else if (isPhase(6)) {
                    send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_ONE, "Well, maybe I could help out?");
                    player.putAttrib(AttributeKey.KYLIE_MINNOW_DIALOGUE_STARTED, true);
                    setPhase(7);
                } else if (isPhase(7)) {
                    send(DialogueType.NPC_STATEMENT, KYLIE_MINNOW, Expression.NODDING_ONE, "Well I don't know. I don't want to be letting just anyone", "onto me fishing platform! But I guess if you could show", "me that you are a worthy fisher than that would be okay.", "I can even give you shark in exchange");
                    setPhase(8);
                } else if (isPhase(8)) {
                    send(DialogueType.NPC_STATEMENT, KYLIE_MINNOW, Expression.NODDING_ONE, "for the minnows you catch, 'cos them I can catch", "no problem!");
                    setPhase(9);
                } else if (isPhase(9)) {
                    send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_ONE, "Sound fair. What can I do to show that I am worthy?");
                    setPhase(10);
                } else if (isPhase(10)) {
                    send(DialogueType.NPC_STATEMENT, KYLIE_MINNOW, Expression.NODDING_ONE, "Well you should be very experienced in the art of fishing,", "own the proper attire, and be recognised by your peers", "as a great fisherman.");
                    setPhase(11);
                } else if (isPhase(11)) {
                    send(DialogueType.STATEMENT, "You must have level 82 Fishing" + " in order to suitably impress Kylie.");
                    setPhase(12);
                } else if (isPhase(12)) {
                    stop();
                }
            }
        });
    }

    private void experiencedDialogue(Player player) {
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_ONE, "So, how about letting me out onto your fishing platform?");
                setPhase(0);
            }

            @Override
            protected void next() {
                if (isPhase(0)) {
                    send(DialogueType.NPC_STATEMENT, KYLIE_MINNOW, Expression.NODDING_ONE, "Ripper! You certainly seem to be the real deal, so sure,", "go ahead and take the boat over there.");
                    setPhase(1);
                } else if (isPhase(1)) {
                    stop();
                }
            }
        });
    }

    private void notExperiencedDialogue(Player player) {
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.PLAYER_STATEMENT, Expression.NODDING_ONE, "So, how about letting me out onto your fishing platform?");
                setPhase(0);
            }

            @Override
            protected void next() {
                if (isPhase(0)) {
                    send(DialogueType.NPC_STATEMENT, KYLIE_MINNOW, Expression.NODDING_ONE, "Sorry, but I just don't think you are experienced enough.");
                    setPhase(1);
                } else if (isPhase(1)) {
                    send(DialogueType.STATEMENT, "You must have level 82 Fishing" + " in order to suitably impress Kylie.");
                    setPhase(2);
                } else if (isPhase(2)) {
                    stop();
                }
            }
        });
    }
}
