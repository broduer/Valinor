package com.valinor.game.content.skill.impl.woodcutting;

import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.items.ItemSet;
import com.valinor.game.content.tasks.BottleTasks;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.pets.Pet;
import com.valinor.game.world.entity.mob.npc.pets.PetAI;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.route.RouteFinder;
import com.valinor.util.Color;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.valinor.game.action.Action;
import com.valinor.game.action.policy.WalkablePolicy;
import com.valinor.game.content.areas.zeah.woodcutting_guild.WoodcuttingGuild;
import com.valinor.game.content.skill.impl.firemaking.LogLighting;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.ObjectIdentifiers;
import com.valinor.util.Utils;

import java.util.Optional;

import static com.valinor.util.CustomItemIdentifiers.TASK_BOTTLE_SKILLING;
import static com.valinor.util.CustomItemIdentifiers.WOODCUTTING_MASTER_CAPE;
import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.*;

/**
 * Created by Bart on 8/28/2015.
 *
 * @author PVE
 * @Since augustus 29, 2020
 */
public class Woodcutting extends Interaction {

    public enum Tree {
        REGULAR(ItemIdentifiers.LOGS, "logs", 1, 55, 25.0, 75, true, 2764),
        ACHEY(ACHEY_TREE_LOGS, "achey logs", 1, 55, 25.0, 75, true,2764),
        OAK(OAK_LOGS, "oak logs", 15, 95, 37.5, 15, false,2664),
        WILLOW(WILLOW_LOGS, "willow logs", 30, 140, 67.5, 10, false,2500),
        TEAK(TEAK_LOGS, "teak logs", 35, 140, 85.0, 10, false,2400),
        JUNIPER(JUNIPER_LOGS, "juniper logs", 42, 150, 35.0, 30, false,2300),
        MAPLE(MAPLE_LOGS, "maple logs", 45, 180, 100.0, 60, false,2200),
        MAHOGANY(MAHOGANY_LOGS, "mahogany logs", 50, 200, 125.0, 80, false,2100),
        YEW(YEW_LOGS, "yew logs", 60, 225, 175.0, 100, false,2000),
        MAGIC(MAGIC_LOGS, "magic logs", 75, 375, 250.0, 100, false,1400),
        REDWOOD(REDWOOD_LOGS, "redwood logs", 90, 460, 380.0, 200, false,1000),
        ENTTRUNK(-1, "ent trunk", -1, 250, 0.0, 0, false,0); // Used for algo only

        private final int logs;
        private final String treeName;
        private final int level;
        private final int difficulty;
        private final double xp;
        private final int respawnTime;
        private final boolean single;
        private final int petOdds;

        Tree(int logs, String treeName, int level, int difficulty, double xp, int respawnTime, boolean single, int petOdds) {
            this.logs = logs;
            this.treeName = treeName;
            this.level = level;
            this.difficulty = difficulty;
            this.xp = xp;
            this.respawnTime = respawnTime;
            this.single = single;
            this.petOdds = petOdds;
        }
    }

    public enum Hatchet {
        BRONZE(ItemIdentifiers.BRONZE_AXE, 13, 879, 1),
        IRON(IRON_AXE, 15, 877, 1),
        STEEL(STEEL_AXE, 18, 875, 6),
        BLACK(BLACK_AXE, 21, 873, 11),
        MITHRIL(MITHRIL_AXE, 26, 871, 21),
        ADAMANT(ADAMANT_AXE, 30, 869, 31),
        RUNE(RUNE_AXE, 35, 867, 41),
        DRAGON(DRAGON_AXE, 42, 2846, 61),
        THIRD_AGE(_3RD_AGE_AXE, 42, 7264, 61),
        INFERNAL(INFERNAL_AXE, 45, 2117, 61);

        public final int id;
        public final int points;
        public final int anim;
        public final int level;

        Hatchet(int id, int points, int anim, int level) {
            this.id = id;
            this.points = points;
            this.anim = anim;
            this.level = level;
        }

        public static final ImmutableSet<Hatchet> VALUES = ImmutableSortedSet.copyOf(values()).descendingSet();
    }

    public static int chance(int level, Tree type, Hatchet axe) {
        double points = ((level - type.level) + 1 + axe.points);
        double denom = type.difficulty;
        return (int) (Math.min(0.95, points / denom) * 100);
    }

    public static int getEntLog(Player player) {
        if (player.skills().level(Skills.WOODCUTTING) >= 75 && World.getWorld().rollDie(10, 4)) {
            return 1514;
        } else if (player.skills().level(Skills.WOODCUTTING) > 60 && World.getWorld().rollDie(10, 5)) { // NOT a typo, 61 gives you yews.
            return 1516;
        } else if (player.skills().level(Skills.WOODCUTTING) >= 40 && World.getWorld().rollDie(10, 4)) {
            return 1518;
        } else if (World.getWorld().rollDie(10, 5)) {
            return 1520;
        }

        return 1522; // Oak
    }

    private static int nestChance(Player player) {
        int chance = 200;
        if (player.getMemberRights().isZenyteMemberOrGreater(player)) {
            chance = 170;
        } else if (player.getMemberRights().isOnyxMemberOrGreater(player)) {
            chance = 175;
        } else if (player.getMemberRights().isDragonstoneMemberOrGreater(player)) {
            chance = 180;
        } else if (player.getMemberRights().isDiamondMemberOrGreater(player)) {
            chance = 185;
        } else if (player.getMemberRights().isRubyMemberOrGreater(player)) {
            chance = 190;
        } else if (player.getMemberRights().isEmeraldMemberOrGreater(player)) {
            chance = 195;
        } else if (player.getMemberRights().isSapphireMemberOrGreater(player)) {
            chance = 197;
        }

        if (wearsWoodcuttingCape(player)) {
            chance += chance / 10;
        }

        return chance;
    }

    public static boolean wearsWoodcuttingCape(Player player) {
        int cape = player.getEquipment().getId(EquipSlot.CAPE);
        return cape == WOODCUTTING_CAPE || cape == WOODCUT_CAPET || cape == MAX_CAPE || cape == WOODCUTTING_MASTER_CAPE;
    }

    public static Optional<Hatchet> findAxe(Player player) {
        if (player.getEquipment().hasWeapon()) {
            Optional<Hatchet> result = Hatchet.VALUES.stream().filter(it -> player.getEquipment().contains(it.id) && player.skills().levels()[Skills.WOODCUTTING] >= it.level).findFirst();

            if (result.isPresent()) {
                return result;
            }
        }
        return Hatchet.VALUES.stream().filter(def -> player.inventory().contains(def.id) && player.skills().levels()[Skills.WOODCUTTING] >= def.level).findAny();
    }

    public static void cut(Player player, Tree tree, int trunkObjectId) {
        Hatchet axe = findAxe(player).orElse(null);

        //Does our player have an axe?
        if (axe == null) {
            player.sound(2277, 0);
            player.message("You do not have an axe which you have the Woodcutting level to use.");
            return;
        }

        //Does our player have the required woodcutting level?
        if (player.skills().levels()[Skills.WOODCUTTING] < tree.level) {
            player.sound(2277, 0);
            player.message("You need a Woodcutting level of " + tree.level + " to chop down this tree.");
            return;
        }

        //Does out player have enough inventory space?
        if (player.inventory().isFull()) {
            player.sound(2277, 0);
            player.message("Your inventory is too full to hold any more logs.");
            return;
        }

        player.runFn(1, () -> {
            player.message("You swing your axe at the tree.");
            player.animate(axe.anim);
            player.action.execute(cut(player, axe, tree, trunkObjectId), true);
        });
    }

    private static Action<Player> cut(Player player, Hatchet axe, Tree tree, int trunkObjectId) {
        return new Action<>(player, 2, false) {

            @Override
            public void execute() {
                GameObject obj = player.getAttribOr(AttributeKey.INTERACTION_OBJECT, null);
                player.faceObj(obj);

                int level = player.skills().levels()[Skills.WOODCUTTING];
                if (player.tile().inArea(WoodcuttingGuild.AREA_EAST) || player.tile().inArea(WoodcuttingGuild.AREA_WEST))
                    level += 7; // +7 invisible boost in WC guild!

                if (Utils.random(100) <= chance(level, tree, axe)) {
                    player.message("You get some " + tree.treeName + ".");

                    if (tree.single || Utils.random(10) == 3) {
                        player.sound(3600, 0);
                        player.animate(-1);
                        stop();//Tree despawned stop skilling

                        GameObject old = new GameObject(obj.getId(), obj.tile(), obj.getType(), obj.getRotation());
                        GameObject spawned = new GameObject(trunkObjectId, obj.tile(), obj.getType(), obj.getRotation());
                        ObjectManager.replace(old, spawned, tree.respawnTime);
                        player.skills().addXp(Skills.WOODCUTTING, tree.xp * ItemSet.lumberJackOutfitBonus(player)); // Xp as last, it can spawn a dialogue
                        AchievementsManager.activate(player, Achievements.LUMBERJACK,1);

                        if (World.getWorld().rollDie(125, 1)) {
                            GroundItem item = new GroundItem(new Item(TASK_BOTTLE_SKILLING), player.tile(), player);
                            GroundItemHandler.createGroundItem(item);
                        }

                        // Woo! A pet! The reason we do this BEFORE the item is because it's... quite some more valuable :)
                        // Rather have a pet than a clumsy log thing, right?
                        var odds = (int) (tree.petOdds * player.getMemberRights().petRateMultiplier());
                        if (World.getWorld().rollDie(odds, 1)) {
                            unlockBeaver(player);
                        }

                        // If we're using the infernal axe, we have 1/3 odds to burn the log and get 50% FM xp.
                        if (axe == Hatchet.INFERNAL && Utils.rollDie(30, 10) && tree.logs > 0) {
                            LogLighting.LightableLog log = LogLighting.LightableLog.logForId(tree.logs);

                            if (log != null) {
                                player.graphic(580, 50, 0);
                                player.skills().addXp(Skills.FIREMAKING, (log.xp * ItemSet.pyromancerOutfitBonus(player)) / 2);
                            }
                        } else {
                            int amount = player.getEquipment().hasAt(EquipSlot.RING, RING_OF_CHAROSA) ? 2 : 1;
                            player.inventory().add(new Item(tree.logs, amount));
                        }

                        return;
                    }

                    //Finding a casket Money, money, money..
                    if (World.getWorld().rollDie(100, 1)) {
                        player.inventory().addOrDrop(new Item(7956, 1));
                        player.message("You find a casket whilst cutting down the tree.");
                    }

                    if (World.getWorld().rollDie(nestChance(player), 1)) {
                        GroundItem groundItem = new GroundItem(BirdNest.getRandomNest(tree), RouteFinder.findWalkable(player.tile()), player);
                        GroundItemHandler.createGroundItem(groundItem);
                        player.message("A bird's nest falls out of the tree.");
                    }

                    if (tree == Tree.YEW) {
                        player.getTaskBottleManager().increase(BottleTasks.CUT_YEW_TREES);
                    }

                    if (tree == Tree.MAGIC) {
                        player.getTaskBottleManager().increase(BottleTasks.CUT_MAGIC_TREES);
                    }

                    AchievementsManager.activate(player, Achievements.LUMBERJACK,1);

                    player.skills().addXp(Skills.WOODCUTTING, tree.xp * ItemSet.lumberJackOutfitBonus(player)); // Xp as last, it can spawn a dialogue

                    // If we're using the infernal axe, we have 1/3 odds to burn the log and get 50% FM xp.
                    if (axe == Hatchet.INFERNAL && Utils.rollDie(30, 10) && tree.logs > 0) {
                        LogLighting.LightableLog log = LogLighting.LightableLog.logForId(tree.logs);

                        if (log != null) {
                            player.graphic(580, 50, 0);
                            player.skills().addXp(Skills.FIREMAKING, (log.xp * ItemSet.pyromancerOutfitBonus(player)) / 2);
                        }
                    } else {
                        int amount = player.getEquipment().hasAt(EquipSlot.RING, RING_OF_CHAROSA) ? 2 : 1;
                        player.inventory().add(new Item(tree.logs, amount));
                    }
                }
                if (player.inventory().isFull()) {
                    player.sound(2277, 0);
                    player.message("Your inventory is too full to hold any more logs.");
                    player.animate(-1);
                    stop();
                    return;
                }

                player.animate(axe.anim);
            }

            @Override
            public String getName() {
                return "Woodcutting";
            }

            @Override
            public boolean prioritized() {
                return false;
            }

            @Override
            public WalkablePolicy getWalkablePolicy() {
                return WalkablePolicy.NON_WALKABLE;
            }

            @Override
            public void onStop() {
                super.onStop();
                if (player.getAnimation() != null && player.getAnimation().getId() == axe.anim)
                    player.animate(-1);
            }
        };
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if (option == 1) {
            if (obj.getId() == TREE_1278) {
                Woodcutting.cut(player, Woodcutting.Tree.REGULAR, TREE_STUMP_1342);
                return true;
            }
            if (obj.getId() == ObjectIdentifiers.TREE) {
                Woodcutting.cut(player, Woodcutting.Tree.REGULAR, TREE_STUMP_1342);
                return true;
            }
            if (obj.getId() == EVERGREEN_2091) {
                Woodcutting.cut(player, Woodcutting.Tree.REGULAR, TREE_STUMP_1342);
                return true;
            }
            if (obj.getId() == DEAD_TREE_1286) {
                Woodcutting.cut(player, Woodcutting.Tree.REGULAR, TREE_STUMP_1351);
                return true;
            }
            if (obj.getId() == DEAD_TREE_1283) {
                Woodcutting.cut(player, Woodcutting.Tree.REGULAR, TREE_STUMP_1347);
                return true;
            }
            if (obj.getId() == DEAD_TREE_1383) {
                Woodcutting.cut(player, Woodcutting.Tree.REGULAR, TREE_STUMP_1358);
                return true;
            }
            if (obj.getId() == DEAD_TREE_1289) {
                Woodcutting.cut(player, Woodcutting.Tree.REGULAR, TREE_STUMP_1353);
                return true;
            }
            if (obj.getId() == ACHEY_TREE) {
                Woodcutting.cut(player, Woodcutting.Tree.ACHEY, ACHEY_TREE_STUMP);
                return true;
            }
            if (obj.getId() == OAK_10820) {
                Woodcutting.cut(player, Woodcutting.Tree.OAK, TREE_STUMP_1356);
                return true;
            }
            if (obj.getId() == WILLOW) {
                Woodcutting.cut(player, Woodcutting.Tree.WILLOW, TREE_STUMP_9711);
                return true;
            }
            if (obj.getId() == WILLOW_10829) {
                Woodcutting.cut(player, Woodcutting.Tree.WILLOW, TREE_STUMP_9711);
                return true;
            }
            if (obj.getId() == WILLOW_10831) {
                Woodcutting.cut(player, Woodcutting.Tree.WILLOW, TREE_STUMP_9711);
                return true;
            }
            if (obj.getId() == WILLOW_10833) {
                Woodcutting.cut(player, Woodcutting.Tree.WILLOW, TREE_STUMP_9711);
                return true;
            }
            if (obj.getId() == TEAK) {
                Woodcutting.cut(player, Woodcutting.Tree.TEAK, TREE_STUMP_9037);
                return true;
            }
            if (obj.getId() == MATURE_JUNIPER_TREE) {
                Woodcutting.cut(player, Woodcutting.Tree.JUNIPER, STUMP_27500);
                return true;
            }
            if (obj.getId() == MAPLE_TREE_10832) {
                Woodcutting.cut(player, Woodcutting.Tree.MAPLE, TREE_STUMP_9712);
                return true;
            }
            if (obj.getId() == YEW) {
                Woodcutting.cut(player, Woodcutting.Tree.YEW, TREE_STUMP_9714);
                return true;
            }
            if (obj.getId() == MAGIC_TREE_10834) {
                Woodcutting.cut(player, Woodcutting.Tree.MAGIC, TREE_STUMP_9713);
                return true;
            }
            if (obj.getId() == REDWOOD) {
                player.waitUntil(1, () -> !player.getMovementQueue().isMoving(), () -> Woodcutting.cut(player, Tree.REDWOOD, REDWOOD_29669));
                return true;
            }
            if (obj.getId() == REDWOOD_29670) {
                player.waitUntil(1, () -> !player.getMovementQueue().isMoving(), () -> Woodcutting.cut(player, Tree.REDWOOD, REDWOOD_29671));
                return true;
            }
            if (obj.getId() == MAHOGANY) {
                Woodcutting.cut(player, Woodcutting.Tree.MAHOGANY, TREE_STUMP_9035);
                return true;
            }
        }
        return false;
    }

    private static void unlockBeaver(Player player) {
        if (!PetAI.hasUnlocked(player, Pet.BEAVER)) {
            // Unlock the varbit. Just do it, rather safe than sorry.
            player.addUnlockedPet(Pet.BEAVER.varbit);

            // RS tries to add it as follower first. That only works if you don't have one.
            var currentPet = player.pet();
            if (currentPet == null) {
                player.message("You have a funny feeling like you're being followed.");
                PetAI.spawnPet(player, Pet.BEAVER, false);
            } else {
                // Sneak it into their inventory. If that fails, fuck you, no pet for you!
                if (player.inventory().add(new Item(Pet.BEAVER.item), true)) {
                    player.message("You feel something weird sneaking into your backpack.");
                } else {
                    player.message("Speak to Probita to claim your pet!");
                }
            }

            World.getWorld().sendWorldMessage("<img=452><shad=0>" + Color.RED.wrap(player.getUsername()) + " has unlocked the pet: <col="+Color.HOTPINK.getColorValue()+">" + new Item(Pet.BEAVER.item).name()+ "</col>.");
        } else {
            player.message("You have a funny feeling like you would have been followed...");
        }
    }

}
