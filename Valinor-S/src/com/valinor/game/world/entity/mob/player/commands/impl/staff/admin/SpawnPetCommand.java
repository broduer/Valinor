package com.valinor.game.world.entity.mob.player.commands.impl.staff.admin;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.droptables.ScalarLootTable;
import com.valinor.game.world.entity.mob.npc.pets.Pet;
import com.valinor.game.world.entity.mob.npc.pets.PetAI;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.items.Item;
import com.valinor.util.Utils;

import java.util.Optional;

import static com.valinor.game.content.collection_logs.LogType.BOSSES;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 02, 2022
 */
public class SpawnPetCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (parts.length < 3) {
            player.message("Invalid use of command.");
            player.message("Example: ::spawnpet hc_skii boss");
            return;
        }
        String username = Utils.formatText(parts[1].replace("_", " ")); // after "spawnpet "
        try {
            int boss = Integer.parseInt(parts[2]);
            Optional<Player> plr = World.getWorld().getPlayerByName(username);
            if (plr.isPresent()) {
                ScalarLootTable table = ScalarLootTable.forNPC(boss);
                if (table != null) {
                    Optional<Pet> pet = Optional.ofNullable(Pet.getPetByItem(table.petItem));
                    if (pet.isPresent()) {
                        // Do we already own this pet?
                        boolean caught = plr.get().isPetUnlocked(pet.get().varbit);

                        // RS tries to add it as follower first. That only works if you don't have one.
                        Npc currentPet = plr.get().pet();
                        if (caught && pet.get().varbit != -1) {//Only applies to untradeable pets
                            plr.get().message("You have a funny feeling like you would have been followed...");
                        } else if (currentPet == null) {
                            plr.get().message("You have a funny feeling like you're being followed.");
                            PetAI.spawnPet(plr.get(), pet.get(), false,false);
                        } else {
                            plr.get().inventory().addOrBank(new Item(pet.get().item));
                            plr.get().message("You feel something weird sneaking into your backpack.");
                        }

                        if (!plr.get().isPetUnlocked(pet.get().varbit)) {
                            if (pet.get().varbit != -1) { // -1 means tradeable pet
                                if (!plr.get().isPetUnlocked(pet.get().varbit)) {
                                    plr.get().addUnlockedPet(pet.get().varbit);
                                }
                            }
                        }

                        World.getWorld().sendWorldMessage("<img=1081> <col=844e0d>" + plr.get().getUsername() + " has received a: " + new Item(pet.get().item).name() + ".");
                        Utils.sendDiscordInfoLog("Player " + plr.get().getUsername() + " has received a: " + new Item(pet.get().item).name() + ".", "yell_item_drop");
                    }
                    pet.ifPresent(value -> BOSSES.log(plr.get(), boss, new Item(value.item)));
                }
            }
        } catch (NumberFormatException e) {
            player.message("Command failed: incorrect usage of the command.");
        }
    }

    @Override
    public boolean canUse(Player player) {
        return player.getPlayerRights().isAdminOrGreater(player);
    }
}
