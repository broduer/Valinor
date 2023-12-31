package com.valinor.game.content.treasure;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.npc.pets.Pet;
import com.valinor.game.world.entity.mob.npc.pets.PetAI;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.text.NumberFormat;

import static com.valinor.util.CustomItemIdentifiers.TREASURE_CASKET;

/**
 * Created by Situations on 2016-11-05.
 */
public class TreasureRewardCaskets {

    public static boolean openCasket(Player player, Item casket) {
        if (casket.getId() == TREASURE_CASKET) {
            reward(player, new Item(TREASURE_CASKET));
            return true;
        }
        return false;
    }

    public static void unlockBloodhound(Player player) {
        if (!PetAI.hasUnlocked(player, Pet.BLOODHOUND)) {
            // Unlock the varbit. Just do it, rather safe than sorry.
            player.addUnlockedPet(Pet.BLOODHOUND.varbit);

            // RS tries to add it as follower first. That only works if you don't have one.
            var currentPet = player.pet();
            if (currentPet == null) {
                player.message("You have a funny feeling like you're being followed.");
                PetAI.spawnPet(player, Pet.BLOODHOUND, false);
            } else {
                // Sneak it into their inventory. If that fails, fuck you, no pet for you!
                if (player.inventory().add(new Item(Pet.BLOODHOUND.item), true)) {
                    player.message("You feel something weird sneaking into your backpack.");
                } else {
                    player.message("Speak to Probita to claim your pet!");
                }
            }

            World.getWorld().sendWorldMessage("<img=452><shad=0>" + Color.RED.wrap(player.getUsername()) + " has unlocked the pet: <col="+Color.HOTPINK.getColorValue()+">" + new Item(Pet.BLOODHOUND.item).name()+ "</col>.");
        } else {
            player.message("You have a funny feeling like you would have been followed...");
        }
    }

    private static void reward(Player player, Item casket) {
        if (player.inventory().remove(casket, true)) {
            Rewards.generateReward(player);

            if (Utils.rollDie(2500, 1))
                unlockBloodhound(player);

            //Add the reward to the players inventory, or bank if no space
            player.clueScrollReward().forEach(item -> {
                if (item != null) {
                    player.inventory().addOrBank(new Item(item.getId(), item.getAmount()));
                }
            });

            player.message("<col=3300ff>Your treasure is worth around " + NumberFormat.getInstance().format(value(player)) + " Coins!</col>");

            //Display the interface
            //System.out.println(Arrays.toString(player.clueScrollReward().toArray()));
            player.getPacketSender().sendItemOnInterface(6963, player.clueScrollReward().toArray());
            player.getInterfaceManager().open(6960);

            //Clear the clue scroll reward and unlock the player
            player.clearAttrib(AttributeKey.CLUE_SCROLL_REWARD);
        }
    }

    // Calculate what the clue scroll's reward value is
    private static int value(Player player) {
        int rewardTotal = 0;

        for (Item reward : player.clueScrollReward()) {
            if (reward != null) {
                rewardTotal += reward.getValue() * reward.getAmount();
            }
        }

        return rewardTotal;
    }

}
