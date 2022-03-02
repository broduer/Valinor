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

import static com.valinor.game.content.collection_logs.CollectionLog.COX_RAIDS_KEY;
import static com.valinor.game.content.collection_logs.LogType.BOSSES;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen | May, 13, 2021, 12:29
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class ChamberOfXericReward {

    public static void unlockOlmlet(Player player) {
        BOSSES.log(player, COX_RAIDS_KEY, new Item(Pet.OLMLET.item));
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
            World.getWorld().sendWorldMessage("<img=452><shad=0>" + Color.RED.wrap(player.getUsername()) + " has unlocked the pet: <col=" + Color.HOTPINK.getColorValue() + ">" + new Item(Pet.OLMLET.item).name() + "</col>.");
        } else {
            player.message("You have a funny feeling like you would have been followed...");
        }
    }

    public static void withdrawReward(Player player) {
        player.inventory().addOrBank(player.getRaidRewards().getItems());
        for (Item item : player.getRaidRewards().getItems()) {
            if (item == null)
                continue;
            if (COXUniqueTable.allItems().stream().anyMatch(i -> i.matchesId(item.getId()))) {
                String worldMessage = "<img=452><shad=0>[<col=" + Color.RAID_PURPLE.getColorValue() + ">Chamber of xeric</col>]</shad></col>: " + Color.BLUE.wrap(player.getUsername()) + " received " + Utils.getAOrAn(item.unnote().name()) + " <shad=0><col=AD800F>" + item.unnote().name() + "</shad>!";
                World.getWorld().sendWorldMessage(worldMessage);
                Utils.sendDiscordInfoLog("Rare drop collected: (COX)" + player.getUsername() + " withdrew " + item.unnote().name() + " ", "raids");
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

    private static final LootTable COXUniqueTable = new LootTable()
        .addTable(1,
            new LootItem(DEXTEROUS_PRAYER_SCROLL, 1, 20),
            new LootItem(ARCANE_PRAYER_SCROLL, 1, 20),
            new LootItem(TWISTED_BUCKLER, 1, 7),
            new LootItem(DRAGON_HUNTER_CROSSBOW, 1, 6),
            new LootItem(DINHS_BULWARK, 1, 5),
            new LootItem(ANCESTRAL_HAT, 1, 5),
            new LootItem(ANCESTRAL_ROBE_TOP, 1, 4),
            new LootItem(ANCESTRAL_ROBE_BOTTOM, 1, 4),
            new LootItem(DRAGON_CLAWS, 1,3),
            new LootItem(ELDER_MAUL, 1,3),
            new LootItem(KODAI_INSIGNIA, 1, 3),
            new LootItem(TWISTED_BOW, 1, 2),
            new LootItem(METAMORPHIC_DUST, 1, 1),
            new LootItem(TWISTED_ANCESTRAL_COLOUR_KIT, 1, 1)
        );

    private static final LootTable regularTable = new LootTable()
        .addTable(1,
            new LootItem(ARMADYL_GODSWORD, 1, 1),
            new LootItem(ARMADYL_CROSSBOW, 1, 1),
            new LootItem(DONATOR_MYSTERY_BOX, 1, 1),
            new LootItem(PKP_TICKET, World.getWorld().random(2000, 5000), 8),
            new LootItem(DRAGON_CROSSBOW, 1, 9),
            new LootItem(DRAGON_THROWNAXE, World.getWorld().random(25, 100), 9),
            new LootItem(DRAGON_KNIFE, World.getWorld().random(25, 100), 9),
            new LootItem(BANDOS_GODSWORD, 1, 7),
            new LootItem(ZAMORAK_GODSWORD, 1, 7),
            new LootItem(SARADOMIN_GODSWORD, 1, 7),
            new LootItem(ARMOUR_MYSTERY_BOX, 1, 6),
            new LootItem(WEAPON_MYSTERY_BOX, 1, 6),
            new LootItem(DRAGONFIRE_SHIELD, 1, 5),
            new LootItem(ABYSSAL_DAGGER_P_13271, 1, 5),
            new LootItem(IMBUEMENT_SCROLL, 5, 9),
            new LootItem(560, 10000, 10), // death rune
            new LootItem(565, 10000, 10), // blood rune
            new LootItem(566, 10000, 10), // soul rune
            new LootItem(892, 2500, 10), // rune arrow
            new LootItem(11212, 1000, 10), // dragon arrow
            new LootItem(3050, 370, 10), // grimy toadflax
            new LootItem(208, 250, 10), // grimy ranarr weed
            new LootItem(210, 196, 10), // grimy irit
            new LootItem(212, 370, 10), // grimy avantoe
            new LootItem(214, 405, 10), // grimy kwuarm
            new LootItem(3052, 200, 10), // grimy snapdragon
            new LootItem(216, 400, 10), // grimy cadantine
            new LootItem(2486, 293, 10), // grimy lantadyme
            new LootItem(218, 212, 10), // grimy dwarf weed
            new LootItem(220, 856, 10), // grimy torstol
            new LootItem(443, 500, 10), // silver ore
            new LootItem(454, 1000, 10), // coal
            new LootItem(445, 1000, 10), // gold ore
            new LootItem(448, 500, 10), // mithril ore
            new LootItem(450, 350, 10), // adamantite ore
            new LootItem(452, 200, 10), // runite ore
            new LootItem(1624, 250, 10), // uncut sapphire
            new LootItem(1622, 225, 10), // uncut emerald
            new LootItem(1620, 200, 10), // uncut ruby
            new LootItem(1618, 175, 10), // uncut diamond
            new LootItem(7937, 10000, 10), // pure essence
            new LootItem(8781, 500, 10), // teak plank
            new LootItem(8783, 500, 10), // mahogany plank
            new LootItem(21047, 1, 10), // torn prayer scroll
            new LootItem(21027, 1, 10), // dark relic
            new LootItem(ONYX, 1, 10) // onyx
        );

    public static void giveRewards(Player player) {
        //uniques
        int personalPoints = player.getAttribOr(AttributeKey.PERSONAL_POINTS, 0);

        if (personalPoints <= 10_000) {//Can't get any loot if below 10,000 points
            player.message("You need at least 10k points to get a drop from Raids.");
            return;
        }



        double chance = (float) personalPoints / 100 / 100.0;
        //System.out.println(chance);
        Player rare = null;
        if (Utils.percentageChance((int) chance)) {
            Item item = rollUnique().copy();
            player.getRaidRewards().add(item);
            BOSSES.log(player, COX_RAIDS_KEY, item);
            rare = player;
        }

        //Only give normal drops when you did not receive any rares.
        //regular drops
        if (player != rare) {
            Item item = rollRegular().copy();
            Item item2 = rollRegular().copy();
            player.getRaidRewards().add(item);
            player.getRaidRewards().add(item2);
            BOSSES.log(player, COX_RAIDS_KEY, item);
            BOSSES.log(player, COX_RAIDS_KEY, item2);
            Utils.sendDiscordInfoLog("Regular Drop: " + player.getUsername() + " Has just received " + item.unnote().name() + " and " + item2.unnote().name() + " from Chambers of Xeric! Personal Points: " + Utils.formatNumber(personalPoints), "cox_reward");
        }
    }

    private static Item rollRegular() {
        return regularTable.rollItem();
    }

    private static Item rollUnique() {
        return COXUniqueTable.rollItem();
    }
}
