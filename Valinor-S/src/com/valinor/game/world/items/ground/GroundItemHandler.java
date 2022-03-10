package com.valinor.game.world.items.ground;

import com.valinor.game.GameEngine;
import com.valinor.game.content.events.wilderness_key.WildernessKeyPlugin;
import com.valinor.game.content.duel.Dueling;
import com.valinor.game.content.tournaments.TournamentManager;
import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.combat.skull.SkullType;
import com.valinor.game.world.entity.combat.skull.Skulling;
import com.valinor.game.world.entity.mob.player.GameMode;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem.State;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.Color;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.valinor.util.CustomItemIdentifiers.ELDER_WAND_RAIDS;
import static com.valinor.util.CustomItemIdentifiers.WILDERNESS_KEY;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * A handler for a collection of {@link GroundItem}s
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class GroundItemHandler {

    private static final Logger logger = LogManager.getLogger(GroundItemHandler.class);

    /**
     * A list containing all the ground items
     */
    private static final List<GroundItem> groundItems = new ArrayList<>();

    public static List<GroundItem> getGroundItems() {
        return groundItems;
    }

    /**
     * Checks if the ground item is actually on the clicked location.
     *
     * @param id The ground item being checked
     */
    public static Optional<GroundItem> getGroundItem(int id, Tile tile, Player owner) {
        return groundItems.stream().filter(item -> item.getItem().getId() == id
            && item.getTile().getX() == tile.getX()
            && item.getTile().getY() == tile.getY()
            && (item.getState() == State.SEEN_BY_EVERYONE || item.getOwnerHash() == -1 || owner.getLongUsername() == item.getOwnerHash())
        ).findFirst();
    }

    /**
     * Sends a remove ground item packet to all players.
     *
     * @param groundItem the ground item
     */
    public static boolean sendRemoveGroundItem(GroundItem groundItem) {
        if (!groundItem.isRemoved()) {
            groundItem.setRemoved(true);
            if (groundItem.getState() == State.SEEN_BY_EVERYONE) {
                removeRegionalItem(groundItem);
            } else {
                World.getWorld().getPlayers().stream().filter(p -> Objects.nonNull(p) && p.getLongUsername() == groundItem.getOwnerHash()
                    && p.tile().distance(groundItem.getTile()) <= 64)
                    .forEach(p -> p.getPacketSender().deleteGroundItem(groundItem));
            }
            return true;
        }
        return false;
    }

    /**
     * Removes all ground items when the player leaves the region
     *
     * @param groundItem the ground item
     */
    private static void removeRegionalItem(GroundItem groundItem) {
        for (Player player : World.getWorld().getPlayers()) {
            if (player == null || player.distanceToPoint(groundItem.getTile().getX(), groundItem.getTile().getY()) > 64) {
                continue;
            }

            //We can go ahead and send the remove ground item packet
            player.getPacketSender().deleteGroundItem(groundItem);
        }
    }

    /**
     * Add ground items for players when entering region.
     *
     * @param groundItem The ground item
     */
    private static void addRegionalItem(GroundItem groundItem) {
        for (Player player : World.getWorld().getPlayers()) {
            if (player == null) {
                continue;
            }
            if (player.tile().getLevel() != groundItem.getTile().getLevel() || player.distanceToPoint(groundItem.getTile().getX(), groundItem.getTile().getY()) > 64) {
                continue;
            }

            if (player.getUsername().equalsIgnoreCase("Box test")) {
                continue;
            }

            // If we are globalizing an item, don't re-add it for the owner
            if (player.getLongUsername() != groundItem.getOwnerHash()) {

                //Don't add private items to the region yet, we only add public items
                if (groundItem.getState() == State.SEEN_BY_OWNER) {
                    continue;
                }

                Item item = new Item(groundItem.getItem().getId());

                //If the item is a non-tradable item continue
                if (!item.rawtradable() && item.getId() != ELDER_WAND_RAIDS) {
                    continue;
                }

                //Checks if we're able to view the ground item
                if (player.distanceToPoint(groundItem.getTile().getX(), groundItem.getTile().getY()) <= 60 && player.tile().getLevel() == player.tile().getLevel()) {
                    player.getPacketSender().createGroundItem(groundItem);
                }
            }
        }
    }

    /**
     * The ground item task, removes the ticks
     */
    public static void pulse() {
        long start = System.currentTimeMillis();
        Iterator<GroundItem> iterator = groundItems.iterator();
        while (iterator.hasNext()) {
            GroundItem item = iterator.next();

            if (item.isRemoved()) {
                iterator.remove();
                continue;
            }

            if (item.decreaseTimer() < 1) {
                if (item.getState() == State.SEEN_BY_EVERYONE) {
                    item.setRemoved(true);
                    iterator.remove();
                    removeRegionalItem(item);
                }

                if (item.getState() == State.SEEN_BY_OWNER) {
                    item.setState(State.SEEN_BY_EVERYONE);
                    item.setTimer(200);
                    addRegionalItem(item);
                }
            }
        }
        GameEngine.profile.gitems = (System.currentTimeMillis() - start);
        //logger.info("it took "+end+"ms for processing GroundItems.");
    }

    /**
     * The method that updates all items in the region for {@code player}.
     *
     * @param player the player to update items for.
     */
    public static void updateRegionItems(Player player) {
        for (GroundItem item : groundItems) {
            player.getPacketSender().deleteGroundItem(item);
        }
        for (GroundItem item : groundItems) {

            if (player.tile().getLevel() != item.getTile().getLevel() || player.distanceToPoint(item.getTile().getX(), item.getTile().getY()) > 60) {
                continue;
            }

            Item items = new Item(item.getItem().getId());

            if (items.rawtradable() || item.getOwnerHash() == player.getLongUsername()) {

                if (item.getState() == State.SEEN_BY_EVERYONE || item.getOwnerHash() == player.getLongUsername()) {
                    //System.out.println(String.format("spawned: %s%n", item));
                    player.getPacketSender().createGroundItem(item);
                }
            }
        }
    }

    public static boolean createGroundItem(GroundItem item) {
        Player player = item.getPlayer();
        if (item.getItem().getId() < 0) {
            return false;
        }

        // Nonstackable but more than 1?
        if (item.getItem().getAmount() > 1 && !item.getItem().definition(World.getWorld()).stackable()) {
            for (int i = 0; i < Math.min(10, item.getItem().getAmount()); i++) {
                createGroundItem(new GroundItem(new Item(item.getItem(), 1), item.getTile(), item.getPlayer()));
            }

            return true;
        }

        boolean illegalItem = false;

        List<Integer> ILLEGAL_ITEMS = Arrays.asList(DAWNBRINGER, SARADOMIN_CAPE, GUTHIX_CAPE, ZAMORAK_CAPE, ANTIDRAGON_SHIELD, BRONZE_AXE, BRONZE_PICKAXE);

        if(ILLEGAL_ITEMS.stream().anyMatch(i -> i == item.getItem().getId()) && item.vanishes()) {
            illegalItem = true;
        }

        if(illegalItem) {
            player.message("The "+item.getItem().unnote().name()+" vanishes as it touches the ground.");
            return false;
        }

        if(item.getItem().definition(World.getWorld()).pvpSpawnable
            && player != null && player.gameMode() == GameMode.INSTANT_PKER) {
            return false;
        }

        // Stackable? Can group with existing of the same item on that tile
        if (item.getItem().definition(World.getWorld()).stackable()) {
            for (GroundItem other : groundItems) {
                // Same id, location, still valid
                if (item.getItem().getId() == other.getItem().getId()
                    && item.getTile().getX() == other.getTile().getX()
                    && item.getTile().getY() == other.getTile().getY()
                    && item.getTile().getLevel() == other.getTile().getLevel() && !other.isRemoved()) {

                    // Global or seen by all
                    if (other.getState() == State.SEEN_BY_EVERYONE || (other.getOwnerHash() != -1 && other.getOwnerHash() == item.getOwnerHash())) {

                        // Amount of that item.
                        long existing = other.getItem().getAmount();

                        // If added together total is less than int overload.
                        if (existing + item.getItem().getAmount() <= Integer.MAX_VALUE) {

                            final int oldAmt = other.getItem().getAmount();
                            // Update amount
                            other.getItem().setAmount((int) (existing + item.getItem().getAmount()));

                            // Reset expiry timer, same as dropping a new item.
                            other.setTimer(item.getState() == State.SEEN_BY_EVERYONE ? 200 : 100);

                            if (other.getState() == State.SEEN_BY_EVERYONE) {
                                for (Player p2 : World.getWorld().getPlayers()) {
                                    if (p2 == null || p2.distanceToPoint(other.getTile().getX(), other.getTile().getY()) > 64) {
                                        continue;
                                    }
                                    p2.getPacketSender().updateGroundItemAmount(oldAmt, other);
                                }
                            } else {
                                if (player != null && player.distanceToPoint(other.getTile().getX(), other.getTile().getY()) <= 64) {
                                    player.getPacketSender().updateGroundItemAmount(oldAmt, other);
                                }
                            }

                            // Return true for entire method. No need to re-send items.
                            //logger.info("INFO: item "+item.getItem().getId()+" stacks with existing "+other.getItem().getId());
                            return true;
                        }
                    }
                }
            }
        }

        groundItems.add(item);
        // disappear in 2 mins if starts as global, otherwise 1 minute until it'll go from private to global
        item.setTimer(item.getState() == State.SEEN_BY_EVERYONE ? 200 : 100);
        if (player != null) {
            player.getPacketSender().createGroundItem(item);
        }

        if (item.getState() == State.SEEN_BY_EVERYONE) {
            addRegionalItem(item);
        }

        return true;
    }

    public static void pickup(Player player, int id, Tile tile) {
        Optional<GroundItem> optionalGroundItem = getGroundItem(id, tile, player);
        if (optionalGroundItem.isPresent()) {
            player.face(tile);

            player.action.clearNonWalkableActions();

            GroundItem groundItem = optionalGroundItem.get();

            var different_owner = !groundItem.ownerMatches(player);
            var groundItemPked = groundItem.pkedFrom() != null && groundItem.pkedFrom().equalsIgnoreCase(player.getUsername());

            //System.out.println("different_owner "+different_owner+" groundItemPked "+groundItemPked);

            // Ironman checks (if it's not a respawning item)
            var playerIsIron = player.gameMode().isIronman() || player.gameMode().isHardcoreIronman() || player.gameMode().isUltimateIronman() || player.gameMode().isCollectionIron();
            if (playerIsIron && groundItem.getItem().getId() != WILDERNESS_KEY) {
                if (different_owner && !groundItemPked) { // Owner different? It could be pked!
                    player.message("You're an Iron Man, so you can't take items that other players have dropped.");
                    return;
                } else if (groundItem.pkedFrom() != null && !groundItem.pkedFrom().equalsIgnoreCase(player.getUsername())) {
                    player.message("You're an Iron Man, so you can't take items that other players have dropped.");
                    return;
                }
            }

            /*if (player.gameMode() == GameMode.INSTANT_PKER && groundItem.droppedFromGamemode != GameMode.INSTANT_PKER) {
                player.message("You're an Instant pker, so you can't take items that other players have dropped.");
                return;
            }

            if (player.gameMode() != GameMode.INSTANT_PKER && groundItem.droppedFromGamemode == GameMode.INSTANT_PKER) {
                player.message("You're an Instant pker, so you can't take items that other players have dropped.");
                return;
            }*/

            if (different_owner && Dueling.in_duel(player)) {
                player.message("You can't pickup other players items in the duel arena.");
                return;
            }

            if (!TournamentManager.canPickupItem(player, groundItem)) {
                return;
            }

            TaskManager.submit(new Task("GroundItemPickupTask", 1, true) {
                @Override
                public void execute() {
                    if (groundItem.getState() != State.SEEN_BY_EVERYONE && groundItem.getOwnerHash() != player.getLongUsername()) {
                        stop();
                        return;
                    }

                    if (groundItem.isRemoved()) {
                        stop();
                        return;
                    }

                    Item item = groundItem.getItem();

                    boolean lootingBagOpened = player.getLootingBag().lootbagOpen();
                    if (player.tile().isWithinDistance(groundItem.getTile(),1)) {
                        // Add to looting bag if open.
                        if (lootingBagOpened && player.getLootingBag().deposit(item, item.getAmount(), groundItem)) {
                            sendRemoveGroundItem(groundItem);
                            stop();
                            return;
                        }

                        // If we've made it here then it added to the inventory.
                        if (player.inventory().getFreeSlots() == 0 && !(player.inventory().contains(item.getId()) && item.stackable())) {
                            player.message("You don't have enough inventory space to hold that item.");
                            stop();
                            return;
                        } else {
                            if (item.getId() == WILDERNESS_KEY) {
                                if (WildernessArea.inWilderness(player.tile())) {
                                    player.confirmDialogue(new String[]{Color.RED.wrap("Are you sure you wish to pick up this key? You will be red"), Color.RED.wrap("skulled and all your items will be lost on death!")}, "", "Proceed.", "Cancel.", () -> {
                                        Optional<GroundItem> gItem = GroundItemHandler.getGroundItem(WILDERNESS_KEY, tile, player);
                                        if (gItem.isEmpty()) {
                                            return;
                                        }
                                        final GroundItem groundItem1 = gItem.get();
                                        if (groundItem1.isRemoved()) {
                                            return;
                                        }
                                        final Item item2 = groundItem1.getItem();
                                        sendRemoveGroundItem(groundItem1);
                                        WildernessKeyPlugin.announceKeyPickup(player, tile);
                                        Skulling.assignSkullState(player, SkullType.RED_SKULL);
                                        player.inventory().add(item2);
                                        Utils.sendDiscordInfoLog("Player " + player.getUsername() + " picked up item " + item2.getAmount() + "x " + item2.unnote().name() + " (id " + item2.getId() + ")", "player_pickup");
                                        player.inventory().refresh();
                                        player.getRisk().update();
                                    });
                                }
                                stop();
                                return;
                            }

                            boolean added = player.inventory().add(item);
                            if (!added) {
                                player.message("There is not enough space in your inventory to hold any more items.");
                                stop();
                                return;
                            }
                        }

                        // If we've made it here then it added to the inventory.
                        sendRemoveGroundItem(groundItem);
                        player.getInventory().refresh();
                        player.getRisk().update();
                        Utils.sendDiscordInfoLog("Player " + player.getUsername() + " with IP "+player.getHostAddress()+" picked up item " + item.getAmount() + "x " + item.unnote().name() + " at: "+groundItem.getTile().toString(), "player_pickup");
                        stop();

                        // Does this ground item respawn?
                        if (groundItem.respawns()) {
                            Chain.runGlobal(groundItem.respawnTimer(), () -> {
                                GroundItem itemToSpawn = new GroundItem(item, groundItem.getTile().copy(), null);
                                itemToSpawn.respawns(true);
                                GroundItemHandler.createGroundItem(itemToSpawn);
                            });
                        }
                    }
                }
            });
        }
    }
}
