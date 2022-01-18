package com.valinor.game.content.raids.chamber_of_secrets;

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
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen | May, 13, 2021, 12:29
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class ChamberOfSecretsReward {

    public static void unlockNagini(Player player) {
        BOSSES.log(player, COS_RAIDS_KEY, new Item(Pet.NAGINI.item));

        // RS tries to add it as follower first. That only works if you don't have one.
        var currentPet = player.pet();
        if (currentPet == null) {
            player.message("You have a funny feeling like you're being followed.");
            PetAI.spawnPet(player, Pet.NAGINI, false);
        } else {
            // Sneak it into their inventory. If that fails, fuck you, no pet for you!
            if (player.inventory().add(new Item(Pet.NAGINI.item), true)) {
                player.message("You feel something weird sneaking into your backpack.");
            }
        }

        Utils.sendDiscordInfoLog("Player " + player.getUsername() + " has received a: " + new Item(Pet.NAGINI.item).name() + ".", "yell_item_drop");
        World.getWorld().sendWorldMessage("<img=1081>" + player.getUsername() + " has unlocked the pet: <col=" + Color.HOTPINK.getColorValue() + ">" + new Item(Pet.NAGINI.item).name() + "</col>.");
    }

    public static void withdrawReward(Player player) {
        if(player.getRaidRewards().isEmpty()) {
            player.message("There are no rewards, if you feel that this is wrong contact a staff member.");
            return;
        }

        player.inventory().addOrBank(player.getRaidRewards().getItems());
        for (Item item : player.getRaidRewards().getItems()) {
            if (item == null)
                continue;
            if (uniqueTable.allItems().stream().anyMatch(i -> i.matchesId(item.getId()))) {
                String worldMessage = "<img=1081>[<col=" + Color.RAID_PURPLE.getColorValue() + ">Chamber of secrets</col>]</shad></col>: " + Color.BLUE.wrap(player.getUsername()) + " received " + Utils.getAOrAn(item.unnote().name()) + " <shad=0><col=AD800F>" + item.unnote().name() + "</shad>!";
                World.getWorld().sendWorldMessage(worldMessage);
                Utils.sendDiscordInfoLog("Rare drop collected: (COS)" + player.getUsername() + " withdrew " + item.unnote().name() + " ", "raids");
            }
        }

        player.getRaidRewards().clear();

        //Roll for pet
        if (World.getWorld().rollDie(650, 1)) {
            unlockNagini(player);
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
            new LootItem(KEY_OF_DROPS, 1, 7),
            new LootItem(SWORD_OF_GRYFFINDOR, 1, 6),
            new LootItem(SWORD_OF_GRYFFINDOR, 1, 6),
            new LootItem(CLOAK_OF_INVISIBILITY, 1, 5),
            new LootItem(MARVOLO_GAUNTS_RING, 1, 4),
            new LootItem(TOM_RIDDLE_DIARY, 1, 3),
            new LootItem(TALONHAWK_CROSSBOW, 1, 2),
            new LootItem(SALAZAR_SLYTHERINS_LOCKET, 1, 2),
            new LootItem(ELDER_WAND_HANDLE, 1, 1),
            new LootItem(ELDER_WAND_STICK, 1, 1)
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
            new LootItem(8783, 500, 1) // mahogany plank
        );

    private static int doubleChestRoll(Player player) {
        int chance;
        chance = switch (player.getMemberRights()) {
            case NONE, EMERALD_MEMBER, SAPPHIRE_MEMBER -> 0;
            case RUBY_MEMBER -> 1;
            case DIAMOND_MEMBER -> 2;
            case DRAGONSTONE_MEMBER -> 3;
            case ONYX_MEMBER -> 4;
            case ZENYTE_MEMBER -> 5;
        };
        return chance;
    }

    public static void giveRewards(Player player) {
        //uniques
        int personalPoints = player.getAttribOr(AttributeKey.PERSONAL_POINTS, 0);

        if (personalPoints <= 10_000) {//Can't get any loot if below 10,000 points
            player.message("You need at least 10k points to get a drop from Raids.");
            return;
        }

        if (personalPoints > 100_000) {
            personalPoints = 100_000;
        }

        double chance = (float) personalPoints / 100 / 100.0;
        //System.out.println(chance);
        Player rare = null;
        if (Utils.percentageChance((int) chance)) {
            int rolls = Utils.percentageChance(doubleChestRoll(player)) ? 2 : 1;
            if (rolls == 2)
                player.message(Color.PURPLE.wrap("You received an extra drop roll because of your member rank."));
            for (int i = 0; i < rolls; i++) {
                Item item = rollUnique();
                boolean added = player.getRaidRewards().add(item);
                BOSSES.log(player, COS_RAIDS_KEY, item);
                rare = player;
            }
        }

        //Only give normal drops when you did not receive any rares.
        //regular drops
        if (player != rare) {
            int rolls = Utils.percentageChance(doubleChestRoll(player)) ? 2 : 1;
            if (rolls == 2)
                player.message(Color.PURPLE.wrap("You received an extra drop roll because of your member rank."));
            for (int i = 0; i < rolls; i++) {
                Item item = rollRegular();
                Item item2 = rollRegular();
                player.getRaidRewards().add(item);
                player.getRaidRewards().add(item2);
                Utils.sendDiscordInfoLog("Regular Drop: " + player.getUsername() + " Has just received " + item.unnote().name() + " and " + item2.unnote().name() + " from Chambers of Secrets! Personal Points: " + Utils.formatNumber(personalPoints), "cos_reward");
            }
        }
    }

    private static Item rollRegular() {
        return regularTable.rollItem();
    }

    private static Item rollUnique() {
        return uniqueTable.rollItem();
    }
}
