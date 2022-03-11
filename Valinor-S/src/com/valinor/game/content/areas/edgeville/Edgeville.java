package com.valinor.game.content.areas.edgeville;

import com.valinor.game.content.areas.wilderness.content.hitman_services.Hitman;
import com.valinor.game.content.events.wilderness_key.WildernessKeyPlugin;
import com.valinor.game.content.areas.wilderness.dialogue.ArtifactTraderDialogue;
import com.valinor.game.content.teleport.OrnateJewelleryBox;
import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.ForceMovementTask;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.masks.animations.Animation;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.ForceMovement;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.chainedwork.Chain;
import com.valinor.util.timers.TimerKey;

import static com.valinor.util.NpcIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.*;

/**
 * @author Patrick van Elderen | Zerikoth | PVE
 * @date februari 21, 2020 17:06
 */
public class Edgeville extends Interaction {

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        if (option == 1) {
            if (npc.id() == EMBLEM_TRADER) {
                player.getDialogueManager().start(new ArtifactTraderDialogue());
                return true;
            }
            if (npc.id() == FANCY_DAN) {
                World.getWorld().shop(6).open(player);
                return true;
            }
            if (npc.id() == WISE_OLD_MAN) {
                World.getWorld().shop(43).open(player);
                return true;
            }
        } else if (option == 2) {
            if (npc.id() == EMBLEM_TRADER) {
                player.getDialogueManager().start(new Dialogue() {
                    @Override
                    protected void start(Object... parameters) {
                        send(DialogueType.OPTION, "Which shop would you like to open?", "Boss Points shop", "PkP shop", "Nevermind");
                        setPhase(0);
                    }

                    @Override
                    protected void select(int option) {
                        if(isPhase(0)) {
                            if(option == 1) {
                                World.getWorld().shop(47).open(player);
                            }

                            if(option == 2) {
                                World.getWorld().shop(18).open(player);
                            }

                            if(option == 3) {
                                stop();
                            }
                        }
                    }
                });
                return true;
            }
        } else if (option == 3) {
            if (npc.id() == EMBLEM_TRADER) {
                Hitman.requestBounty(player);
                return true;
            }
        } else if (option == 4) {
            if (npc.id() == EMBLEM_TRADER) {
                Hitman.listOfBounties(player);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if (option == 1) {
            if (obj.getId() == CHAOS_ALTAR_411) {
                if (player.skills().level(Skills.PRAYER) < player.skills().xpLevel(Skills.PRAYER)) {
                    player.animate(new Animation(645));
                    player.skills().replenishSkill(5, player.skills().xpLevel(5));
                    player.message("You recharge your Prayer points.");
                } else {
                    player.message("You already have full prayer points.");
                }
                return true;
            }

            if (obj.getId() == LEVER_26761) {
                player.faceObj(obj);

                //Check to see if the player is teleblocked
                if (player.getTimers().has(TimerKey.TELEBLOCK) || player.getTimers().has(TimerKey.SPECIAL_TELEBLOCK)) {
                    player.teleblockMessage();
                    return true;
                }

                if (WildernessKeyPlugin.hasKey(player) && WildernessArea.inWilderness(player.tile())) {
                    player.message("You cannot teleport outside the Wilderness with the Wilderness key.");
                    return true;
                }

                player.lockNoDamage();
                GameObject spawned = new GameObject(88, obj.tile(), obj.getType(), obj.getRotation());
                player.runFn(1, () -> {
                    player.animate(2140);
                    player.message("You pull the lever...");
                }).then(1, () -> {
                    ObjectManager.addObj(spawned);
                    ObjectManager.replace(obj, spawned, 5);
                }).then(1, () -> {
                    player.animate(714);
                    player.graphic(111, 110, 0);
                }).then(4, () -> {
                    player.teleport(3154, 3924);
                    player.animate(-1);
                    player.unlock();
                    player.message("...And teleport into the wilderness.");
                });
                return true;
            }

            if (obj.getId() == TRAPDOOR_1579) {
                GameObject open = new GameObject(1581, obj.tile(), obj.getType(), obj.getRotation());
                ObjectManager.replaceWith(obj, open);
                return true;
            }

            if (obj.getId() == WILDERNESS_DITCH) {
                Chain.bound(null).runFn(1, () -> {
                    player.getMovementQueue().clear();
                    player.lock();
                    player.animate(6132);
                    player.sound(2462);
                    final Tile crossDitch = new Tile(0, player.tile().getY() < 3522 ? 3 : -3);
                    TaskManager.submit(new ForceMovementTask(player, 3, new ForceMovement(player.tile().copy(), crossDitch, 0, 70, crossDitch.getY() == 3 ? 0 : 2)));
                }).then(2, () -> {
                    player.getClickDelay().reset();
                    player.unlock();
                });
                return true;
            }

            if (obj.getId() == DOOR_1536) {
                player.message("You feel like it wouldn't be wise to do that...");
                return true;
            }
        } else if (option == 2) {
            
            if (obj.getId() == DWARVEN_MACHINERY) {
                World.getWorld().shop(15).open(player);
                return true;
            }

            if (obj.getId() == ORNATE_JEWELLERY_BOX) {
                OrnateJewelleryBox.open(player);
                return true;
            }

            if (obj.getId() == TRAPDOOR_1581) {
                GameObject open = new GameObject(1581, obj.tile(), obj.getType(), obj.getRotation());
                GameObject close = new GameObject(1579, obj.tile(), obj.getType(), obj.getRotation());
                ObjectManager.replaceWith(open, close);
                return true;
            }
        } else if (option == 3) {

        }
        return false;
    }

    @Override
    public boolean handleItemOnObject(Player player, Item item, GameObject object) {
        return false;
    }

}
