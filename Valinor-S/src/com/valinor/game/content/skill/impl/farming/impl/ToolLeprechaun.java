package com.valinor.game.content.skill.impl.farming.impl;

import com.valinor.game.content.skill.impl.farming.FarmingConstants;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;

import static com.valinor.game.world.InterfaceConstants.SHOP_INVENTORY;
import static com.valinor.game.world.items.container.shop.ShopUtility.*;

/**
 * Handles the tool leprechaun store.
 *
 * @author Gabriel || Wolfsdarker
 */
public class ToolLeprechaun {

    /**
     * The tool's interface title.
     */
    public static final String TITLE = "Amazing Farming Equipment Store";

    /**
     * Opens the leprechaun's tools.
     *
     * @param player
     */
    public static void open(Player player) {

        player.setToolStoreOpen(true);

        Item[] items = new Item[FarmingConstants.TOOLS.size()];

        int index = 0;

        for (int item : FarmingConstants.TOOLS) {
            items[index] = new Item(item, player.getFarming().getToolStored().getOrDefault(item, 0));
            index++;
        }

        player.getPacketSender().sendItemOnInterface(SHOP_INVENTORY, player.getInventory().getItems());
        player.getPacketSender().sendItemOnInterface(ITEM_CHILD_ID, items);

        player.getPacketSender().sendString(NAME_INTERFACE_CHILD_ID, TITLE);

        player.getInterfaceManager().openInventory(INTERFACE_ID, SHOP_INVENTORY - 1);
    }

    /**
     * Stores a item in the leprechaun's tools.
     *
     * @param player
     * @param storing_id
     * @param amount
     */
    public static void storeItem(Player player, int storing_id, int amount) {

        if (!FarmingConstants.TOOLS.contains(storing_id)) {
            player.message("You can't store this item.");
            return;
        }

        int inventory_count = player.getInventory().count(storing_id);

        if (inventory_count < amount) {
            amount = inventory_count;
        }

        if (amount < 1) {
            return;
        }

        amount += player.getFarming().getToolStored().getOrDefault(storing_id, 0);

        player.getInventory().remove(storing_id, amount);

        player.getFarming().getToolStored().put(storing_id, amount);

        Item[] items = new Item[FarmingConstants.TOOLS.size()];

        int index = 0;

        for (int item : FarmingConstants.TOOLS) {
            items[index] = new Item(item, player.getFarming().getToolStored().getOrDefault(item, 0));
            index++;
        }

        player.getPacketSender().sendItemOnInterface(SHOP_INVENTORY, player.getInventory().getItems());
        player.getPacketSender().sendItemOnInterface(ITEM_CHILD_ID, items);
    }

    /**
     * Withdraws a item from the leprechaun's tools.
     *
     * @param player
     * @param buying_id
     * @param amount
     */
    public static void withdraw(Player player, int buying_id, int amount) {

        if (!FarmingConstants.TOOLS.contains(buying_id)) {
            player.message("You can't store this item.");
            return;
        }

        int stored_count = player.getFarming().getToolStored().getOrDefault(buying_id, 0);

        if (stored_count < amount) {
            amount = stored_count;
        }

        if (amount < 1) {
            return;
        }

        player.getInventory().add(buying_id, amount);

        player.getFarming().getToolStored().put(buying_id, stored_count - amount);

        Item[] items = new Item[FarmingConstants.TOOLS.size()];

        int index = 0;

        for (int item : FarmingConstants.TOOLS) {
            items[index] = new Item(item, player.getFarming().getToolStored().getOrDefault(item, 0));
            index++;
        }

        player.getPacketSender().sendItemOnInterface(SHOP_INVENTORY, player.getInventory().getItems());
        player.getPacketSender().sendItemOnInterface(ITEM_CHILD_ID, items);
    }

}
