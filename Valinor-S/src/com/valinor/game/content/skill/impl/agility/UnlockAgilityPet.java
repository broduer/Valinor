package com.valinor.game.content.skill.impl.agility;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.pets.Pet;
import com.valinor.game.world.entity.mob.npc.pets.PetAI;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.util.Color;

/**
 * @author Patrick van Elderen | May, 26, 2021, 08:56
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class UnlockAgilityPet {

    public static void unlockGiantSquirrel(Player player) {
        if (!PetAI.hasUnlocked(player, Pet.GIANT_SQUIRREL)) {
            // Unlock the varbit. Just do it, rather safe than sorry.
            player.addUnlockedPet(Pet.GIANT_SQUIRREL.varbit);

            // RS tries to add it as follower first. That only works if you don't have one.
            var currentPet = player.pet();
            if (currentPet == null) {
                player.message("You have a funny feeling like you're being followed.");
                PetAI.spawnPet(player, Pet.GIANT_SQUIRREL, false);
            } else {
                // Sneak it into their inventory. If that fails, fuck you, no pet for you!
                if (player.inventory().add(new Item(Pet.GIANT_SQUIRREL.item), true)) {
                    player.message("You feel something weird sneaking into your backpack.");
                } else {
                    player.message("Speak to Probita to claim your pet!");
                }
            }

            World.getWorld().sendWorldMessage("<img=452><shad=0>" + Color.RED.wrap(player.getUsername()) + " has unlocked the pet: <col="+Color.HOTPINK.getColorValue()+">" + new Item(Pet.GIANT_SQUIRREL.item).name()+ "</col>.");
        } else {
            player.message("You have a funny feeling like you would have been followed...");
        }
    }
}
