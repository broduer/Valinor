package com.valinor.game.content.mechanics;

import com.valinor.fs.ItemDefinition;
import com.valinor.game.content.events.wilderness_key.WildernessKeyPlugin;
import com.valinor.game.content.areas.wilderness.content.revenant_caves.AncientArtifacts;
import com.valinor.game.content.items_kept_on_death.Conversions;
import com.valinor.game.content.items_kept_on_death.ItemsKeptOnDeath;
import com.valinor.game.content.mechanics.break_items.BrokenItem;
import com.valinor.game.content.minigames.MinigameManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.content.areas.wilderness.content.bounty_hunter.emblem.BountyHunterEmblem;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.combat.skull.SkullType;
import com.valinor.game.world.entity.combat.skull.Skulling;
import com.valinor.game.world.entity.mob.npc.pets.Pet;
import com.valinor.game.world.entity.mob.player.GameMode;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.net.packet.incoming_packets.PickupItemPacketListener;
import com.valinor.test.unit.IKODTest;
import com.valinor.test.unit.PlayerDeathConvertResult;
import com.valinor.test.unit.PlayerDeathDropResult;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.valinor.util.CustomItemIdentifiers.RUNE_POUCH_I;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen | June, 27, 2021, 12:56
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class ItemsOnDeath {

    /**
     * The items the Player lost.
     */
    private static final List<Item> lostItems = new ArrayList<>();

    /**
     * If our account has the ability for the custom Pet Shout mechanic - where when you kill someone
     * your pet will shout something.
     */
    public static boolean hasShoutAbility(Player player) {
        // Are we a user with the mechanic enabled
        return player.getAttribOr(AttributeKey.PET_SHOUT_ABILITY, false);
    }

    /**
     * Calculates and drops all of the items from {@code player} for {@code killer}.
     * @return
     */
    public static PlayerDeathDropResult droplootToKiller(Player player, Mob killer) {
        var in_tournament = player.inActiveTournament() || player.isInTournamentLobby();
        var nightmare_area = player.tile().region() == 15515;
        var donator_zone = player.tile().region() == 13462;
        var vorkath_area = player.tile().region() == 9023;
        var hydra_area = player.tile().region() == 5536;
        var zulrah_area = player.tile().region() == 9007 || player.tile().region() == 9008;
        var safe_accounts = player.getUsername().equalsIgnoreCase("Box test");
        var duel_arena = player.getDueling().inDuel() || player.getDueling().endingDuel();
        var pest_control = player.tile().region() == 10536;
        var raids_area = player.getRaids() != null && player.getRaids().raiding(player);
        var minigame_safe_death = player.getMinigame() != null && player.getMinigame().getType().equals(MinigameManager.ItemType.SAFE);
        var hunleff_area = player.tile().region() == 6810;
        Mob lastAttacker = player.getAttribOr(AttributeKey.LAST_DAMAGER,null);
        final boolean npcFlag = lastAttacker != null && lastAttacker.isNpc() && !WildernessArea.inWilderness(player.tile());

        // If we're in FFA clan wars, don't drop our items.
        // Have these safe area checks before we do some expensive code ... looking for who killed us.
        if (npcFlag || nightmare_area || donator_zone || vorkath_area || zulrah_area || hydra_area || safe_accounts || duel_arena || pest_control || raids_area || in_tournament || minigame_safe_death || hunleff_area) {
            Utils.sendDiscordInfoLog("Player: "+ player.getUsername() + " died in a safe area " + (killer != null && killer.isPlayer() ? " to " + killer.toString() : ""), "player_death");
            Utils.sendDiscordInfoLog("Safe deaths activated for: "+ player.getUsername() + "" + (killer != null && killer.isPlayer() ? " to " + killer.toString() : ""+ "nightmare_area: "+nightmare_area+" donator_zone: "+donator_zone+" vorkath_area: "+vorkath_area+" hydra_area: "+hydra_area+" zulrah_area: "+zulrah_area+" in safe_accounts: "+safe_accounts+" duel_arena: "+duel_arena+" pest_control: "+pest_control+" raids_area: "+raids_area+" in_tournament: "+in_tournament+" minigame_safe_death: "+minigame_safe_death+" hunleff_area: "+hunleff_area), "player_death");
            return null;
        }

        // Past this point.. we're in a dangerous zone! Drop our items....

        Player theKiller = killer == null || killer.isNpc() ? player : killer.getAsPlayer();

        final Tile tile = player.tile();

        // Game Lists
        LinkedList<Item> toDrop = new LinkedList<>();
        List<Item> keep = new LinkedList<>();
        List<Item> toDropPre = new LinkedList<>();

        // Unit Testing Lists
        List<Item> outputDrop = new ArrayList<>(toDrop.size());
        List<Item> outputKept = new ArrayList<>(1);
        List<Item> outputDeleted = new ArrayList<>(0);
        List<PlayerDeathConvertResult> outputConverted = new ArrayList<>(0);

        player.getEquipment().forEach(toDropPre::add);
        player.inventory().forEach(item -> {
            if (!item.matchesId(LOOTING_BAG) && !item.matchesId(LOOTING_BAG_22586) && !item.matchesId(RUNE_POUCH)) { // always lost
                toDropPre.add(item);
            } else {
                outputDeleted.add(item); // looting bag goes into deleted
            }
        });
        player.getEquipment().clear(); // everything gets cleared no matter what
        player.inventory().clear();

        toDrop.addAll(toDropPre);

        // Any extra custom logic here for alwaysKept under special circumstances
        List<Item> keptPets = toDrop.stream().filter(i -> {
            Pet petByItem = Pet.getPetByItem(i.getId());
            if (petByItem == null)
                return false;
            boolean canTransfer = petByItem.varbit == -1;
            boolean loseByTransfer = player.getSkullType() != SkullType.NO_SKULL;
            return !(canTransfer && loseByTransfer); // this will be lost on death
        }).collect(Collectors.toList());
        keep.addAll(keptPets);
        for (Item keptPet : keptPets) {
            toDrop.remove(keptPet);
        }

        //System.out.println("Dropping: " + Arrays.toString(toDrop.toArray()));

        // remove always kept before calculating kept-3 by value
        List<Item> alwaysKept = toDrop.stream().filter(ItemsKeptOnDeath::alwaysKept).collect(Collectors.toList());
        IKODTest.debug("death alwaysKept list : " + Arrays.toString(alwaysKept.stream().map(Item::toShortValueString).toArray()));
        keep.addAll(alwaysKept);
        toDrop.removeIf(ItemsKeptOnDeath::alwaysKept);

        // custom always lost
        final List<Item> alwaysLostSpecial = toDrop.stream().filter(i -> i.getId() == RUNE_POUCH || i.getId() == LOOTING_BAG || i.getId() == LOOTING_BAG_22586).collect(Collectors.toList());
        for (Item item : alwaysLostSpecial) {
            toDrop.remove(item); // not included in kept-3 if unskulled
            Item currency;
            currency = new Item(COINS_995, item.getId() == LOOTING_BAG || item.getId() == LOOTING_BAG_22586 ? 500_000 : 2_500_000);

            outputDrop.add(currency); // this list isn't whats dropped its for logging
            GroundItemHandler.createGroundItem(new GroundItem(currency, player.tile(), theKiller)); // manually drop it here
        }

        // Sort remaining lost items by value.
        toDrop.sort((o1, o2) -> {
            o1 = o1.unnote();
            o2 = o2.unnote();

            ItemDefinition def = o1.definition(World.getWorld());
            ItemDefinition def2 = o2.definition(World.getWorld());

            int v1 = 0;
            int v2 = 0;

            if (def != null) {
                v1 = o1.getValue();
            }
            if (def2 != null) {
                v2 = o2.getValue();
            }

            return Integer.compare(v2, v1);
        });
        int keptItems = (Skulling.skulled(player) ? 0 : 3);

        // On Ultimate Iron Man, you drop everything!
        if (player.gameMode() == GameMode.ULTIMATE) {
            keptItems = 0;
        }

        boolean protection_prayer = Prayers.usingPrayer(player, Prayers.PROTECT_ITEM);
        if (protection_prayer) {
            keptItems++;
        }

        //#Update as of 16/02/2021 when smited you're actually smited the pet effect will not work!
        var reaper = player.hasPetOut("Grim Reaper pet") || player.hasPetOut("Blood Reaper pet");
        if (reaper && protection_prayer) {
            keptItems++;
        }
        // On Ultimate Iron Man, you drop everything!
        if (player.getSkullType().equals(SkullType.RED_SKULL) || player.gameMode() == GameMode.ULTIMATE) {
            keptItems = 0;
        }
        IKODTest.debug("keeping " + keptItems + " items");

        while (keptItems-- > 0 && !toDrop.isEmpty()) {
            Item head = toDrop.peek();
            if (head == null) {
                keptItems++;
                toDrop.poll();
                continue;
            }
            keep.add(new Item(head.getId(), 1));

            //Always drop wildy keys
            if(head.getId() == CustomItemIdentifiers.WILDERNESS_KEY) {
                if (WildernessArea.inWilderness(player.tile())) {
                    player.inventory().remove(CustomItemIdentifiers.WILDERNESS_KEY, Integer.MAX_VALUE);
                    PickupItemPacketListener.respawn(Item.of(CustomItemIdentifiers.WILDERNESS_KEY), tile, 3);
                    WildernessKeyPlugin.announceKeyDrop(player, tile);
                    keep.remove(head);
                }
            }

            if (head.getAmount() == 1) { // Amount 1? Remove the item entirely.
                Item delete = toDrop.poll();
                IKODTest.debug("kept " + delete.toShortString());
            } else { // Amount more? Subtract one amount.
                int index = toDrop.indexOf(head);
                toDrop.set(index, new Item(head, head.getAmount() - 1));
                IKODTest.debug("kept " + toDrop.get(index).toShortString());
            }
        }
        for (Item item : keep) {
            // Handle item breaking..
            BrokenItem brokenItem = BrokenItem.get(item.getId());
            if (brokenItem != null) {
                player.getPacketSender().sendMessage("Your " + item.unnote().name() + " has been broken. You can fix it by talking to").sendMessage("Perdu who is located in Edgeville at the furnace.");
                item.setId(brokenItem.brokenItem);

                //Drop coins for the killer
                GroundItem groundItem = new GroundItem(new Item(COINS_995, brokenItem.costToRepair), player.tile(), theKiller);
                GroundItemHandler.createGroundItem(groundItem);
            }
            player.inventory().add(item, true);
        }

        // Looting bag items are NOT in top-3 kept from prot item/unskulled. Always lost.
        if (outputDeleted.stream().anyMatch(i -> i.getId() == LOOTING_BAG || i.getId() == LOOTING_BAG_22586)) {
            Item[] lootingBag = player.getLootingBag().toNonNullArray(); // bypass check if carrying bag since inv is cleared above
            toDrop.addAll(Arrays.asList(lootingBag));
            Utils.sendDiscordInfoLog(player.getUsername() + " (Skulled: " + Skulling.skulled(player) + ") looting bag lost items: " + Arrays.toString(Arrays.asList(lootingBag).toArray()) + (killer != null && killer.isPlayer() ? " to " + killer.getMobName() : ""), "player_death");

            player.getLootingBag().clear();
            IKODTest.debug("looting bag had now: " + Arrays.toString(Arrays.asList(lootingBag).toArray()));
        }

        // Rune pouch items are NOT in top-3 kept from prot item/unskulled. Always lost.
        if (outputDeleted.stream().anyMatch(i -> i.getId() == RUNE_POUCH || i.getId() == RUNE_POUCH_I)) {
            Item[] runePouch = player.getRunePouch().toNonNullArray(); // bypass check if carrying pouch since inv is cleared above
            toDrop.addAll(Arrays.asList(runePouch));
            player.getRunePouch().clear();
            IKODTest.debug("rune pouch had now: " + Arrays.toString(Arrays.asList(runePouch).toArray()));
        }

        if (player.hasPetOut("Niffler")) {
            //Get the current stored item list
            var nifflerItemsStored = player.<ArrayList<Item>>getAttribOr(AttributeKey.NIFFLER_ITEMS_STORED, new ArrayList<Item>());
            if (nifflerItemsStored != null) {
                toDrop.addAll(nifflerItemsStored);
                Utils.sendDiscordInfoLog(player.getUsername() + " (Skulled: " + Skulling.skulled(player) + ") niffler lost items: " + Arrays.toString(nifflerItemsStored.toArray()) + (killer != null && killer.isPlayer() ? " to " + killer.getMobName() : ""), "player_death");

                nifflerItemsStored.clear();
                IKODTest.debug("niffler had now: " + Arrays.toString(nifflerItemsStored.toArray()));
            }
        }

        lostItems.clear();
        IKODTest.debug("Dropping now: " + Arrays.toString(toDrop.stream().map(Item::toShortString).toArray()));

        outputKept.addAll(keep);
        IKODTest.debug("Kept-3: " + Arrays.toString(keep.stream().map(Item::toShortString).toArray()));

        LinkedList<Item> toDropConverted = new LinkedList<>();

        toDrop.forEach(item -> {
            if(item.getId() == CustomItemIdentifiers.WILDERNESS_KEY) {
                if (WildernessArea.inWilderness(player.tile())) {
                    player.inventory().remove(CustomItemIdentifiers.WILDERNESS_KEY, Integer.MAX_VALUE);
                    PickupItemPacketListener.respawn(Item.of(CustomItemIdentifiers.WILDERNESS_KEY), tile, 3);
                    WildernessKeyPlugin.announceKeyDrop(player, tile);
                    return;
                }
            }

            if (item.getId() == AncientArtifacts.ANCIENT_EFFIGY.getItemId()
                || item.getId() == AncientArtifacts.ANCIENT_EMBLEM.getItemId()
                || item.getId() == AncientArtifacts.ANCIENT_MEDALLION.getItemId()
                || item.getId() == AncientArtifacts.ANCIENT_RELIC.getItemId()
                || item.getId() == AncientArtifacts.ANCIENT_STATUETTE.getItemId()
                || item.getId() == AncientArtifacts.ANCIENT_TOTEM.getItemId()
                || item.getId() == AncientArtifacts.DARK_ANCIENT_EMBLEM.getItemId()
                || item.getId() == AncientArtifacts.DARK_ANCIENT_TOTEM.getItemId()
                || item.getId() == AncientArtifacts.DARK_ANCIENT_STATUETTE.getItemId()
                || item.getId() == AncientArtifacts.DARK_ANCIENT_MEDALLION.getItemId()
                || item.getId() == AncientArtifacts.DARK_ANCIENT_EFFIGY.getItemId()
                || item.getId() == AncientArtifacts.DARK_ANCIENT_RELIC.getItemId()) {
                GroundItemHandler.createGroundItem(new GroundItem(new Item(item.getId()), player.tile(), theKiller));
                outputDrop.add(new Item(item.getId()));
                // dont add to toDropConverted, we're manually dropping it
                return;
            }

            //Drop emblems but downgrade them a tier.
            if (item.getId() == BountyHunterEmblem.ANTIQUE_EMBLEM_TIER_1.getItemId()
                || item.getId() == BountyHunterEmblem.ANTIQUE_EMBLEM_TIER_2.getItemId() ||
                item.getId() == BountyHunterEmblem.ANTIQUE_EMBLEM_TIER_3.getItemId() ||
                item.getId() == BountyHunterEmblem.ANTIQUE_EMBLEM_TIER_4.getItemId() ||
                item.getId() == BountyHunterEmblem.ANTIQUE_EMBLEM_TIER_5.getItemId() ||
                item.getId() == BountyHunterEmblem.ANTIQUE_EMBLEM_TIER_6.getItemId() ||
                item.getId() == BountyHunterEmblem.ANTIQUE_EMBLEM_TIER_7.getItemId() ||
                item.getId() == BountyHunterEmblem.ANTIQUE_EMBLEM_TIER_8.getItemId() ||
                item.getId() == BountyHunterEmblem.ANTIQUE_EMBLEM_TIER_9.getItemId() ||
                item.getId() == BountyHunterEmblem.ANTIQUE_EMBLEM_TIER_10.getItemId()) {

                //Tier 1 shouldnt be dropped cause it cant be downgraded
                if (item.matchesId(BountyHunterEmblem.ANTIQUE_EMBLEM_TIER_1.getItemId())) {
                    return;
                }

                final int lowerEmblem = item.getId() - 2;

                ItemDefinition def = World.getWorld().definitions().get(ItemDefinition.class, lowerEmblem);
                GroundItemHandler.createGroundItem(new GroundItem(new Item(lowerEmblem), player.tile(), theKiller));
                theKiller.message("<col=ca0d0d>" + player.getUsername() + " dropped a " + def.name + "!");
                outputDrop.add(new Item(lowerEmblem));
                // dont add to toDropConverted, we're manually dropping it
                return;
            }

            // IKODTest.debug("dc2: "+item.toShortString());
            boolean[] converted = new boolean[1];
            boolean doNotConvert = true;

            if (item.getId() == SARADOMINS_BLESSED_SWORD) { //Saradomin's blessed sword
                converted[0] = true;
                toDropConverted.add(new Item(SARADOMIN_SWORD));
                toDropConverted.add(new Item(SARADOMINS_TEAR));
            }

            if (!npcFlag && !doNotConvert) { // Don't bother converting for suicide
                for (Conversions con : Conversions.values()) {
                    //These don't convert
                    if (!converted[0] && con.src.getId() == item.getId()) {
                        converted[0] = true; // Set that the original item should not be dropped
                        for (Item out : con.out) {
                            toDropConverted.add(new Item(out));
                        }
                        break;
                    }
                }
                if (converted[0]) {
                    // dont add the OG item to toDropConverted - its already been changed+added above
                    return;
                }
            }

            // Not yet converted... check some more. Only if non-suicide (you got pked) otherwise just be nice and give back the item untouched.
            if (!converted[0] && !npcFlag) {
                if (item.getId() >= 8714 && item.getId() <= 8744) {
                    item = new Item(1201); // Rune kiteshield
                } else if (item.getId() >= 8682 && item.getId() <= 8712) {
                    item = new Item(1157); // Steel fullhelm
                } else if (item.getId() == 12006 && !doNotConvert) {
                    item = new Item(12004); //Abyssal tent -> Kraken tent
                } else if (Item.isCrystal(item.getId())) {
                    item = new Item(4207);
                } else if (item.skillcape()) {
                    item = new Item(995, 11800);
                }
            }

            // if we've got to here, add the original or changed SINGLE item to the newer list
            toDropConverted.add(item);
        });
        // replace the original list with the newer list which reflects changes
        toDrop = toDropConverted;

        // Dropping in-game the finalized items list on death here
        toDrop.forEach(item -> {
            //   IKODTest.debug("dropping check: "+item.toShortString());

            if (ItemsKeptOnDeath.alwaysKept(item)) {
                //System.out.println("Autokeep");
                //QOL OSRS doesn't drop them anymore but spawns in inventory.
                player.inventory().add(item);
                outputKept.add(item);
                return;
            }

            if(player.gameMode().instantPker()) {
                if(item.definition(World.getWorld()).pvpSpawnable) {
                    return;
                }
            }

            //Drop item
            //System.out.println("Creating ground item " + item.getId());
            //Add the items to the lost list regardless of if the player died to a bot.
            lostItems.add(item);

            boolean diedToSelf = theKiller == player;
            //System.out.println("died to npc "+npcFlag+" or died to self "+diedToSelf);
            boolean nifflerShouldLoot = !diedToSelf && !npcFlag;
            //System.out.println("nifflerShouldLoot "+nifflerShouldLoot);

            //Niffler should only pick up items of monsters and players that you've killed.
            if(theKiller.nifflerPetOut() && theKiller.nifflerCanStore(player) && nifflerShouldLoot) {
                if (item.getValue() > 0) {
                    theKiller.nifflerStore(item);
                }
            } else {
                GroundItem g = new GroundItem(item, player.tile(), theKiller);
                GroundItemHandler.createGroundItem(g);
                g.pkedFrom(player.getUsername()); // Mark item as from PvP to avoid ironmen picking it up.
                g.droppedFromGamemode = player.gameMode();
            }
            outputDrop.add(item);
        });

        GroundItemHandler.createGroundItem(new GroundItem(new Item(BONES), player.tile(), theKiller));
        outputDrop.add(new Item(BONES));
        Utils.sendDiscordInfoLog(player.getUsername() + " (Skulled: " + Skulling.skulled(player) + ") lost items: " + Arrays.toString(lostItems.stream().map(Item::toShortString).toArray()) + (killer != null && killer.isPlayer() ? " to " + killer.getMobName() : ""), "player_death");
        //Reset last attacked by, since we already handled it above, and the player is already dead.
        player.clearAttrib(AttributeKey.LAST_DAMAGER);
        return new PlayerDeathDropResult(theKiller, outputDrop, outputKept, outputDeleted, outputConverted);
    }
}
