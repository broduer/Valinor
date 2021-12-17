package com.valinor.game.content.raids.chamber_of_xeric;

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

import static com.valinor.game.content.collection_logs.CollectionLog.COS_RAIDS_KEY;
import static com.valinor.game.content.collection_logs.LogType.BOSSES;
import static com.valinor.game.content.collection_logs.LogType.OTHER;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen | May, 13, 2021, 12:29
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class ChamberOfXericReward {

    public static void unlockOlmlet(Player player) {
        BOSSES.log(player, COS_RAIDS_KEY, new Item(Pet.OLMLET.item));
        if (!PetAI.hasUnlocked(player, Pet.OLMLET)) {
            // Unlock the varbit. Just do it, rather safe than sorry.
            player.addUnlockedPet(Pet.OLMLET.varbit);

            // RS tries to add it as follower first. That only works if you don't have one.
            var currentPet = player.pet();
            if (currentPet == null) {
                player.message("You have a funny feeling like you're being followed.");
                PetAI.spawnPet(player, Pet.OLMLET, false);
            } else {
                // Sneak it into their inventory. If that fails, fuck you, no pet for you!
                if (player.inventory().add(new Item(Pet.OLMLET.item), true)) {
                    player.message("You feel something weird sneaking into your backpack.");
                } else {
                    player.message("Speak to Probita to claim your pet!");
                }
            }

            Utils.sendDiscordInfoLog("Player " + player.getUsername() + " has received a: " + new Item(Pet.OLMLET.item).name() + ".", "yell_item_drop");
            World.getWorld().sendWorldMessage("<img=1081>" + player.getUsername() + " has unlocked the pet: <col=" + Color.HOTPINK.getColorValue() + ">" + new Item(Pet.OLMLET.item).name() + "</col>.");
        } else {
            player.message("You have a funny feeling like you would have been followed...");
        }
    }

    public static void withdrawReward(Player player) {
        player.inventory().addOrBank(player.getRaidRewards().getItems());
        for (Item item : player.getRaidRewards().getItems()) {
            if (item == null)
                continue;
            if (ChamberOfXericReward.uniqueTable.allItems().stream().anyMatch(i -> item.matchesId(item.getId()))) {
                String worldMessage = "<img=1081>[<col=" + Color.RAID_PURPLE.getColorValue() + ">Chambers of Xerics</col>]</shad></col>: " + Color.BLUE.wrap(player.getUsername()) + " received " + Utils.getAOrAn(item.unnote().name()) + " <shad=0><col=AD800F>" + item.unnote().name() + "</shad>!";
                World.getWorld().sendWorldMessage(worldMessage);
            }
        }
        player.getRaidRewards().clear();

        //Roll for pet
        if (World.getWorld().rollDie(650, 1)) {
            unlockOlmlet(player);
        }
    }

    public static void displayRewards(Player player) { // shows
        int totalRewards = player.getRaidRewards().getItems().length;

        // clear slots
        player.getPacketSender().sendItemOnInterfaceSlot(12022, null, 0);
        player.getPacketSender().sendItemOnInterfaceSlot(12023, null, 0);
        player.getPacketSender().sendItemOnInterfaceSlot(12024, null, 0);

        player.getInterfaceManager().open(12020);

        if (totalRewards >= 1) {
            Item reward1 = player.getRaidRewards().getItems()[0];
            player.getPacketSender().sendItemOnInterfaceSlot(12022, reward1, 0);
        }

        if (totalRewards >= 2) {
            Item reward2 = player.getRaidRewards().getItems()[1];
            player.getPacketSender().sendItemOnInterfaceSlot(12023, reward2, 0);
        }

        player.getPacketSender().sendItemOnInterfaceSlot(12024, new Item(DARK_JOURNAL), 0);
    }

    private static final LootTable uniqueTable = new LootTable()
        .addTable(1,
            new LootItem(21034, 1, 20), // dexterous scroll
            new LootItem(21079, 1, 20), // arcane scroll
            new LootItem(21000, 1, 4), // twisted buckler
            new LootItem(21012, 1, 4), // dragon hunter crossbow
            new LootItem(21015, 1, 3), // dinh's bulwark
            new LootItem(21018, 1, 3), // ancestral hat
            new LootItem(21021, 1, 3), // ancestral top
            new LootItem(21024, 1, 3), // ancestral bottom
            new LootItem(13652, 1,3), // dragon claws
            new LootItem(21003, 1,2), // elder maul
            new LootItem(21043, 1, 2), // kodai insignia
            new LootItem(20997, 1, 2), // twisted bow
            new LootItem(22386, 1, 1) // Metamorphic dust
        );

    private static final LootTable regularTable = new LootTable()
        .addTable(1,
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
            new LootItem(8783, 500, 1), // mahogany plank
            new LootItem(21047, 1, 1), // torn prayer scroll
            new LootItem(21027, 1, 1) // dark relic
        );

    public static void giveRewards(Player player) {
        //uniques
        int personalPoints = player.getAttribOr(AttributeKey.PERSONAL_POINTS, 0);

        if (personalPoints <= 10_000) {//Can't get any loot if below 10,000 points
            player.message("You need at least 10k points to get a drop from Raids.");
            return;
        }

        if (personalPoints > 150_000) {
            personalPoints = 150_000;
        }

        double chance = (float) personalPoints / 100 / 100.0;
        //System.out.println(chance);
        Player rare = null;
        if (Utils.percentageChance((int) chance)) {
            Item item = rollUnique();
            boolean added = player.getRaidRewards().add(item);
            OTHER.log(player, COS_RAIDS_KEY, item);
            Utils.sendDiscordInfoLog("Rare drop: " + player.getUsername() + " Has just received " + item.unnote().name() + " from Chambers of Xeric! Party Points: " + Utils.formatNumber(personalPoints) + " [debug: added=" + added + "]", "cox_reward");
            rare = player;
        }

        //Only give normal drops when you did not receive any rares.
        //regular drops
        if (player != rare) {
            Item item = rollRegular();
            player.getRaidRewards().add(item);
            Utils.sendDiscordInfoLog("Regular Drop: " + player.getUsername() + " Has just received " + item.unnote().name() + " from Chambers of Xeric! Personal Points: " + Utils.formatNumber(personalPoints), "cox_reward");
        }
    }

    private static Item rollRegular() {
        return regularTable.rollItem();
    }

    private static Item rollUnique() {
        return uniqueTable.rollItem();
    }
}
