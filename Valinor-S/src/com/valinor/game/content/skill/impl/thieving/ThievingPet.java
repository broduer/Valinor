package com.valinor.game.content.skill.impl.thieving;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.pets.Pet;
import com.valinor.game.world.entity.mob.npc.pets.PetAI;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.util.Color;

/**
 * @author Patrick van Elderen | May, 26, 2021, 09:37
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class ThievingPet {

    public static void unlockRaccoon(Player player) {
        if (!PetAI.hasUnlocked(player, Pet.ROCKY)) {
            // Unlock the varbit. Just do it, rather safe than sorry.
            player.addUnlockedPet(Pet.ROCKY.varbit);

            // RS tries to add it as follower first. That only works if you don't have one.
            var currentPet = player.pet();
            if (currentPet == null) {
                player.message("You have a funny feeling like you're being followed.");
                PetAI.spawnPet(player, Pet.ROCKY, false);
            } else {
                // Sneak it into their inventory. If that fails, fuck you, no pet for you!
                if (player.inventory().add(new Item(Pet.ROCKY.item), true)) {
                    player.message("You feel something weird sneaking into your backpack.");
                } else {
                    player.message("Speak to Probita to claim your pet!");
                }
            }

            World.getWorld().sendWorldMessage("<img=452><shad=0>" + Color.RED.wrap(player.getUsername()) + " has unlocked the pet: <col="+Color.HOTPINK.getColorValue()+">" + new Item(Pet.ROCKY.item).name()+ "</col>.");
        } else {
            player.message("You have a funny feeling like you would have been followed...");
        }
    }
}
