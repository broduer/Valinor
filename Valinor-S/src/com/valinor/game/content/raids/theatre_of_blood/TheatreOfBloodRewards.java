package com.valinor.game.content.raids.theatre_of_blood;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.npc.pets.Pet;
import com.valinor.game.world.entity.mob.npc.pets.PetAI;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.loot.LootItem;
import com.valinor.game.world.items.loot.LootTable;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import static com.valinor.game.content.collection_logs.CollectionLog.TOB_RAIDS_KEY;
import static com.valinor.game.content.collection_logs.LogType.BOSSES;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 07, 2022
 */
public class TheatreOfBloodRewards {

    public static void unlockLilZik(Player player) {
        BOSSES.log(player, TOB_RAIDS_KEY, new Item(Pet.LIL_ZIK.item));
        if (!PetAI.hasUnlocked(player, Pet.LIL_ZIK)) {
            // Unlock the varbit. Just do it, rather safe than sorry.
            player.addUnlockedPet(Pet.LIL_ZIK.varbit);

            // RS tries to add it as follower first. That only works if you don't have one.
            var currentPet = player.pet();
            if (currentPet == null) {
                player.message("You have a funny feeling like you're being followed.");
                PetAI.spawnPet(player, Pet.LIL_ZIK, false);
            } else {
                // Sneak it into their inventory. If that fails, fuck you, no pet for you!
                if (player.inventory().add(new Item(Pet.LIL_ZIK.item), true)) {
                    player.message("You feel something weird sneaking into your backpack.");
                } else {
                    player.message("Speak to Probita to claim your pet!");
                }
            }

            Utils.sendDiscordInfoLog("Player " + player.getUsername() + " has received a: " + new Item(Pet.LIL_ZIK.item).name() + ".", "yell_item_drop");
            World.getWorld().sendWorldMessage("<img=452><shad=0>" + Color.RED.wrap(player.getUsername()) + " has unlocked the pet: <col=" + Color.HOTPINK.getColorValue() + ">" + new Item(Pet.LIL_ZIK.item).name() + "</col>.");
        } else {
            player.message("You have a funny feeling like you would have been followed...");
        }
    }

    public static boolean containsRare(Player partyMember) {
        for (Item item : partyMember.getRaidRewards().getItems()) {
            if (item == null)
                continue;
            if (TOBUniqueTable.allItems().stream().anyMatch(i -> i.matchesId(item.getId()))) {
                return true;
            }
        }
        return false;
    }

    public static void withdrawReward(Player player) {
        player.inventory().addOrBank(player.getRaidRewards().getItems());
        for (Item item : player.getRaidRewards().getItems()) {
            if (item == null)
                continue;
            if (TOBUniqueTable.allItems().stream().anyMatch(i -> i.matchesId(item.getId()))) {
                String worldMessage = "<img=452><shad=0>[<col=" + Color.RAID_PURPLE.getColorValue() + ">Theatre of blood</col>]</shad></col>: " + Color.BLUE.wrap(player.getUsername()) + " received " + Utils.getAOrAn(item.unnote().name()) + " <shad=0><col=AD800F>" + item.unnote().name() + "</shad>!";
                World.getWorld().sendWorldMessage(worldMessage);
                Utils.sendDiscordInfoLog("Rare drop collected: (TOB)" + player.getUsername() + " withdrew " + item.unnote().name() + " ", "raids");
            }
        }
        player.getRaidRewards().clear();

        //Roll for pet
        if (World.getWorld().rollDie(650, 1)) {
            unlockLilZik(player);
        }
    }

    public static void displayRewards(Player player) { // shows
        int totalRewards = player.getRaidRewards().getItems().length;

        // clear slots
        player.getPacketSender().sendItemOnInterfaceSlot(12022, null, 0);
        player.getPacketSender().sendItemOnInterfaceSlot(12023, null, 0);
        player.getPacketSender().sendItemOnInterfaceSlot(12024, null, 0);

        player.getInterfaceManager().open(12220);

        if (totalRewards >= 1) {
            Item reward1 = player.getRaidRewards().getItems()[0];
            player.getPacketSender().sendItemOnInterfaceSlot(12222, reward1, 0);
        }

        if (totalRewards >= 2) {
            Item reward2 = player.getRaidRewards().getItems()[1];
            player.getPacketSender().sendItemOnInterfaceSlot(12223, reward2, 0);
        }

        if (totalRewards >= 3) {
            Item reward3 = player.getRaidRewards().getItems()[2];
            player.getPacketSender().sendItemOnInterfaceSlot(12224, reward3, 0);
        }
    }

    private static final LootTable TOBUniqueTable = new LootTable()
        .addTable(1,
            new LootItem(AVERNIC_DEFENDER_HILT, 1, 6),
            new LootItem(JUSTICIAR_FACEGUARD, 1, 5),
            new LootItem(JUSTICIAR_CHESTGUARD, 1, 4),
            new LootItem(JUSTICIAR_LEGGUARDS, 1, 4),
            new LootItem(SANGUINESTI_STAFF, 1, 3),
            new LootItem(GHRAZI_RAPIER, 1, 3),
            new LootItem(SCYTHE_OF_VITUR, 1, 2),
            new LootItem(SANGUINE_DUST, 1, 1),
            new LootItem(SANGUINE_ORNAMENT_KIT, 1, 1),
            new LootItem(HOLY_ORNAMENT_KIT, 1, 1)
        );

    private static final LootTable regularTable = new LootTable()
        .addTable(1,
            new LootItem(MOLTEN_GLASS, 125, 1), // molten glass
            new LootItem(560, 10000, 1), // death rune
            new LootItem(565, 10000, 1), // blood rune
            new LootItem(566, 10000, 1), // soul rune
            new LootItem(892, 2500, 1), // rune arrow
            new LootItem(11212, 1000, 1), // dragon arrow
            new LootItem(3050, 370, 1), // grimy toadflax
            new LootItem(208, 250, 1), // grimy ranarr weed
            new LootItem(210, 196, 1), // grimy irit
            new LootItem(212, 370, 1), // grimy avantoe
            new LootItem(214, 405, 1), // grimy kwuarm
            new LootItem(3052, 200, 1), // grimy snapdragon
            new LootItem(216, 400, 1), // grimy cadantine
            new LootItem(2486, 293, 1), // grimy lantadyme
            new LootItem(218, 212, 1), // grimy dwarf weed
            new LootItem(220, 856, 1), // grimy torstol
            new LootItem(443, 500, 1), // silver ore
            new LootItem(454, 1000, 1), // coal
            new LootItem(445, 1000, 1), // gold ore
            new LootItem(448, 500, 1), // mithril ore
            new LootItem(450, 350, 1), // adamantite ore
            new LootItem(452, 200, 1), // runite ore
            new LootItem(1624, 250, 1), // uncut sapphire
            new LootItem(1622, 225, 1), // uncut emerald
            new LootItem(1620, 200, 1), // uncut ruby
            new LootItem(1618, 175, 1), // uncut diamond
            new LootItem(7937, 10000, 1), // pure essence
            new LootItem(8781, 500, 1), // teak plank
            new LootItem(8783, 500, 1) // mahogany plank
        );

    public static void giveRewards(Player player) {
        //uniques
        int personalPoints = player.getAttribOr(AttributeKey.PERSONAL_POINTS, 0);
        if (personalPoints <= 10_000) {//Can't get any loot if below 10,000 points
            player.message("You need at least 10k points to get a drop from Raids.");
            return;
        }

        if (personalPoints > 180_000 && !player.getPlayerRights().isDeveloperOrGreater(player)) {
            personalPoints = 180_000;
        }

        double chance = (float) personalPoints / 100 / 100.0;
        //System.out.println("chance: "+chance);
        Player rare = null;
        if (Utils.percentageChance((int) chance)) {
            Item item = rollUnique().copy();
            player.getRaidRewards().add(item);
            BOSSES.log(player, TOB_RAIDS_KEY, item);
            rare = player;
        }

        //Only give normal drops when you did not receive any rares.
        //regular drops
        if (player != rare) {
            Item item1 = rollRegular().copy();
            Item item2 = rollRegular().copy();
            Item item3 = rollRegular().copy();
            player.getRaidRewards().add(item1);
            player.getRaidRewards().add(item2);
            player.getRaidRewards().add(item3);
            Utils.sendDiscordInfoLog("Regular Drop: " + player.getUsername() + " Has just received " + item1.unnote().name() + ", " + item2.unnote().name() + " and " + item3.unnote().name() + " from Theatre of blood! Personal Points: " + Utils.formatNumber(personalPoints), "tob_reward");
        }
    }

    private static Item rollRegular() {
        return regularTable.rollItem();
    }

    private static Item rollUnique() {
        return TOBUniqueTable.rollItem();
    }
}
