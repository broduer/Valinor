package com.valinor.game.content.skill.impl.fishing;

import com.google.gson.Gson;
import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.items.ItemSet;
import com.valinor.game.content.tasks.BottleTasks;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
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
import com.valinor.game.world.position.Tile;
import com.valinor.util.Color;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.ref.WeakReference;
import java.util.*;
import java.util.stream.Collectors;

import static com.valinor.util.CustomItemIdentifiers.TASK_BOTTLE_SKILLING;
import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.NpcIdentifiers.FISHING_SPOT_7731;

/**
 * Created by Bart on 11/21/2015.
 */
public class Fishing {

    private static List<FishSpotDef> fishSpots = new ArrayList<>();

    public static void respawnAllSpots(World world) throws FileNotFoundException {
        Gson gson = new Gson();
        fishSpots = Arrays.stream(gson.fromJson(new FileReader("data/map/fishspots.json"), FishSpotDef[].class)).collect(Collectors.toList());
        fishSpots.forEach(spot -> createSpot(world, spot.spot, spot.tiles));
    }

    public static boolean onNpcOption1(Player player, Npc npc) {

        for (FishSpotDef fishSpot : fishSpots) {
            if (npc.id() == fishSpot.spot.id) {
                fish(player, fishSpot.spot.types.get(0), npc);
                return true;
            }
        }
        return false;
    }

    public static boolean onNpcOption2(Player player, Npc npc) {
        for (FishSpotDef fishSpot : fishSpots) {
            if (npc.id() == fishSpot.spot.id) {
                fish(player, fishSpot.spot.types.get(1), npc);
                return true;
            }
        }
        return false;
    }

    private static double xpBonus(Player player) {
        double multiplier = 1;
        multiplier *= ItemSet.anglerBonus(player);
        multiplier *= ItemSet.spiritAnglerBonus(player);

        return multiplier;
    }

    private static int catchChance(Player player, Fish type, FishingToolType fishingToolType) {
        double specialToolMod = fishingToolType != FishingToolType.NONE ? fishingToolType.boost() : 1.0;
        int points = 20;
        int diff = player.skills().levels()[Skills.FISHING] - type.lvl;
        return (int) Math.min(85, (points + diff * specialToolMod));
    }

    public static void fish(Player player, FishSpotType selectedAction, Npc npc) {

        // Level requirement
        if (player.skills().level(Skills.FISHING) < selectedAction.levelReq()) {
            DialogueManager.sendStatement(player, "You need to be at least level "+selectedAction.levelReq()+" Fishing to catch these fish.");
            return;
        }

        //Represents a definition for the found tool being used, if any, to fish.
        Optional<FishingToolType> fishingToolDef = FishingToolType.locateItemFor(player);
        boolean overrideTool = (fishingToolDef.isPresent() && FishingToolType.canUseOnSpot(fishingToolDef.get(), selectedAction) && player.skills().level(Skills.FISHING) >= fishingToolDef.get().levelRequired());

        // Check for the basic item first
        if (!overrideTool && !player.inventory().contains(selectedAction.staticRequiredItem)) {
            player.animate(-1);
            DialogueManager.sendStatement(player, selectedAction.missingText);
            return;
        }

        // Inventory full?
        if (player.inventory().isFull()) {
            player.animate(-1);
            DialogueManager.sendStatement(player, "You can't carry any more fish.");
            return;
        }

        // Bait check!
        if (!overrideTool && selectedAction.baitItem != -1 && !player.inventory().contains(selectedAction.baitItem)) {
            player.animate(-1);
            DialogueManager.sendStatement(player, selectedAction.baitMissing != null ? selectedAction.baitMissing : "You don't have any bait left.");
            return;
        }

        player.animate(overrideTool ? fishingToolDef.get().animationId() : selectedAction.anim);
        player.message(selectedAction.start);
        player.animate(overrideTool ? fishingToolDef.get().animationId() : selectedAction.anim);

        Chain.bound(player).runFn(1, () -> {
            // Rod has an extra one here
            if (selectedAction == FishSpotType.BAIT || selectedAction == FishSpotType.FLY) {
                player.message("You attempt to catch a fish.");
            }

            // repeat every 3t until boolSupplier returns false.
            Chain.bound(player).repeatingTask(3, t -> {

                player.animate(overrideTool ? fishingToolDef.get().animationId() : selectedAction.anim);

                if (player.inventory().isFull()) {
                    player.animate(-1);
                    DialogueManager.sendStatement(player, "You can't carry any more fish.");
                    t.stop();
                    return; // cancel the repeating task
                }

                // Fish spot ok mate?
                Mob target = ((WeakReference<Mob>) player.getAttribOr(AttributeKey.TARGET, new WeakReference<>(null))).get();
                if (target == null || !target.isNpc() || target.dead() || target.finished()) {
                    player.animate(-1);
                    t.stop();
                    return;
                }

                Fish weCatch = selectedAction.randomFish(player.skills().level(Skills.FISHING));

                if(npc.id() == FISHING_SPOT_7731 && (npc.minnowsFish || (npc.minnowsFish = World.getWorld().rollDie(15)))) {
                    npc.graphic(1387);

                    if(player.inventory().contains(weCatch.item)) {
                        player.getInventory().remove(weCatch.item, World.getWorld().random(20, 26));
                        player.message("A flying fish jumps up and eats some of your minnows!");
                    }
                }

                if (Utils.rollDie(100, catchChance(player, weCatch, overrideTool ? fishingToolDef.get() : FishingToolType.NONE))) {
                    if(weCatch == Fish.MINNOWS) {
                        player.message("You catch some minnows!");
                    } else {
                        player.message("You catch " + weCatch.prefix + " " + weCatch.fishName + ".");
                    }

                    if(weCatch == Fish.SHARK) {
                        player.getTaskBottleManager().increase(BottleTasks.CATCH_SHARKS);
                    }

                    // Do we need to remove bait?
                    if (selectedAction.baitItem != -1) {
                        player.inventory().remove(new Item(selectedAction.baitItem), true);
                    }

                    // Woo! A pet! The reason we do this BEFORE the item is because it's... quite some more valuable :)
                    // Rather have a pet than a slimy fishy thing, right?
                    var odds = (int) (weCatch.petChance * player.getMemberRights().petRateMultiplier());
                    if (World.getWorld().rollDie(odds, 1)) {
                        unlockHeron(player);
                    }

                    int amount = player.getEquipment().hasAt(EquipSlot.RING, RING_OF_CHAROSA) ? 2 : 1;
                    player.inventory().add(new Item(weCatch.item, weCatch == Fish.MINNOWS ? World.getWorld().random(10, 26) : amount), true);
                    player.skills().addXp(Skills.FISHING, weCatch.xp * xpBonus(player));
                    AchievementsManager.activate(player, Achievements.FISHERMAN,1);

                    if (World.getWorld().rollDie(100, 1) && weCatch != Fish.MINNOWS) {
                        GroundItem item = new GroundItem(new Item(TASK_BOTTLE_SKILLING), player.tile(), player);
                        GroundItemHandler.createGroundItem(item);
                    }

                    //Finding a casket in the water! Money, money, money..
                    if (Utils.rollDie(20, 1) && weCatch != Fish.MINNOWS) {
                        player.inventory().addOrDrop(new Item(7956, 1));
                        player.message("You find a casket in the water.");
                    }
                }
            });
        });
    }

    private static void unlockHeron(Player player) {
        if (!PetAI.hasUnlocked(player, Pet.HERON)) {
            // Unlock the varbit. Just do it, rather safe than sorry.
            player.addUnlockedPet(Pet.HERON.varbit);

            // RS tries to add it as follower first. That only works if you don't have one.
            var currentPet = player.pet();
            if (currentPet == null) {
                player.message("You have a funny feeling like you're being followed.");
                PetAI.spawnPet(player, Pet.HERON, false);
            } else {
                // Sneak it into their inventory. If that fails, fuck you, no pet for you!
                if (player.inventory().add(new Item(Pet.HERON.item), true)) {
                    player.message("You feel something weird sneaking into your backpack.");
                } else {
                    player.message("Speak to Probita to claim your pet!");
                }
            }

            World.getWorld().sendWorldMessage("<img=1081> " + player.getUsername() + " has unlocked the pet: <col="+Color.HOTPINK.getColorValue()+">" + new Item(Pet.HERON.item).name()+ "</col>.");
        } else {
            player.message("You have a funny feeling like you would have been followed...");
        }
    }

    public static Npc createSpot(World world, FishSpot spot, List<Tile> possible) {
        Collections.shuffle(possible);
        Npc npc = new Npc(spot.id, randomFreeSpotTile(world, possible));
        npc.putAttrib(AttributeKey.POSSIBLE_FISH_TILES, possible);
        world.registerNpc(npc);
        return npc;
    }

    public static Tile randomFreeSpotTile(World world, List<Tile> tiles) {
        return tiles.parallelStream().filter(t -> world.getNpcs().stream().filter(Objects::nonNull).noneMatch(n  -> n.tile().equals(t))).findAny().orElse(tiles.get(0));
    }

    public static class FishSpotDef {
        FishSpot spot = null;
        List<Tile> tiles = new ArrayList<>();
    }

}
