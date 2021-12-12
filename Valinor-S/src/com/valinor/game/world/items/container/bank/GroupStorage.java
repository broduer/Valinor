package com.valinor.game.world.items.container.bank;

import com.valinor.game.content.group_ironman.IronmanGroup;
import com.valinor.game.content.group_ironman.IronmanGroupHandler;
import com.valinor.game.world.InterfaceConstants;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.container.ItemContainer;
import com.valinor.game.world.items.container.ItemContainerAdapter;
import com.valinor.util.Color;

import java.util.Optional;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 07, 2021
 */
public class GroupStorage extends ItemContainer {

    /**
     * The player instance.
     */
    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * The noting flag.
     */
    private boolean noting = false;

    public boolean isNoting() {
        return noting;
    }

    /**
     * The inserting flag.
     */
    private boolean inserting = false;

    public boolean isInserting() {
        return inserting;
    }

    /**
     * The size of the container.
     */
    public static final int SIZE = 80;

    public GroupStorage(Player player) {
        super(SIZE, StackPolicy.ALWAYS);
        this.player = player;
        addListener(new GroupStorageListener());
    }

    /** Opens the group storage interface. */
    public void open() {
        if (!player.getBankPin().enterPin(this::open)) {
            return;
        }

        if(player.inActiveTournament() || player.isInTournamentLobby()) {
            player.message(Color.RED.wrap("You can't bank here."));
            return;
        }

        Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(player);
        if(group.isPresent()) {
            group.get().storageInUse(true);
            player.getPacketSender().sendString(67504, Integer.toString(group.get().getGroupStorage(player).size()));
            player.getPacketSender().sendString(67506, Integer.toString(SIZE));
            player.getPacketSender().sendString(67509,"Infinity");
            player.getInterfaceManager().openInventory(InterfaceConstants.GROUP_STORAGE_WIDGET, InterfaceConstants.INVENTORY_STORE - 1);
            refresh();
            refreshConfigs();
        }

    }

    private void refreshConfigs() {
        player.getPacketSender().sendConfig(1460, inserting ? 0 : 1);
        player.getPacketSender().sendConfig(1461, inserting ? 1 : 0);
        player.getPacketSender().sendConfig(1462, noting ? 0 : 1);
        player.getPacketSender().sendConfig(1463, noting ? 1 : 0);
    }

    /**
     * Closes the group storage item container
     */
    public void close() {
        save();
    }

    /**
     * Refreshes the group storage item container.
     */
    @Override
    public void sync() {
        Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(player);
        if(group.isPresent()) {
            player.inventory().refresh(player, InterfaceConstants.INVENTORY_STORE);
            //System.out.println("gi inv sync "+Arrays.toString(group.get().getGroupStorage(player).toArray()));
            player.getPacketSender().sendItemOnInterface(InterfaceConstants.GROUP_STORAGE_CONTAINER, group.get().getGroupStorage(player).toArray());
            player.inventory().refresh();
        }
    }

    public void deposit(int slot, int amount) {
        if (!player.getInterfaceManager().isInterfaceOpen(InterfaceConstants.GROUP_STORAGE_WIDGET)) {
            return;
        }

        if(player.inActiveTournament() || player.isInTournamentLobby()) {
            player.message(Color.RED.wrap("You can't bank here."));
            return;
        }
        deposit(slot, amount, player.inventory());
    }

    /** Deposits an item into the group storage. */
    public void deposit(int slot, int amount, ItemContainer fromIc) {
        Item item = fromIc.get(slot);
        if (item == null)
            return;

        //Extra safety
        if(!IronmanGroupHandler.isGroupIronman(player)) {
            player.message(Color.RED.wrap("I shouldn't be in here, contact a Administrator."));
            return;
        }

        //Can't store untradeable items
        if (!item.rawtradable()) {
            player.message("This is item is untradeable!");
            return;
        }

        int id = item.getId();

        int invAmount = fromIc.count(id);

        if (invAmount < amount) {
            amount = invAmount;
        }

        setFiringEvents(false);
        add(id, amount);
        if (amount > 0)
            fromIc.remove(item.getId(), amount);
        setFiringEvents(true);
        refresh();
    }

    /** Withdraws an item from the group storage. */
    public void withdraw(int itemId, int slot, int amount) {
        if (!player.getInterfaceManager().isInterfaceOpen(InterfaceConstants.GROUP_STORAGE_WIDGET)) {
            return;
        }

        if(player.inActiveTournament() || player.isInTournamentLobby()) {
            player.message(Color.RED.wrap("You can't bank here."));
            return;
        }

        if (itemId < 0 || slot < 0 || slot > capacity()) return;
        Item item = get(slot);
        if (item == null || itemId != item.getId())
            return;

        //Extra safety
        if(!IronmanGroupHandler.isGroupIronman(player)) {
            player.message(Color.RED.wrap("I shouldn't be in here, contact a Administrator."));
            return;
        }

        if (item.getAmount() < amount) {
            amount = item.getAmount();
        }

        int id = item.getId();
        if (noting) {
            if (!item.noteable()) {
                player.message("This item cannot be withdrawn as a note.");
            } else {
                id = item.note().getId();
            }
        }
        setFiringEvents(false);
        if (!new Item(id).stackable() && amount > player.getInventory().getFreeSlots()) {
            amount = player.getInventory().getFreeSlots();
        } else if (item.stackable() && player.getInventory().getFreeSlots() == 0) {
            if (!player.getInventory().contains(id)) {
                amount = 0;
            } else if (player.getInventory().count(id) + amount > Integer.MAX_VALUE) {
                amount = Integer.MAX_VALUE - player.getInventory().count(id);
            }
        }

        if (amount == 0) {
            player.message("You do not have enough inventory spaces to withdraw this item.");
            return;
        }

        int withdrawSlot = player.getInventory().getSlot(id);
        if (withdrawSlot != -1) {
            Item withdrawItem = player.getInventory().get(withdrawSlot);
            if (withdrawItem == null) return;
            if (Integer.MAX_VALUE - withdrawItem.getAmount() < amount) {
                amount = Integer.MAX_VALUE - withdrawItem.getAmount();
                player.message("Your inventory didn't have enough space to withdraw all that!");
            }
        }

        if (remove(item.getId(), amount)) {
            player.inventory().add(id, amount);
            shift();
        }

        setFiringEvents(true);
        refresh();
    }

    public void save() {
        Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(player);
        group.ifPresent(ironmanGroup -> ironmanGroup.loadSaveTemp = ironmanGroup.getGroupStorage().toNonNullArray());
        IronmanGroupHandler.saveIronmanGroups();
    }

    /**
     * Handles depositing the entire inventory.
     */
    public void depositInventory() {
        for (int i = 0; i <= 27; i++) {
            var itemAt = player.inventory().get(i);
            if(itemAt == null) continue; // Get item or continue
            deposit(i, itemAt.getAmount(), player.inventory());
        }
    }

    public boolean buttonAction(int button) {
        if (!player.getInterfaceManager().isInterfaceOpen(InterfaceConstants.GROUP_STORAGE_WIDGET))
            return false;

        if(button == 67507) {//back to bank
            player.getInterfaceManager().close();//Close group storage
            player.getBank().open();//Open bank
            return true;
        }
        if(button == 67510) {//Shuffle items up
            return true;
        }
        if(button == 67511) {
            // client sided search btn does nothing here.
            return true;
        }
        if(button == 67512) {//Deposit inv
            depositInventory();
            return true;
        }
        if(button == 67526) {//Swap
            inserting = false;
            player.getPacketSender().sendConfig(1460, 1);
            player.getPacketSender().sendConfig(1461, 0);
            return true;
        }
        if(button == 67527) {//Insert
            inserting = true;
            player.getPacketSender().sendConfig(1460, 0);
            player.getPacketSender().sendConfig(1461, 1);
            return true;
        }
        if(button == 67530) {//Item
            noting = false;
            player.getPacketSender().sendConfig(1462, 1);
            player.getPacketSender().sendConfig(1463, 0);
            return true;
        }
        if(button == 67531) {//Note
            noting = true;
            player.getPacketSender().sendConfig(1462, 0);
            player.getPacketSender().sendConfig(1463, 1);
            return true;
        }
        if(button == 67535 || button == 67536) {//Save
            save();
            return true;
        }
        if(button == 67513) {//1
            return true;
        }
        if(button == 67514) {//5
            return true;
        }
        if(button == 67515) {//10
            return true;
        }
        if(button == 67516) {//x
            return true;
        }
        if(button == 67517) {//all
            return true;
        }
        return false;
    }

    private final class GroupStorageListener extends ItemContainerAdapter {

        GroupStorageListener() {
            super(player);
        }

        @Override
        public int getWidgetId() {
            return InterfaceConstants.GROUP_STORAGE_CONTAINER;
        }

        @Override
        public String getCapacityExceededMsg() {
            return "The storage is currently full!";
        }

        @Override
        public void itemUpdated(ItemContainer container, Optional<Item> oldItem, Optional<Item> newItem, int index, boolean refresh) {
        }

        @Override
        public void bulkItemsUpdated(ItemContainer container) {
            refresh();
        }
    }
}
