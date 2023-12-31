package com.valinor.game.content.skill.impl.mining;

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
import com.valinor.util.Color;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.valinor.game.action.impl.UnwalkableAction;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;
import com.valinor.util.timers.TimerKey;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.valinor.util.CustomItemIdentifiers.TASK_BOTTLE_SKILLING;
import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.*;

/**
 * Created by Bart on 8/28/2015.
 * <p>
 * Handles the mining skill, as well as a few utility functions to add mining-like features elsewhere in the game.
 */
public class Mining extends Interaction {

    public enum Rock {
        CLAY(434, "clay", 1, 15, 5.0, 3, 74160),
        COPPER(436, "copper", 1, 35, 17.5, 5, 74160),
        TIN(438, "tin", 1, 35, 17.5, 5, 74160),
        IRON(440, "iron", 15, 75, 35.0, 10, 74160),
        SILVER(442, "silver", 20, 85, 40.0, 20, 74160),
        COAL(453, "coal", 30, 110, 50.0, 30, 29064),
        GOLD(444, "gold", 40, 180, 65.0, 40, 29664),
        MITHRIL(447, "mithril", 55, 210, 80.0, 60, 14832),
        LOVAKITE(13356, "lovakite", 65, 210, 10.0, 40, 24556),
        ADAMANT(449, "adamant", 70, 310, 95.0, 100, 9328),
        RUNE(451, "rune", 85, 380, 125.0, 220, 4237),
        JAIL_BLURITE(668, "blurite", 1, 320, 0.0, 3, 1000000),
        AMETHYST(21347, "amethyst", 92, 380, 246.0, 125, 2500);

        public int ore;
        public String rockName;
        public int level;
        public int difficulty;
        public double xp;
        public int respawnTime;
        public int petOdds;

        Rock(int ore, String rockName, int level, int difficulty, double xp, int respawnTime, int petOdds) {
            this.ore = ore;
            this.rockName = rockName;
            this.level = level;
            this.difficulty = difficulty;
            this.xp = xp;
            this.respawnTime = respawnTime;
            this.petOdds = petOdds;
        }
    }

    public enum Pickaxe {
        BRONZE(1265, 5, 625, 6753, 1),
        IRON(1267, 9, 626, 6754, 1),
        STEEL(1269, 14, 627, 6755, 6),
        BLACK(12297, 21, 3866, 3866, 11),
        MITHRIL(1273, 26, 629, 6757, 21),
        ADAMANT(1271, 30, 628, 6756, 31),
        RUNE(1275, 36, 624, 6752, 41),
        DRAGON(11920, 42, 7139, 6758, 61),
        THIRD_AGE(20014, 42, 7283, 7282, 61),
        DRAGON_OR(12797, 42, 642, 335, 61),
        INFERNAL(13243, 42, 4482, 4481, 61),
        CRYSTAL(23680, 45, 8329, 8329, 81);

        public int id;
        public int points;
        public int anim;
        public int crystalAnim;
        public int level;

        Pickaxe(int id, int points, int anim, int crystalAnim, int level) {
            this.id = id;
            this.points = points;
            this.anim = anim;
            this.crystalAnim = crystalAnim;
            this.level = level;
        }

        public static final ImmutableSet<Pickaxe> VALUES = ImmutableSortedSet.copyOf(values()).descendingSet();
    }

    public static int chance(int level, Rock type, Pickaxe pick) {
        double points = ((level - type.level) + 1 + pick.points);
        double denom = type.difficulty;
        return (int) (Math.min(0.95, points / denom) * 100);
    }

    public static Optional<Pickaxe> findPickaxe(Player player) {
        if (player.getEquipment().hasWeapon()) {
            Optional<Pickaxe> pickaxe = Pickaxe.VALUES.stream().filter(it -> player.getEquipment().contains(it.id) && player.skills().levels()[Skills.MINING] >= it.level).findFirst();
            if (pickaxe.isPresent()) {
                return pickaxe;
            }

        }
        return Pickaxe.VALUES.stream().filter(def -> player.inventory().contains(def.id) && player.skills().levels()[Skills.MINING] >= def.level).findAny();
    }

    private static void interact(Player player, Rock rockType, int replId) {
        int option = player.getAttribOr(AttributeKey.INTERACTION_OPTION, 0);
        if (option == 1) {
            mine(player, rockType, replId);
        } else {
            prospect(player, rockType);
        }
    }

    private static double nonDepletionChance(Rock rockType, Player player) {
        double chance;
        switch (rockType) {
            case SILVER -> chance = 50.0;
            case COAL -> chance = 40.0;
            case GOLD -> chance = 33.33;
            case MITHRIL, AMETHYST -> chance = 25.0;
            case ADAMANT -> chance = 16.66;
            case RUNE -> chance = 12.5;
            default -> chance = 0;
        }
        return chance;
    }

    private static void mine(Player player, Rock rockType, int replId) {
        Rock rock = rockType;
        GameObject obj = player.getAttribOr(AttributeKey.INTERACTION_OBJECT, null);
        Optional<Mining.Pickaxe> pick = Mining.findPickaxe(player);

        // Any rocks mined in the jail are converted to bluite. This is the ore required to leave the jail.
        // If it was coal, people could mine coal before they are even jailed and instantly escape!
        if ((int) player.getAttribOr(AttributeKey.JAILED, 0) == 1) {
            rock = Rock.JAIL_BLURITE;

            // Expensive code but stops retards stockpiling ores.
            if (player.getBank().count(Rock.JAIL_BLURITE.ore) + player.inventory().count(Rock.JAIL_BLURITE.ore) >= (int) player.getAttribOr(AttributeKey.JAIL_ORES_TO_ESCAPE, 0)) {
                player.message("You don't need any more ores to escape.");
                return;
            }
        }

        // Is the inventory full?
        if (player.inventory().isFull()) {
            player.sound(2277, 0);
            DialogueManager.sendStatement(player, "Your inventory is too full to hold any more " + rock.rockName + ".");
            return;
        }

        if (pick.isEmpty()) {
            player.sound(2277, 0);
            DialogueManager.sendStatement(player, "You need a pickaxe to mine this rock.", "You do not have a pickaxe which " + "you have the Mining level to use.");
            return;
        }

        if (player.skills().levels()[Skills.MINING] < rock.level && (int) player.getAttribOr(AttributeKey.JAILED, 0) == 0) {
            player.sound(2277, 0);
            DialogueManager.sendStatement(player, "You need a Mining level of " + rock.level + " to mine this rock.");
            return;
        }
        player.faceObj(obj);
        player.animate(rock == Rock.AMETHYST ? pick.get().crystalAnim : pick.get().anim);
        Chain.bound(player).runFn(1, () -> player.message("You swing your pick at the rock."));

        int gemOdds = player.getEquipment().containsAny(1706, 1708, 1710, 1712, 11976, 11978) ? 3 : 1;

        Rock finalRock = rock;
        player.action.execute(new UnwalkableAction(player, 4) {
            @Override
            public void execute() {
                // check obj
                if (!ObjectManager.objWithTypeExists(10, obj.tile()) && !ObjectManager.objWithTypeExists(11, obj.tile()) && !ObjectManager.objWithTypeExists(0, obj.tile())) {
                    player.animate(-1);
                    stop();
                    return;
                }

                var doubleRoll = Utils.rollPercent((int) ItemSet.varrockDiaryArmour(player));

                // Roll for an uncut
                if (Utils.rollDie(256, gemOdds)) {
                    if (doubleRoll) {
                        giveGem(player);
                    }
                    giveGem(player);
                }

                var odds = Math.max(1, Mining.chance(player.skills().levels()[Skills.MINING], finalRock, pick.get()));

                //For jailing we want to boost the rates of mining otherwise some players will literally be there for days.
                if (player.jailed()) {
                    odds = Utils.random(90, 95);
                }

                if (player.getAttribOr(AttributeKey.DEBUG_MESSAGES, false)) {
                    player.debug("Mining chance: " + odds + ".");
                }

                if (Utils.random(100) <= odds) {
                    player.message("You manage to mine some " + finalRock.rockName + ".");

                    // Woo! A pet! The reason we do this BEFORE the item is because it's... quite some more valuable :)
                    // Rather have a pet than a pesky ore thing, right?
                    var petOdds = (int) (finalRock.petOdds * player.getMemberRights().petRateMultiplier());
                    if (World.getWorld().rollDie(petOdds, 1)) {
                        unlockGolem(player);
                    }

                    // Infernal pickaxe logic
                    if (finalRock != Rock.COAL && pick.get() == Pickaxe.INFERNAL && Utils.random(2) == 0) {
                        player.graphic(580, 155, 0);
                        addBar(player, finalRock);
                    } else {
                        int amount = 1;
                        if (doubleRoll)
                            amount += 1;
                        if (finalRock.ore != RUNITE_ORE && player.getEquipment().hasAt(EquipSlot.CAPE, MINING_CAPET) && Utils.rollPercent(5))
                            amount += 1;
                        if(player.getEquipment().hasAt(EquipSlot.RING, RING_OF_CHAROSA))
                            amount += 1;

                        player.inventory().addOrDrop(new Item(finalRock.ore, amount));
                        if(amount > 1)
                            player.message("You manage to mine an additional ore.");
                    }

                    if (finalRock == Rock.RUNE) {
                        player.getTaskBottleManager().increase(BottleTasks.MINE_RUNITE_ORE);
                    }

                    player.sound(3600, 0);
                    player.animate(-1); // does -1 a same time it does the real an want me to open OSS no oke ye lol

                    // The Prospector blessing reduces rock respawn timers by half.
                    int rockTime = finalRock.respawnTime;

                    // High-level rocks on PvP are faster. Nobody waits on a PvP server.
                    rockTime = finalRock == Rock.RUNE ? 200 : // 2 minutes
                        finalRock == Rock.ADAMANT ? 50 : // 30 seconds
                            finalRock == Rock.MITHRIL ? 25 : // 15 seconds
                                rockTime;

                    GameObject original = new GameObject(obj.getId(), obj.tile(), obj.getType(), obj.getRotation());
                    GameObject spawned = new GameObject(replId, obj.tile(), obj.getType(), obj.getRotation());

                    var nonDepletion = Utils.rollPercent((int) nonDepletionChance(finalRock, player)) && player.getEquipment().hasAt(EquipSlot.HANDS, EXPERT_MINING_GLOVES);
                    if(!nonDepletion) {
                        ObjectManager.replace(original, spawned, Math.max(1, rockTime - 1));
                    }

                    if (!player.jailed()) {
                        player.skills().addXp(Skills.MINING, finalRock.xp * xpBonus(player));
                        AchievementsManager.activate(player, Achievements.MINER, 1);
                    }

                    if (World.getWorld().rollDie(125, 1)) {
                        GroundItem item = new GroundItem(new Item(TASK_BOTTLE_SKILLING), player.tile(), player);
                        GroundItemHandler.createGroundItem(item);
                    }

                    //Caskets Money, money, money..
                    if (Utils.rollDie(20, 1)) {
                        player.inventory().addOrDrop(new Item(7956, 1));
                        player.message("The rock broke, inside you find a casket!");
                    }

                    stop();
                    return;
                }
                player.animate(finalRock == Rock.AMETHYST ? pick.get().crystalAnim : pick.get().anim);
            }
        });
    }

    /**
     * Attempts to roll for a gem. Chances to get a gem are 1/256, or 3/256 when wearing a charged glory amulet.
     */
    private static void giveGem(Player player) {
        int roll = Utils.random(9);
        switch (roll) {
            case 0 -> {
                player.message("You just found a Diamond!");
                player.inventory().add(new Item(1617));
            }
            case 1, 2 -> {
                player.message("You just found a Ruby!");
                player.inventory().add(new Item(1619));
            }
            case 3, 4, 5 -> {
                player.message("You just found an Emerald!");
                player.inventory().add(new Item(1621));
            }
            case 6, 7, 8, 9 -> {
                player.message("You just found a Sapphire!");
                player.inventory().add(new Item(1623));
            }
        }
    }

    private static void prospect(Player player, Rock rock) {
        player.stopActions(true);
        player.message("You examine the rock for ores...");
        player.sound(2661, 0);
        Chain.bound(player).runFn(4, () -> {
            player.message("This rock contains " + rock.rockName + ".");
            player.stopActions(true);
        });
    }

    public static double xpBonus(Player player) {
        double multiplier = 1;
        multiplier *= ItemSet.prospectorBonus(player);
        multiplier *= ItemSet.goldProspectorBonus(player);

        //Acts as a prospector jacket in terms of full prospector outfit set XP bonus and master clue step but does not count as a prospector jacket to fulfill the Falador Diary's hard task of entering the Mining Guild wearing full prospector.
        Item body = player.getEquipment().get(EquipSlot.BODY);
        if (body != null && body.getId() == VARROCK_ARMOUR_4)
            multiplier *= 2.5;

        Item gloves = player.getEquipment().get(EquipSlot.HANDS);
        if (gloves != null && gloves.getId() == MINING_GLOVES)
            multiplier *= 1.01;
        if (gloves != null && gloves.getId() == EXPERT_MINING_GLOVES)
            multiplier *= 1.03;
        if (gloves != null && gloves.getId() == SUPERIOR_MINING_GLOVES)
            multiplier *= 1.05;
        return multiplier;
    }

    private static void addBar(Player player, Mining.Rock rock) {
        switch (rock) {
            case COPPER, TIN -> {
                player.inventory().add(new Item(2349));
                player.skills().addXp(Skills.SMITHING, 2.5);
            }
            case IRON -> {
                player.inventory().add(new Item(2351));
                player.skills().addXp(Skills.SMITHING, 5.0);
            }
            case SILVER -> {
                player.inventory().add(new Item(2355));
                player.skills().addXp(Skills.SMITHING, 5.5);
            }
            case GOLD -> {
                player.inventory().add(new Item(2357));
                player.skills().addXp(Skills.SMITHING, 9.0);
            }
            case MITHRIL -> {
                player.inventory().add(new Item(2359));
                player.skills().addXp(Skills.SMITHING, 12.0);
            }
            case ADAMANT -> {
                player.inventory().add(new Item(2361));
                player.skills().addXp(Skills.SMITHING, 15.0);
            }
            case RUNE -> {
                player.inventory().add(new Item(2363));
                player.skills().addXp(Skills.SMITHING, 20.0);
            }
        }
    }

    private static void unlockGolem(Player player) {
        if (!PetAI.hasUnlocked(player, Pet.ROCK_GOLEM)) {
            // Unlock the varbit. Just do it, rather safe than sorry.
            player.addUnlockedPet(Pet.ROCK_GOLEM.varbit);

            // RS tries to add it as follower first. That only works if you don't have one.
            var currentPet = player.pet();
            if (currentPet == null) {
                player.message("You have a funny feeling like you're being followed.");
                PetAI.spawnPet(player, Pet.ROCK_GOLEM, false);
            } else {
                // Sneak it into their inventory. If that fails, fuck you, no pet for you!
                if (player.inventory().add(new Item(Pet.ROCK_GOLEM.item), true)) {
                    player.message("You feel something weird sneaking into your backpack.");
                } else {
                    player.message("Speak to Probita to claim your pet!");
                }
            }

            World.getWorld().sendWorldMessage("<img=452><shad=0>" + Color.RED.wrap(player.getUsername()) + " has unlocked the pet: <col=" + Color.HOTPINK.getColorValue() + ">" + new Item(Pet.ROCK_GOLEM.item).name() + "</col>.");
        } else {
            player.message("You have a funny feeling like you would have been followed...");
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Mining.Rock type = Mining.Rock.GOLD;
        DecimalFormat format = new DecimalFormat("###.##");

        PrintStream out = new PrintStream(System.getProperty("user.home") + "\\Desktop\\mining_" + type.toString().toLowerCase() + ".csv");
        out.print("lvl");
        for (Mining.Pickaxe h : Mining.Pickaxe.values())
            out.print("," + h);
        out.println();

        for (int i = 1; i < 99; i++) {
            out.print(i);
            for (Mining.Pickaxe h : Mining.Pickaxe.values()) {
                out.print("," + format.format(Mining.chance(i, type, h)) + "%");
            }
            out.println();
        }

        out.flush();
        out.close();
    }

    private static final class RegisterableRock {

        int obj;
        Mining.Rock type;
        int empty;

        RegisterableRock(int obj, Mining.Rock type, int empty) {
            this.obj = obj;
            this.type = type;
            this.empty = empty;
        }

        RegisterableRock(int obj, Mining.Rock type) {
            this.obj = obj;
            this.type = type;
            this.empty = ROCKS_11390;
        }
    }

    private static final List<RegisterableRock> rocks;

    static {
        rocks = Arrays.asList(
            new RegisterableRock(ROCKS_11363, Rock.CLAY),
            new RegisterableRock(ROCKS_11362, Rock.CLAY),
            new RegisterableRock(ROCKS_11161, Rock.COPPER, ROCKS_11391),
            new RegisterableRock(ROCKS_10943, Rock.COPPER),
            new RegisterableRock(ROCKS_11364, Rock.IRON),
            new RegisterableRock(ROCKS_11365, Rock.IRON, ROCKS_11391),
            new RegisterableRock(ROCKS_11360, Rock.TIN),
            new RegisterableRock(ROCKS_11361, Rock.TIN, ROCKS_11391),
            new RegisterableRock(ROCKS_11366, Rock.COAL, ROCKS_11391),
            new RegisterableRock(ROCKS_11367, Rock.COAL),
            new RegisterableRock(ROCKS_11368, Rock.SILVER),
            new RegisterableRock(ROCKS_11369, Rock.SILVER, ROCKS_11391),
            new RegisterableRock(ROCKS_11370, Rock.GOLD),
            new RegisterableRock(ROCKS_11371, Rock.GOLD, ROCKS_11391),
            new RegisterableRock(ROCKS_11372, Rock.MITHRIL),
            new RegisterableRock(ROCKS_11373, Rock.MITHRIL, ROCKS_11391),
            new RegisterableRock(ROCKS_28596, Rock.LOVAKITE),
            new RegisterableRock(ROCKS_28597, Rock.LOVAKITE, ROCKS_11391),
            new RegisterableRock(ROCKS_11374, Rock.ADAMANT),
            new RegisterableRock(ROCKS_11375, Rock.ADAMANT, ROCKS_11391),
            new RegisterableRock(ROCKS_11376, Rock.RUNE),
            new RegisterableRock(ROCKS_11377, Rock.RUNE, ROCKS_11391),
            new RegisterableRock(CRYSTALS, Rock.AMETHYST, EMPTY_WALL),
            new RegisterableRock(CRYSTALS_11389, Rock.AMETHYST, EMPTY_WALL));
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        for (RegisterableRock rock : rocks) {
            if (obj.getId() == rock.obj) {
                Mining.interact(player, rock.type, rock.empty);
                return true;
            }
        }

        if (obj.getId() == ROCKS_11390 || obj.getId() == ROCKS_11391) {
            player.sound(2661, 0);
            player.message("There is no ore currently available in this rock.");
            return true;
        }
        return false;
    }

}
