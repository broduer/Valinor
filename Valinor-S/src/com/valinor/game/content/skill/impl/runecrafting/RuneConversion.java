package com.valinor.game.content.skill.impl.runecrafting;

import com.valinor.game.action.Action;
import com.valinor.game.action.policy.WalkablePolicy;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.tasks.BottleTasks;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.pets.Pet;
import com.valinor.game.world.entity.mob.npc.pets.PetAI;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.game.content.achievements.Achievements.RUNE_MYSTERIES;
import static com.valinor.util.CustomItemIdentifiers.TASK_BOTTLE_SKILLING;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * Created by Carl on 2015-08-26.
 * Edited & finished by Situations on 2015-09-12
 */
public class RuneConversion extends Interaction {

    public enum Altar {
        AIR(1, 5.0, AIR_TALISMAN, AIR_RUNE, 34760, 34813, new Tile(2841, 4830), 34748, new Tile(2983, 3293), false, 11, 4800, Pet.RIFT_GUARDIAN_AIR),
        MIND(2, 5.5, MIND_TALISMAN, MIND_RUNE, 34761, 34814, new Tile(2792, 4827), 34749, new Tile(2984, 3512), false, 14, 4700, Pet.RIFT_GUARDIAN_MIND),
        WATER(5, 6.0, WATER_TALISMAN, WATER_RUNE, 34762, 34815, new Tile(2726, 4832), 34750, new Tile(3183, 3167), false, 19, 4600, Pet.RIFT_GUARDIAN_WATER),
        EARTH(9, 6.5, EARTH_TALISMAN, EARTH_RUNE, 34763, 34816, new Tile(2655, 4830), 34751, new Tile(3305, 3472), false, 29, 4500, Pet.RIFT_GUARDIAN_EARTH),
        FIRE(14, 7.0, FIRE_TALISMAN, FIRE_RUNE, 34764, 34817, new Tile(2574, 4849), -1, new Tile(3312, 3253), false, 35, 4400, Pet.RIFT_GUARDIAN_FIRE),
        BODY(20, 7.5, BODY_TALISMAN, BODY_RUNE, 34765, 34818, new Tile(2521, 4834), 34753, new Tile(3054, 3443), false, 46, 4300, Pet.RIFT_GUARDIAN_BODY),
        COSMIC(27, 8.0, COSMIC_TALISMAN, COSMIC_RUNE, 34766, 34819, new Tile(2162, 4833), 34754, new Tile(2410, 4377), true, 59, 4200, Pet.RIFT_GUARDIAN_COSMIC),
        LAW(54, 9.5, LAW_TALISMAN, LAW_RUNE, 34767, 34820, new Tile(2464, 4818), 34755, new Tile(2860, 3381), true, 200, 4100, Pet.RIFT_GUARDIAN_LAW),
        NATURE(44, 9.0, NATURE_TALISMAN, NATURE_RUNE, 34768, 34821, new Tile(2400, 4835), 34754, new Tile(2868, 3017), true, 91, 4000, Pet.RIFT_GUARDIAN_NATURE),
        CHAOS(35, 8.5, CHAOS_TALISMAN, CHAOS_RUNE, 34769, 34822, new Tile(2281, 4837), 34757, new Tile(3062, 3590), true, 74, 3900, Pet.RIFT_GUARDIAN_CHAOS),
        DEATH(65, 10.0, DEATH_TALISMAN, DEATH_RUNE, 34770, 34823, new Tile(2208, 4830), 34758, new Tile(1862, 4639), true, 200, 3800, Pet.RIFT_GUARDIAN_DEATH),
        ASTRAL(40, 8.7, -1, ASTRAL_RUNE, 34771, -1, new Tile(2156, 3863), -1, new Tile(2156, 3863), true, 42, 3700, Pet.RIFT_GUARDIAN_ASTRAL),
        BLOOD(77, 23.8, -1, BLOOD_RUNE, 27978, -1, null, -1, null, true, 42, 3600, Pet.RIFT_GUARDIAN_BLOOD),
        SOUL(90, 29.7, -1, SOUL_RUNE, 27980, -1, null, -1, null, true, 42, 3500, Pet.RIFT_GUARDIAN_SOUL),
        WRATH(95, 8.0, WRATH_TALISMAN, WRATH_RUNE, 34772, 34824, new Tile(2337, 4833), 34759, new Tile(2448,2822), true, 42, 3400, Pet.WRATH_RIFT_GUARDIAN);

        private final int levelReq;
        private final double xp;
        private final int talisman;
        private final int rune;
        private final int altarObj;
        private final int entranceObj;
        private final Tile entranceTile;
        private final int exitObject;
        private final Tile exitTile;
        private final boolean pure;
        private final int multiplier;
        public int petOdds;
        public Pet petTransform;

        Altar(int levelReq, double xp, int talisman, int rune, int altarObj, int entranceObj, Tile entranceTile, int exitObject, Tile exitTile, boolean pure, int multiplier, int petOdds, Pet petTransform) {
            this.levelReq = levelReq;
            this.xp = xp;
            this.talisman = talisman;
            this.rune = rune;
            this.altarObj = altarObj;
            this.entranceObj = entranceObj;
            this.entranceTile = entranceTile;
            this.exitObject = exitObject;
            this.exitTile = exitTile;
            this.pure = pure;
            this.multiplier = multiplier;
            this.petOdds = petOdds;
            this.petTransform = petTransform;
        }

        public static Altar get(int talisman) {
            for (Altar altar : Altar.values()) {
                if (talisman == altar.talisman) {
                    return altar;
                }
            }
            return null;
        }
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if(option == 1) {
            for (Altar altar : Altar.values()) {
                if (altar.altarObj == object.getId()) {
                    craft(player, altar);
                    return true;
                }

                if (altar.exitObject == object.getId()) {
                    player.lock();
                    player.message("You step through the portal...");
                    player.teleport(altar.exitTile);
                    Chain.bound(player).runFn(1, player::unlock);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean handleItemOnObject(Player player, Item item, GameObject object) {
        Altar talisman = Altar.get(item.getId());

        for (Altar altar : Altar.values()) {
            if (object.getId() == altar.entranceObj) {
                if (talisman != null && altar.talisman == item.getId()) {
                    player.lock();
                    Chain.bound(player).runFn(1, () -> {
                        player.animate(827);
                        player.message("You hold the " + altar.name().toLowerCase() + " talisman towards the mysterous ruins.");
                    }).then(2, () -> {
                        player.message("You feel a powerful force take hold of you...");
                        player.lock();
                    }).then(1, () -> {
                        player.teleport(altar.entranceTile);
                        player.unlock();
                    });
                } else {
                    String aAn = (altar == Altar.EARTH || altar == Altar.ASTRAL || altar == Altar.AIR) ? "an" : "a";
                    player.message("You need " + aAn + " " + altar.name().toLowerCase() + " talisman to access the " + altar.name().toLowerCase() + " altar.");
                }
                return true;
            }
        }
        return false;
    }

    private static void craft(Player player, Altar altar) {
        if (player.skills().xpLevel(Skills.RUNECRAFTING) >= altar.levelReq) {
            int amount = player.inventory().count(PURE_ESSENCE);
            if (!altar.pure)
                amount += player.inventory().count(RUNE_ESSENCE);

            String msg = "pure";
            if (!altar.pure)
                msg = "rune";

            if (amount >= 1) {
                player.getInterfaceManager().close();
                player.lock();
                player.animate(791);
                player.graphic(186);
                int finalAmount = amount;
                Chain.bound(player).runFn(4, () -> {
                    if (altar.pure)
                        player.inventory().remove(new Item(PURE_ESSENCE, finalAmount), true);
                    else {
                        player.inventory().remove(new Item(PURE_ESSENCE, player.inventory().count(PURE_ESSENCE)), true);
                        player.inventory().remove(new Item(RUNE_ESSENCE, player.inventory().count(RUNE_ESSENCE)), true);
                    }
                    int multi = 1;
                    for (int i = altar.multiplier; i < altar.multiplier * 10; i += altar.multiplier) {
                        if (player.skills().xpLevel(Skills.RUNECRAFTING) >= i)
                            multi++;
                    }

                    if (altar == Altar.DEATH) {
                        player.getTaskBottleManager().increase(BottleTasks.CRAFT_DEATH_RUNES, finalAmount);
                    }

                    int runeAmount = finalAmount * multi;

                    if (player.getEquipment().hasAt(EquipSlot.RING, RING_OF_CHAROSA))
                        runeAmount *= 2;

                    player.inventory().add(new Item(altar.rune, runeAmount), true);
                    player.skills().addXp(Skills.RUNECRAFTING, altar.xp * finalAmount);
                    player.putAttrib(AttributeKey.RUNECRAFTING, false);
                    AchievementsManager.activate(player, RUNE_MYSTERIES,finalAmount * multi);

                    if (World.getWorld().rollDie(300, 1)) {
                        GroundItem item = new GroundItem(new Item(TASK_BOTTLE_SKILLING), player.tile(), player);
                        GroundItemHandler.createGroundItem(item);
                    }

                    // Woo! A pet!
                    if (altar.petTransform != null) {
                        var odds = (int) (altar.petOdds * player.getMemberRights().petRateMultiplier());
                        if (World.getWorld().rollDie(odds, 1)) {
                            unlockRiftGuarding(player, altar.petTransform);
                        } else {
                            var pet = player.pet();
                            if (pet != null) {
                                // Our current pet is a runecrafting pet.
                                if (pet.id() >= 7337 && pet.id() <= 7367) {
                                    Npc petNpc = new Npc(altar.petTransform.npc, pet.tile());
                                    PetAI.metamorph(player, petNpc);
                                }
                            }
                        }
                    }
                    player.unlock();
                });
            } else {
                player.putAttrib(AttributeKey.RUNECRAFTING, false);
                player.getInterfaceManager().close();
                DialogueManager.sendStatement(player, "You do not have any " + msg + " essence to bind.");
            }
        } else {
            player.putAttrib(AttributeKey.RUNECRAFTING, false);
            player.getInterfaceManager().close();
            player.message("You need a Runecrafting level of " + altar.levelReq + " to infuse these runes.");
        }
    }

    public static Action<Player> action(Player player, Altar altar, int amount) {
        return new Action<>(player,1,true) {
            int ticks = 0;

            @Override
            public void execute() {
                craft(player, altar);
                if (++ticks == amount) {
                    stop();
                }
            }

            @Override
            public String getName() {
                return "";
            }

            @Override
            public boolean prioritized() {
                return false;
            }

            @Override
            public WalkablePolicy getWalkablePolicy() {
                return WalkablePolicy.NON_WALKABLE;
            }
        };
    }

    private static void unlockRiftGuarding(Player player, Pet guardian) {
        if (!PetAI.hasUnlocked(player, guardian)) {
            // Unlock the varbit. Just do it, rather safe than sorry.
            player.addUnlockedPet(guardian.varbit);

            // RS tries to add it as follower first. That only works if you don't have one.
            var currentPet = player.pet();
            if (currentPet == null) {
                player.message("You have a funny feeling like you're being followed.");
                PetAI.spawnPet(player, guardian, false);
            } else {
                // Sneak it into their inventory. If that fails, fuck you, no pet for you!
                if (player.inventory().add(new Item(guardian.item),true)) {
                    player.message("You feel something weird sneaking into your backpack.");
                } else {
                    player.message("Speak to Probita to claim your pet!");
                }
            }

            World.getWorld().sendWorldMessage("<img=452><shad=0>" + Color.RED.wrap(player.getUsername()) + " has unlocked the pet: <col="+Color.HOTPINK.getColorValue()+">" + new Item(guardian.item).name()+ "</col>.");
        } else {
            player.message("You have a funny feeling like you would have been followed...");
        }
    }

}
