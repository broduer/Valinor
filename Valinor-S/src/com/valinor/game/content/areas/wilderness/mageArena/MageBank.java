package com.valinor.game.content.areas.wilderness.mageArena;

import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.ForceMovementTask;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.ForceMovement;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.*;

public class MageBank extends Interaction {

    //TODO 2879 -> return to otherside

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if(obj.getId() == SACK_14743) {
            if(player.inventory().isFull()) {
                player.message("Nothing interesting happens.");
                return true;
            }

            player.inventory().add(KNIFE, 1);
            player.message("You search the sack and find a knife.");
            return true;
        }
        if(obj.getId() == SPARKLING_POOL) {
            player.getDialogueManager().start(new Dialogue() {
                @Override
                protected void start(Object... parameters) {
                    send(DialogueType.STATEMENT, "You step into the pool of sparkling water. You feel energy rush", "through your veins.");
                    setPhase(0);
                }

                @Override
                public void next() {
                    if(getPhase() == 0) {
                        stop();
                        Chain.bound(player).name("SparklingPool").runFn(1, () -> {
                            player.lockDelayDamage();
                            player.getRouteFinder().routeAbsolute(2542, 4718);
                        }).waitForTile(new Tile(2542, 4718), () -> {
                            player.animate(741);
                            TaskManager.submit(new ForceMovementTask(player, 1, new ForceMovement(player.tile().clone(), new Tile(0,2), 5, 40, 0)));
                        }).then(1, () -> {
                            player.animate(804);
                            player.graphic(68);
                        }).then(1, () -> {
                            player.teleport(2509, 4689, 0);
                            player.animate(-1);
                            player.unlock();
                        });
                    }
                }
            });
            return true;
        }
        if(obj.getId() == SPARKLING_POOL_2879) {
            player.getDialogueManager().start(new Dialogue() {
                @Override
                protected void start(Object... parameters) {
                    send(DialogueType.STATEMENT, "You step into the pool of sparkling water. You feel energy rush", "through your veins.");
                    setPhase(0);
                }

                @Override
                public void next() {
                    if(getPhase() == 0) {
                        stop();
                        Chain.bound(player).name("SparklingPoolBack").runFn(1, () -> {
                            player.lockDelayDamage();
                            player.getRouteFinder().routeAbsolute(2509, 4689);
                        }).waitForTile(new Tile(2509, 4689), () -> {
                            player.animate(741);
                            TaskManager.submit(new ForceMovementTask(player, 1, new ForceMovement(player.tile().clone(), new Tile(0,-2), 5, 40, 2)));
                        }).then(1, () -> {
                            player.animate(804);
                            player.graphic(68);
                        }).then(1, () -> {
                            player.teleport(2542, 4718, 0);
                            player.animate(-1);
                            player.unlock();
                        });
                    }
                }
            });
            return true;
        }
        if(obj.getId() == STATUE_OF_SARADOMIN_2873) {
            player.runFn(1, () -> {
                player.lockMovement();
                player.animate(645);
                DialogueManager.sendStatement(player,"You kneel and chant to Saradomin...");
            }).then(2, () -> {
                DialogueManager.sendStatement(player,"You kneel and chant to Saradomin...", "You feel a rush of energy charge through your veins.", "Suddenly a cape appears before you.");
            }).then(1, () -> {
                World.getWorld().tileGraphic(188, new Tile(2500, 4719,0), 50, 0);
                GroundItem groundItem = new GroundItem(new Item(SARADOMIN_CAPE), new Tile(2500, 4719, 0), player);
                groundItem.setState(GroundItem.State.SEEN_BY_OWNER);
                GroundItemHandler.createGroundItem(groundItem);
                player.unlock();
            });
            return true;
        }
        if(obj.getId() == STATUE_OF_ZAMORAK_2874) {
            player.runFn(1, () -> {
                player.lockMovement();
                player.animate(645);
                DialogueManager.sendStatement(player,"You kneel and chant to Zamorak...");
            }).then(2, () -> {
                DialogueManager.sendStatement(player,"You kneel and chant to Zamorak...", "You feel a rush of energy charge through your veins.", "Suddenly a cape appears before you.");
            }).then(1, () -> {
                World.getWorld().tileGraphic(188, new Tile(2516, 4719,0), 50, 0);
                GroundItem groundItem = new GroundItem(new Item(ZAMORAK_CAPE), new Tile(2516, 4719, 0), player);
                groundItem.setState(GroundItem.State.SEEN_BY_OWNER);
                GroundItemHandler.createGroundItem(groundItem);
                player.unlock();
            });
            return true;
        }
        if(obj.getId() == STATUE_OF_GUTHIX) {
            player.runFn(1, () -> {
                player.lockMovement();
                player.animate(645);
                DialogueManager.sendStatement(player,"You kneel and chant to Guthix...");
            }).then(2, () -> {
                DialogueManager.sendStatement(player,"You kneel and chant to Guthix...", "You feel a rush of energy charge through your veins.", "Suddenly a cape appears before you.");
            }).then(1, () -> {
                World.getWorld().tileGraphic(188, new Tile(2507, 4722,0), 50, 0);
                GroundItem groundItem = new GroundItem(new Item(GUTHIX_CAPE), new Tile(2507, 4722, 0), player);
                groundItem.setState(GroundItem.State.SEEN_BY_OWNER);
                GroundItemHandler.createGroundItem(groundItem);
                player.unlock();
            });
            return true;
        }
        return false;
    }
}
