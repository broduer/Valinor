package com.valinor.game.content.skill.impl.agility.course;

import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.packet_actions.interactions.objects.Ladders;
import com.valinor.game.content.skill.impl.agility.UnlockAgilityPet;
import com.valinor.game.content.tasks.BottleTasks;
import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.ForceMovementTask;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.movement.MovementQueue;
import com.valinor.game.world.entity.mob.player.ForceMovement;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.game.world.entity.AttributeKey.WILDY_COURSE_STATE;
import static com.valinor.util.CustomItemIdentifiers.TASK_BOTTLE_SKILLING;

/**
 * Created by Jak on 13/06/2016.
 */
public class WildernessCourse extends Interaction {

    public final static int LOWER_GATE = 23555;
    public final static int UPPERGATE_EAST = 23552;
    public final static int UPPERGATE_WEST = 23554;
    private final static int PIPE = 23137;
    private final static int ROPESWING = 23132;
    private final static int LADDERDOWN = 14758;
    // ladder up is same as barb course
    private final static int STEPPINGSTONE = 23556;
    private final static int LOGBALANCE = 23542;
    private final static int ROCKS = 23640;

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if (option == 1) {
            if (obj.getId() == LOWER_GATE) {
                if (player.skills().level(Skills.AGILITY) < 52) {
                    player.message("You do not have the required level to enter this course.");
                    return true;
                }
                lowergate(player, obj);
                return true;
            }
            if (obj.getId() == UPPERGATE_EAST) {
                uppergate(player, obj);
                return true;
            }

            if (obj.getId() == UPPERGATE_WEST) {
                uppergate(player, obj);
                return true;
            }

            if (obj.getId() == PIPE) {
                // Get in position
                player.smartPathTo(obj.tile());
                Chain.bound(player).name("WildernessCourse1Task").waitForTile(new Tile(3004, 3937), () -> {
                    player.lockDelayDamage();
                    player.animate(749, 30);
                    //TaskManager.submit(new ForceMovementTask(player, 1, new ForceMovement(player.tile().clone(), new Tile(0, 6), 33, 126, FaceDirection.SOUTH)));
                }).name("WildernessCourse2Task")
                    /*.then(3, () -> {
                        TaskManager.submit(new ForceMovementTask(player, 3, new ForceMovement(player.tile().clone(), new Tile(0, 7), 33, 126, 0)));
                    })*/.then(1, () -> {
                    player.teleport(3004, 3950);
                    //player.animate(749, 30);
                    player.unlock();
                    putStage(player, 1);
                    player.skills().addXp(Skills.AGILITY, 12.5 * 3);
                });
                return true;
            }

            if (obj.getId() == ROPESWING) {
                player.smartPathTo(new Tile(3005, 3951)); // turns out this tile is solid clipped.
                // when you get here it should do a run up but doesnt.
                player.waitForTile(new Tile(3005, 3951), () -> {
                    player.face(player.tile().transform(0, 10));
                    player.lockDelayDamage();
                    player.getPacketSender().sendObjectAnimation(obj, 54);// make rope pull back
                    player.animate(751); // swing

                    TaskManager.submit(new ForceMovementTask(player, 1, new ForceMovement(player.tile().clone(), new Tile(0, 7), 30, 50, 0))); // move

                    // 2t later
                    Chain.bound(player).name("WildernessCourse4Task").runFn(1, () -> {
                        player.getPacketSender().sendObjectAnimation(obj, 55);
                        putStage(player, 2);
                        player.skills().addXp(Skills.AGILITY, 20.0 * 3);
                    }).then(2, player::unlock);
                });
                return true;
            }

            if (obj.getId() == LADDERDOWN) {
                Ladders.ladderDown(player, player.tile().transform(0, 6400), true);
                return true;
            }

            if (obj.getId() == STEPPINGSTONE) {
                player.smartPathTo(new Tile(3002, 3960));
                player.waitUntil(() -> player.tile().equals(3002, 3960), () -> {
                    player.getMovementQueue().interpolate(3002, 3960, MovementQueue.StepType.FORCED_WALK);
                    player.lockDelayDamage();
                }).then(2, () -> {
                    player.face(new Tile(0, player.tile().y));
                    //Increase stage when starting the obstacle
                    putStage(player, 4);
                    for (int i = 0; i < 6; i++) {
                        // add new Tasks @ instantly (0*3=0), 3 6, 9, 12
                        Chain.bound(player).name("WildernessCourse7Task").runFn(i * 3, () -> {
                            player.animate(741);
                            TaskManager.submit(new ForceMovementTask(player, 1, new ForceMovement(player.tile().clone(), new Tile(-1, 0), 5, 35, 3)));
                        });
                    }
                    Chain.bound(player).name("WildernessCourse8Task").waitForTile(new Tile(2996, 3960), () -> {
                        player.skills().addXp(Skills.AGILITY, 20.0 * 3);
                        player.unlock();
                    });
                });
                return true;
            }

            if (obj.getId() == LOGBALANCE) {
                player.smartPathTo(new Tile(3002, 3945));
                player.waitForTile(new Tile(3002, 3945), () -> {

                    if (!player.tile().equals(3002, 3945)) { // Get in position
                        player.getMovementQueue().interpolate(3002, 3945, MovementQueue.StepType.FORCED_WALK);
                    }

                    Tile end = new Tile(2994, 3945);
                    Chain.bound(player).name("WildernessCourse9Task").runFn(1, () -> {
                        player.lockDelayDamage();
                        player.message("You walk carefully across the slippery log...");
                        player.getMovementQueue().clear();
                        player.agilityWalk(false);
                        player.getMovementQueue().interpolate(end, MovementQueue.StepType.FORCED_WALK);
                        player.looks().render(763, 762, 762, 762, 762, 762, -1);
                    });

                    Chain.bound(player).name("WildernessCourse10Task").waitForTile(end, () -> {
                        player.looks().resetRender();
                        putStage(player, 8);
                        player.skills().addXp(Skills.AGILITY, 20.0 * 3);
                        player.message("...You make it safely to the other side.");
                        player.agilityWalk(true);
                        player.unlock();
                    });
                });
                return true;
            }

            if (obj.getId() == ROCKS) {
                player.waitUntil(1, () -> player.tile().y == 3937, () -> {
                    if (!player.tile().equals(2995, 3937)) { // Get in position
                        player.getMovementQueue().interpolate(2995, 3937, MovementQueue.StepType.FORCED_WALK);
                        player.getMovementQueue().clear();
                    }

                    Chain.bound(player).name("WildernessCourse13Task").runFn(1, () -> {
                        player.lockDelayDamage();
                        player.animate(740);
                        TaskManager.submit(new ForceMovementTask(player, 1, new ForceMovement(player.tile().clone(), new Tile(0, -4), 5, 80, 2))); // move
                    }).then(3, () -> {
                        player.teleport(new Tile(2995, 3934));
                        player.looks().resetRender();
                        player.animate(-1);
                        if (player.<Integer>getAttribOr(AttributeKey.WILDY_COURSE_STATE, 0) == 15) {
                            player.putAttrib(AttributeKey.WILDY_COURSE_STATE, 0);
                            player.skills().addXp(Skills.AGILITY, 498.9 * 3);
                            player.getTaskBottleManager().increase(BottleTasks.WILDERNESS_COURSE);
                            AchievementsManager.activate(player, Achievements.PARKOUR, 1);


                            if (World.getWorld().rollDie(10, 1)) {
                                GroundItem item = new GroundItem(new Item(TASK_BOTTLE_SKILLING), player.tile(), player);
                                GroundItemHandler.createGroundItem(item);
                            }

                            // Woo! A pet!
                            var odds = (int) (18000 * player.getMemberRights().petRateMultiplier());
                            if (World.getWorld().rollDie(odds, 1)) {
                                UnlockAgilityPet.unlockGiantSquirrel(player);
                            }
                        }
                        player.unlock();
                    });
                });
                return true;
            }
        }
        return false;
    }

    // TODO make formula include exponential success growth for levels over requirement ending at 70
    public static boolean successful(Player player) {
        int req = 35;
        int endeffectiveness = 70;
        int gap = player.skills().xpLevel(Skills.AGILITY) - req;
        double failrate = 0.30;
        return Math.random() >= (failrate - ((failrate / (endeffectiveness - req)) * gap));
    }

    private static void uppergate(Player player, GameObject obj) {
        if (!player.tile().equals(obj.tile())) {
            player.getMovementQueue().walkTo(obj.tile());
        }

        var end = new Tile(2998, 3916);
        Chain.bound(player).waitForTile(obj.tile(), () -> {
            player.face(player.tile().transform(0, -1));

            Chain.bound(player).name("uppergate").runFn(1, () -> {
                // Walk away!
                player.lockDelayDamage();

                // Agility animation
                player.getMovementQueue().clear();

                player.agilityWalk(false);
                player.getMovementQueue().interpolate(end, MovementQueue.StepType.FORCED_WALK);
                player.looks().render(763, 762, 762, 762, 762, 762, -1);
                // The ending doors
                openDoubleDoors();

                // Walk to the end
            }).then(13, () -> {
                openEntranceGate(player, obj);
            }).waitForTile(end, () -> {
                player.looks().resetRender();
                player.agilityWalk(true);
                player.unlock();
            });
        });
    }

    private static void lowergate(Player player, GameObject obj) {
        if (player.skills().xpLevel(Skills.AGILITY) < 52) {
            player.message("You need a Agility level of 52 to pass this gate.");
            return;
        }

        if (obj.interactAble() && obj.tile().equals(2998, 3917)) {
            player.lockDelayDamage();

            // Agility animation
            player.getMovementQueue().clear();
            var end = new Tile(2998, 3931);
            player.getMovementQueue().interpolate(end, MovementQueue.StepType.FORCED_WALK);
            player.agilityWalk(false);
            player.looks().render(763, 762, 762, 762, 762, 762, -1);

            // Handle the door
            openEntranceGate(player, obj);

            // Finish up agility part
            Chain.bound(player).name("lowergateTask").runFn(13, () -> {
                // The ending doors
                openDoubleDoors();
            }).waitForTile(end, () -> {
                player.looks().resetRender();
                player.agilityWalk(true);
                player.unlock();
            });
        }
    }

    private static void openEntranceGate(Player player, GameObject obj) {
        obj.interactAble(false);
        ObjectManager.removeObj(obj);
        var newobj = new GameObject(1548, new Tile(2997, 3917), obj.getType(), 2).interactAble(false);
        ObjectManager.addObj(newobj);
        Chain.bound(player).name("openEntranceGateTask").runFn(2, () -> {
            // Put the old door back
            ObjectManager.removeObj(newobj);
            ObjectManager.addObj(obj);
            obj.interactAble(true);
        });
    }

    private static void openDoubleDoors() {
        GameObject openDoor1 = new GameObject(23552, new Tile(2998, 3931, 0), 0, 3);
        GameObject rotateDoor1 = new GameObject(23552, new Tile(2998, 3931, 0), 0, 2);
        ObjectManager.replace(openDoor1, rotateDoor1, 3);

        GameObject openDoor2 = new GameObject(23554, new Tile(2997, 3931, 0), 0, 3);
        GameObject rotateDoor2 = new GameObject(23554, new Tile(2997, 3931, 0), 0, 4);
        ObjectManager.replace(openDoor2, rotateDoor2, 3);
    }

    private int putStage(Player player, int stageBit) {
        var stage = player.<Integer>getAttribOr(AttributeKey.WILDY_COURSE_STATE, 0);
        stage |= stageBit;
        player.putAttrib(AttributeKey.WILDY_COURSE_STATE, stage);
        return stage;
    }
}
