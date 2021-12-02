package com.valinor.game.world.items.container.bank;

import com.valinor.game.world.InterfaceConstants;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.container.ItemContainer;
import com.valinor.game.world.items.container.ItemContainerAdapter;
import com.valinor.game.world.items.container.inventory.Inventory;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 01, 2021
 */
public class DepositBox extends ItemContainer {

    /**
     * The player instance.
     */
    private final Player player;

    /**
     * The size of the container
     */
    public static final int SIZE = 28;

    public boolean quantityAll = false, quantityX = false, quantityTen = false, quantityFive = false, quantityOne = true;
    public int currentQuantityX = 0;

    public DepositBox(Player player) {
        super(SIZE, StackPolicy.STANDARD);
        this.addListener(new DepositBoxListener(player));
        this.player = player;
    }

    /**
     * Opens the looting bag widget
     */
    public void open() {
        player.getInterfaceManager().openInventory(InterfaceConstants.DEPOSIT_BOX_WIDGET_ID, InterfaceConstants.INVENTORY_STORE - 1);
        Inventory inventory = player.inventory();
        player.getPacketSender().sendItemOnInterface(InterfaceConstants.INVENTORY_STORE, inventory.toArray());
        player.getPacketSender().sendItemOnInterface(InterfaceConstants.DEPOSIT_BOX_CONTAINER_ID, inventory.toArray());
        fillContainer();
        resetButtons();
    }

    private void resetButtons() {
        quantityOne = true;
        quantityFive = false;
        quantityTen = false;
        quantityX = false;
        quantityAll = false;
        player.getPacketSender().sendConfig(1305, 0).sendConfig(1304, 0).sendConfig(1303, 0).sendConfig(1302, 0).sendConfig(1301, 1);
        player.message("depositbox 1");//Shifts container actions in the client
    }

    private void fillContainer() {
        Inventory inventory = player.inventory();
        for (Item invItem : inventory.toArray()) {
            if(invItem == null) continue;
            player.getDepositBox().add(invItem);
        }
    }

    /** Withdraws an item from the deposit box. */
    public void deposit(int itemId, int amount) {
        int slot = getSlot(itemId);
        if (itemId < 0)
            return;

        Item item = get(slot);
        if (item == null || itemId != item.getId()) {
            return;
        }

        //Check if item actually exists in the deposit box
        int contains = count(itemId);

        //Check overflow
        if (contains < amount) {
            amount = contains;
        }

        setFiringEvents(false);

        //Remove from deposit box
        if (remove(item.getId(), amount)) {
            //Remove from inventory
            int id = item.getId();
            int count = player.inventory().count(item.getId());
            if(amount > count) {
                amount = count;
            }
            int inventorySlot = player.inventory().getSlot(id);
            player.inventory().remove(new Item(id, amount), inventorySlot,true);

            //Bank the item
            player.getBank().depositFromNothing(new Item(id, amount));
        }

        setFiringEvents(true);
        refresh();
    }

    public boolean buttonActions(int button) {
        switch (button) {
            case 34412 -> { // Quantity all
                quantityAll = true;
                quantityOne = false;
                quantityFive = false;
                quantityTen = false;
                quantityX = false;
                player.getPacketSender().sendConfig(1305, 1);
                player.getPacketSender().sendConfig(1304, 0);
                player.getPacketSender().sendConfig(1303, 0);
                player.getPacketSender().sendConfig(1302, 0);
                player.getPacketSender().sendConfig(1301, 0);
                player.message("depositbox All");//Shifts container actions in the client
                return true;
            }
            case 34410 -> { // Quantity X
                quantityX = true;
                quantityOne = false;
                quantityFive = false;
                quantityTen = false;
                quantityAll = false;
                player.getPacketSender().sendConfig(1305, 0);
                player.getPacketSender().sendConfig(1304, 1);
                player.getPacketSender().sendConfig(1303, 0);
                player.getPacketSender().sendConfig(1302, 0);
                player.getPacketSender().sendConfig(1301, 0);
                player.message("depositbox X");//Shifts container actions in the client
            }
            case 34408 -> { // Quantity ten
                quantityTen = true;
                quantityOne = false;
                quantityFive = false;
                quantityX = false;
                quantityAll = false;
                player.getPacketSender().sendConfig(1305, 0);
                player.getPacketSender().sendConfig(1304, 0);
                player.getPacketSender().sendConfig(1303, 1);
                player.getPacketSender().sendConfig(1302, 0);
                player.getPacketSender().sendConfig(1301, 0);
                player.message("depositbox 10");//Shifts container actions in the client
            }
            case 34406 -> { // Quantity five
                quantityFive = true;
                quantityOne = false;
                quantityTen = false;
                quantityX = false;
                quantityAll = false;
                player.getPacketSender().sendConfig(1305, 0);
                player.getPacketSender().sendConfig(1304, 0);
                player.getPacketSender().sendConfig(1303, 0);
                player.getPacketSender().sendConfig(1302, 1);
                player.getPacketSender().sendConfig(1301, 0);
                player.message("depositbox 5");//Shifts container actions in the client
            }
            case 34404 -> { //Quantity one
                quantityOne = true;
                quantityFive = false;
                quantityTen = false;
                quantityX = false;
                quantityAll = false;
                player.getPacketSender().sendConfig(1305, 0);
                player.getPacketSender().sendConfig(1304, 0);
                player.getPacketSender().sendConfig(1303, 0);
                player.getPacketSender().sendConfig(1302, 0);
                player.getPacketSender().sendConfig(1301, 1);
                player.message("depositbox 1");//Shifts container actions in the client
            }
            case 34414 -> player.getBank().depositInventory();
            case 34415 -> player.getBank().depositeEquipment();
            case 34416 -> player.getLootingBag().depositLootingBag();
        }
        return false;
    }

    /** Refreshes the players deposit box. */
    public void sync() {
        refresh(player, InterfaceConstants.DEPOSIT_BOX_CONTAINER_ID);
    }

    /** Refreshes the deposit box container. */
    @Override
    public void refresh(Player player, int widget) {
        player.getPacketSender().sendItemOnInterface(widget, player.inventory().toArray());
        player.getPacketSender().sendItemOnInterface(InterfaceConstants.INVENTORY_STORE, player.inventory().toArray());
    }

    /**
     * An {@link ItemContainerAdapter} implementation that listens for changes to
     * the deposit box.
     */
    private static final class DepositBoxListener extends ItemContainerAdapter {

        /**
         * Creates a new {@link DepositBoxListener}.
         */
        DepositBoxListener(Player player) {
            super(player);
        }

        @Override
        public int getWidgetId() {
            return InterfaceConstants.DEPOSIT_BOX_CONTAINER_ID;
        }

        @Override
        public String getCapacityExceededMsg() {
            return "The deposit box cannot hold anymore items.";
        }
    }
}
